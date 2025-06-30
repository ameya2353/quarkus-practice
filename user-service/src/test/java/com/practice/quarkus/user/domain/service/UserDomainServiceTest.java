package com.practice.quarkus.user.domain.service;

import com.practice.quarkus.user.domain.model.Pet;
import com.practice.quarkus.user.domain.ports.incoming.FetchOwnedPetsPort;
import com.practice.quarkus.user.domain.ports.outgoing.AddPetToTheOwner;
import com.practice.quarkus.user.domain.service.UserDomainService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDomainServiceTest {

    @Test
    void addPetToUserDelegatesToPort() {
        FetchOwnedPetsPort fetchPort = mock(FetchOwnedPetsPort.class);
        AddPetToTheOwner addPort = mock(AddPetToTheOwner.class);
        UserDomainService service = new UserDomainService(fetchPort, addPort);

        service.addPetToUser("user1", "dog", "labrador");

        verify(addPort).addPet("user1", "dog", "labrador");
        verifyNoInteractions(fetchPort);
    }

    @Test
    void petsOwnedByUserDelegatesToFetchPort() {
        FetchOwnedPetsPort fetchPort = mock(FetchOwnedPetsPort.class);
        AddPetToTheOwner addPort = mock(AddPetToTheOwner.class);
        UserDomainService service = new UserDomainService(fetchPort, addPort);

        List<Pet> pets = List.of(new Pet("Buddy", "dog", "labrador"));
        when(fetchPort.fetchOwnedPets("user2")).thenReturn(pets);

        List<Pet> result = service.petsOwnedByUser("user2");

        assertEquals(pets, result);
        verify(fetchPort).fetchOwnedPets("user2");
        verifyNoInteractions(addPort);
    }
}
