package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Pleasewor.
 */

@Document(collection = "pleasewor")
public class Pleasewor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
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

    public Pleasewor nom(String nom) {
        this.nom = nom;
        return this;
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
        Pleasewor pleasewor = (Pleasewor) o;
        if (pleasewor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pleasewor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pleasewor{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            '}';
    }
}
