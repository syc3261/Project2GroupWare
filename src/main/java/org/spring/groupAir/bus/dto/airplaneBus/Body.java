package org.spring.groupAir.bus.dto.airplaneBus;

import lombok.Data;

import java.util.List;

@Data
public class Body {
    private int numOfRows;

    private int pageNo;

    private int totalCount;

    private List<Items> items;
}
