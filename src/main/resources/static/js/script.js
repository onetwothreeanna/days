$(document).ready(function(){
    $('html').hide().fadeIn(3000).delay(6000)
    $('[data-toggle="tooltip"]').tooltip();
});

function validateForm() {
    var x = document.forms["entryForm"]["entryText"].value;
    if (x == "" || x.length < 3 || x.length > 300) {
        alert("Fill it out, MANG");
        return false;
    }
}
