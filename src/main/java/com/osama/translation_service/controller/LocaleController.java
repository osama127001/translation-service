package com.osama.translation_service.controller;

import com.osama.translation_service.dao.LocaleDao;
import com.osama.translation_service.domain.Locale;
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
@RequestMapping("api/v1/locale")
public class LocaleController {

    @Autowired
    private LocaleDao localeDao;

    @GetMapping
    public ResponseEntity<List<Locale>> getLocaleById() {
        List<Locale> locales = localeDao.findAll();
        return ResponseEntity.ok(locales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locale> getLocaleById(@PathVariable("id") UUID id) {
        Locale locale = localeDao
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find locale with id " + id));
        return ResponseEntity.ok(locale);
    }

    @PostMapping
    public ResponseEntity<UUID> saveLocale(@RequestBody @Valid Locale locale) {
        Locale newLocale = new Locale(locale.getRegion(), locale.getCode());
        localeDao.save(newLocale);
        return new ResponseEntity<>(newLocale.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateLocale(@RequestBody @Valid Locale locale,
                                          @PathVariable("id") UUID id) {
        Optional<Locale> localeToUpdate = localeDao.findById(id);
        if (localeToUpdate.isPresent()) {
            Locale updatedlocale = localeToUpdate.get();
            updatedlocale.setCode(locale.getCode());
            updatedlocale.setRegion(locale.getRegion());
            localeDao.save(updatedlocale);
            return new ResponseEntity<>(updatedlocale.getId(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteLocaleById(@PathVariable("id") UUID id) {
        localeDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
