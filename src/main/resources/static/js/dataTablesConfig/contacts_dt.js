$(document).ready(function() {
    var table = $('#contactstable').DataTable( {
        lengthChange: false,
        dom: 'Bfrtip',
        responsive: true,
        //buttons: ['print', 'excel', 'pdf', 'colvis']
        buttons: [
            {
                extend: 'print',
                exportOptions: {
                    columns: [ 0, 1, 2, 3, 4, 5 ]
                }
            },
            {
                extend: 'excel',
                exportOptions: {
                    columns: [ 0, 1, 2, 3, 4, 5 ]
                }
            },
            {
                extend: 'pdf',
                exportOptions: {
                    columns: [ 0, 1, 2, 3, 4, 5 ]
                }
            },
            'colvis'
        ]
    } );
} );