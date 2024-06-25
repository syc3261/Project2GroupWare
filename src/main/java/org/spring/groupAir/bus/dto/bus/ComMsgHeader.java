package org.spring.groupAir.bus.dto.bus;


import lombok.Data;

@Data
public class ComMsgHeader {

    private String successYN;
    private String responseTime;
    private String responseMsgID;
    private String requestMsgID;
    private String returnCode;
    private String errMsg;
}
