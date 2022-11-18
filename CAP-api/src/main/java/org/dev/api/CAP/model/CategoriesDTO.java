package org.dev.api.CAP.model;


import lombok.*;
import org.dev.api.CAP.enums.Range;
import org.dev.api.CAP.enums.Type;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoriesDTO {

    private int id;

    private String name;

    private Type type;

    private Range range;

    private String username;
}
