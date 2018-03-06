$(document).ready(function() {
    var table = $('#messagesTable').DataTable( {
            "columnDefs": [
                { "type": "date", "targets": 2 }
            ],
            "order": [ 3, 'asc' ],

            language: {
                searchPlaceholder: "Search message"
            }
        }
    )
        .on( 'click', '#deleteMessage', function () {
            var message_id = $(this).attr('value');

            $.ajax({
                url: 'message-delete',
                data: {id: message_id},
                type: 'GET'
            })
            table
                .row( $(this).parents('tr') )
                .remove()
                .draw();
        } );;
} );