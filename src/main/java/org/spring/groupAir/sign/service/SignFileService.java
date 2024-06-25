package org.spring.groupAir.sign.service;

import lombok.RequiredArgsConstructor;
import org.spring.groupAir.sign.repository.SignFileRepository;
import org.spring.groupAir.sign.service.serviceInterface.SignFileServiceInterface;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignFileService implements SignFileServiceInterface {

    private final SignFileRepository signFileRepository;



}
