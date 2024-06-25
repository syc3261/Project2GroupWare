package org.spring.groupAir.chatbot.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeInfo {

    private String phone;
    private String name;
    private String role;
    private String deptName;
    private String topDeptName;
    private String email;



}
