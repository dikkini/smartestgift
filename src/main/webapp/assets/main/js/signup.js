$(document).ready(function () {
    var usernameOkIcon = $("#username-ok-icon");
    var usernameNotOkIcon = $("#username-not-ok-icon");
    var emailOkIcon = $("#email-ok-icon");
    var emailNotOkIcon = $("#email-not-ok-icon");
    var cityOkIcon = $("#city-ok-icon");
    var cityNotOkIcon = $("#city-not-ok-icon");

    var usernameObj = $("#username");
    var emailObj = $("#email");
    var firstnameObj = $("#firstname");
    var cityObj = $("#city");
    var passwordObj = $("#password");

    var usernameInputErrorObj = $("#username-input-error");
    var usernameBusyErrorObj = $("#username-busy-error");

    var usernameRegexp = new RegExp("^.{5,255}$");
    var emailRegexp = new RegExp("^[a-zA-Z0-9_.]+@\\w+.\\w+");

    var emailInputErrorObj = $("#email-input-error");
    var emailBusyErrorObj = $("#email-busy-error");

    var usernameForm = usernameObj.parent().parent();
    var emailForm = emailObj.parent().parent();
    var firstnameForm = firstnameObj.parent().parent();
    var cityForm = cityObj.parent().parent();
    var passwordForm = passwordObj.parent().parent();

    $(".loading").loading({width: '25', text: 'Waiting...'});

    function showUsernameInputError() {
        usernameForm.removeClass("has-success");
        usernameForm.addClass("has-error");
        usernameNotOkIcon.show();
        usernameInputErrorObj.show();
        usernameOkIcon.hide();
    }

    function showEmailInputError() {
        emailForm.removeClass("has-success");
        emailForm.addClass("has-error");
        emailOkIcon.hide();
        emailNotOkIcon.show();
        emailInputErrorObj.show();
        emailBusyErrorObj.hide();
    }

    usernameObj.on('focusout', function (e) {
        var ajaxLoadingUsername = $("#loading-username");
        ajaxLoadingUsername.loading('start');

        var usernameVal = usernameObj.val();
        if (!usernameRegexp.test(usernameVal)) {
            showUsernameInputError();
            ajaxLoadingUsername.loading('stop');
            return;
        } else {
            usernameInputErrorObj.hide();
        }

        $.ajax({
            type: "post",
            url: "/signup/checkLogin",
            cache: false,
            data: "login=" + $('#username').val(),
            success: function (response) {
                if (response.success) {
                    usernameForm.removeClass("has-error");
                    usernameForm.addClass("has-success");
                    usernameOkIcon.show();
                    usernameInputErrorObj.hide();
                    usernameBusyErrorObj.hide();
                    usernameNotOkIcon.hide();
                } else {
                    usernameForm.removeClass("has-success");
                    usernameForm.addClass("has-error");
                    usernameNotOkIcon.show();
                    usernameBusyErrorObj.show();
                    usernameOkIcon.hide();
                }
                ajaxLoadingUsername.loading('stop');
            },
            error: function (response) {
                alert(response.error);
            }
        });
    });

    emailObj.on('focusout', function (e) {
        var emailLoading = $("#loading-email");
        emailLoading.loading('start');

        var emailVal = emailObj.val();

        if (!emailRegexp.test(emailVal)) {
            showEmailInputError();
            emailLoading.loading('stop');
            return;
        } else {
            emailInputErrorObj.hide();
        }
        $.ajax({
            type: "post",
            url: "/signup/checkEmail",
            cache: false,
            data: "email=" + $('#email').val(),
            success: function (response) {
                if (response.success) {
                    emailForm.removeClass("has-error");
                    emailForm.addClass("has-success");
                    emailOkIcon.show();

                    emailNotOkIcon.hide();
                    emailInputErrorObj.hide();
                    emailBusyErrorObj.hide();
                } else {
                    emailForm.removeClass("has-success");
                    emailForm.addClass("has-error");

                    emailBusyErrorObj.show();
                    emailNotOkIcon.show();
                    emailOkIcon.hide();
                }
                emailLoading.loading('stop');
            },
            error: function (response) {
                alert(response.error);
            }
        });
    });

    cityObj.on('focusout', function(e) {
        //TODO проверить ID выдавший КЛАДР и вынести в отдельную функцию
        if (cityObj.val().trim() == "") {
            cityForm.addClass("has-error");
            cityOkIcon.hide();
            cityNotOkIcon.show();
        } else {
            cityForm.addClass("has-success");
            cityOkIcon.show();
            cityNotOkIcon.hide();
        }
    });

    firstnameObj.on('focusout', function(e) {
        if (firstnameObj.val().trim() == "") {
            firstnameForm.addClass("has-error");
        } else {
            firstnameForm.addClass("has-success")
        }
    });

    passwordObj.on('focusout', function(e) {
        if (passwordObj.val().trim() == "") {
            passwordForm.addClass("has-error");
        } else {
            passwordForm.addClass("has-success")
        }
    });

    emailObj.focus(function() {
        emailBusyErrorObj.hide();
        emailInputErrorObj.hide();
        emailNotOkIcon.hide();
        emailOkIcon.hide();
        emailForm.removeClass("has-error");
        emailForm.removeClass("has-success");
    });

    usernameObj.focus(function() {
        usernameNotOkIcon.hide();
        usernameOkIcon.hide();
        usernameInputErrorObj.hide();
        usernameBusyErrorObj.hide();
        usernameForm.removeClass("has-error");
        usernameForm.removeClass("has-success");
    });

    firstnameObj.focus(function() {
        firstnameForm.removeClass("has-error");
        firstnameForm.removeClass("has-success")
    });

    cityObj.focus(function() {
        cityForm.removeClass("has-error");
        cityForm.removeClass("has-success")
    });

    passwordObj.focus(function() {
        passwordForm.removeClass("has-error");
        passwordForm.removeClass("has-success")
    });

    $("#sign-up-btn").click(function (e) {
        var loadingSignUp = $("#loading-sign-up");
        loadingSignUp.loading('start');

        var validForm = true;

        if (!emailRegexp.test(emailObj.val())) {
            showEmailInputError();
            validForm = false;
        }

        if (!usernameRegexp.test(usernameObj.val())) {
            showUsernameInputError();
            validForm = false;
        }

        if (firstnameObj.val().trim() == "") {
            firstnameForm.addClass("has-error");
            validForm = false;
        }

        //TODO добавить проверку ID полученного из КЛАДР
        if (cityObj.val().trim() == "") {
            cityForm.addClass("has-error");
            validForm = false;
        }

        if (passwordObj.val().trim() == "") {
            passwordForm.addClass("has-error");
            validForm = false;
        }

        if (!validForm) {
            loadingSignUp.loading('stop');
            e.preventDefault();
            return;
        }

        var data = {};
        $('input').each(function () {
            data[$(this).attr('name')] = $(this).val();
        });

        $.ajax({
            type: "post",
            url: "/signup/register",
            cache: false,
            data: data,
            success: function (response) {
                if (response.success) {
                    window.location = "/profile?successes=signup_success";
                } else {
                    $.showNotifications(response);
                }
                loadingSignUp.loading('stop');
            },
            error: function (response) {
                alert(response.error);
            }
        });

        e.preventDefault();
    });
    $("#sign-up-facebook-user-btn").click(function (e) {
        var loadingSignUp = $("#loading-sign-up");
        loadingSignUp.loading('start');

        var validForm = true;

        if (!emailRegexp.test(emailObj.val())) {
            showEmailInputError();
            validForm = false;
        }

        if (!usernameRegexp.test(usernameObj.val())) {
            showUsernameInputError();
            validForm = false;
        }

        if (firstnameObj.val().trim() == "") {
            firstnameForm.addClass("has-error");
            validForm = false;
        }

//        //TODO добавить проверку ID полученного из КЛАДР
//        if (cityObj.val().trim() == "") {
//            cityForm.addClass("has-error");
//            validForm = false;
//        }

        if (!validForm) {
            loadingSignUp.loading('stop');
            e.preventDefault();
            return;
        }

        var data = {};
        $('input').each(function () {
            data[$(this).attr('name')] = $(this).val();
        });

        data.id = $("#username").data("social");

        $.ajax({
            type: "post",
            url: "/signup/facebook/register",
            cache: false,
            data: data,
            success: function (response) {
                if (response.success) {
                    window.location = "/profile?successes=signup_success";
                } else {
                    $.showNotifications(response);
                }
                loadingSignUp.loading('stop');
            },
            error: function (response) {
                alert(response.error);
            }
        });

        e.preventDefault();
    });

    (function($){
        $(function() {
            $('#city').kladr({
                token: '533bdf64dba5c74349000000',
                key: '11a0052fed5a99278a594ca8ded998b160aeef7b',
                type: $.kladr.type.city,
                labelFormat: function( obj, query) {
                    console.log(query);
                    console.log(obj);
                    return obj.type + " " + obj.name +  (obj.zip == null ? "" : ", Индекс: " + obj.zip);
                }
            });

/*            // Автодополнение населённых пунктов
            $( '[name="location"]' ).kladr({
                token: '533bdf64dba5c74349000000',
                key: '11a0052fed5a99278a594ca8ded998b160aeef7b',
                type: $.kladr.type.city,
                select: function( obj ) {
                    // Изменения родительского объекта для автодополнения улиц
                    $( '[name="street"]' ).kladr('parentId', obj.id);
                }
            });*/

/*            // Автодополнение улиц
            $( '[name="street"]' ).kladr({
                token: '533bdf64dba5c74349000000',
                key: '11a0052fed5a99278a594ca8ded998b160aeef7b',
                type: $.kladr.type.street,
                parentType: $.kladr.type.city
            });*/
        });
    })(jQuery);
});