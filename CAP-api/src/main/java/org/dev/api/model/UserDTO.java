package org.dev.api.model;

import lombok.*;
import org.dev.api.enums.Role;

@AllArgsConstructor
@NoArgsConstructor
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
