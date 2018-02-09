$(document).ready(function () {
    $('#calendar').fullCalendar({
        firstDay: 1,
        themeSystem: 'bootstrap3',
        editable: false,
        dragable: false,
        header: {
            left: '',
            center: 'title',
            right: ''
        },
        defaultView: 'listDay',
        timeFormat: 'H:mm',
        allDayText: 'Whole day',
        views: 'listWeek',
        googleCalendarApiKey: 'AIzaSyB3TNtPD1CNpwIZW2W2Yqx2LRXBkskgIKs',
        eventSources: [
            {
                googleCalendarId: 'nl.be#holiday@group.v.calendar.google.com',
                className: 'gcal-event',
                color: '#00E676'

            },
            {
                url : '/api/event/all',
            }
        ]
    });
})