package com.osama.translation_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Locale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String region;

    @Pattern(regexp = "^[A-Z]{2}$", message = "Must be a 2-letter uppercase country code")
    private String code;

    @ManyToMany(mappedBy = "locales")
    @JsonIgnore
    private Set<Translation> translations = new HashSet<>();

    public Locale() {
    }

    public Locale(String region, String code) {
        this.region = region;
        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translation> translations) {
        this.translations = translations;
    }
}
