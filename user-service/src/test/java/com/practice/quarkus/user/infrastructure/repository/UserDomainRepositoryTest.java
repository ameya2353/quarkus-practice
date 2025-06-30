import com.practice.quarkus.user.domain.model.Pet;
import com.practice.quarkus.user.infrastructure.repository.UserDomainRepository;
import com.practice.quarkus.user.infrastructure.rest.api.PetServiceApiClient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDomainRepositoryTest {

    @Test
    void fetchOwnedPetsMapsResponseToPets() {
        PetServiceApiClient api = mock(PetServiceApiClient.class);
        String json = "{\"petName\":\"Buddy\",\"petType\":\"dog\",\"breed\":\"labrador\"}";
        when(api.getAllOwnedPets("user1")).thenReturn(List.of(json));

        UserDomainRepository repository = new UserDomainRepository(api);
        List<Pet> pets = repository.fetchOwnedPets("user1");

        assertEquals(1, pets.size());
        assertEquals(new Pet("Buddy", "dog", "labrador"), pets.get(0));
    }

    @Test
    void addPetDelegatesToClient() {
        PetServiceApiClient api = mock(PetServiceApiClient.class);
        UserDomainRepository repository = new UserDomainRepository(api);

        repository.addPet("u", "cat", "persian");

        verify(api).addPetForUser("u", "cat", "persian");
    }
}
