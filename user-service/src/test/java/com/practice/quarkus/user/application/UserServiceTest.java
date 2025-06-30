package com.practice.quarkus.user.application;

import com.practice.quarkus.user.domain.model.Pet;
import com.practice.quarkus.user.domain.service.UserDomainService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private static void inject(UserService service, UserDomainService domainService) throws Exception {
        Field field = UserService.class.getDeclaredField("userDomainService");
        field.setAccessible(true);
        field.set(service, domainService);
    }

    @Test
    void addPetReturnsSuccessMessage() throws Exception {
        UserDomainService domainService = mock(UserDomainService.class);
        UserService service = new UserService();
        inject(service, domainService);

        String result = service.addPetToUser("1", "dog", "bulldog");

        verify(domainService).addPetToUser("1", "dog", "bulldog");
        assertEquals("Pet added  to user 1", result);
    }

    @Test
    void addPetReturnsErrorMessageOnFailure() throws Exception {
        UserDomainService domainService = mock(UserDomainService.class);
        doThrow(new RuntimeException("boom")).when(domainService).addPetToUser(any(), any(), any());
        UserService service = new UserService();
        inject(service, domainService);

        String result = service.addPetToUser("2", "cat", "persian");

        verify(domainService).addPetToUser("2", "cat", "persian");
        assertTrue(result.contains("Unable to add pet to user"));
    }

    @Test
    void listPetsReturnsFromService() throws Exception {
        UserDomainService domainService = mock(UserDomainService.class);
        List<Pet> pets = List.of(new Pet("Buddy", "dog", "labrador"));
        when(domainService.petsOwnedByUser("3")).thenReturn(pets);
        UserService service = new UserService();
        inject(service, domainService);

        List<Pet> result = service.listPets("3");

        verify(domainService).petsOwnedByUser("3");
        assertEquals(pets, result);
    }

    @Test
    void listPetsReturnsEmptyListOnFailure() throws Exception {
        UserDomainService domainService = mock(UserDomainService.class);
        when(domainService.petsOwnedByUser(any())).thenThrow(new RuntimeException());
        UserService service = new UserService();
        inject(service, domainService);

        List<Pet> result = service.listPets("4");

        verify(domainService).petsOwnedByUser("4");
        assertTrue(result.isEmpty());
    }
}

