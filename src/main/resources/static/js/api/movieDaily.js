

///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////daily////////////////////////////////////////////////////////

//오늘의 박스오피스 순위
function menu2(){ // 테이블 -> 그리드 숨김
    boxMovieFn();
 $('.grid').css('display', 'none');
}

function menu1(){ // 그리드 -> 테이블 숨김
 $('.grid').css('display', 'block');
  $('.box_movie_table').css('display', 'none');
}

function boxMovieFn() {
    $.ajax({
        url: '/api/boxMovieList',
        method: 'GET',
        success: function(response) {
             $('.daily').css('display', 'block');
             $('#grid1').css('display', 'none');

             $('.box_movie_table').css('display', 'block');
             $('.menuBtn2').css('display', 'none');
             $('.menuBtn').css('display', 'flex');
            console.log("서버 응답:", response);

            try {
              const dataString = response.toString(); // 데이터를 문자열로 변환
              console.log("dataString:", dataString);

              const items = dataString.split(',');

              const movieData = [];
              for (let i = 0; i < items.length; i += 9) {
                const movie = {
                   rank: items[i].trim(),            // 영화 코드
                   movieCd: items[i+ 1].trim(),            // 영화 코드
                   movieNm: items[i + 2].trim(),        // 영화 이름
                   openDt: items[i + 3].trim(),         // 개봉일
                   salesAmt: items[i + 4].trim(),       // 매출액
                   audiAcc: items[i + 5].trim(),        // 누적 관객수
                   originalTitle: items[i + 6].trim(),  // 원제
                   originalReleaseDate: items[i + 7].trim(), // 원 개봉일
                   posterUrl: items[i + 8].trim()       // 포스터 URL
                };
                movieData.push(movie);
              }

              console.log("박스오피스 데이터:", movieData);

                createBoxMovieTable(movieData); // 테이블 생성 함수 호출
            } catch (error) {
                console.error('데이터 처리 중 오류 발생:', error);
            }
        },
        error: function(xhr, status, error) {
            console.error('AJAX 요청 에러:', status, error);
        }
    });
}

// 테이블에 오늘의 박스오피스 순위를 표시하는 함수
function createBoxMovieTable(movieData) {

    //목록
    const tableBody1 = $('#movie_table tbody');
    tableBody1.empty(); // 이전 데이터를 지웁니다.


    //주말
    const tableContainer2 = $('#boxOffice_table tbody');
    const gridContainer2 = $('#grid1 .span-on');
    tableContainer2.empty();
    gridContainer2.empty();

    //금일
    const tableContainer = $('#box_movie_table tbody');
    const gridContainer = $('#grid .span-on');
    tableContainer.empty();
    gridContainer.empty();
   $('.movie_table, .boxOffice_table').css('display', 'none'); // 테이블 숨기기
  movieData.forEach(movie => {
        const tableRow = `
            <tr>
                <td>${movie.rank || 'N/A'}</td>
                <td>${movie.movieCd || 'N/A'}</td>
                <td>${movie.movieNm || 'N/A'}</td>
                <td>${movie.openDt || 'N/A'}</td>
                <td>${movie.salesAmt || 'N/A'}</td>
                <td>${movie.audiAcc || 'N/A'}</td>
            </tr>
        `;
        tableContainer.append(tableRow);

        // 영화 데이터를 그리드에 추가 (포스터 추가)
        const gridItem = `
                <div><img src="${movie.posterUrl || 'N/A'}" alt="포스터"></div>
        `;
        gridContainer.append(gridItem);
    });
}


$('#box_movie_table').on('click', '.movieNm-cell', function() {
    var movieCd = $(this).prev().text(); // 바로 앞에 있는 td의 텍스트를 가져옵니다.
    // movieCd 값을 이용하여 원하는 동작을 수행합니다.
    movieDetailFn(movieCd); // 함수 호출
    // 추가 작업 수행
});



