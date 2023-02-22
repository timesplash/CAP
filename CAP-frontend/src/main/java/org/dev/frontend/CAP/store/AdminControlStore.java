package org.dev.frontend.CAP.store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.dev.api.CAP.model.LogInDateDTO;

import java.util.List;

@Getter
public class AdminControlStore {
    private static final AdminControlStore adminControlStore = new AdminControlStore();

    public static AdminControlStore getStore() {
        return adminControlStore;
    }

    private final StoreRestUtils storeRestUtils = StoreRestUtils.getInstance();

    @Getter
    private final ObservableList<String> allEntries = FXCollections.observableArrayList();

    public void refreshStore() {
        allEntries.clear();
    }

    public void populateEntries() {
        List<LogInDateDTO> logInDateDTOS = storeRestUtils.getLastLogInDateList().orElseThrow(RuntimeException::new);
        for (LogInDateDTO logInDateDTO : logInDateDTOS) {
            allEntries.add(logInDateDTO.getLogin() + "; " + logInDateDTO.getDate());
        }
    }

    public void deleteInactiveUser(String login) {
        storeRestUtils.deleteInactiveUser(login);
    }
}
