$(document).ready(function() {
    $('#bugstable').DataTable({
            "columnDefs": [
                { "type": "date", "targets": 1 }
            ],
            responsive: true,
            "order": [ 0, 'desc' ],
            language: {
                searchPlaceholder: "Search bug"
            }
        }
    );
} );