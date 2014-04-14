$(document).ready(function () {
    var usernameOkIcon = $("#username-ok-icon");
    var usernameNotOkIcon = $("#username-not-ok-icon");
    var emailOkIcon = $("#email-ok-icon");
    var emailNotOkIcon = $("#email-not-ok-icon");
    var cityOkIcon = $("#city-ok-icon");
    var cityNotOkIcon = $("#city-not-ok-icon");

    var usernameObj = $("#username");
    var emailObj = $("#email");

    var usernameForm = usernameObj.parent().parent();
    var emailForm = emailObj.parent().parent();

    $(".loading").loading({width: '25', text: 'Waiting...'});



    usernameObj.on('keydown focusout', function (e) {
        var ajaxLoadingUsername = $("#loading-username");
        ajaxLoadingUsername.loading('start');

        var usernameVal = usernameObj.val();
        var usernameRegexp = new RegExp("^[a-zA-Z]{5,255}$");
        if (!usernameRegexp.test(usernameVal)) {
            usernameForm.removeClass("has-success");
            usernameForm.addClass("has-error");
            usernameNotOkIcon.show();
            usernameOkIcon.hide();

            ajaxLoadingUsername.loading('stop');
            return;
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
                    usernameNotOkIcon.hide();
                } else {
                    usernameForm.removeClass("has-success");
                    usernameForm.addClass("has-error");
                    usernameNotOkIcon.show();
                    usernameOkIcon.hide();
                }
                ajaxLoadingUsername.loading('stop');
            },
            error: function (response) {
                alert(response.error);
            }
        });
    });

    usernameObj.focus(function() {
        usernameNotOkIcon.hide();
        usernameOkIcon.hide();
        usernameForm.removeClass("has-error")
    });

    emailObj.on('focusout', function (e) {
        $("#loading-email").loading('start');
        $.ajax({
            type: "post",
            url: "/signup/checkEmail",
            cache: false,
            data: "email=" + $('#email').val(),
            success: function (response) {
                if (response.success) {
                    emailForm.addClass("has-success");
                    emailOkIcon.show();
                    emailNotOkIcon.hide();
                } else {
                    emailForm.addClass("has-error");
                    emailOkIcon.show();
                    emailNotOkIcon.hide();
                }
                $("#loading-email").loading('stop');
            },
            error: function (response) {
                alert(response.error);
            }
        });
    });

    $("#sign-up-btn").click(function (e) {
        $("#loading-sign-up").loading('start');

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
                    window.location = $.updateQueryStringParameter(window.location.href, "errors", response.errors);
                }
                $("#loading-sign-up").loading('stop');
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
                    return obj.name;
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