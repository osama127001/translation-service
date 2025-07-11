package com.osama.translation_service.controller;

import com.osama.translation_service.dao.TranslationDao;
import com.osama.translation_service.domain.Translation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/translation")
public class TranslationController {

    @Autowired
    private TranslationDao translationDao;

    @GetMapping
    public ResponseEntity<List<Translation>> getTranslations() {
        List<Translation> translations = translationDao.findAll();
        return ResponseEntity.ok(translations);
    }    

    @GetMapping("/{id}")
    public ResponseEntity<Translation> getTranslationById(@PathVariable("id") UUID id) {
        Translation translation = translationDao
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find translation with id " + id));
        return ResponseEntity.ok(translation);
    }

    @PostMapping
    public ResponseEntity<UUID> saveTranslation(@RequestBody @Valid Translation translation) {
        Translation newTranslation = new Translation(translation.getLanguage(), translation.getCode());
        translationDao.save(newTranslation);
        return new ResponseEntity<>(newTranslation.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateTranslation(@RequestBody @Valid Translation translation,
                                                  @PathVariable("id") UUID id) {
        Optional<Translation> translationToUpdate = translationDao.findById(id);
        if (translationToUpdate.isPresent()) {
            Translation updatedTranslation = translationToUpdate.get();
            updatedTranslation.setLanguage(translation.getLanguage());
            updatedTranslation.setCode(translation.getCode());
            translationDao.save(updatedTranslation);
            return new ResponseEntity<>(updatedTranslation.getId(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteTranslationById(@PathVariable("id") UUID id) {
        translationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
