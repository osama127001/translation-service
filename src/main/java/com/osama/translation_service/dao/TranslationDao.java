package com.osama.translation_service.dao;

import com.osama.translation_service.domain.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TranslationDao extends JpaRepository<Translation, UUID> {
}
