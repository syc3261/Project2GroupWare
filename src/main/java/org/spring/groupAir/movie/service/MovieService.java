package org.spring.groupAir.movie.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.spring.groupAir.movie.dto.*;
import org.spring.groupAir.movie.entity.*;
import org.spring.groupAir.movie.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieDetailRepository movieDetailRepository;
    private final BoxOfficeRepository boxOfficeRepository;
    private final BoxMovieRepository boxMovieRepository;
    private final MoviePosterRepository moviePosterRepository;
    private final WeeklyMoviePosterRepository weeklyMoviePosterRepository;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String tmdbApiKey = "356502e3eb5f699db857bec83e765cda";

    public void insertResposeBody(String responseBody) {

        ObjectMapper objectMapper = new ObjectMapper();

        MovieListResponse response = null;
        try {
            // json 문자열데이터를 -> 클래스에 매핑
            response = objectMapper.readValue(responseBody, MovieListResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<MovieList> list = response.getMovieListResult().getMovieList()
            .stream()
            .collect(Collectors.toList());// return List

        AtomicReference<String> companyCd = new AtomicReference<>("");
        AtomicReference<String> companyNm = new AtomicReference<>("");

        //DB 테이블 필드에 맞추어 설정
        for (MovieList el : list) {
            el.getCompanys().stream().forEach(el2 -> {
                companyCd.set(el2.getCompanyCd());
                companyCd.set(el2.getCompanyNm());
            });

            Optional<MovieEntity> optionalMovieEntity = movieRepository.findByMovieCd(el.getMovieCd());

            if (!optionalMovieEntity.isPresent()) {
                movieRepository.save(MovieEntity.builder()
                    .movieCd(el.getMovieCd())
                    .movieNm(el.getMovieNm())
                    .movieNmEn(el.getMovieNmEn())
                    .prdtYear(el.getPrdtYear())
                    .repNationNm(el.getRepNationNm())
                    .repNationNm(el.getRepNationNm())
                    .typeNm(el.getTypeNm())
                    .getCompanyCd(companyCd.toString())
                    .getCompanyNm(companyCd.toString())
                    .build());
            }
        }
    }

    public void movieInfoResultResponseBody(String responseBody) {

        ObjectMapper objectMapper = new ObjectMapper();

        MovieDetailDto response = null;
        try {
            // json 문자열데이터를 -> 클래스에 매핑
            response = objectMapper.readValue(responseBody, MovieDetailDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Optional<MovieDetailEntity> optionalMovieDetailEntity
            = movieDetailRepository.findByMovieNm(response.getMovieInfoResult().getMovieInfo().getMovieNm());

        if (!optionalMovieDetailEntity.isPresent()) {

            MovieDetailEntity movieDetailEntity = MovieDetailEntity.builder()
                .movieCd(response.getMovieInfoResult().getMovieInfo().getMovieCd())
                .movieNm(response.getMovieInfoResult().getMovieInfo().getMovieNm())
                .movieNmEn(response.getMovieInfoResult().getMovieInfo().getMovieNmEn())
                .openDt(response.getMovieInfoResult().getMovieInfo().getOpenDt())
                .prdtStatNm(response.getMovieInfoResult().getMovieInfo().getPrdtStatNm())
                .prdtYear(response.getMovieInfoResult().getMovieInfo().getPrdtYear())
                .showTm(response.getMovieInfoResult().getMovieInfo().getShowTm())
                .typeNm(response.getMovieInfoResult().getMovieInfo().getTypeNm())
                .source(response.getMovieInfoResult().getSource())
                .build();
            movieDetailRepository.save(movieDetailEntity);
        }
        System.out.println(" <<  response " + response);
    }

    /////////////////////////////////////////////////////////////////////
    public void insertBoxOfficeResponseBody(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        BoxOfficeDto response = null;
        try {
            // json 문자열데이터를 -> 클래스에 매핑
            response = objectMapper.readValue(responseBody, BoxOfficeDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<WeeklyBoxOfficeList> list = response.getBoxOfficeResult().getWeeklyBoxOfficeList()
            .stream()
            .collect(Collectors.toList());

        for (WeeklyBoxOfficeList el : list) {
            Optional<BoxOfficeEntity> optionalBoxOfficeEntity = boxOfficeRepository.findByMovieCd(el.getMovieCd());

            if (!optionalBoxOfficeEntity.isPresent()) {
                boxOfficeRepository.save(BoxOfficeEntity.builder()
                    .movieCd(el.getMovieCd())
                    .movieNm(el.getMovieNm())
                    .rank(el.getRank())
                    .audiCnt(el.getAudiCnt())
                    .rankInten(el.getRankInten())
                    .rankOldAndNew(el.getRankOldAndNew())
                    .salesAcc(el.getSalesAcc())
                    .build());
            }
        }
    }

    public void boxinsertResposeBody(String responseBody) {

        boxOfficeRepository.deleteAll();

        ObjectMapper objectMapper = new ObjectMapper();

        BoxMovieDto response = null;
        try {
            response = objectMapper.readValue(responseBody, BoxMovieDto.class);
        } catch (IOException e) {
            throw new RuntimeException("응답 데이터를 매핑하는 도중 오류가 발생했습니다.", e);
        }
        List<DailyBoxOfficeList> dailyBoxOfficeList = response.getBoxOfficeResult().getDailyBoxOfficeList();

        for (DailyBoxOfficeList item : dailyBoxOfficeList) {
            String rank = item.getRank();
            // 다른 필드들을 가져와서 처리하는 예시:
            // String movieName = item.getMovieName();
            // int salesAmt = item.getSalesAmt();

            // 이제 각 영화의 정보를 사용하여 원하는 작업을 수행할 수 있습니다.
            System.out.println("Rank: " + rank);
            Optional<BoxMovieEntity> optionalBoxMovieEntity = boxMovieRepository.findByMovieCd(item.getMovieNm());

            if (!optionalBoxMovieEntity.isPresent()) {
                boxMovieRepository.save(BoxMovieEntity.builder()

                    .movieCd(item.getMovieCd())
                    .movieNm(item.getMovieNm())
                    .openDt(item.getOpenDt())
                    .salesAmt(item.getSalesAmt())
                    .audiAcc(item.getAudiAcc())
                    .build());
            }

        }
    }


    public String getMoviePoster(String movieTitle, String releaseDate , String movieCd, String movieNm, String openDt, String salesAmt, String audiAcc,String rank) throws IOException, URISyntaxException, InterruptedException {
        String year = releaseDate.split("-")[0];
        String encodedMovieTitle = URLEncoder.encode(movieTitle, "UTF-8");
        String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + tmdbApiKey + "&query=" + encodedMovieTitle + "&year=" + year;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(searchUrl))
            .GET()
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String searchResponse = response.body();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode searchResult = mapper.readTree(searchResponse);
        JsonNode results = searchResult.path("results");

        if (results.isArray() && !results.isEmpty()) {
            String posterPath = results.get(0).path("poster_path").asText();
            String posterUrlAdd="https://image.tmdb.org/t/p/w200" + posterPath;

            System.out.println("하나 for문으로 서비스 10번실행됨"+posterUrlAdd);

            MoviePosterEntity moviePosterMatch = new MoviePosterEntity();
            moviePosterMatch.setRank(rank);
            moviePosterMatch.setMovieCd(movieCd);
            moviePosterMatch.setMovieNm(movieNm);
            moviePosterMatch.setOpenDt(openDt);
            moviePosterMatch.setSalesAmt(salesAmt);
            moviePosterMatch.setAudiAcc(audiAcc);
            moviePosterMatch.setMovieTitle(movieTitle);
            moviePosterMatch.setReleaseDate(releaseDate);
            moviePosterMatch.setPosterUrl(posterUrlAdd);
            moviePosterRepository.save(moviePosterMatch);

            return posterUrlAdd;
        }
        return null;
    }


    //주간주간
    public String getMovieWeeklyPoster(String movieTitle, String releaseDate, String rank,String movieCd, String movieNm, String rankInten, String audiCnt, String salesAcc, String openDt,String rankOldAndNew) throws IOException, InterruptedException, URISyntaxException {
        String year = releaseDate.split("-")[0];
        String encodedMovieTitle = URLEncoder.encode(movieTitle, "UTF-8");
        String searchUrl = "https://api.themoviedb.org/3/search/movie?api_key=" + tmdbApiKey + "&query=" + encodedMovieTitle + "&year=" + year;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(searchUrl))
            .GET()
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String searchResponse = response.body();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode searchResult = mapper.readTree(searchResponse);
        JsonNode results = searchResult.path("results");

        if (results.isArray() && !results.isEmpty()) {
            String posterPath = results.get(0).path("poster_path").asText();
            String posterUrlAdd="https://image.tmdb.org/t/p/w200" + posterPath;

            System.out.println("하나 for문으로 서비스 10번실행됨"+posterUrlAdd);

            WeeklyMoviePosterEntity moviePosterMatch = new WeeklyMoviePosterEntity();
            moviePosterMatch.setRank(rank);
            moviePosterMatch.setMovieCd(movieCd);
            moviePosterMatch.setMovieNm(movieNm);
            moviePosterMatch.setRankInten(rankInten);
            moviePosterMatch.setOpenDt(openDt);
            moviePosterMatch.setRankOldAndNew(rankOldAndNew);
            moviePosterMatch.setAudiCnt(audiCnt);
            moviePosterMatch.setSalesAcc(salesAcc);
            moviePosterMatch.setPosterUrl(posterUrlAdd);
            moviePosterMatch.setMovieTitle(movieNm);
            moviePosterMatch.setReleaseDate(openDt);
            weeklyMoviePosterRepository.save(moviePosterMatch);

            return posterUrlAdd;
        }
        return null;

    }
}



