package com.eminenceinnovation.enc.model;


import lombok.Data;

import java.util.List;

@Data
public class MatchAllDetails {
    Integer page;
    Integer per_page;
    Integer total;
    Integer total_pages;
    List<MatchDatum> data;
}
