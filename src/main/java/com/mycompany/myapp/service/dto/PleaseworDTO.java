package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Pleasewor entity.
 */
public class PleaseworDTO implements Serializable {

    private String id;

    private String nom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PleaseworDTO pleaseworDTO = (PleaseworDTO) o;

        if ( ! Objects.equals(id, pleaseworDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PleaseworDTO{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            '}';
    }
}
