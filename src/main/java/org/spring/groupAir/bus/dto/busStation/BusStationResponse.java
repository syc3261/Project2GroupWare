package org.spring.groupAir.bus.dto.busStation;

import lombok.Data;

@Data
public class BusStationResponse {

    private ComMsgHeader comMsgHeader;

    private MsgHeader msgHeader;

    private MsgBody msgBody;
}
