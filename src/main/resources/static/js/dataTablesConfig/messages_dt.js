$(document).ready(function() {
    $('#messagestable').DataTable( {
            "columnDefs": [
                { "type": "date", "targets": 2 }
            ],
            "order": [ 3, 'asc' ],
            responsive: true,
            language: {
                searchPlaceholder: "Search message"
            }
        }
    );
} );