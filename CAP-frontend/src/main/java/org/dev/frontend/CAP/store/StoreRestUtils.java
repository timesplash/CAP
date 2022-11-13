package org.dev.frontend.CAP.store;

import lombok.Getter;
import lombok.Setter;
import org.dev.api.CAP.enums.Role;
import org.dev.api.CAP.model.LogInDateDTO;
import org.dev.api.CAP.model.UserDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.file.AccessDeniedException;
import java.util.*;

public class StoreRestUtils {
    private static final StoreRestUtils storeRestUtils = new StoreRestUtils();

    public static final String ANY_USERS_REQUEST_URL = "http://localhost:8888/api/users";

    public static final String GET_USER_BY_LOGIN_REQUEST_URL = "http://localhost:8888/api/users/";

    public static final String SAVE_USER_REQUEST_URL = "http://localhost:8888/api/users/new_user";

    public static final String SAVE_DATE_OF_ENTRY = "http://localhost:8888/api/login_dates";

    public static final String GET_LAST_DATE_OF_ENTRY = "http://localhost:8888/api/login_dates";

    public static final String DELETE_INACTIVE_USER = "http://localhost:8888/api/login_dates/{login}";

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

    public void saveNewEntryDate(LogInDateDTO logInDateDTO) {
        restTemplate.put(SAVE_DATE_OF_ENTRY, logInDateDTO);
    }

    public Optional<List<LogInDateDTO>> getLastLogInDateList() {
        LogInDateDTO[] logInDateDTOS = restTemplate.getForObject(GET_LAST_DATE_OF_ENTRY, LogInDateDTO[].class);
        if (logInDateDTOS != null) {
            return Optional.of(Arrays.asList(logInDateDTOS));
        } else {
            return Optional.empty();
        }
    }

    public void deleteInactiveUser(String login){
        Map<String,String> deletion = new HashMap<>();
        deletion.put("login", login);
        restTemplate.delete(DELETE_INACTIVE_USER, deletion);
    }
}
