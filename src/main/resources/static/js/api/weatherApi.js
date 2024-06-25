const weatherDescriptionsApi = {
    200: "가벼운 비를 동반한 천둥구름",
    201: "비를 동반한 천둥구름",
    202: "폭우를 동반한 천둥구름",
    210: "약한 천둥구름",
    211: "천둥구름",
    212: "강한 천둥구름",
    221: "불규칙적 천둥구름",
    230: "약한 연무를 동반한 천둥구름",
    231: "연무를 동반한 천둥구름",
    232: "강한 안개비를 동반한 천둥구름",
    300: "가벼운 안개비",
    301: "안개비",
    302: "강한 안개비",
    310: "가벼운 적은비",
    311: "적은비",
    312: "강한 적은비",
    313: "소나기와 안개비",
    314: "강한 소나기와 안개비",
    321: "소나기",
    500: "악한 비",
    501: "중간 비",
    502: "강한 비",
    503: "매우 강한 비",
    504: "극심한 비",
    511: "우박",
    520: "약한 소나기 비",
    521: "소나기 비",
    522: "강한 소나기 비",
    531: "불규칙적 소나기 비",
    600: "가벼운 눈",
    601: "눈",
    602: "강한 눈",
    611: "진눈깨비",
    612: "소나기 진눈깨비",
    615: "약한 비와 눈",
    616: "비와 눈",
    620: "약한 소나기 눈",
    621: "소나기 눈",
    622: "강한 소나기 눈",
    701: "박무",
    711: "연기",
    721: "연무",
    731: "모래 먼지",
    741: "안개",
    751: "모래",
    761: "먼지",
    762: "화산재",
    771: "돌풍",
    781: "토네이도",
    800: "구름 한 점 없는 맑은 하늘",
    801: "약간의 구름이 낀 하늘",
    802: "드문드문 구름이 낀 하늘",
    803: "구름이 거의 없는 하늘",
    804: "구름으로 뒤덮인 흐린 하늘",
    900: "토네이도",
    901: "태풍",
    902: "허리케인",
    903: "한랭",
    904: "고온",
    905: "바람부는",
    906: "우박",
    951: "바람이 거의 없는",
    952: "약한 바람",
    953: "부드러운 바람",
    954: "중간 세기 바람",
    955: "신선한 바람",
    956: "센 바람",
    957: "돌풍에 가까운 센 바람",
    958: "돌풍",
    959: "심각한 돌풍",
    960: "폭풍",
    961: "강한 폭풍",
    962: "허리케인"
};


// ajax 사용해 openWeather 정보 가져오기

function weatherApi(city){

// `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=745e1514332e9aa9afb7b3c1507c7cfc&lang=kr`

const appUrl = `/weather?q=${city}`;

    $.ajax({
        url: appUrl,
        dataType: 'json',
        type: 'GET',
        success: function(result){

     // 현재 시간
             function nowTimeApi(){
                    var nowApi = new Date();

                    var yearApi = nowApi.getFullYear();
                    var monthApi = nowApi.getMonth() + 1;
                    var dayApi = nowApi.getDate();
                    var hoursApi = nowApi.getHours();
                    var minutesApi = nowApi.getMinutes();
                    var daysApi = ["일", "월", "화", "수", "목", "금", "토"];
                    var dayNameApi = daysApi[nowApi.getDay()];

                    var currentTimeApi = `${yearApi}.${monthApi}.${dayApi}(${dayNameApi}) ${hoursApi}시 ${minutesApi}분`;
                    return currentTimeApi;
                }

                function updateTimeApi() {
                    var currentTimeApi = nowTimeApi(); // currentTime 변수를 함수 내에서 선언
                    $('.nowTimeApi').text(`${currentTimeApi}`); // 시간추가
                }

                updateTimeApi(); // 초기에 한 번 실행

                setInterval(updateTimeApi, 1000); // 1초마다 업데이트


            // WController 에서 받은 weather 를
            console.log(result.weather);
            // json 형식으로 변경
            const weather = JSON.parse(result.weather);

            // kakaoMap 에 marker 찍기
            map(weather.coord.lat, weather.coord.lon);

            const cityN = weather.name;
            const nowApi = Math.round(weather.main.temp * 10) / 10;
            const minApi =  Math.round(weather.main.temp_min * 10) / 10;
            const maxApi =  Math.round(weather.main.temp_max * 10) / 10;
            const nowWeatherApi = weather.weather[0].main;


          $('.SeoulNowTempApi').text(`${nowApi}°C`);
          $('.SeoulTempMaxApi').text(`최고기온: ${maxApi}°C`);
          $('.SeoulTempMinApi').text(`최저기온: ${minApi}°C`);
          $('.airTitle').text(`${cityN} 날씨`)

          // 날씨 설명
          var weatherCodeApi = weather.weather[0].id;
          var weatherDescriptionApi = weatherDescriptions[weatherCodeApi] || "기타";

      $('.nowWeatherApi').text(weatherDescriptionApi);

      var weatherImgApi =
     '<img src="http://openweathermap.org/img/wn/'
     + weather.weather[0].icon + '.png" alt="'+ weather.weather[0].description +'"/>';

     $('.SeoulIconApi').html(weatherImgApi);
        }
    });
}

function map(lat,lon){


var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

// 마커가 표시될 위치입니다
var markerPosition  = new kakao.maps.LatLng(lat, lon);

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
    position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);

// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
// marker.setMap(null);

}

function weatherSearchApi(){
    const search = document.querySelector('#searchApi').value;
    weatherApi(search);
}


// 즉시 실행 함수
(()=>{
    weatherApi("Seoul");
})();