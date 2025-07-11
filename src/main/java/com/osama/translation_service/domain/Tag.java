package com.osama.translation_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Tag {

    @Id
    private UUID id;
    private String tag;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Translation> translations = new HashSet<>();

    public Tag() {
        this.id = UUID.randomUUID();
    }

    public Tag(String tag) {
        this.id = UUID.randomUUID();
        this.tag = tag;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
    }
}
