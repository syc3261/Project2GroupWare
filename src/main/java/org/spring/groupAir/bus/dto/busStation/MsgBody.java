package org.spring.groupAir.bus.dto.busStation;

import lombok.Data;

import java.util.List;

@Data
public class MsgBody {
    private List<ItemStationList> itemList;
}