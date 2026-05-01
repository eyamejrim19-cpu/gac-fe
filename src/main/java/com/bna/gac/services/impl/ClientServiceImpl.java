package com.bna.gac.services.impl;

import com.bna.gac.dto.ClientDTO;
import com.bna.gac.dto.StatusUpdateDTO;
import com.bna.gac.entities.Client;
import com.bna.gac.mapper.ClientMapper;
import com.bna.gac.repositories.ClientRepository;
import com.bna.gac.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new RuntimeException("Nom, tel, email sont obligatoires");
        }

        // Tel validation: exactly 8 digits, starting with 2, 4, 5, 7, or 9
        validateTel(dto.getTel());

        // Email validation
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            validateEmail(dto.getEmail());
        }

        // Duplicate check — each field reported separately
        checkDuplicatesForCreate(dto);

        // RNE validation: required for MORALE clients, must be exactly 7 digits
        if ("MORALE".equalsIgnoreCase(dto.getTypeClient())) {
            validateRne(dto.getRne());
        }

        // CIN validation: required for PHYSIQUE clients, must be exactly 8 digits
        if ("PHYSIQUE".equalsIgnoreCase(dto.getTypeClient())) {
            validateCin(dto.getCin());
        }

        Client c = mapper.toEntity(dto);
        return mapper.toDto(repo.save(c));
    }

    @Override
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {

        Client c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));

        // RNE validation: only validate when a new RNE value is being provided
        if (dto.getRne() != null) {
            String targetType = dto.getTypeClient() != null ? dto.getTypeClient() : c.getTypeClient();
            if ("MORALE".equalsIgnoreCase(targetType)) {
                validateRne(dto.getRne());
            }
        }

        // CIN validation: only validate when a new CIN value is being provided
        if (dto.getCin() != null) {
            String targetType = dto.getTypeClient() != null ? dto.getTypeClient() : c.getTypeClient();
            if ("PHYSIQUE".equalsIgnoreCase(targetType)) {
                validateCin(dto.getCin());
            }
        }

        // Tel validation: only validate when a new tel value is being provided
        if (dto.getTel() != null) {
            validateTel(dto.getTel());
        }

        // Email validation: only validate when a new email value is being provided
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            validateEmail(dto.getEmail());
        }

        // Duplicate check — each field reported separately, excluding current record
        checkDuplicatesForUpdate(id, dto);

        // Partial update — only overwrite fields that are explicitly provided in the DTO.
        // This prevents a partial payload (e.g. { active: false }) from wiping other fields.
        if (dto.getNom() != null)        c.setNom(dto.getNom());
        if (dto.getPrenom() != null)     c.setPrenom(dto.getPrenom());
        if (dto.getEmail() != null)      c.setEmail(dto.getEmail());
        if (dto.getTel() != null)        c.setTel(dto.getTel());
        if (dto.getCin() != null)        c.setCin(dto.getCin());
        if (dto.getRne() != null)        c.setRne(dto.getRne());
        if (dto.getAdresse() != null)    c.setAdresse(dto.getAdresse());
        if (dto.getTypeClient() != null) c.setTypeClient(dto.getTypeClient());
        if (dto.getActive() != null)     c.setActive(dto.getActive());

        log.info("update client id={} active={}", id, c.getActive());
        return mapper.toDto(repo.save(c));
    }

    /**
     * Dedicated status-only update. Loads the entity, touches ONLY the active
     * field, and saves. No other field is read from the request or written to
     * the database.
     */
    @Override
    @Transactional
    public ClientDTO updateStatus(Long id, StatusUpdateDTO dto) {
        if (dto.getActive() == null) {
            throw new RuntimeException("Le champ 'active' est obligatoire");
        }
        log.info("updateStatus client id={} active={}", id, dto.getActive());
        Client c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));
        c.setActive(dto.getActive());
        return mapper.toDto(repo.save(c));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Client c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        repo.delete(c);
    }

    @Override
    public ClientDTO getById(Long id) {
        return mapper.toDto(repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found")));
    }

    @Override
    @Transactional
    public ClientDTO deactivate(Long id) {
        log.info("deactivate client id={}", id);
        Client c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));
        c.setActive(false);
        return mapper.toDto(repo.save(c));
    }

    @Override
    @Transactional
    public ClientDTO reactivate(Long id) {
        log.info("reactivate client id={}", id);
        Client c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found: " + id));
        c.setActive(true);
        return mapper.toDto(repo.save(c));
    }

    // ================= HELPERS =================

    /**
     * RNE must be exactly 7 digits (0–9). No letters, spaces, or special chars.
     * Throws RuntimeException with the standard message if invalid.
     */
    private void validateRne(String rne) {
        if (rne == null || !rne.matches("^[0-9]{7}$")) {
            throw new RuntimeException("Le RNE doit contenir exactement 7 chiffres.");
        }
    }

    /**
     * CIN must be exactly 8 digits (0–9). No letters, spaces, or special chars.
     * Throws RuntimeException with the standard message if invalid.
     */
    private void validateCin(String cin) {
        if (cin == null || !cin.matches("^[0-9]{8}$")) {
            throw new RuntimeException("La CIN doit contenir exactement 8 chiffres.");
        }
    }

    /**
     * Tunisian phone number: exactly 8 digits, first digit must be 2, 4, 5, 7, or 9.
     * No letters, spaces, or special characters allowed.
     * Throws RuntimeException with the standard message if invalid.
     */
    private void validateTel(String tel) {
        if (tel == null || !tel.matches("^[24579][0-9]{7}$")) {
            throw new RuntimeException("Le numéro de téléphone doit contenir exactement 8 chiffres valides.");
        }
    }

    /**
     * Email must contain exactly one "@", at least one character before and after it,
     * a "." in the domain part, and no spaces.
     * Throws RuntimeException with the standard message if invalid.
     */
    private void validateEmail(String email) {
        if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new RuntimeException("Adresse email invalide.");
        }
    }

    /**
     * Checks for duplicate tel / email / cin / rne on CREATE.
     * Each conflicting field produces its own specific error message.
     * All conflicts are collected and thrown together so the caller sees every issue at once.
     */
    private void checkDuplicatesForCreate(ClientDTO dto) {
        List<String> errors = new java.util.ArrayList<>();

        if (dto.getTel() != null)
            repo.findByTel(dto.getTel()).ifPresent(c -> errors.add("Ce numéro de téléphone est déjà utilisé."));

        if (dto.getEmail() != null && !dto.getEmail().isBlank())
            repo.findByEmail(dto.getEmail()).ifPresent(c -> errors.add("Cette adresse email est déjà utilisée."));

        if (dto.getCin() != null && !dto.getCin().isBlank())
            repo.findByCin(dto.getCin()).ifPresent(c -> errors.add("Cette CIN est déjà utilisée."));

        if (dto.getRne() != null && !dto.getRne().isBlank())
            repo.findByRne(dto.getRne()).ifPresent(c -> errors.add("Ce RNE est déjà utilisé."));

        if (!errors.isEmpty()) {
            throw new RuntimeException(String.join(" | ", errors));
        }
    }

    /**
     * Checks for duplicate tel / email / cin / rne on UPDATE, excluding the record being updated.
     * Each conflicting field produces its own specific error message.
     */
    private void checkDuplicatesForUpdate(Long currentId, ClientDTO dto) {
        List<String> errors = new java.util.ArrayList<>();

        if (dto.getTel() != null)
            repo.findByTel(dto.getTel())
                .filter(c -> !c.getId().equals(currentId))
                .ifPresent(c -> errors.add("Ce numéro de téléphone est déjà utilisé."));

        if (dto.getEmail() != null && !dto.getEmail().isBlank())
            repo.findByEmail(dto.getEmail())
                .filter(c -> !c.getId().equals(currentId))
                .ifPresent(c -> errors.add("Cette adresse email est déjà utilisée."));

        if (dto.getCin() != null && !dto.getCin().isBlank())
            repo.findByCin(dto.getCin())
                .filter(c -> !c.getId().equals(currentId))
                .ifPresent(c -> errors.add("Cette CIN est déjà utilisée."));

        if (dto.getRne() != null && !dto.getRne().isBlank())
            repo.findByRne(dto.getRne())
                .filter(c -> !c.getId().equals(currentId))
                .ifPresent(c -> errors.add("Ce RNE est déjà utilisé."));

        if (!errors.isEmpty()) {
            throw new RuntimeException(String.join(" | ", errors));
        }
    }
}
