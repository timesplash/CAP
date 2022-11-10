package org.dev.frontend.store;

import lombok.Getter;
import org.dev.api.model.UserDTO;
import org.springframework.web.client.RestTemplate;

public class StoreRestUtils {
    private static final StoreRestUtils storeRestUtils = new StoreRestUtils();

    public static final String ANY_USERS_REQUEST_URL = "http://localhost:8888/api/users";

    public static StoreRestUtils getInstance(){
        return storeRestUtils;
    }

    @Getter
    private UserDTO currentUser;

    private final RestTemplate restTemplate = new RestTemplate();

    public Boolean anyUsersPresent(){
        return restTemplate.getForObject(ANY_USERS_REQUEST_URL, Boolean.class);
    }
}
