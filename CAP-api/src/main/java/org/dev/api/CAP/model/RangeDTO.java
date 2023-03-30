package org.dev.api.CAP.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RangeDTO {

    private int month;

    private int year;

    private String userName;
}
