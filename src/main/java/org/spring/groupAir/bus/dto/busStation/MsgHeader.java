package org.spring.groupAir.bus.dto.busStation;

import lombok.Data;

@Data
public class MsgHeader {
    private String headerMsg;
    private String headerCd;
    private String itemCount;
}
