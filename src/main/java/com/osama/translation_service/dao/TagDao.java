package com.osama.translation_service.dao;

import com.osama.translation_service.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagDao extends JpaRepository<Tag, UUID> {
}
