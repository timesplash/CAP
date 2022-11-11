package org.dev.frontend.store;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.dev.api.enums.Role;
import org.dev.api.model.UserDTO;

@Getter
public class RegisterStore {

    private static final RegisterStore registerStore = new RegisterStore();

    private final ObservableList<String> roles = FXCollections.observableArrayList("Corporate", "Personal");

    public static RegisterStore getStore() {
        return registerStore;
    }

    private final StoreRestUtils storeRestUtils = StoreRestUtils.getInstance();

    private final StringProperty login = new SimpleStringProperty();

    private final StringProperty password = new SimpleStringProperty();

    private final StringProperty keyQuestion = new SimpleStringProperty();

    private final StringProperty keyAnswer = new SimpleStringProperty();

    private final IntegerProperty role = new SimpleIntegerProperty();

    public void addUser() throws RuntimeException {
        UserDTO userDTO;
        if (!storeRestUtils.anyUsersPresent()) {
            userDTO = UserDTO.builder()
                    .userName(login.get())
                    .password(password.get())
                    .keyQuestion(keyQuestion.get())
                    .keyAnswer(keyAnswer.get())
                    .role(Role.ADMIN)
                    .build();
        } else {
            userDTO = UserDTO.builder()
                    .userName(login.get())
                    .password(password.get())
                    .keyQuestion(keyQuestion.get())
                    .keyAnswer(keyAnswer.get())
                    .role(Role.values()[role.get() + 1])
                    .build();
        }

        storeRestUtils.saveUser(userDTO);
    }

    public void bindRolesToEnum(ReadOnlyIntegerProperty property) {
        role.bind(property);
    }
}
