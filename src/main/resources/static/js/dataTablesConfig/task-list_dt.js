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
    $('#completedTaskList').on( 'click', '#deleteTask', function () {
        var task_id = $(this).attr('value');
        console.log(task_id);

        $.ajax({
            url: 'task-delete',
            data: {id: task_id},
            type: 'GET',
            success: function (json) {
                table
                    .row( $(this).parents('tr') )
                    .remove()
                    .draw();
                    console.log("DELETED");
            }
        })
    } );
} );