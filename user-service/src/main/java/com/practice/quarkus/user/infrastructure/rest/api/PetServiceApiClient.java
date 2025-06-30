package com.practice.quarkus.user.infrastructure.rest.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

import static com.practice.quarkus.user.infrastructure.InfrastructureConstants.PET_SERVICE_KEY;

@Path("/"+PET_SERVICE_KEY)
@RegisterRestClient(configKey = PET_SERVICE_KEY)
public interface PetServiceApiClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getAllOwnedPets(@QueryParam("userId") String userId);

    @Path("/add-pet")
    @POST
    boolean addPetForUser(@QueryParam("userId") String userId,
                           @QueryParam("petType") String petType,
                           @QueryParam("petBreed") String petBreed);


}
