package org.spring.groupAir.sign.service.serviceInterface;

import org.spring.groupAir.department.dto.TopDepartmentDto;
import org.spring.groupAir.sign.dto.SignDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface SignServiceInterface {


    public int write(SignDto signDto);



//  List<SignDto> signSearchList(Pageable pageable, String subject, String search);

    Page<SignDto> signSearchPagingList(Pageable pageable, String subject, String search);

    SignDto signOne(Long id);

    public int deleteSign(Long id);

    SignDto approveSignOne(Long id);

    void update(SignDto signDto);

    Long insertSign(SignDto signDto) throws IOException;

    List<SignDto> signList(Pageable pageable);

    Page<SignDto> signListByAuthor(String currentUserName, Pageable unpaged);


//    List<SignDto> getSignListByLastApprover(String currentUserName);

//    List<SignDto> getSignListByLastApprover(String currentUserName, String name);

//    List<SignDto> getSignListByLastApprover(String name);

    Page<SignDto> apvList(Pageable pageable, String subject, String search, String name);

    void signOk(SignDto signDto);

    List<SignDto> signSubContnetList(String subContent);

    Page<SignDto> myApvList(Pageable pageable, String subject, String search, String name);

    List<SignDto> getAllSignOk(String name);

    List<SignDto> getAllSignNo(String name);


    int notSignCount(String name);

    int myNotSignCount(Long id);

    int mySignOkCount(Long id);

    int mySignNoCount(Long id);
}
