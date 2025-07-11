package com.osama.translation_service.dao;

import com.osama.translation_service.domain.Locale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocaleDao extends JpaRepository<Locale, UUID> {
}
