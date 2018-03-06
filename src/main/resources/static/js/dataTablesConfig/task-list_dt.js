$(document).ready(function() {
    var table = $('#completedTaskList').DataTable( {
            "columnDefs": [
                { "type": "date", "targets": 1 }
            ],
            "order": [ 1, 'asc' ],
            language: {
                searchPlaceholder: "Search task"
            }
        }

    );
    $('#completedTaskList').on( 'click', '#deleteCompletedTask', function () {
        var task_id = $(this).attr('value');

        $.ajax({
            url: 'task-delete',
            data: {id: task_id},
            type: 'GET'
        })
        table
            .row( $(this).parents('tr') )
            .remove()
            .draw();
    } );

    $('#TodoList').on( 'click', '#deleteTask', function () {
        var task_id = $(this).attr('value');

        $.ajax({
            url: 'task-delete',
            data: {id: task_id},
            type: 'GET'
        })
        $('#todoTableRow').remove();
    } );
} );