package com.practice.quarkus.user.application;

import com.practice.quarkus.user.domain.model.Pet;
import com.practice.quarkus.user.domain.service.UserDomainService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

import static com.practice.quarkus.user.application.ApplicationConstants.*;

@Path(USER_SERVICE)
public class UserService {

    @Inject
    private UserDomainService userDomainService;

    @Path(ADD_PET_TO_USER)
    @POST
    public String addPetToUser(@PathParam(USER_ID) String userId,
                            @PathParam(PET_TYPE) String petType,
                            @PathParam(PET_BREED) String petBreed) {
        try {
            userDomainService.addPetToUser(userId, petType, petBreed);
            return String.format("Pet added  to user %s" ,userId);
        }catch (Exception exception){
            return String.format("Unable to add pet to user cause %s" ,exception.getMessage());
        }

    }


    @Path("/list-pets")
    @GET
    public List<Pet> listPets(@PathParam(USER_ID) String userId){
        try {
            return userDomainService.petsOwnedByUser(userId);
        }catch (Exception ex){
            return List.of();
        }
    }


}
