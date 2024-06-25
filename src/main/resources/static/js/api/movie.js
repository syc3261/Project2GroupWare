//영화목록 조회


function movieFn() {
    $.ajax({
        url: '/api/movieList',
        method: 'GET',
        success: function(result) {
            $('.menuBtn2').css('display', 'none');
            $('.menuBtn').css('display', 'none');
            $('.movie_table').css('display', 'block');
             $('#grid1').css('display', 'none');
             $('#grid').css('display', 'none');
            const movieData = JSON.parse(result.movie); // JSON 데이터 파싱
            createMovieTable(movieData); // 테이블 생성 함수 호출
        }
    });
}

function createMovieTable(movieData) {
 //목록
 const tableBody1 = $('#movie_table tbody');
     tableBody1.empty(); // 이전 데이터를 지웁니다.


 //주말

     const tableContainer2 = $('#boxOffice_table tbody');
     const gridContainer2 = $('#grid .span-on');
     tableContainer2.empty();
     gridContainer2.empty();



 //금일
 const tableContainer = $('#box_movie_table tbody');
     const gridContainer = $('#grid2 .span-on');
        tableContainer.empty();
         gridContainer.empty();

    $('.boxOffice_table, .box_movie_table').css('display', 'none'); // 테이블 숨기기


    movieData.movieListResult.movieList.forEach(movie => {
        // 각 영화의 회사 정보에 대해 반복합니다.
        movie.companys.forEach(company => {
            const row = `
                <tr>
                    <td>${movie.movieCd}</td>
                    <td class="movieNm-cell">${movie.movieNm}</td>
                    <td>${movie.movieNmEn}</td>
                    <td>${movie.prdtYear}</td>
                    <td>${movie.repGenreNm}</td>
                    <td>${movie.repNationNm}</td>
                    <td>${movie.typeNm}</td>
                    <td>${company.companyCd}</td>
                    <td>${company.companyNm}</td>
                </tr>
            `;
            tableBody1.append(row);
        });
    });
}

// 영화 이름 클릭 시 영화 코드를 가져옴
$('#movie_table').on('click', '.movieNm-cell', function() {
    var movieCd = $(this).prev().text(); // 바로 앞에 있는 td의 텍스트를 가져옵니다.
    // movieCd 값을 이용하여 원하는 동작을 수행합니다.
    movieDetailFn(movieCd); // 함수 호출
    // 추가 작업 수행
});

//모달창으로 상세조회
function movieDetailFn(movieCd) {
    let appUrl = `/api/movieListId?movieCd=${movieCd}`;

    $.ajax({
        url: appUrl,
        type: "GET",
        dataType: 'json',
        data: { movieCode: movieCd },
        success: function(response) {

         console.log(response);
            const movieDetail = JSON.parse(response.movie);
            const movieNm = movieDetail.movieInfoResult.movieInfo.movieNm;
            const movieNmEn = movieDetail.movieInfoResult.movieInfo.movieNmEn;
            const openDt = movieDetail.movieInfoResult.movieInfo.openDt;
            const prdtStatNm = movieDetail.movieInfoResult.movieInfo.prdtStatNm;

            $('.detailModal').css('display', 'block');
            $('.kr').text(movieNm);
            $('.en').text(movieNmEn);
            $('.open').text(openDt);
            const openStatus = (prdtStatNm === '개봉') ? 'O' : 'X';
            $('.open1').text(openStatus);
        },
    });
}
