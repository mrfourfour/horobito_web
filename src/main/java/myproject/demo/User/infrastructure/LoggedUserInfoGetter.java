package myproject.demo.User.infrastructure;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import myproject.demo.User.service.LoggedUserInfo;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class LoggedUserInfoGetter implements LoggedUserInfo {
    private final Keycloak keycloakAdminClient;
    private final KeycloakSpringBootProperties properties;

    @Override
    public String getLoggedUsername(){
        UsersResource userResources = getUserResources();
        return userResources.get(getPrincipal()).toRepresentation().getUsername();

    }

    public UsersResource getUserResources(){
        return keycloakAdminClient
                .realm(properties.getRealm())
                .users();
    }

    public String getPrincipal(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
