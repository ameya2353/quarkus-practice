package com.practice.quarkus.pet;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PetRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void addPet(String ownerId, String type, String breed) {
        PetEntity pet = new PetEntity();
        pet.ownerId = ownerId;
        pet.petType = type;
        pet.petBreed = breed;
        em.persist(pet);
    }

    public List<Pet> findByOwner(String ownerId) {
        return em.createQuery("from PetEntity where ownerId = :owner", PetEntity.class)
                .setParameter("owner", ownerId)
                .getResultStream()
                .map(e -> new Pet(e.petBreed, e.petType, e.petBreed))
                .toList();
    }
}
