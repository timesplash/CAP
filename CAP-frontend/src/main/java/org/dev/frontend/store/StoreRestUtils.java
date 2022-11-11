package org.dev.frontend.store;

import lombok.Getter;
import lombok.Setter;
import org.dev.api.enums.Role;
import org.dev.api.model.UserDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.file.AccessDeniedException;

public class StoreRestUtils {
    private static final StoreRestUtils storeRestUtils = new StoreRestUtils();

    public static final String ANY_USERS_REQUEST_URL = "http://localhost:8888/api/users";

    public static final String GET_USER_BY_LOGIN_REQUEST_URL = "http://localhost:8888/api/users/";

    public static final String SAVE_USER_REQUEST_URL = "http://localhost:8888/api/users/new_user";

    public static StoreRestUtils getInstance(){
        return storeRestUtils;
    }

    @Setter
    private String username;

    @Setter
    private String password;

    @Getter
    private UserDTO currentUser;

    private final RestTemplate restTemplate = new RestTemplate();

    public Boolean anyUsersPresent(){
        return restTemplate.getForObject(ANY_USERS_REQUEST_URL, Boolean.class);
    }

    public Role login(String username, String password) throws AccessDeniedException {
        this.username = username;
        this.password = password;
        restTemplate.getInterceptors().clear();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
        try {
            ResponseEntity<UserDTO> userDTOResponseEntity = restTemplate.exchange(GET_USER_BY_LOGIN_REQUEST_URL + this.username,
                    HttpMethod.GET, new HttpEntity<>(restTemplate.getInterceptors()), UserDTO.class);
            currentUser = userDTOResponseEntity.getBody();
            return currentUser.getRole();
        } catch (HttpClientErrorException e) {
            throw new AccessDeniedException("");
        }
    }

    public void saveUser(UserDTO userDTO) {
        restTemplate.put(SAVE_USER_REQUEST_URL, userDTO);
    }
}
