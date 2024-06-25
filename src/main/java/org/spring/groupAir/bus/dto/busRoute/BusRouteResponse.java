package org.spring.groupAir.bus.dto.busRoute;

import lombok.Data;

@Data
public class BusRouteResponse {

    private ComMsgHeader comMsgHeader;

    private MsgHeader msgHeader;

    private MsgBody msgBody;
}
