$(document).ready(function() {
    $('#summernote').summernote({
        toolbar: [
            // [groupName, [list of button]]
            ['style', ['style', 'bold', 'italic', 'underline', 'clear']],
            ['fontsize', ['fontsize']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['media', ['link', 'picture']],
            ['misc',['undo', 'redo', 'fullscreen']]

        ],
        disableDragAndDrop: true,
        height: 300

    });
});