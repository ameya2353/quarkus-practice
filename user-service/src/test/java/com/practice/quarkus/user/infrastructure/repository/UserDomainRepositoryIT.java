import com.practice.quarkus.user.domain.model.Pet;
import com.practice.quarkus.user.infrastructure.repository.UserDomainRepository;
import com.practice.quarkus.user.infrastructure.rest.api.PetServiceApiClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDomainRepositoryIT {

    private static MockWebServer server;

    @BeforeAll
    static void setup() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    private UserDomainRepository createRepository() {
        URI baseUri = server.url("/").uri();
        PetServiceApiClient client = RestClientBuilder.newBuilder()
                .baseUri(baseUri)
                .build(PetServiceApiClient.class);
        return new UserDomainRepository(client);
    }

    @Test
    void fetchOwnedPetsUsesHttpClient() throws Exception {
        String json = "{\"petName\":\"Buddy\",\"petType\":\"dog\",\"breed\":\"golden\"}";
        server.enqueue(new MockResponse()
                .setBody("[\"" + json + "\"]")
                .addHeader("Content-Type", "application/json"));

        UserDomainRepository repository = createRepository();
        List<Pet> pets = repository.fetchOwnedPets("42");

        RecordedRequest request = server.takeRequest();
        assertEquals("/pet-service?userId=42", request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals(1, pets.size());
        assertEquals(new Pet("Buddy", "dog", "golden"), pets.get(0));
    }

    @Test
    void addPetSendsPostRequest() throws Exception {
        server.enqueue(new MockResponse().setBody("true"));

        UserDomainRepository repository = createRepository();
        repository.addPet("99", "cat", "persian");

        RecordedRequest request = server.takeRequest();
        assertEquals("/pet-service/add-pet?userId=99&petType=cat&petBreed=persian", request.getPath());
        assertEquals("POST", request.getMethod());
    }
}
