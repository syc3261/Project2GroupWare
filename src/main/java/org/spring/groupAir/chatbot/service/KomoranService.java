package org.spring.groupAir.chatbot.service;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;


import org.spring.groupAir.bus.dto.bus.ItemList;
import org.spring.groupAir.bus.entity.BusEntity;
import org.spring.groupAir.bus.repository.BusRepository;
import org.spring.groupAir.chatbot.dto.AnswerDto;
import org.spring.groupAir.chatbot.dto.EmployeeInfo;
import org.spring.groupAir.chatbot.dto.MessageDto;
import org.spring.groupAir.chatbot.entity.AnswerEntity;
import org.spring.groupAir.chatbot.entity.ChatBotIntentionEntity;
import org.spring.groupAir.chatbot.repository.ChatBotIntentionRepository;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.repository.MemberRepository;
import org.spring.groupAir.movie.dto.MovieInfo;
import org.spring.groupAir.movie.dto.WeeklyBoxOfficeList;
import org.spring.groupAir.movie.entity.BoxOfficeEntity;
import org.spring.groupAir.movie.entity.MovieDetailEntity;
import org.spring.groupAir.movie.repository.BoxOfficeRepository;
import org.spring.groupAir.movie.repository.MovieDetailRepository;
import org.spring.groupAir.weather.dto.Main;
import org.spring.groupAir.weather.dto.WeatherDto;
import org.spring.groupAir.weather.entity.WeatherEntity;
import org.spring.groupAir.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.catalina.manager.StatusTransformer.formatTime;

@Service
public class KomoranService {

    @Autowired
    private Komoran komoran;

    @Autowired
    private ChatBotIntentionRepository intention;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private BusRepository busRepository;


    @Autowired
    private BoxOfficeRepository boxOfficeRepository;

    @Autowired
    private MovieDetailRepository movieDetailRepository;

    public MessageDto nlpAnalyze(String message) {
        KomoranResult result = komoran.analyze(message);

//        문자에서 명사들만 추출한 목록 중복제거해서 set
        Set<String> nouns = result.getNouns().stream()
                .collect(Collectors.toSet());

        return analyzeToken(nouns);
    }

    // 입력된 목적어를 하나씩 파악해 DB 에서 검색위해 decisionTree()메소드로 전달
    private MessageDto analyzeToken(Set<String> nouns) {

        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a H:mm");
        MessageDto messageDto = MessageDto.builder()
                .time(today.format(timeFormatter))
                .build();

        for (String token : nouns) {

            // 1차 -> 의도가 존재하는지 파악
            Optional<ChatBotIntentionEntity> result = decisionTree(token, null);

            if (result.isEmpty()) continue; // 존재하지않으면 다음 토큰 검색

            // 1차 토큰 확인 시 실행
            System.out.println("1차 >>>: " + token);
            // 1차 목록 복사
            Set<String> next = nouns.stream().collect(Collectors.toSet());
            // 목록에서 1차 토큰 제거
            next.remove(token);


            // 2차 -> 분석 메소드
            AnswerDto answer = analyzeToken(next, result).toAnswerDto();



            if (token.contains("전화")) {
                EmployeeInfo phone = analyzeTokenIsPhone(next);
                answer.employeeInfo(phone); // "전화" 일경우 전화데이터
            } else if (token.contains("안녕")) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
                messageDto.today(today.format(dateTimeFormatter)); // 처음 접속할 때만 날짜 표기
            } else if (token.contains("부서")) {
                EmployeeInfo dept = analyzeTokenIsDept(next);
                answer.employeeInfo(dept);
            } else if (token.contains("이메일")) {
                EmployeeInfo email = analyzeTokenIsEmail(next);
                answer.employeeInfo(email);
            } else if (token.contains("날씨")) {
                Main weather = analyzeTokenIsTemp(next);
                answer.weatherTemp(weather);
            } else if (token.contains("버스")) {
                ItemList bus = analyzeTokenIsBus(next);
                answer.busInfo(bus);
            } else if (token.contains("주간")) {
                WeeklyBoxOfficeList movie = analyzeTokenisMovie(next);
                answer.weeklyMovie(movie);
            } else if (token.contains("정보")) {
                MovieInfo movieInfo = analyzeTokenisMovieInfo(next);
                answer.weeklyMovieDetail(movieInfo);
            }

            messageDto.answerDto(answer); // 토큰에 대한 응답정보

            return messageDto;
        }
        // 분석 명사들이 등록한 의도와 일치하지 않을 경우 null
        AnswerDto answerDto = decisionTree("기타", null).get().getAnswerEntity().toAnswerDto();


