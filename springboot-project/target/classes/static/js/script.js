/* CREATE_EVENT */
var initialDate = document.getElementById("initialDate");
var endDate = document.getElementById("endDate");
initialDate.addEventListener("change", function (event) {
    var actualDate = new Date();
    var userDate = new Date(initialDate.value);
    var iDate = new Date(initialDate.value);
    var eDate = new Date(endDate.value);

    if (userDate < actualDate) {
        alert("La fecha de inicio no puede ser anterior al dia de hoy");
        initialDate.value = '';
    }
    else if (eDate < iDate) {
        alert("La fecha de inicio no puede ser posterior a la fecha de fin");
        initialDate.value = '';
    }
});
endDate.addEventListener("change", function (event) {
    var actualDate = new Date();
    var iDate = new Date(initialDate.value);
    var eDate = new Date(endDate.value);

    if (eDate < iDate) {
        alert("La fecha de finalizacion no puede ser anterior a la fecha de comienzo");
        endDate.value = '';
    }
});

/* CREATE_USER */

var birthdate = document.getElementById("birthdate");
birthdate.addEventListener("change", function (event) {
    var actualDate = new Date();
    var userDate = new Date(birthdate.value);

    if (userDate > actualDate) {
        alert("La fecha no puede ser mayor a la actual");
        $(".alert1").addClass("show");
        birthdate.value = '';
    }
    else {
        $(".alert1").removeClass("show");
    }
});


function selectAvatar(element) {
    var avatarImages = document.querySelectorAll('.avatar');
    avatarImages.forEach(function (img) {
        img.classList.remove('border');
        img.classList.remove('border-primary');
        img.classList.remove('border-4');
    });
    element.classList.add('border');
    element.classList.add('border-primary');
    element.classList.add('border-4');
}

$(".form-check-input").change(function () {
    var checkboxes = document.getElementsByName("interests");
    var checkedCount = 0;

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            checkedCount++;
            $(".alert2").remove("show");
        }
    }
});

function validateForm() {
    var checkboxes = document.getElementsByName("interests");
    var checkedCount = 0;

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            checkedCount++;
        }
    }

    $(".alert2").remove("show");

    if (checkedCount < 5 || checkedCount > 5) {
        $(".alert2").addClass("show");
        return false;
    }

    return true;
}

	// Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();