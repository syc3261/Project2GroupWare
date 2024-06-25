let tbodyTag = document.querySelector('#bus1');
const stationNm = document.querySelector('.airplaneBusStationInfo-con');
const startEnd = document.querySelector('.startEnd');

function busSearch() {
    let html1 = "";
    let airplaneBusSearch = document.querySelector('#airplaneBusSearch')
    let strSrch = airplaneBusSearch.value;

    let apiUrl = `/api/airplaneBusList?q=${strSrch}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(function (msg) {
            //아래부터는 html로 가져오기 위한 코드-->

            let jsonRs = JSON.parse(msg.airplaneBusList);

            const areaNames = {
                1: "서울",
                2: "경기",
                3: "인천",
                4: "강원",
                5: "충청",
                6: "경상",
                7: "전라"
            };

            jsonRs.response.body.items.forEach(el => {
                // routeinfo가 존재하고 null 또는 undefined가 아닌지 확인
                if (el.routeinfo) {
                    const routeInfo = el.routeinfo;
                    const routeArray = routeInfo.split(', ');
                    const lastRoute = routeArray[routeArray.length - 1];

                    html1 += "<tr>";
                    html1 += `
                    <tr>
                    <td>${areaNames[el.area]} ${lastRoute}</td>
                    <td onclick='stationPost(event.target.innerText)' style="background-color:#96b3d9">${el.busnumber}</td>
                    <td>${formatTime(el.toawfirst)}</td>
                    <td>${formatTime(el.toawlast)}</td>
                    <td>${formatTime(el.t1endfirst)}</td>
                    <td>${formatTime(el.t1endlast)}</td>
                    <td>${formatTime(el.t2endfirst)}</td>
                    <td>${formatTime(el.t2endlast)}</td>
                    <td>${el.adultfare}</td>
                    <td>${el.busclass}</td>
                    <td>${el.cpname}</td>
                    <td>${el.t1ridelo}</td>
                    <td>${el.t2ridelo}</td>
                `;
                    html1 += "</tr>";
                }
            });
            tbodyTag.innerHTML = html1;
        });
}

function stationPost(busNumber) {
    let html1 = "";
    let html2 = "";


    let apiUrl = `/api/airplaneBusStationPost?busNumber=${busNumber}`;

    console.log(`Fetching data from: ${apiUrl}`);

    fetch(apiUrl)
        .then(response => response.json())
        .then(function (msg) {
            let jsonRs = msg.airplaneBusRoute;

            html2 = `<div> ${jsonRs[0].routeinfo} -> ${jsonRs[jsonRs.length - 1].routeinfo} </div>`

            if (jsonRs.length === 0) {
                html1 = "<p>No routes found.</p>";
            } else {
                let halfwayIndex = Math.ceil(jsonRs.length / 2);

                html1 += '<div class="upper-half">';
                jsonRs.slice(0, halfwayIndex).forEach(el => {
                    html1 += `<p class="upPoint" style="padding: 5px"><img src="/images/bus.png" alt="bus" height="20px">${el.routeinfo}</p>`;
                });
                html1 += '</div>';

                html1 += '<div class="lower-half">';
                jsonRs.slice(halfwayIndex).forEach(el => {
                    html1 += `<p class="downPoint" style="padding: 5px"><img src="/images/bus.png" alt="bus" height="20px">${el.routeinfo}</p>`;
                });
                html1 += '</div>';
            }

            stationNm.innerHTML = html1;
            startEnd.innerHTML = html2;
        })
        .catch(error => {
            console.error('Error fetching data:', error);
            stationNm.innerHTML = "<p>Error fetching data.</p>";
        });

    $('.airplaneBusStationInfo').css('display', 'flex');
}

function formatTime(timeStr) {
    if (!timeStr || timeStr.length < 4) return '';
    const hours = timeStr.substring(0, 2);
    const minutes = timeStr.substring(2, 4);
    return `${hours}시 ${minutes}분`;
}

(() => {
    busSearch();
})();
