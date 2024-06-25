package org.spring.groupAir.sign.repository;

import org.junit.jupiter.api.Test;
import org.spring.groupAir.sign.entity.SignEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SingTest {


    @Autowired
    SignRepository signRepository;

    @Test
    void list(){

        String subContent="승인";

        List<SignEntity> signEntityList =signRepository.findAllBySubContent(subContent);
        System.out.println(signEntityList);

    }
}
