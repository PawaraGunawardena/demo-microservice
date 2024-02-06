package com.demo.application.publicationservice.service;

import com.demo.application.publicationservice.dto.PublicationRequest;
import com.demo.application.publicationservice.dto.PublicationResponse;
import com.demo.application.publicationservice.exception.ResourceNotFoundException;
import com.demo.application.publicationservice.model.Publication;
import com.demo.application.publicationservice.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationService {
    private final PublicationRepository publicationRepository;

    public void createPublication(PublicationRequest publicationRequest) {
        Publication publication = Publication.builder()
                .name(publicationRequest.getName())
                .type(publicationRequest.getType())
                .author(publicationRequest.getAuthor())
                .publisher(publicationRequest.getPublisher())
                .price(publicationRequest.getPrice())
                .build();
        publicationRepository.save(publication);
        log.info("Publication [{}] saved successfully.", publication.getId());
    }

    public List<PublicationResponse> getAllPublications() {
        List<Publication> publications = publicationRepository.findAll();
        log.info("Fetched [{}] publications successfully.", publications.size());
        return publications.stream().map(
                this::fromPublicationToPublicationResponse
        ).toList();
    }

    public PublicationResponse getPublicationById(String id) throws ResourceNotFoundException {
        Publication publication = publicationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Publication not found with id: "+id)
        );
        return fromPublicationToPublicationResponse(publication);
    }

    public void deletePublication(String id) {
        publicationRepository.deleteById(id);
    }

    public PublicationResponse updatePublication(PublicationRequest publicationRequest, String id) throws IllegalAccessException, ResourceNotFoundException {
        Publication publication = publicationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Publication not found with id: "+id)
        );
        ArrayList<String> nullFieldsInPublicationRequest = getNullFields(publicationRequest);
        BeanUtils.copyProperties(publicationRequest, publication, nullFieldsInPublicationRequest.toArray(String[]::new));
        publicationRepository.save(publication);
        return fromPublicationToPublicationResponse(publication);
    }

    private PublicationResponse fromPublicationToPublicationResponse(Publication publication) {
        return PublicationResponse.builder()
                .id(publication.getId())
                .name(publication.getName())
                .type(publication.getType())
                .author(publication.getAuthor())
                .publisher(publication.getPublisher())
                .price(publication.getPrice())
                .build();
    }

    private ArrayList<String> getNullFields(Object object) throws IllegalAccessException {
        ArrayList<String> nullFields = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field: fields) {
            field.setAccessible(true);
            if (field.get(object) == null) {
                nullFields.add(field.getName());
            }
        }
        return nullFields;
    }
}
