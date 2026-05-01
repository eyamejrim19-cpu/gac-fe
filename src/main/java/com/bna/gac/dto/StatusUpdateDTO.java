package com.bna.gac.dto;

/**
 * Minimal DTO used exclusively for toggling a client's active status.
 * Keeps the toggle path completely separate from the full update path,
 * so there is zero risk of other fields being touched.
 */
public class StatusUpdateDTO {

    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