        System.out.println("answerDto: " + answerDto);

        messageDto.answerDto(answerDto); // 토큰에 대한 응답정보
        return messageDto;
    }


    private ItemList analyzeTokenIsBus(Set<String> next) {
        //버스번호 조회 -> 아이템리스트
        for (String busRouteName : next) {
            System.out.println("2차 버스 >>>>: " + busRouteName);

            Optional<BusEntity> bus = busRepository.findByBusRouteNm(busRouteName);
            if (!bus.isPresent()) continue;
            String busRouteNm = bus.get().getBusRouteNm();
//          String firstBusTm = bus.get().getFirstBusTm();
//          String lastLowTm = bus.get().getLastLowTm();
            String firstBusTm = formatTime(bus.get().getFirstBusTm());
            String lastLowTm = formatTime(bus.get().getLastLowTm());

            String stStationNm = bus.get().getStStationNm();
            String edStationNm = bus.get().getEdStationNm();
            String term = bus.get().getTerm();
            String corpNm=bus.get().getCorpNm();

            ItemList itemList = new ItemList();
            itemList.setBusRouteNm(busRouteNm);
            itemList.setFirstBusTm(firstBusTm);
            itemList.setLastLowTm(lastLowTm);
            itemList.setStStationNm(stStationNm);
            itemList.setEdStationNm(edStationNm);
            itemList.setTerm(term);
            itemList.setCorpNm(corpNm);


            return itemList;


        }
        return null;
    }

  private String formatTime(String timeStr) {
    if(timeStr == null || timeStr.length() <14){
      return "";
    }
    String hours = timeStr.substring(8, 10);
    String minutes = timeStr.substring(10, 12);
    return hours + " : " + minutes + "";


  }

    //영화
    private WeeklyBoxOfficeList analyzeTokenisMovie(Set<String> next) {

        for (String name : next) {
            System.out.println("주간???" + name);
            Optional<BoxOfficeEntity> boxOffice = boxOfficeRepository.findByRank(name);
            if (!boxOffice.isPresent()) continue;

            String movieRank = boxOffice.get().getRank();
            String rankName = boxOffice.get().getMovieNm();


            return WeeklyBoxOfficeList.builder()
                    .movieNm(rankName)
                    .rank(movieRank)
                    .build();
        }
        System.out.println("영화정보 없음");
        return null;
    }

    //영화상세
    private MovieInfo analyzeTokenisMovieInfo(Set<String> next) {

        for (String name : next) {
            System.out.println("주간???" + name);
            Optional<MovieDetailEntity> movieInfo = movieDetailRepository.findByMovieNm(name);
            if (!movieInfo.isPresent()) continue;

            String infoName = movieInfo.get().getMovieNm();
            String infoPrdtStatNm = movieInfo.get().getPrdtStatNm();
            String infoOpenDate = movieInfo.get().getOpenDt();
            String infoShowTm = movieInfo.get().getShowTm();

            return MovieInfo.builder()
                    .movieNm(infoName)
                    .prdtStatNm(infoPrdtStatNm)
                    .openDt(infoOpenDate)
                    .showTm(infoShowTm)
                    .build();
        }
        System.out.println("영화정보 없음");
        return null;
    }

    private Main analyzeTokenIsTemp(Set<String> next) {

        for (String name : next) {

            System.out.println("2차 날씨 >>>>: " + name);
            Optional<WeatherEntity> weather = weatherRepository.findByName(name);
            if (!weather.isPresent()) continue;


//            String weatherName = weather.get().getName();
            String temp = weather.get().getTemp();
            String max = weather.get().getTemp_max();
            String min = weather.get().getTemp_min();

            Main main = new Main();
            main.setTemp(temp);
            main.setTemp_max(max);
            main.setTemp_min(min);

            return main;


        }
        System.out.println("날씨 온도정보X");
        return null;
    }

    // 날씨
    private WeatherDto analyzeTokenisWeather(Set<String> next) {

        for (String name : next) {

            System.out.println("2차 날씨 >>>>: " + name);
            Optional<WeatherEntity> weather = weatherRepository.findByName(name);
            if (!weather.isPresent()) continue;

            String weatherName = weather.get().getName();
            String temp = weather.get().getTemp();
            String max = weather.get().getTemp_max();
            String min = weather.get().getTemp_min();

            return WeatherDto.builder()
                    .name(weatherName)
                    .build();

        }

        System.out.println("날씨정보 X");
        return null;
    }

    private EmployeeInfo analyzeTokenIsEmail(Set<String> next) {

        for (String name : next) {

            System.out.println("2차 email >>>: " + name);
            Optional<MemberEntity> member = memberRepository.findByName(name);
            if (!member.isPresent()) continue;
            // 존재하면
            String memberName = member.get().getName();
            String email = member.get().getUserEmail();

            System.out.println("이메일 >>>: " + email);
            System.out.println("이름 >>>: " + memberName);

            return EmployeeInfo.builder()
                    .email(email)
                    .name(memberName)
                    .build();

        }
        System.out.println("이메일 못찾음");
        return null;
    }

    // "부서"를 물어볼 경우
    private EmployeeInfo analyzeTokenIsDept(Set<String> next) {

        for (String name : next) {

            System.out.println("2차 dept >>>: " + name);
            Optional<MemberEntity> member = memberRepository.findByName(name);
            if (!member.isPresent()) continue;
            // 존재하면
            String deptName = member.get().getDepartmentEntity().getDepartmentName();
            String memberName = member.get().getName();
            String email = member.get().getUserEmail();
            String topDeptName = member.get().getDepartmentEntity().getTopDepartmentEntity().getTopDepartmentName();

            System.out.println("부서이름 >>>: " + deptName);
            System.out.println("이름 >>>: " + memberName);

            return EmployeeInfo.builder().
                    deptName(deptName)
                    .topDeptName(topDeptName)
                    .name(memberName)
                    .build();

        }
        System.out.println("부서 못찾음");
        return null;
    }

    // "전화"를 물어볼 경우
    private EmployeeInfo analyzeTokenIsPhone(Set<String> next) {


        for (String name : next) {

            System.out.println("2차 phone >>>: " + name);

            Optional<MemberEntity> member = memberRepository.findByNameEquals(name);


            if (!member.isPresent()) {
                System.out.println("memberRepository 에러 >>>" + name);
                continue;
            }

            // 존재하면
            String deptName = member.get().getDepartmentEntity().getDepartmentName();
            String phone = member.get().getPhone();
            String memberName = member.get().getName();
            String email = member.get().getUserEmail();


            return EmployeeInfo.builder()
                    .phone(phone)
                    .name(memberName)
                    .build();
        }

        System.out.println("전화 못찾음");
        return null;
    }

    // 1차 의도가 존재하면
    // 하위 의도가 존재하는지 파악
    private AnswerEntity analyzeToken(Set<String> next, Optional<ChatBotIntentionEntity> upper) {

        for (String token : next) {
            // 1차 의도를 부모로 하는 토큰이 존재하는지 파악`
            Optional<ChatBotIntentionEntity> result = decisionTree(token, upper.get());

            System.out.println("analyzeToken: " + result);

            if (result.isEmpty()) continue;

            System.out.println("2차 >>>: " + token);
            return result.get().getAnswerEntity();
        }
        return upper.get().getAnswerEntity();
    }

    // 의도가 존재하는지 DB에서 파악
    private Optional<ChatBotIntentionEntity> decisionTree(String token, ChatBotIntentionEntity upper) {
        return intention.findByNameAndUpper(token, upper);
    }

}
