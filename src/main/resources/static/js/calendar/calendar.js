document.addEventListener('DOMContentLoaded', function () {
  let calendarEl = document.getElementById('calendar');
  let Calendar = FullCalendar.Calendar;
  let calendar = new FullCalendar.Calendar(calendarEl, {
    eventClick: function(info) {
      // 모달에 이벤트 정보 표시
      let schedule = info.event.extendedProps;
      $('#event-id').text("ID: " + info.event.id);
      $('#event-content').text("내용: " + info.event.title);
      $('#event-start').text("시작 시간: " + info.event.start.toISOString());
      $('#event-end').text("종료 시간: " + info.event.end.toISOString());
      $('#event-employeeId').text("직원 ID: " + schedule.employeeId);

      // 모달 표시
      $('#eventModal').modal('show');

      $('#deleteEventBtn').off('click').on('click', function() {
        $.ajax({
          url: "/api/calendar/" + info.event.id,
          method: "DELETE",
          success: function(response) {
            info.event.remove();
            alert("일정이 삭제되었습니다.");
            $('#eventModal').modal('hide');
          },
          error: function(xhr, status, error) {
            console.error("일정 삭제 실패:", error);
            alert("일정 삭제에 실패했습니다.");
          }
        });
      });
    },
    initialView: 'dayGridMonth',
    defaultDate: new Date(),
    customButtons: {
      prev: {
        text: "Prev month",
        click: function () {
          calendar.prev();
          getCalendar(calendar.getDate());
        }
      },
      next: {
        text: "Next month",
        click: function () {
          calendar.next();
          getCalendar(calendar.getDate());
        }
      },
      today: {
        text: "today",
        click: function () {
          calendar.gotoDate(new Date());
          getCalendar(calendar.getDate());
        }
      },
      AddEventButton: {
        text: "일정 추가",
        click: function () {
          $('#calendarModal').modal('show');

          $("#addBtn").on("click", function () {
            location.replace(location.href);

            let content = $("#calendar_content").val();
            let start_date = $("#calendar_start_date").val();
            let end_date = $("#calendar_end_date").val();
            let employeeId = $("#employeeId").val();

            if (content == null || content == "") {
              alert("내용을 입력하세요.");
            } else if (start_date == "" || end_date == "") {
              alert("날짜를 입력하세요.");
            } else if (new Date(end_date) - new Date(start_date) < 0) {
              alert("종료일이 시작일보다 먼저입니다.");
            } else {
              let obj = {
                "content": content,
                "start": start_date,
                "end": end_date,
                "employeeId": employeeId
              };
              setCalendar(content, start_date, end_date, employeeId);
            }
          });
          $("#calendarModal").modal('hide');
        }
      },
    },
    eventSources: [],
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'AddEventButton'
    },
    editable: false,
    droppable: true,
    eventContent: function(arg) {
      let color = arg.event.backgroundColor;
      let textColor = color === '#eff6ff' ? 'black' : 'white';

      let eventEl = document.createElement('div');
      eventEl.style.backgroundColor = color;
      eventEl.style.color = textColor;
      eventEl.innerHTML = `
        <div class="fc-event-title">${arg.event.title}</div>
      `;

      return { domNodes: [eventEl] };
    }
  });

  const rainbowColors = ["#3767a6", "#96b3d9", "#324153", "#eff6ff"];
  let colorIndex = 0;

  function setCalendar(content, start, end, employeeId) {
    $.ajax({
      url: "/api/calendar",
      method: "POST",
      dataType: "json",
      async: false,
      data: {
        content: content,
        start: start,
        end: end,
        employeeId: employeeId
      }
    })
        .done(function (data) {
          getCalendar(calendar.getDate());
          calendar.render();
        })
        .fail(function (xhr, status, errorThrown) {
          console.log("오류");
        })
        .always(function (xhr, status) {
          console.log("완료");
        });
  }

  function getCalendar(date) {
    calendar.removeAllEvents();
    let result;
    month = date.getMonth() + 1;
    if (month < 10) {
      month = "0" + month;
    }
    $.ajax({
      url: "/api/calendar",
      dataType: "json",
      async: false
    })
        .done(function (data) {
          console.log(data);
          $.each(data, function (index, element) {
            console.log(element.content, element.start);
            calendar.addEvent({
              id: element.id,
              title: element.content,
              start: element.start,
              end: element.end,
              employeeId: element.employeeId,
              backgroundColor: rainbowColors[colorIndex % rainbowColors.length]
            });
            colorIndex++;
          });
          result = data;
        })
        .fail(function (xhr, status, errorThrown) {
          console.log("오류");
        })
        .always(function (xhr, status) {
          console.log("완료");
        });
    return result;
  }

  calendar.addEvent({
    title: "목요일",
    start: "2024-05-30",
    backgroundColor: rainbowColors[colorIndex % rainbowColors.length]
  });
  colorIndex++;

  calendar.render();
  getCalendar(calendar.getDate());
});
