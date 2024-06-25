const stationNm = document.querySelector('.bus-station');
let map, positions = [];
let tbodyTag = document.querySelector('#bus1');
let markers = []; // 마커를 담을 배열
let busMarkers = [];
let polyline = null; // 폴리라인 객체

function initializeMap(centerLat, centerLng) {
    let mapContainer = document.getElementById('map');
    let mapOption = {
        center: new kakao.maps.LatLng(centerLat, centerLng),
        level: 7 // 초기 줌 레벨 설정
    };

    map = new kakao.maps.Map(mapContainer, mapOption);
}

function stationPost(busRouteId) {
    let html1 = "";

    let apiUrl = `/api/busStationPost?busRouteId=${busRouteId}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(function (msg) {
            let jsonRs = JSON.parse(msg.rs);
            let itemList = jsonRs.msgBody.itemList;

            // 기존 마커와 폴리라인 제거
            removeMarkers();
            removePolyline();

            // 새로운 정류소 정보 표시
            html1 = itemList.map(el => {
                return `<div class="busStation-con" data-lat="${el.gpsY}" data-lng="${el.gpsX}"
                            data-stationNm = "${el.stationNm}" data-stationNo = "${el.stationNo}">
                            <img src="/images/train.png" alt="bus" height="16px">${el.stationNm}
                        </div>`;
            }).join('');

            stationNm.innerHTML = html1;
            positionFn(itemList);
            addClickEventToStations();
            busRoute(busRouteId);
        });
}


function busRoute(busRouteId) {
    console.log("busRouteId : " + busRouteId);
    let apiUrl = `/api/busRouteInfo?busRouteId=${busRouteId}`;


    fetch(apiUrl)
        .then(response => response.json())
        .then(function (msg) {
            let jsonRs = JSON.parse(msg.rs);
            let itemList = jsonRs.msgBody.itemList;
            busRouteMap(itemList);
        });
}


function addClickEventToStations() {
    document.querySelectorAll('.busStation-con').forEach(station => {
        station.addEventListener('click', function () {
            const lat = parseFloat(this.getAttribute('data-lat'));
            const lng = parseFloat(this.getAttribute('data-lng'));
            const stationNm = this.getAttribute('data-stationNm');
            const stationNo = this.getAttribute('data-stationNo');
            if (map && typeof map.setCenter === 'function') {
                map.setCenter(new kakao.maps.LatLng(lat, lng));
                map.setLevel(2); // 클릭 시 줌 레벨을 2로 설정
                addMarker(lat, lng, stationNm, stationNo); // 클릭된 위치에 마커 추가
                $('.roadviewTop').css('display', 'none');
            } else {
                console.error("Map is not initialized or setCenter is not a function.");
            }

            // 모든 .busStation-con 요소에서 selected 클래스를 제거합니다.
            document.querySelectorAll('.busStation-con').forEach(el => el.classList.remove('selected'));

            // 클릭한 요소에 selected 클래스를 추가합니다.
            this.classList.add('selected');
        });
    });
}

function busRouteMap(dataVal) {
    let positions = [];
    let lat = 0;
    let lng = 0;

    removeBusMarkers();

    dataVal.forEach((el) => {
        lat = el.gpsY;
        lng = el.gpsX;
        let result = {
            title: el.stationNm,
            latlng: new kakao.maps.LatLng(parseFloat(lat), parseFloat(lng)),
            nextStId: el.nextStId // 추가된 부분: 다음 정류장 이름
        };
        positions.push(result);
    });

    let imageSrc = "/images/bus.png";

    for (let i = 0; i < positions.length; i++) {
        // 마커 이미지의 이미지 크기 입니다
        let imageSize = new kakao.maps.Size(30, 30);
        // 마커 이미지를 생성합니다
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
        // 마커를 생성합니다
        let marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: positions[i].latlng, // 마커를 표시할 위치
            title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage, // 마커 이미지
            zIndex: 1
        });

        let iwContent = `<div style="padding:5px; text-align: center; width: 150px;">${positions[i].nextStId} 도착 전</div>`; // 수정된 부분

        let infoWindow = new kakao.maps.InfoWindow({
            content: iwContent,
            zIndex: 1,
            minWidth: 50,
            maxWidth: 150,
            minHeight: 50,
            maxHeight: 150
        });

        // 마커에 마우스오버 이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'mouseover', function () {
            // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
            infoWindow.open(map, marker);
        });

        // 마커에 마우스아웃 이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'mouseout', function () {
            // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
            infoWindow.close();
        });

        // 마커 배열에 추가
        busMarkers.push(marker);
    }
}


function addMarker(lat, lng, stationNm, stationNo) {
    // 기존 마커 제거
    // removeMarkers();

    // 새로운 마커 추가
    let markerPosition = new kakao.maps.LatLng(lat, lng);
    let marker = new kakao.maps.Marker({
        position: markerPosition
    });

    markers.push(marker);
    marker.setMap(map);

    var iwContent = '<div style="padding:5px;">로드뷰 보기</div>'; // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다

    var infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        position: markerPosition,
        zIndex: 1,
        minWidth: 50,
        maxWidth: 150,
        minHeight: 50,
        maxHeight: 150
    });

    infowindow.setContent('<div style="padding:5px; text-align: center; width: 150px;">로드뷰 보기</div>');

    // 마커에 마우스오버 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'mouseover', function () {
        // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
        infowindow.open(map, marker);
    });

    // 마커에 마우스아웃 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'mouseout', function () {
        // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
        infowindow.close();
    });

    kakao.maps.event.addListener(marker, 'click', function () {
        showRoadview(markerPosition);
        stationInfo(stationNm, stationNo);
    });
}

function positionFn(dataVal) {
    positions = [];
    dataVal.forEach((el, idx) => {
        let lat = el.gpsY;
        let lng = el.gpsX;
        let result = {
            title: el.stationNm,
            latlng: new kakao.maps.LatLng(parseFloat(lat), parseFloat(lng))
        };
        positions.push(result);
    });

    let centerIndex = Math.floor(positions.length / 4);
    let centerLat = parseFloat(positions[centerIndex].latlng.getLat());
    let centerLng = parseFloat(positions[centerIndex].latlng.getLng());

    if (!map) {
        initializeMap(centerLat, centerLng);
    } else {
        map.setCenter(new kakao.maps.LatLng(centerLat, centerLng));
        map.setLevel(7); // 기본 레벨을 5로 설정
        var bounds = new kakao.maps.LatLngBounds();
    }
    var lineColor;

    if (dataVal[0].routeType == 1) {
        lineColor = 'red';
    } else if (dataVal[0].routeType == 2) {
        lineColor = '#00ff00';
    } else if (dataVal[0].routeType == 3) {
        lineColor = 'blue';
    } else if (dataVal[0].routeType == 4) {
        lineColor = '#00ff00';
    } else if (dataVal[0].routeType == 5) {
        lineColor = 'yellow';
    } else if (dataVal[0].routeType == 6) {
        lineColor = 'red';
    } else if (dataVal[0].routeType == 7) {
        lineColor = '#00ff00';
    } else if (dataVal[0].routeType == 8) {
        lineColor = 'cyan';
    } else {
        lineColor = '#000000';
    }

    positions.forEach(position => {
        let circle = new kakao.maps.Circle({
            center: position.latlng,
            radius: 3,
            strokeWeight: 1,
            strokeColor: lineColor,
            strokeOpacity: 1,
            strokeStyle: 'solid',
            fillColor: '#324153',
            fillOpacity: 1,
            zIndex: 10
        });

        circle.setMap(map);
    });

    // 폴리라인 그리기
    drawPolyline(positions, lineColor);
}

function drawPolyline(positions, lineColor) {
    // 기존 폴리라인 제거
    removePolyline();

    let linePath = positions.map(pos => pos.latlng);
    polyline = new kakao.maps.Polyline({
        path: linePath,
        strokeWeight: 5,
        strokeColor: lineColor,
        strokeOpacity: 0.8,
        strokeStyle: 'solid'
    });

    polyline.setMap(map);

    var bounds = new kakao.maps.LatLngBounds();
    bounds.extend(positions[0].latlng);
    bounds.extend(positions[Math.floor(positions.length / 2)].latlng);

    // 출발지와 도착지가 화면에 딱 맞도록 지도를 조절합니다.
    map.setBounds(bounds);
}




function stationInfo(stationNm, stationNo) {
    var stationInfo = document.getElementById('stationInfo');
    let html = "";
    html += `<div>정류장 번호 : ${stationNo}</div>
                    <div>이름 : ${stationNm}</div>`;
    stationInfo.innerHTML = html;
}

function showRoadview(position) {
    var roadviewContainer = document.getElementById('roadview'); //로드뷰를 표시할 div
    var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
    var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체
    $('.roadviewTop').css('display', 'flex');
    var position = new kakao.maps.LatLng(position.getLat(), position.getLng());

    // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
    roadviewClient.getNearestPanoId(position, 50, function (panoId) {
        roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
    });
}

function removeMarkers() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
}

function removeBusMarkers() {
    busMarkers.forEach(marker => marker.setMap(null));
    busMarkers = [];
}

function removePolyline() {
    if (polyline !== null) {
        polyline.setMap(null);
        polyline = null;
    }
}

function busSearch() {
    let html1 = "";
    let busSearch = document.getElementById("busSearch");
    let strSrch = busSearch.value;

    let apiUrl = `/api/busList?strSrch=${strSrch}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(function (msg) {
            let jsonRs = JSON.parse(msg.rs);
            jsonRs.msgBody.itemList.forEach(el => {
                const routeColor = getRouteColor(el.routeType);
                html1 += "<tr>";
                html1 += `
                    <td style="color:${routeColor}">●</td>
                    <td>${el.busRouteNm}</td>
                    <td>${el.edStationNm}</td>
                    <td>${el.stStationNm}</td>
                    <td>${formatTime(el.firstBusTm)}</td>
                    <td>${formatTime(el.lastBusTm)}</td>
                    <td>${el.term}분</td>
                    <td onclick='stationPost(event.target.innerText)' style="background-color:#eff6ff; cursor: pointer;">${el.busRouteId}</td>
                `;
                html1 += "</tr>";
            });
            tbodyTag.innerHTML = html1;
        });
}

function getRouteColor(routeType) {
    const routeColors = {
        1: 'red',
        2: '#00ff00',
        3: 'blue',
        4: '#00ff00',
        5: 'yellow',
        6: 'red',
        7: '#00ff00',
        8: 'cyan'
    };
    return routeColors[routeType] || 'black';
}

function formatTime(timeStr) {
    if (!timeStr || timeStr.length < 14) return '';
    const hours = timeStr.substring(8, 10);
    const minutes = timeStr.substring(10, 12);
    return `${hours}시 ${minutes}분`;
}

(() => {
    busSearch();
    stationPost(100100154);
})();
