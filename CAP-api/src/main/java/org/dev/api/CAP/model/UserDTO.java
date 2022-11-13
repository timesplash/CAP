package org.dev.api.CAP.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.dev.api.CAP.enums.Role;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
//@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private String userName;

    private String password;

    private String keyQuestion;

    private String keyAnswer;

    private Role role;
}
