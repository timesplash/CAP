package org.dev.api.CAP.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LogInDateDTO {

    private String login;

    private LocalDateTime date;

}
