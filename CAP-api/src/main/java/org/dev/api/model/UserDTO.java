package org.dev.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.dev.api.enums.Role;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {
    private int id;

    private String userName;

    private String password;

    private String keyQuestion;

    private String keyAnswer;

    private Role role;
}
