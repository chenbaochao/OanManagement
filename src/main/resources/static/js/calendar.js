$(document).ready(function () {
    $('#calendar').fullCalendar({
        aspectRatio: 1.2,
        editable: true,
        selectable: true,
        themeSystem: 'bootstrap3',
        handleWindowResize: true,
        eventLimit: true,
        nowIndicator: true,
        timeFormat: 'H(:mm)',
        allDayText: 'Day',
        views: {
            month: {
                eventLimit: 6
            }
        },
        firstDay: 1,
        googleCalendarApiKey: 'AIzaSyB3TNtPD1CNpwIZW2W2Yqx2LRXBkskgIKs',
        /*events: {
            url : '/api/event/all',
            googleCalendarId: 'https://calendar.google.com/calendar?cid=MGVoYXR1ZGxuaTVkMjQ5dHE0cnVybXZiaTBAZ3JvdXAuY2FsZW5kYXIuZ29vZ2xlLmNvbQ'
        },*/
        eventSources: [
            {
                googleCalendarId: 'nl.be#holiday@group.v.calendar.google.com',
                className: 'gcal-event',
                color: '#00E676'

            },
            {
                url : '/api/event/all',
            }
        ],
        eventClick:  function(event, jsEvent, view) {
            endtime = $.fullCalendar.moment(event.end).format('h:mm');
            starttime = $.fullCalendar.moment(event.start).format('dddd, MMMM Do YYYY, h:mm');
            var mywhen = starttime + ' - ' + endtime;
            $('#modalTitle').html(event.title);
            $('#modalDesc').html(event.description)
            $('#modalWhen').text(mywhen);
            $('#eventID').val(event.id);
            $('#calendarModal').modal();
        },
        select: function(start, end, jsEvent) {
            endtime = $.fullCalendar.moment(end).format('h:mm');
            starttime = $.fullCalendar.moment(start).format('dddd, MMMM Do YYYY');
            var mywhen = starttime + ' - ' + endtime;
            start = moment(start).format();
            end = moment(end).format();
            $('#createEventModal #startTime').val(start);
            $('#createEventModal #endTime').val(end);
            $('#createEventModal #when').text(mywhen);
            $('#createEventModal').modal('toggle');
            $(".fc-highlight").css("background", "#2196F3");
        },
        eventDrop: function(event, delta){
            $.ajax({
                url: '/calendar-update',
                data: {title: event.title, start: moment(event.start).format(), end: moment(event.end).format(), id: event.id},
                type: "GET",
                success: function(json) {
                    //alert(json);
                }
            });
        },
        eventResize: function(event) {
            $.ajax({
                url: '/calendar-update',
                data: {title: event.title, start: moment(event.start).format(), end: moment(event.end).format(), id: event.id},
                type: "GET",
                success: function(json) {
                    //alert(json);
                }
            });
        },
        header: {
            left: 'prev, next today',
            center: 'title',
            right: 'month, agendaWeek, agendaDay, listWeek'
        }
        /*    eventSources: [
                '/api/event/all',
                {
                    url: 'https://calendar.google.com/calendar?cid=MGVoYXR1ZGxuaTVkMjQ5dHE0cnVybXZiaTBAZ3JvdXAuY2FsZW5kYXIuZ29vZ2xlLmNvbQ'
                }
            ],*/
    })
    $('#deleteButton').on('click', function(e){
        // We don't want this to act as a link so cancel the link action
        e.preventDefault();
        doDelete();
    });

    function doDelete(){
        $("#calendarModal").modal('hide');
        var eventID = $('#eventID').val();
        $.ajax({
            url: 'calendar-delete',
            data: {id: eventID},
            type: "GET",
            success: function(json) {
                if(json == 1)
                    $("#calendar").fullCalendar('removeEvents',eventID);
                else
                    return false;
            }
        });
        location.reload();
    }
});