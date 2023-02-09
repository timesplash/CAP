package org.dev.api.CAP.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DataDTO {

    private String categoryName;

    private LocalDateTime date;

    private Double amount;

    private String username;
}
