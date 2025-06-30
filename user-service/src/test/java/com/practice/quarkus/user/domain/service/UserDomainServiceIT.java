package com.practice.quarkus.user.domain.service;

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

public class UserDomainServiceIT {

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

    private UserDomainService createService() {
        URI baseUri = server.url("/").uri();
        PetServiceApiClient client = RestClientBuilder.newBuilder()
                .baseUri(baseUri)
                .build(PetServiceApiClient.class);
        UserDomainRepository repo = new UserDomainRepository(client);
        return new UserDomainService(repo, repo);
    }

    @Test
    void petsOwnedByUserCallsRepository() throws Exception {
        String json = "{\"petName\":\"Max\",\"petType\":\"dog\",\"breed\":\"terrier\"}";
        server.enqueue(new MockResponse()
                .setBody("[\"" + json + "\"]")
                .addHeader("Content-Type", "application/json"));

        UserDomainService service = createService();
        List<Pet> pets = service.petsOwnedByUser("5");

        RecordedRequest request = server.takeRequest();
        assertEquals("/pet-service?userId=5", request.getPath());
        assertEquals("GET", request.getMethod());
        assertEquals(List.of(new Pet("Max", "dog", "terrier")), pets);
    }

    @Test
    void addPetToUserCallsRepository() throws Exception {
        server.enqueue(new MockResponse().setBody("true"));
        UserDomainService service = createService();
        service.addPetToUser("7", "cat", "siamese");

        RecordedRequest request = server.takeRequest();
        assertEquals("/pet-service/add-pet?userId=7&petType=cat&petBreed=siamese", request.getPath());
        assertEquals("POST", request.getMethod());
    }
}

