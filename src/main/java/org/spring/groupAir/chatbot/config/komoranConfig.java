package org.spring.groupAir.chatbot.config;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.spring.groupAir.bus.repository.BusRepository;
import org.spring.groupAir.department.repository.DepartmentRepository;
import org.spring.groupAir.member.repository.MemberRepository;
import org.spring.groupAir.movie.repository.BoxMovieRepository;
import org.spring.groupAir.movie.repository.BoxOfficeRepository;
import org.spring.groupAir.movie.repository.MovieDetailRepository;
import org.spring.groupAir.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class komoranConfig {

    private String USER_DIC = "user.dic";

    @Autowired
    DepartmentRepository department; // 부서이름

    @Autowired
    MemberRepository member; // 사원이름

    @Autowired
    WeatherRepository weather;

    @Autowired
    BoxOfficeRepository boxOffice;

    @Autowired
    MovieDetailRepository movieInfo;

    @Autowired
    BoxMovieRepository rank;

    @Autowired
    BusRepository bus;


    @Bean
    Komoran komoran() {
        userDic();
        Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
        komoran.setUserDic(USER_DIC);
        return komoran;
    }

    //    부서테이블,(부서명), 멤버테이블(멤버명)
    private void userDic() {

        Set<String> keys = new HashSet<>();

        // 기존 수동 등록 파일에서 고유 명사만 추출
        try {
            File file = new File(USER_DIC);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String data = null;
                while ((data = br.readLine()) != null) {
                    if (data.startsWith("#")) // 주석이 있으면 주석 제거
                        continue;
                    String[] str = data.split("\\t"); // tab 영역만큼 간격 설정 (이이름 NNP)
                    keys.add(str[0]); // str 에 저장
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //////////////////////////////////////////

        // 부서명을 set 에 저장
        department.findAll().forEach(e -> {
            keys.add(e.getDepartmentName());
        });
        // 사원명을 set 에 저장
        member.findAll().forEach(e -> {
            keys.add(e.getName());
        });
        // 이메일을 set 에 저장
        member.findAll().forEach(e -> {
            keys.add(e.getUserEmail());
        });

        weather.findAll().forEach(e -> {
            keys.add(e.getName());
        });

        boxOffice.findAll().forEach(e -> {
            keys.add(e.getRank());
        });

        rank.findAll().forEach(e -> {
            keys.add(e.getRank());
        });

        movieInfo.findAll().forEach(e -> {
            keys.add(e.getMovieNm());
        });
        bus.findAll().forEach(e -> {
            keys.add(e.getBusRouteNm());

        });


        // Set 에 저장된 명단을 고유명사로 파일에 등록
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(USER_DIC));
            keys.forEach(key -> {
                try {
                    bw.write(key + "\tNNP\n");
                    // 이이름 NNP
//                    System.out.println(key);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            bw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }


}
