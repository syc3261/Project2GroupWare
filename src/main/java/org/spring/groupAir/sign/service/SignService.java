package org.spring.groupAir.sign.service;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.repository.MemberRepository;
import org.spring.groupAir.sign.dto.SignDto;
import org.spring.groupAir.sign.entity.SignEntity;
import org.spring.groupAir.sign.repository.SignFileRepository;
import org.spring.groupAir.sign.repository.SignRepository;
import org.spring.groupAir.sign.service.serviceInterface.SignServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class SignService implements SignServiceInterface {

    private final SignRepository signRepository;
    private final SignFileRepository signFileRepository;
    private final MemberRepository memberRepository;
    private static final Logger logger = Logger.getLogger(SignService.class.getName());


    @Transactional
    @Override
    public int write(SignDto signDto) {
//        SignEntity signEntity = SignEntity.towriteSignEntity(signDto);
//        Long id = signRepository.save(signEntity).getId();
//        if (id != null) {
//            return 1;
//        }
//
//        return 0;
//    }
        MemberEntity memberEntity = signDto.getMemberEntity();
        if (memberEntity != null && memberEntity.getId() == null) {
            memberEntity = memberRepository.save(memberEntity);
        }
        signDto.setMemberEntity(memberEntity);

        SignEntity signEntity = SignEntity.towriteSignEntity(signDto);
        Long id = signRepository.save(signEntity).getId();
        if (id != null) {
            return 1;
        }
        return 0;
    }


//    @Override
//    public List<SignDto> signSearchList(Pageable pageable, String subject, String search) {
//        return null;
//    }

    @Override
    public Page<SignDto> signSearchPagingList(Pageable pageable, String subject, String search) {
        Page<SignEntity> signEntities = null;
        if (subject == null || search == null) {
            signEntities = signRepository.findAll(pageable);
        } else {
            if (subject.equals("title")) {
                signEntities = signRepository.findByTitleContaining(pageable, search);
            } else if (subject.equals("approve")) {
                signEntities = signRepository.findByApproveContaining(pageable, search);
            } else {
                signEntities = signRepository.findAll(pageable);
            }
        }
//        Page<SignDto> signDtos = signEntities.map(SignDto::toSelectSignDto);
//
//
//        return signDtos;
        return signEntities.map(SignDto::toSelectSignDto);
    }

    @Override
    public SignDto signOne(Long id) {
//        Optional<SignEntity> optionalSignEntity = signRepository.findById(id);
//        if (optionalSignEntity.isPresent()) {
//
//            SignEntity signEntity = optionalSignEntity.get();
//            SignDto signDto = SignDto.toSignDto(signEntity);
//            return signDto;
//        }
//
//        return null;
        Optional<SignEntity> optionalSignEntity = signRepository.findById(id);
        return optionalSignEntity.map(SignDto::toSignDto).orElse(null);
    }

    @Override
    public SignDto approveSignOne(Long id) {
        Optional<SignEntity> optionalSignEntity = signRepository.findById(id);


        return optionalSignEntity.map(SignDto::toSignDto).orElse(null);
    }

    @Transactional
    @Override
    public int deleteSign(Long id) {
        signRepository.deleteById(id);
        return 0;
    }

    @Override
    public void update(SignDto signDto) {

        signDto.setMemberEntity(MemberEntity.builder().id(signDto.getEmployee_id()).build());

        System.out.println(">>>>>>>" + signDto.getEmployee_id());

        SignEntity signEntity = SignEntity.toUpdateEntity(signDto);

        Long id = signRepository.save(signEntity).getId();

        Optional<SignEntity> optionalSignEntity = signRepository.findById(id);
        if (optionalSignEntity.isPresent()) {
            System.out.println("수정성공");
            return;
        }
        System.out.println("수정실패");
        throw new IllegalArgumentException("수정실패");

    }


    @Override
    public Long insertSign(SignDto signDto) throws IOException {


        MemberEntity memberEntity = signDto.getMemberEntity();
        if (memberEntity != null && memberEntity.getId() == null) {
            memberEntity = memberRepository.save(memberEntity);
        }
        signDto.setMemberEntity(memberEntity);

        // 파일 전송 부분을 제외한 코드

        SignEntity signEntity = SignEntity.toInsertSignEntity(signDto);
        Long signId = signRepository.save(signEntity).getId();
        return signId;


//        MemberEntity memberEntity = signDto.getMemberEntity();
//        if (memberEntity != null && memberEntity.getId() == null) {
//
//            memberEntity = memberRepository.save(memberEntity);
//
//
//        }
//        signDto.setMemberEntity(memberEntity);
//
//
//        System.out.println(signDto.getRejectReason()+" rejectReason");
//
//
//        if (signDto.getSignFile().isEmpty()) {
//            SignEntity signEntity = SignEntity.toInsertSignEntity(signDto);
//
//           Long signId= signRepository.save(signEntity).getId();
//           return signId;
//        } else {
//            MultipartFile signFile = signDto.getSignFile();
//            String signOldFile = signFile.getOriginalFilename();
//            UUID uuid = UUID.randomUUID();
//            String signNewFile = uuid + "_" + signOldFile;
//            String fileSavePath = "c:/groupAir/" + signNewFile;
//            signFile.transferTo(new File(fileSavePath));
//
//            SignEntity signEntity = SignEntity.toInsertFileSignEntity(signDto);
//            Long id = signRepository.save(signEntity).getId();
//
//            System.out.println(id+" <<< id");
//            SignEntity signEntity1 = signRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
//            SignFileDto signFileDto = SignFileDto.builder()
//                    .signOldFile(signOldFile)
//                    .signNewFile(signNewFile)
//                    .content(signEntity.getContent())
//                    .signEntity(signEntity1)
//                    .build();
//
//            SignFileEntity signFileEntity2 = SignFileEntity.toInsertFile(signFileDto);
//            signFileRepository.save(signFileEntity2);
//
//            return id;


    }


    @Override
    public List<SignDto> signList(Pageable pageable) {
        return null;
    }

    @Override
    public Page<SignDto> signListByAuthor(String currentUserName, Pageable pageable) {
        return signRepository.findByMemberEntityName(currentUserName, pageable).map(SignDto::toSelectSignDto);
    }


//    @Override
//    public List<SignDto> getSignListByLastApprover(String name) {
//        List<SignEntity> signEntities = signRepository.findByLastApprover(name);
//        return signEntities.stream().map(SignDto::new).collect(Collectors.toList());
//    }

    @Override
    public Page<SignDto> apvList(Pageable pageable, String subject,
                                 String search, String name) {
        Page<SignEntity> signEntityPage = null;

        if (subject == null || search == null) {
            signEntityPage = signRepository.findByLastApprover(pageable, name);
        } else {
            if (subject.equals("title")) {
                signEntityPage = signRepository.findByTitleContaining(pageable, search);
            } else if (subject.equals("approve")) {
                signEntityPage = signRepository.findByApproveContaining(pageable, search);
            } else if (subject.equals("content")) {
                signEntityPage = signRepository.findByApproveContaining(pageable, search);
            } else {
                signEntityPage = signRepository.findByLastApprover(pageable, name);
            }
        }
        Page<SignDto> signDtoPage = signEntityPage.map(SignDto::toSignDto);

        return signDtoPage;
    }

    @Override
    public Page<SignDto> myApvList(Pageable pageable, String subject, String search, String name) {
        Page<SignEntity> signEntityPage = null;

        if (subject == null || search == null) {
            signEntityPage = signRepository.findByApprove(pageable, name);
        } else {
            if (subject.equals("title")) {
                signEntityPage = signRepository.findByTitleContaining(pageable, search);
            } else if (subject.equals("lastApprover")) {
                signEntityPage = signRepository.findByLastApprover(pageable, search);

            } else if (subject.equals("content")) {
                signEntityPage = signRepository.findByContentContaining(pageable, search);
            } else {
                signEntityPage = signRepository.findByApprove(pageable, name);
            }
        }
        Page<SignDto> signDtoPage = signEntityPage.map(SignDto::toSignDto);

        return signDtoPage;
    }


    @Override
    public void signOk(SignDto signDto) {
        SignEntity signEntity = SignEntity.toUpdateEntity(signDto);
        Long id = signRepository.save(signEntity).getId();

        Optional<SignEntity> optionalSignEntity = signRepository.findById(id);

        if (optionalSignEntity.isPresent()) {
            System.out.println("수정성공");
            return;
        }
        System.out.println("수정실패");
        throw new IllegalArgumentException("수정실패");

    }


    @Override
    public List<SignDto> signSubContnetList(String subContent) {
        List<SignEntity> signEntities = signRepository.findAllBySubContent(subContent);


        System.out.println(signEntities.size() + " size");

        //List<Enity>  -> List<Dto>
        List<SignDto> signDtoList = signEntities.stream().map(SignDto::toSelectSignDto).collect(Collectors.toList());
        return signDtoList;
    }


    @Override
    public List<SignDto> getAllSignOk(String name) {

        String subContent = "승인";

        List<SignEntity> signEntities = signRepository.findAllByLastApproverAndSubContent(name, subContent);
        return signEntities.stream().map(SignDto::toSignDto).collect(Collectors.toList());


    }

    @Override
    public List<SignDto> getAllSignNo(String name) {

        String subContent = "반려";

        List<SignEntity> signEntities = signRepository.findAllByLastApproverAndSubContent(name, subContent);

        return signEntities.stream().map(SignDto::toSignDto).collect(Collectors.toList());
    }

    @Override
    public int notSignCount(String name) {


        String subContent = "미결재";

        int notSign = signRepository.findAllByLastApproverAndSubContent(name, subContent).size();

        return notSign;
    }

    @Override
    public int myNotSignCount(Long id) {

        String subContent = "미결재";

        int myNotSignCount = signRepository.findByMemberEntityIdAndSubContent(id,subContent).size();

        return myNotSignCount;
    }

    @Override
    public int mySignOkCount(Long id) {

        String subContent = "승인";

        int mySignOkCount = signRepository.findByMemberEntityIdAndSubContent(id,subContent).size();

        return mySignOkCount;
    }

    @Override
    public int mySignNoCount(Long id) {
        String subContent = "반려";

        int mySignNoCount = signRepository.findByMemberEntityIdAndSubContent(id,subContent).size();

        return mySignNoCount;
    }


//    @Override
//    public Page<SignDto> signListById(Pageable pageable, String subject, String search,Long id) {
//        Page<SignEntity> signEntityPage = null;
//
//        MemberEntity memberEntity= MemberEntity.builder().id(id).build();
//
//        Page<SignEntity> signEntities=    signRepository.findAllByMemberEntity(pageable,search,memberEntity);
//
//
//
//       Page<SignDto> signDtoPage = signEntities.map(SignDto::toSignDto);
//
//
//
//        return signDtoPage;
//   }


}



