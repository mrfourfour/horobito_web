package myproject.demo.KeyCloak.infrastructure;

import lombok.RequiredArgsConstructor;
import myproject.demo.KeyCloak.service.DuplicateUserSignUpException;
import myproject.demo.KeyCloak.service.Token;
import myproject.demo.KeyCloak.service.TokenProvider;
import myproject.demo.KeyCloak.service.TokenRequest;
import org.apache.http.HttpHeaders;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class KeyCloakTokenProvider implements TokenProvider {

    @Qualifier("keycloakWebClient")
    private final WebClient webClient;

    private final KeycloakSpringBootProperties properties;
    private final Keycloak keycloakAdminClient;


    /*
    발급용 1
     */
    @Override
    public Token issue(TokenRequest tokenRequest) {
        String username = tokenRequest.getEmail();
        String password = tokenRequest.getPassword();
        String resource = properties.getResource();
        String clientSecret = properties.getCredentials().get("secret").toString();
        BodyInserters.FormInserter<String> formData = createFormData(resource, clientSecret, username, password);
        return fetchResource(formData);

    }


    @Override
    public Token refresh(String refreshToken) {
        String resource = properties.getResource();
        String clientSecret = properties.getCredentials().get("secret").toString();
        BodyInserters.FormInserter<String> formData
                = createFormData(resource, clientSecret, refreshToken);
        return fetchResource(formData);
    }


    /*
    역할
    자원을 본격적으로 받아오는 것
    keyCloak도 RestApi를 지원하기때문에,
    이렇게 자원을 요청하는 것
     */
    private Token fetchResource(BodyInserters.FormInserter<String> formData) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("token").build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(formData)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> Mono.error(
                                new IllegalArgumentException("Not authorized")))
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(
                                new IllegalArgumentException("keycloak Server error")))
                .bodyToMono(Token.class).block();
    }

    private BodyInserters.FormInserter<String> createFormData(
            String resource, String clientSecret, String refreshToken) {
        return BodyInserters.fromFormData("refresh_token", refreshToken)
                .with("client_id", resource)
                .with("client_secret", clientSecret)
                .with("grant_type", "refresh_token");
    }

    private BodyInserters.FormInserter<String> createFormData(String resource, String clientSecret, String username, String password) {
        return BodyInserters.fromFormData("client_id", resource)
                .with("grant_type", "password")
                .with("client_secret", clientSecret)
                .with("username", username)
                .with("password", password);
    }

    @Override
    public void signUp(TokenRequest tokenRequest) {
        UsersResource userResources = keycloakAdminClient
                .realm(properties.getRealm())
                .users();
        UserRepresentation userRepresentation
                = getUserRepresentation(tokenRequest);
        Response createUserResponse = userResources
                .create(userRepresentation);
        Response.StatusType statusInfo = createUserResponse.getStatusInfo();
        if (statusInfo.equals(Response.Status.CONFLICT)) {
            throw new DuplicateUserSignUpException();
        }
        if (statusInfo.equals(Response.Status.FORBIDDEN)) {
            throw new IllegalArgumentException("keycloak configuration");
        }
    }

    private UserRepresentation getUserRepresentation(TokenRequest tokenRequest) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(tokenRequest.getPassword());
        credential.setTemporary(false);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(tokenRequest.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(tokenRequest.getEmail());
        userRepresentation.setClientRoles(new HashMap<>() {{
            put(properties.getResource(), Collections.singletonList("role_ichi_user"));
        }});
        userRepresentation.setCredentials(Collections.singletonList(credential));

        return userRepresentation;

    }


}

@Configuration
class keyCloakWebClientConfig {

    /*
    회원가입에 사용되는 녀석, AdminClietn
     */
    @Bean
    public Keycloak keyCloakAdminClient(KeycloakSpringBootProperties properties) {
        return KeycloakBuilder.builder()
                .serverUrl(properties.getAuthServerUrl())
                .realm(properties.getRealm())
                .clientId(properties.getResource())
                .clientSecret(properties.getCredentials().get("secret").toString())
                .grantType("client_credentials")
                .build();
    }

    /*
    keycloak 에 연결을 하고자 하는 곳으로 연결이 되어 있다.
     */
    @Bean
    public WebClient keycloakWebClient(KeycloakSpringBootProperties properties) {

        String baseUrl = getBaseUrl(properties);
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    private String getBaseUrl(KeycloakSpringBootProperties properties) {
        return properties.getAuthServerUrl() + "/realms/" +
                properties.getRealm() + "/protocol/openid-connect";
    }
}
