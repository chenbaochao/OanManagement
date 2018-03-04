$(document).ready(function() {
    $('#userstable').DataTable({
        responsive: true,
        language: {
            searchPlaceholder: "Search user"
        },
        columnDefs: [
            { responsivePriority: 1, targets: 0 },
            { responsivePriority: 2, targets: -1 }
        ]
    });
} );