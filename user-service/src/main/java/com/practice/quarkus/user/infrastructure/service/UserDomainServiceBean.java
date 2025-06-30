package com.practice.quarkus.user.infrastructure.service;

import com.practice.quarkus.user.domain.ports.incoming.FetchOwnedPetsPort;
import com.practice.quarkus.user.domain.ports.outgoing.AddPetToTheOwner;
import com.practice.quarkus.user.domain.service.UserDomainService;
import com.practice.quarkus.user.infrastructure.repository.UserDomainRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserDomainServiceBean {

    private final UserDomainRepository userDomainRepository;


    @Inject
    public UserDomainServiceBean(UserDomainRepository userDomainRepository) {
        this.userDomainRepository = userDomainRepository;
    }

    @Produces
    @ApplicationScoped
    public UserDomainService userDomainService() {
        return new UserDomainService(userDomainRepository, userDomainRepository);
    }

}
