$(document).ready(function() {
    $('#completedTaskList').DataTable( {
            "columnDefs": [
                { "type": "date", "targets": 1 }
            ],
            "order": [ 1, 'asc' ],
            language: {
                searchPlaceholder: "Search task"
            }
        }

    );
} );