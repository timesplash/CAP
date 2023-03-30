package org.dev.api.CAP.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDTO {
    private Double gainSummary;

    private Double lossesSummary;

    private Double overAllSummary;
}
