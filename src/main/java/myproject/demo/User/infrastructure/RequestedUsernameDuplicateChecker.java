package myproject.demo.User.infrastructure;

import myproject.demo.KeyCloak.service.DuplicateUserSignUpException;
import myproject.demo.User.service.UsernameDuplicateChecker;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequestedUsernameDuplicateChecker implements UsernameDuplicateChecker {
    private Keycloak keycloakAdminClient;
    private KeycloakSpringBootProperties properties;

    @Override
    public void checkDuplicate(String username) {
        UsersResource userResources = keycloakAdminClient
                .realm(properties.getRealm())
                .users();

        List<UserRepresentation> result=  userResources.search(username);
        if (result.size()>0){
            throw new DuplicateUserSignUpException();
        }

    }
}
