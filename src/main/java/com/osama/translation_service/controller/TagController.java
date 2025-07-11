package com.osama.translation_service.controller;

import com.osama.translation_service.dao.TagDao;
import com.osama.translation_service.domain.Tag;
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
@RequestMapping("api/v1/tag")
public class TagController {

    @Autowired
    private TagDao tagDao;

    @GetMapping
    public ResponseEntity<List<Tag>> getTagById() {
        List<Tag> tags = tagDao.findAll();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable("id") UUID id) {
        Tag tag = tagDao
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find tag with id " + id));
        return ResponseEntity.ok(tag);
    }

    @PostMapping
    public ResponseEntity<UUID> saveTag(@RequestBody @Valid Tag tag) {
        Tag newTag = new Tag(tag.getTag());
        tagDao.save(newTag);
        return new ResponseEntity<>(newTag.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateTag(@RequestBody @Valid Tag tag,
                                                  @PathVariable("id") UUID id) {
        Optional<Tag> tagToUpdate = tagDao.findById(id);
        if (tagToUpdate.isPresent()) {
            Tag updatedTag = tagToUpdate.get();
            updatedTag.setTag(tag.getTag());
            tagDao.save(updatedTag);
            return new ResponseEntity<>(updatedTag.getId(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteTagById(@PathVariable("id") UUID id) {
        tagDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
