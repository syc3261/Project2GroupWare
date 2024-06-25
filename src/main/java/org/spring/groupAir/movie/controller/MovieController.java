package org.spring.groupAir.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.movie.entity.MoviePosterEntity;
import org.spring.groupAir.movie.repository.MoviePosterRepository;
import org.spring.groupAir.movie.repository.WeeklyMoviePosterRepository;
import org.spring.groupAir.movie.service.MovieService;
import org.spring.groupAir.movie.util.OpenApiUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovieController {


    private final MovieService movieService;
    private final String key = "0785aff285175d606336f19338ad1fe1";
    private final MoviePosterRepository moviePosterRepository;
    private final WeeklyMoviePosterRepository weeklyMoviePosterRepository;


    @GetMapping("/movieList")
    public Map<String, String> movieList() throws JsonProcessingException {
        // 영화 -> 공공 데이터 포털
        String openStartDt = "&openStartDt=" + LocalDate.now().getYear();
        ; // 개봉년도
//        String key = "0785aff285175d606336f19338ad1fe1";      // key 앞에 <?key=>를 붙여야함 : prameter
        String itemPerPage = "&itemPerPage=20";  //20개를 가져올것이라서
        String movieSerchJSON = "movie/searchMovieList.json";
        String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/" + movieSerchJSON + "?key=" + key + itemPerPage + openStartDt;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        String responseBody = OpenApiUtil.get(apiURL, requestHeaders);

        // JSON ->  DB
        movieService.insertResposeBody(responseBody);

        Map<String, String> movie = new HashMap<>();
        movie.put("movie", responseBody);

        return movie;
    }



    //주간 박스오피스////////////////////////////////////////////////////////
    @GetMapping("/boxOffice")
    public List<String> boxOffice() throws IOException, InterruptedException, URISyntaxException {

        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.minusDays(7); // 7일 전 날짜 계산
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = targetDate.format(formatter);

//        String key = "0785aff285175d606336f19338ad1fe1";      // key 앞에 <?key=>를 붙여야함 : prameter
        String movieSerchJSON = "boxoffice/searchWeeklyBoxOfficeList.json";
        String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/" + movieSerchJSON + "?key=" + key + "&targetDt=" + targetDt;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        String responseBody = OpenApiUtil.get(apiURL, requestHeaders);

        movieService.insertBoxOfficeResponseBody(responseBody);

        Map<String, String> movie = new HashMap<>();
        movie.put("movie", responseBody);
//------------------------------------------------

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode weeklyBoxOfficeList = rootNode.path("boxOfficeResult").path("weeklyBoxOfficeList");
        // JSON 구조에서 boxOfficeResult 안에 있는 weeklyBoxOfficeList 찾고 있다

        List<String> posterUrls2 = new ArrayList<>();

        weeklyMoviePosterRepository.deleteAll();

        for (JsonNode movieNode : weeklyBoxOfficeList) {
            String rank = movieNode.path("rank").asText();
            String movieCd = movieNode.path("movieCd").asText();
            String movieNm = movieNode.path("movieNm").asText();
            String rankInten = movieNode.path("rankInten").asText();
            String rankOldAndNew = movieNode.path("rankOldAndNew").asText();
            String audiCnt = movieNode.path("audiCnt").asText();
            String salesAcc = movieNode.path("salesAcc").asText();
            String movieTitle = movieNode.path("movieNm").asText();
            String openDt = movieNode.path("openDt").asText();
            String releaseDate = movieNode.path("openDt").asText();

            String posterUrl = movieService.getMovieWeeklyPoster(movieTitle, releaseDate, rank,movieCd,rankOldAndNew,movieNm,rankInten,audiCnt,salesAcc,openDt);


            System.out.println();
            if (posterUrl != null) {
                posterUrls2.add(rank);
                posterUrls2.add(movieCd);
                posterUrls2.add(movieNm);
                posterUrls2.add(rankInten);
                posterUrls2.add(rankOldAndNew);
                posterUrls2.add(audiCnt);
                posterUrls2.add(salesAcc);
                posterUrls2.add(openDt);
                posterUrls2.add(releaseDate);
                posterUrls2.add(movieTitle);
                posterUrls2.add(posterUrl);

            }
        }
        return posterUrls2;

    }

    //상세+++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @GetMapping("/movieListId")
    public Map<String, String> movieListId(@RequestParam(required = false) String movieCode) {
        // 임의의 영화 코드
        String mCode = null;
        try {
            // movieCode가 null이 아니라면 해당 값을 사용하여 mCode 설정
            if (movieCode != null) {
                mCode = URLEncoder.encode(movieCode, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }
        // 영화
        String key = "0785aff285175d606336f19338ad1fe1"; //
        String movieSerchJSON = "movie/searchMovieInfo.json";
        String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/" + movieSerchJSON + "?key=" + key + "&movieCd=" + mCode;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        String responseBody = OpenApiUtil.get(apiURL, requestHeaders);

        // JSON ->  DB
        movieService.movieInfoResultResponseBody(responseBody);

        Map<String, String> movie = new HashMap<>();
        movie.put("movie", responseBody);


        return movie;
    }

    //오늘의 박스오피스++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @GetMapping("/boxMovieList")
    public List<String> boxMovieList(Model model) throws Exception {
        // 영화 -> 공공 데이터 포털
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.minusDays(1); // 1일 전 날짜 계산
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = targetDate.format(formatter);

        String DailyBoxOfficeJSON = "boxoffice/searchDailyBoxOfficeList.json";
        String apiURL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/" + DailyBoxOfficeJSON + "?key=" + key + "&targetDt=" + targetDt;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-type", "application/json");

        String responseBody = OpenApiUtil.get(apiURL, requestHeaders);

        // JSON ->  DB
        movieService.boxinsertResposeBody(responseBody);

//------------------------------------------------

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode dailyBoxOfficeList = rootNode.path("boxOfficeResult").path("dailyBoxOfficeList");
        // JSON 구조에서 boxOfficeResult 안에 있는 dailyBoxOfficeList를 찾고 있다

        List<String> posterUrls = new ArrayList<>();

        moviePosterRepository.deleteAll();

        for (JsonNode movieNode : dailyBoxOfficeList) {
            String rank = movieNode.path("rank").asText();
            String movieCd = movieNode.path("movieCd").asText();
            String movieNm = movieNode.path("movieNm").asText();
            String openDt = movieNode.path("openDt").asText();
            String salesAmt = movieNode.path("salesAmt").asText();
            String audiAcc = movieNode.path("audiAcc").asText();
            String movieTitle = movieNode.path("movieNm").asText();
            String releaseDate = movieNode.path("openDt").asText();
            String posterUrl = movieService.getMoviePoster(movieTitle, releaseDate,movieCd,movieNm,openDt,salesAmt,audiAcc,rank);



            if (posterUrl != null) {
                posterUrls.add(rank);
                posterUrls.add(movieCd);
                posterUrls.add(movieNm);
                posterUrls.add(openDt);
                posterUrls.add(salesAmt);
                posterUrls.add(audiAcc);
                posterUrls.add(movieTitle);
                posterUrls.add(releaseDate);
                posterUrls.add(posterUrl);

            }
        }
        return posterUrls;
    }

}