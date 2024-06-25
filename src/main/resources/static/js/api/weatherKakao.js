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

            // 공항 정보 배열
            const airports = [
                {
                content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">인천국제공항</div>',
                name: "Incheon",
                 lat: 37.45869, lon: 126.4419 },
                {
                content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">김포국제공항</div>',
                name: "Bucheon-si",
                 lat: 37.56557, lon: 126.8013 },
                {
                content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">김해국제공항</div>',
                name: "Seolman",
                 lat: 35.17290, lon: 128.9472 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">제주국제공항</div>',
                 name: "Jeju City",
                 lat: 33.50710, lon: 126.4934 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">대구국제공항</div>',
                 name: "Ipseokdong",
                 lat: 35.89920, lon: 128.6391 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">청주국제공항</div>',
                 name: "Ipyang-ni",
                 lat: 36.72207, lon: 127.4959 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">무안국제공항</div>',
                 name: "Muan",
                 lat: 34.99391, lon: 126.3878 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">양양국제공항</div>',
                 name: "Jangjolli",
                 lat: 38.05887, lon: 128.6630 },
                {
                content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">광주공항</div>',
                name: "Yach’on",
                 lat: 35.13985, lon: 126.8108 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">군산공항</div>',
                 name: "Sŏnyŏl-li",
                 lat: 35.92594, lon: 126.6157 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">사천공항</div>',
                 name: "Shisen",
                 lat: 35.09231, lon: 128.0866 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">여수공항</div>',
                 name: "Sangok",
                 lat: 34.84016, lon: 127.6140 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">울산공항</div>',
                 name: "Songjeong",
                 lat: 35.59298, lon: 129.3558 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">원주공항</div>',
                 name: "Hup’yŏng",
                 lat: 37.45924, lon: 127.9772 },
                {
                 content: '<div style="text-align: center;width: 150px;height: 25px; display: flex; align-items: center; justify-content: center;" display: flex; align-items: center; justify-content: center;" display: flex; align-items: center;"">포항경주공항</div>',
                 name: "Ch’ŏngnim",
                 lat: 35.98389, lon: 129.4340 }
            ];


function weatherApi(countryName){

// `https://api.openweathermap.org/data/2.5/weather?q=${countryName}&appid=745e1514332e9aa9afb7b3c1507c7cfc&lang=kr`

const appUrl = `/forecast?q=${countryName}`;

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
            console.log(result.weatherList);
            // json 형식으로 변경
            const weather = JSON.parse(result.weatherList);

            // kakaoMap 에 marker 찍기
            const kakaoMap = createMap(weather.city.coord.lat, weather.city.coord.lon);

            let fTemp = [];
            let fTemp_min = [];
            let fTemp_max = [];
            let fWeather = [];
            let weatherDay = [];
            // 그림
            let fIcon = [];
            let code = [];

            for(let i=0; i<weather.list.length; i++){
            fTemp[i]= Math.round(weather.list[i].main.temp * 10) / 10;
            fTemp_min[i]= Math.round(weather.list[i].main.temp_min * 10) / 10;
            fTemp_max[i]= Math.round(weather.list[i].main.temp_max * 10) / 10;
            fWeather[i] = weather.list[i].weather[0].description;
            weatherDay[i] = weather.list[i].dt_txt;
            code[i] = weather.list[i].weather[0].id;
            console.log(code[0]);
//            des[i] = weatherDescriptionsApi[code[i]] || "기타";
//            console.log(des[0]);
            // 날씨
            fIcon[i] =
                 '<img src="http://openweathermap.org/img/wn/'
                 + weather.list[i].weather[0].icon + '.png" alt="'+ weather.list[i].weather[0].description +'"/>';


            var nowApi = new Date();
            var weatherH = nowApi.getHours();

          $('#date'+i).text(`${weatherDay[i]}`);

          $('#temp'+i).text(`${fTemp[i]}°C`);
          $('#weather' + i).text(`${weatherDescriptionsApi[code[i]] || "기타"}`);
          $('#weatherI'+i).html(`${fIcon[i]}`);

          }

          $('.SeoulNowTempApi').text(`${fTemp[3]}°C`);
          $('.SeoulTempMaxApi').text(`최고기온: ${fTemp_min[3]}°C`);
          $('.SeoulTempMinApi').text(`최저기온: ${fTemp_max[3]}°C`);
          // 날씨 설명
          var weatherCodeApi = weather.list[3].weather[0].id;
          var weatherDescriptionApi = weatherDescriptions[weatherCodeApi] || "기타";

      $('.nowWeatherApi').text(weatherDescriptionApi);

      var weatherImgApi =
     '<img src="http://openweathermap.org/img/wn/'
     + weather.list[3].weather[0].icon + '.png" alt="'+ weather.list[3].weather[0].description +'"/>';

     $('.SeoulIconApi').html(weatherImgApi);
     },
     error: function(jqXHR, textStatus, errorThrown) {
             console.error('AJAX Error:', textStatus, errorThrown);
        }
    });
}

function createMap(lat, lon) {
    const mapContainer = document.getElementById('map');
    const mapOption = {
        center: new kakao.maps.LatLng(36.68549, 127.9009),
        level: 14
    };

    const kakaoMap = new kakao.maps.Map(mapContainer, mapOption);

    // 공항들에 마커 추가하는 함수
    function addMarkersToMap(kakaoMap) {
        airports.forEach(function(airport) {
            const markerPosition = new kakao.maps.LatLng(airport.lat, airport.lon);
            const marker = new kakao.maps.Marker({
                map: kakaoMap,
                position: markerPosition
            });

            // 인포윈도우 내용 설정
            const infowindow = new kakao.maps.InfoWindow({
                content: airport.content
            });

            // 마커에 mouseover, mouseout 이벤트 등록
            kakao.maps.event.addListener(marker, 'mouseover', function() {
                infowindow.open(kakaoMap, marker);
            });

            kakao.maps.event.addListener(marker, 'mouseout', function() {
                infowindow.close();
            });

            kakao.maps.event.addListener(marker, 'click', function() {
                weatherApi(airport.name);
                // 공항 이름 가져오기
                var airportName = airport.name;

                // select 요소 가져오기
                var selectElement = document.getElementById('searchApi');

                // select 요소의 옵션 수
                var optionsCount = selectElement.options.length;

                // 각 옵션을 순회하며 선택된 공항 이름과 일치하는 옵션을 찾음
                for (var i = 0; i < optionsCount; i++) {
                    var option = selectElement.options[i];
                    if (option.value === airportName) {
                        // 선택된 공항 이름과 일치하는 경우 해당 옵션을 선택 상태로 설정
                        option.selected = true;
                        break;
                    }
                }

            });

        });
    }

    addMarkersToMap(kakaoMap);

    return kakaoMap;
}



function weatherSearchApi(){
    const search = document.querySelector('#searchApi').value;
    weatherApi(search);
}


// 즉시 실행 함수
(()=>{
    weatherApi("Incheon");
})();
//(() {
//    addMarkersToMap(map); // 생성한 map 객체를 인자로 전달하여 마커 추가
//})();