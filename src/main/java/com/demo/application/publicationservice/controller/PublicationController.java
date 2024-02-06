package com.demo.application.publicationservice.controller;

import com.demo.application.publicationservice.dto.PublicationRequest;
import com.demo.application.publicationservice.dto.PublicationResponse;
import com.demo.application.publicationservice.exception.ResourceNotFoundException;
import com.demo.application.publicationservice.service.PublicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publication")
@RequiredArgsConstructor
@Slf4j
public class PublicationController {

    private final PublicationService publicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPublication(@RequestBody PublicationRequest publicationRequest) {
        log.info("Publication [{}] create request received.", publicationRequest.getName());
        publicationService.createPublication(publicationRequest);
        log.info("Publication [{}] create request completed.", publicationRequest.getName());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PublicationResponse> getAllPublications() {
        log.info("Get all publications request received.");
        return publicationService.getAllPublications();
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<PublicationResponse> getPublicationById(@PathVariable("id") String id) {
        log.info("Get publication by id [{}] request received.", id);
        try {
            PublicationResponse publicationResponse = publicationService.getPublicationById(id);
            log.info("Get publication by id [{}] request completed.", id);
            return new ResponseEntity<>(publicationResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublication(@PathVariable("id") String id) {
        log.info("Delete publication by id [{}] request received.", id);
        publicationService.deletePublication(id);
        log.info("Delete publication by id [{}] request completed.", id);
    }

    @PatchMapping(value="/{id}")
    public ResponseEntity<PublicationResponse> updatePublication(@RequestBody PublicationRequest publicationRequest, @PathVariable("id") String id) {
        log.info("Update publication by id [{}] request received.", id);
        try {
            PublicationResponse publicationResponse = publicationService.updatePublication(publicationRequest, id);
            log.info("Update publication by id [{}] request completed.", id);
            return new ResponseEntity<>(publicationResponse, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
