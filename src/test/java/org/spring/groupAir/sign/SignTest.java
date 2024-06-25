package org.spring.groupAir.sign;

import org.junit.jupiter.api.Test;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.repository.MemberRepository;
import org.spring.groupAir.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SignTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    void testInsert(){
for(int i=0 ; i<2;i++ ){
    memberRepository.save(MemberEntity.builder()

            .name("m1"+i)
            .userEmail("m1@m"+i)
            .userPw("11"+i)
            .address("m"+i)
            .phone("11"+1)
            .build());
}



    }





}
