package org.spring.groupAir.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.weather.dto.WList;
import org.spring.groupAir.weather.dto.WeatherDto;
import org.spring.groupAir.weather.dto.WeatherListDto;
import org.spring.groupAir.weather.entity.WeatherEntity;
import org.spring.groupAir.weather.repository.WeatherRepository;
import org.spring.groupAir.weather.service.Impl.WeatherImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService implements WeatherImpl {


    private final WeatherRepository weatherRepository;

    @Override
    public void insertDB(String response) {

        // java 클래스로 변환해주는 클래스
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("objectMapper: ===>" + objectMapper);

        WeatherDto weatherDto = null;

        try {
            weatherDto = objectMapper.readValue(response, WeatherDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("weatherDto : ===>" + weatherDto);

        // 한국어로 name 집어넣기
        String city = null;

        if (weatherDto.getName().equals("Seoul")) {
            city = "서울";
        } else if (weatherDto.getName().equals("Gwangju")) {
            city = "광주";
        } else if (weatherDto.getName().equals("Busan")) {
            city = "부산";
        } else if (weatherDto.getName().equals("Chuncheon")) {
            city = "춘천";
        } else {
            city = weatherDto.getName();
        }

        WeatherEntity weather = WeatherEntity
                .builder()
                .name(city)
                .lat(weatherDto.getCoord().getLat())
                .lon(weatherDto.getCoord().getLon())
                .temp(weatherDto.getMain().getTemp())
                .temp_min(weatherDto.getMain().getTemp_min())
                .temp_max(weatherDto.getMain().getTemp_max())
                .build();


        Optional<WeatherEntity> optionalWeatherEntity = weatherRepository.findByName(weather.getName());

        if (!optionalWeatherEntity.isPresent()) {
            weatherRepository.save(weather);
        } else {
            System.out.println("없음");
        }

    }

    @Override
    public void insertDBList(String response) {


        // java 클래스로 변환해주는 클래스
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("objectMapper: ===>" + objectMapper);

        WeatherListDto weatherListDto = null;

        try {
            weatherListDto = objectMapper.readValue(response, WeatherListDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("weatherDto : ===>" + weatherListDto);

        // 한국어로 name 집어넣기
        String city = null;
        // 1. if 문 사용
//        if (weatherListDto.getCity().getName().equals("Seoul")) {
//            city = "서울";
//        } else if (weatherListDto.getCity().getName().equals("Gwangju")) {
//            city = "광주";
//        } else if (weatherListDto.getCity().getName().equals("Busan")) {
//            city = "부산";
//        } else if (weatherListDto.getCity().getName().equals("Chuncheon")) {
//            city = "춘천";
//        } else {
//            city = weatherListDto.getCity().getName();
//        }
        
        // 2. switch 사용
        switch (weatherListDto.getCity().getName()) {
            case "Seoul":
                city = "서울";
                break;
            case "Gwangju":
                city = "광주";
                break;
            case "Busan":
                city = "부산";
                break;
            case "Chuncheon":
                city = "춘천";
                break;
            case "Incheon":
                city = "인천국제공항";
                break;
            case "Bucheon-si":
                city = "김포국제공항";
                break;
            case "Seolman":
                city = "김해국제공항";
                break;
            case "Jeju City":
                city = "제주국제공항";
                break;
            case "Ipseokdong":
                city = "대구국제공항";
                break;
            case "Ipyang-ni":
                city = "청주국제공항";
                break;
            case "Muan":
                city = "무안국제공항";
                break;
            case "Jangjolli":
                city = "양양국제공항";
                break;
            case "Yach’on":
                city = "광주공항";
                break;
            case "Sŏnyŏl-li":
                city = "군산공항";
                break;
            case "Shisen":
                city = "사천공항";
                break;
            case "Sangok":
                city = "여수공항";
                break;
            case "Songjeong":
                city = "울산공항";
                break;
            case "Hup’yŏng":
                city = "원주공항";
                break;
            case "Ch’ŏngnim":
                city = "포항경주공항 ";
                break;
            default:
                city = weatherListDto.getCity().getName();
        }


        if (weatherListDto.getList() != null) {
            for (WList list : weatherListDto.getList()) {
                WeatherEntity weather = WeatherEntity.builder()
                        .name(city)
                        .lat(weatherListDto.getCity().getCoord().getLat())
                        .lon(weatherListDto.getCity().getCoord().getLon())
                        .temp(list.getMain().getTemp())
                        .temp_min(list.getMain().getTemp_min())
                        .temp_max(list.getMain().getTemp_max())
                        .build();


                Optional<WeatherEntity> optionalWeatherEntity = weatherRepository.findByName(weather.getName());

                if (!optionalWeatherEntity.isPresent()) {
                    weatherRepository.save(weather);
                } else {
                    System.out.println("없음");
                }

            }
        }

    }
}
