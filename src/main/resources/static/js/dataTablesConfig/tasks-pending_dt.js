$(document).ready(function() {
    $('#pendingTasksList').DataTable( {
            "columnDefs": [
                { "type": "date", "targets": 1 }
            ],
            "order": [ 1, 'desc' ],
            responsive: true,
            language: {
                searchPlaceholder: "Search task"
            }
        }

    );
} );