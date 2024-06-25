package org.spring.groupAir.bus.dto.bus;

import lombok.Data;

@Data
public class MsgHeader {

    private String headerMsg;
    private String headerCd;
    private String itemCount;
}

