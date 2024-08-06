package com.eminenceinnovation.enc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MatchDatum {
    String competition;
    Integer year;
    String round;
    String team1;
    String team2;
    String team1goals;
    String team2goals;
}
