package com.mycompany.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Pleasework.
 */

@Document(collection = "pleasework")
public class Pleasework implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pleasework pleasework = (Pleasework) o;
        if (pleasework.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pleasework.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pleasework{" +
            "id=" + id +
            '}';
    }
}
