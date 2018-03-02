$(document).ready(function() {
    $('#pendingTasksList').DataTable( {
            "columnDefs": [
                { "type": "date", "targets": 1 }
            ],
            "order": [ 1, 'desc' ],
            language: {
                searchPlaceholder: "Search task"
            }
        }

    );
} );