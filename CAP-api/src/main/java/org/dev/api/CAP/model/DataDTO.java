package org.dev.api.CAP.model;

import lombok.*;
import org.dev.api.CAP.enums.Type;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DataDTO {

    private String categoryName;

    private Double amount;
}
