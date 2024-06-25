package org.spring.groupAir.bus.dto.busRoute;

import lombok.Data;

import java.util.List;

@Data
public class MsgBody {
    private List<ItemRouteList> itemList;
}