package com.bna.gac.services.impl;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.dto.StatusUpdateDTO;
import com.bna.gac.entities.Client;
import com.bna.gac.exceptions.BadRequestException;
import com.bna.gac.exceptions.ResourceConflictException;
import com.bna.gac.exceptions.ResourceNotFoundException;
import com.bna.gac.mapper.ClientMapper;
import com.bna.gac.repositories.ClientRepository;
import com.bna.gac.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repo;
    private final ClientMapper mapper;

    @Override
    public Page<ClientDTO> findAll(int page, int size, String search, String type, Boolean active) {

        log.info("findAll page={} size={} search='{}' type='{}' active={}", page, size, search, type, active);

        Pageable pageable = PageRequest.of(page, size);

        List<Client> list = repo.findAll();

        List<Client> filtered = list.stream()
                .filter(c -> search == null ||
                        c.getNom().toLowerCase().contains(search.toLowerCase()) ||
                        (c.getEmail() != null && c.getEmail().toLowerCase().contains(search.toLowerCase())) ||
                        (c.getCin()   != null && c.getCin().toLowerCase().contains(search.toLowerCase())) ||
                        (c.getRne()   != null && c.getRne().toLowerCase().contains(search.toLowerCase())))
                .filter(c -> type == null || type.isBlank() || type.equalsIgnoreCase(c.getTypeClient()))
                .filter(c -> active == null || c.getActive().equals(active))
                .toList();

        log.info("findAll → {} total matches", filtered.size());

        int start = Math.min((int) pageable.getOffset(), filtered.size());
        int end   = Math.min(start + pageable.getPageSize(), filtered.size());

        List<ClientDTO> content = filtered.subList(start, end)
                .stream()
                .map(mapper::toDto)
                .toList();

        return new PageImpl<>(content, pageable, filtered.size());
    }

    @Override
    @Transactional
    public ClientDTO create(ClientDTO dto) {

        if (dto.getNom() == null || dto.getTel() == null || dto.getEmail() == null) {
            throw new BadRequestException("Nom, tel, email sont obligatoires");
        }

        // Validate and normalise typeClient
        String type = resolveAndValidateType(dto.getTypeClient());
        dto.setTypeClient(type);

        // Central identifier gate: enforces type-based rules and rejects mismatches
        validateIdentifierForType(type, dto.getCin(), dto.getRne());

        // Tel validation: exactly 8 digits, starting with 2, 4, 5, 7, or 9
        validateTel(dto.getTel());

        // Email validation
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            validateEmail(dto.getEmail());
        }

        // Duplicate check — each field reported separately
        checkDuplicatesForCreate(dto);

        Client c = mapper.toEntity(dto);
        return mapper.toDto(repo.save(c));
    }

    @Override
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {

        Client c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + id));

        // Determine the effective type after this update
        String effectiveType = dto.getTypeClient() != null
                ? resolveAndValidateType(dto.getTypeClient())
                : c.getTypeClient();

        if (dto.getTypeClient() != null) {
            dto.setTypeClient(effectiveType);
        }

        boolean cinProvided = dto.getCin() != null && !dto.getCin().isBlank();
        boolean rneProvided = dto.getRne() != null && !dto.getRne().isBlank();

        // Reject cross-type mismatches and validate the supplied identifier
        if ("PHYSIQUE".equalsIgnoreCase(effectiveType)) {
            if (rneProvided) {
                throw new BadRequestException(
                        "Un client PHYSIQUE ne peut pas avoir de RNE. Utilisez le champ CIN.");
            }
            if (cinProvided) validateCin(dto.getCin());

        } else if ("MORALE".equalsIgnoreCase(effectiveType)) {
            if (cinProvided) {
                throw new BadRequestException(
                        "Un client MORALE ne peut pas avoir de CIN. Utilisez le champ RNE.");
            }
            if (rneProvided) validateRne(dto.getRne());
        }

        // Tel validation: only when a new value is provided
        if (dto.getTel() != null) validateTel(dto.getTel());

        // Email validation: only when a new value is provided
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) validateEmail(dto.getEmail());

        // Duplicate check — each field reported separately, excluding current record
        checkDuplicatesForUpdate(id, dto);

        // Partial update — only overwrite fields that are explicitly provided
        if (dto.getNom() != null)     c.setNom(dto.getNom());
        if (dto.getPrenom() != null)  c.setPrenom(dto.getPrenom());
        if (dto.getEmail() != null)   c.setEmail(dto.getEmail());
        if (dto.getTel() != null)     c.setTel(dto.getTel());
        if (dto.getAdresse() != null) c.setAdresse(dto.getAdresse());
        if (dto.getActive() != null)  c.setActive(dto.getActive());

        // Apply type change and enforce identifier isolation
        if (dto.getTypeClient() != null) {
            c.setTypeClient(effectiveType);
            if ("PHYSIQUE".equalsIgnoreCase(effectiveType)) {
                c.setRne(null);                          // clear the now-invalid identifier
                if (cinProvided) c.setCin(dto.getCin());
            } else if ("MORALE".equalsIgnoreCase(effectiveType)) {
                c.setCin(null);                          // clear the now-invalid identifier
                if (rneProvided) c.setRne(dto.getRne());
            }
        } else {
            // Type unchanged — update only the applicable identifier
            if ("PHYSIQUE".equalsIgnoreCase(effectiveType) && cinProvided) c.setCin(dto.getCin());
            if ("MORALE".equalsIgnoreCase(effectiveType)   && rneProvided) c.setRne(dto.getRne());
        }

        log.info("update client id={} type={} active={}", id, effectiveType, c.getActive());
        return mapper.toDto(repo.save(c));
    }

    /**
     * Dedicated status-only update. Touches ONLY the active field.
     */
    @Transactional
    @Override
    public ClientDTO updateStatus(Long id, StatusUpdateDTO dto) {
        if (dto.getActive() == null) {
            throw new BadRequestException("Le champ 'active' est obligatoire");
        }
        log.info("updateStatus client id={} active={}", id, dto.getActive());
        Client c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + id));
        c.setActive(dto.getActive());
        return mapper.toDto(repo.save(c));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Client c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + id));
        repo.delete(c);
    }

    @Override
    public ClientDTO getById(Long id) {
        return mapper.toDto(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + id)));
    }

    @Override
    public List<ClientDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public ClientDTO deactivate(Long id) {
        log.info("deactivate client id={}", id);
        Client c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + id));
        c.setActive(false);
        return mapper.toDto(repo.save(c));
    }

    @Transactional
    @Override
    public ClientDTO reactivate(Long id) {
        log.info("reactivate client id={}", id);
        Client c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found: " + id));
        c.setActive(true);
        return mapper.toDto(repo.save(c));
    }

    // ================= HELPERS =================

    /**
     * Validates and normalises typeClient.
     * Accepted values (case-insensitive): "PHYSIQUE", "MORALE".
     */
    private String resolveAndValidateType(String typeClient) {
        if (typeClient == null || typeClient.isBlank()) {
            return "PHYSIQUE"; // default
        }
        String upper = typeClient.trim().toUpperCase();
        if (!upper.equals("PHYSIQUE") && !upper.equals("MORALE")) {
            throw new BadRequestException(
                    "Type client invalide: '" + typeClient + "'. Valeurs acceptées: PHYSIQUE, MORALE.");
        }
        return upper;
    }

    /**
     * Central identifier gate — called on every create.
     *   PHYSIQUE: CIN required (8 digits), RNE must be absent
     *   MORALE:   RNE required (7 digits), CIN must be absent
     */
    private void validateIdentifierForType(String type, String cin, String rne) {
        boolean cinPresent = cin != null && !cin.isBlank();
        boolean rnePresent = rne != null && !rne.isBlank();

        if ("PHYSIQUE".equalsIgnoreCase(type)) {
            if (rnePresent) {
                throw new BadRequestException(
                        "Un client PHYSIQUE ne peut pas avoir de RNE. Utilisez le champ CIN.");
            }
            validateCin(cin);
        } else if ("MORALE".equalsIgnoreCase(type)) {
            if (cinPresent) {
                throw new BadRequestException(
                        "Un client MORALE ne peut pas avoir de CIN. Utilisez le champ RNE.");
            }
            validateRne(rne);
        }
    }

    /** RNE must be exactly 7 digits (0–9). */
    private void validateRne(String rne) {
        if (rne == null || !rne.matches("^[0-9]{7}$")) {
            throw new BadRequestException("Le RNE doit contenir exactement 7 chiffres.");
        }
    }

    /** CIN must be exactly 8 digits (0–9). */
    private void validateCin(String cin) {
        if (cin == null || !cin.matches("^[0-9]{8}$")) {
            throw new BadRequestException("La CIN doit contenir exactement 8 chiffres.");
        }
    }

    /**  phone: exactly 8 digits, first digit 2/4/5/7/9. */
    private void validateTel(String tel) {
        if (tel == null || !tel.matches("^[24579][0-9]{7}$")) {
            throw new BadRequestException(
                    "Le numéro de téléphone doit contenir exactement 8 chiffres valides.");
        }
    }

    /** Email: must contain @, dot in domain, no spaces. */
    private void validateEmail(String email) {
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new BadRequestException("Adresse email invalide.");
        }
    }

    /** Duplicate check on CREATE — collects all conflicts before throwing. */
    private void checkDuplicatesForCreate(ClientDTO dto) {
        List<String> errors = new java.util.ArrayList<>();

        if (dto.getTel() != null)
            repo.findByTel(dto.getTel())
                .ifPresent(c -> errors.add("Ce numéro de téléphone est déjà utilisé."));

        if (dto.getEmail() != null && !dto.getEmail().isBlank())
            repo.findByEmail(dto.getEmail())
                .ifPresent(c -> errors.add("Cette adresse email est déjà utilisée."));

        if (dto.getCin() != null && !dto.getCin().isBlank())
            repo.findByCin(dto.getCin())
                .ifPresent(c -> errors.add("Cette CIN est déjà utilisée."));

        if (dto.getRne() != null && !dto.getRne().isBlank())
            repo.findByRne(dto.getRne())
                .ifPresent(c -> errors.add("Ce RNE est déjà utilisé."));

        if (!errors.isEmpty()) {
            throw new ResourceConflictException(String.join(" | ", errors));
        }
    }

    /** Duplicate check on UPDATE — excludes the record being updated. */
    private void checkDuplicatesForUpdate(Long currentId, ClientDTO dto) {
        List<String> errors = new ArrayList<>();

        if (dto.getTel() != null)
            repo.findByTel(dto.getTel())
                .filter(c -> !c.getClass().equals(currentId))
                .ifPresent(c -> errors.add("Ce numéro de téléphone est déjà utilisé."));

        if (dto.getEmail() != null && !dto.getEmail().isBlank())
            repo.findByEmail(dto.getEmail())
                .filter(c -> !c.getClass().equals(currentId))
                .ifPresent(c -> errors.add("Cette adresse email est déjà utilisée."));

        if (dto.getCin() != null && !dto.getCin().isBlank())
            repo.findByCin(dto.getCin())
                .filter(c -> !c.getClass().equals(currentId))
                .ifPresent(c -> errors.add("Cette CIN est déjà utilisée."));

        if (dto.getRne() != null && !dto.getRne().isBlank())
            repo.findByRne(dto.getRne())
                .filter(c -> !c.getClass().equals(currentId))
                .ifPresent(c -> errors.add("Ce RNE est déjà utilisé."));

        if (!errors.isEmpty()) {
            throw new ResourceConflictException(String.join(" | ", errors));
        }
    }
}

