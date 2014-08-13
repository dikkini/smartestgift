$(document).ready(function () {
    var usernameOkSpan = $("#username-ok-icon");
    var usernameNotOkSpan = $("#username-not-ok-icon");
    var firstNameOkSpan = $("#firstName-ok-icon");
    var firstNameNotOkSpan = $("#firstName-not-ok-icon");
    var emailOkSpan = $("#email-ok-icon");
    var emailNotOkSpan = $("#email-not-ok-icon");
    var passwordOkSpan = $("#password-ok-icon");
    var passwordNotOkSpan = $("#password-not-ok-icon");
    var cityOkSpan = $("#city-ok-icon");
    var cityNotOkSpan = $("#city-not-ok-icon");

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
        usernameNotOkSpan.show();
        usernameInputErrorObj.show();
        usernameOkSpan.hide();
    }

    function showEmailInputError() {
        emailForm.removeClass("has-success");
        emailForm.addClass("has-error");
        emailOkSpan.hide();
        emailNotOkSpan.show();
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
            type: "get",
            url: "/signup/checkLogin",
            cache: false,
            data: "login=" + $('#username').val(),
            success: function (response) {
                if (response.message) {
                    usernameForm.removeClass("has-error");
                    usernameForm.addClass("has-success");
                    usernameOkSpan.show();
                    usernameInputErrorObj.hide();
                    usernameBusyErrorObj.hide();
                    usernameNotOkSpan.hide();

                    ajaxLoadingUsername.loading('stop');
                } else {
                    usernameForm.removeClass("has-success");
                    usernameForm.addClass("has-error");
                    usernameNotOkSpan.show();
                    usernameBusyErrorObj.show();
                    usernameOkSpan.hide();

                    ajaxLoadingUsername.loading('stop');
                }
            },
            error: function (response) {
                alert("error");
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
            type: "get",
            url: "/signup/checkEmail",
            cache: false,
            data: "email=" + $('#email').val(),
            success: function (response) {
                if (response.message) {
                    emailForm.removeClass("has-error");
                    emailForm.addClass("has-success");
                    emailOkSpan.show();

                    emailNotOkSpan.hide();
                    emailInputErrorObj.hide();
                    emailBusyErrorObj.hide();

                    emailLoading.loading('stop');
                } else {
                    emailForm.removeClass("has-success");
                    emailForm.addClass("has-error");

                    emailBusyErrorObj.show();
                    emailNotOkSpan.show();
                    emailOkSpan.hide();

                    emailLoading.loading('stop');
                }
            },
            error: function (response) {
                alert("error");
            }
        });
    });

    cityObj.on('focusout', function(e) {
        //TODO проверить ID выдавший КЛАДР и вынести в отдельную функцию
        if (cityObj.val().trim() == "") {
            cityForm.addClass("has-error");
            cityOkSpan.hide();
            cityNotOkSpan.show();
        } else {
            cityForm.addClass("has-success");
            cityOkSpan.show();
            cityNotOkSpan.hide();
        }
    });

    firstnameObj.on('focusout', function(e) {
        if (firstnameObj.val().trim() == "") {
            firstnameForm.addClass("has-error");
            firstNameOkSpan.hide();
            firstNameNotOkSpan.show();
        } else {
            firstnameForm.addClass("has-success")
            firstNameOkSpan.show();
            firstNameNotOkSpan.hide();
        }
    });

    passwordObj.on('focusout', function(e) {
        if (passwordObj.val().trim() == "") {
            passwordForm.addClass("has-error");
            passwordOkSpan.hide();
            passwordNotOkSpan.show();
        } else {
            passwordForm.addClass("has-success")
            passwordOkSpan.show();
            passwordNotOkSpan.hide();
        }
    });

    emailObj.focus(function() {
        emailBusyErrorObj.hide();
        emailInputErrorObj.hide();
        emailForm.removeClass("has-error");
        emailForm.removeClass("has-success");
        emailNotOkSpan.hide();
        emailOkSpan.hide();
    });

    usernameObj.focus(function() {
        usernameInputErrorObj.hide();
        usernameBusyErrorObj.hide();
        usernameForm.removeClass("has-error");
        usernameForm.removeClass("has-success");
        usernameNotOkSpan.hide();
        usernameOkSpan.hide();
    });

    firstnameObj.focus(function() {
        firstnameForm.removeClass("has-error");
        firstnameForm.removeClass("has-success");
        firstNameOkSpan.hide();
        firstNameNotOkSpan.hide();
    });

    cityObj.focus(function() {
        cityForm.removeClass("has-error");
        cityForm.removeClass("has-success");
        cityOkSpan.hide();
        cityNotOkSpan.hide();
    });

    passwordObj.focus(function() {
        passwordForm.removeClass("has-error");
        passwordForm.removeClass("has-success");
        passwordOkSpan.hide();
        passwordNotOkSpan.hide();
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

        if (!validForm) {
            loadingSignUp.loading('stop');
            e.preventDefault();
            return false;
        }

        var data = {};
        $('input').each(function () {
            data[$(this).attr('name')] = $(this).val();
        });


        if (!social) {
            if (passwordObj.val().trim() == "") {
                passwordForm.addClass("has-error");
                validForm = false;
            }

            if (!validForm) {
                loadingSignUp.loading('stop');
                e.preventDefault();
                return false;
            }

            $.ajax({
                type: "post",
                url: "/signup/register",
                cache: false,
                data: data,
                success: function (response) {
                    if (response.message) {
                        window.location = "/profile?successes=signup_success";
                    }

                    loadingSignUp.loading('stop');
                },
                error: function (response) {
                    alert("error");
                }
            });

            e.preventDefault();
            return true;
        }

        data.id = $("#username").data("social");

        $.ajax({
            type: "post",
            url: "/signup/facebook/register",
            cache: false,
            data: data,
            success: function (response) {
                if (response.message) {
                    window.location = "/profile?successes=signup_success";
                }

                loadingSignUp.loading('stop');
            },
            error: function (response) {
                alert("error");
            }
        });

        e.preventDefault();
        return false;
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