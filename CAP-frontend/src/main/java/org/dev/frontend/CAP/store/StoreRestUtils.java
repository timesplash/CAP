package org.dev.frontend.CAP.store;

import lombok.Getter;
import lombok.Setter;
import org.dev.api.CAP.enums.Role;
import org.dev.api.CAP.model.*;
import org.dev.frontend.CAP.model.Data;
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

    public static final String DELETE_INACTIVE_USER_URL = "http://localhost:8888/api/login_dates/{login}";

    public static final String CATEGORIES_URL = "http://localhost:8888/api/categories";

    public static final String DATA_URL = "http://localhost:8888/api/data";

    public static final String SUMMARY_URL = "http://localhost:8888/api/data/summary";

    public static StoreRestUtils getInstance() {
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
            assert currentUser != null;
            currentUser.setUserName(username);
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
        restTemplate.delete(DELETE_INACTIVE_USER_URL, deletion);
    }

    public Optional<List<CategoriesDTO>> getAllCategories() {
        CategoriesDTO[] categoriesDTOS = restTemplate.getForObject(CATEGORIES_URL, CategoriesDTO[].class);
        if (categoriesDTOS != null) {
            return Optional.of(Arrays.asList(categoriesDTOS));
        } else {
            return Optional.empty();
        }
    }

    public void saveCategory(CategoriesDTO categoriesDTO) {
        restTemplate.put(CATEGORIES_URL, categoriesDTO);
    }

    public void saveData(DataDTO dataDTO) {
        restTemplate.put(DATA_URL, dataDTO);
    }

    public Optional<List<Data>> getData(RangeDTO rangeDTO) {
        Data[] dataDTOS = restTemplate.postForObject(DATA_URL, rangeDTO, Data[].class);
        if (dataDTOS != null) {
            return Optional.of(Arrays.asList(dataDTOS));
        } else {
            return Optional.empty();
        }
    }

    public SummaryDTO getSummary(RangeDTO rangeDTO) {
        return restTemplate.postForObject(SUMMARY_URL, rangeDTO, SummaryDTO.class);
    }
}
