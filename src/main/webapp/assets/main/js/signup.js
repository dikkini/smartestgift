$(document).ready(function () {
    $(".loading").loading({width: '25', text: 'Waiting...'});

    $("#username").on('focusout', function (e) {
        $("#loading-username").loading('start');
        $.ajax({
            type: "post",
            url: "/signup/checkLogin",
            cache: false,
            data: "login=" + $('#username').val(),
            success: function (response) {
                var imageSelector = $("#username-status");
                if (response.success) {
                    imageSelector.attr("src", "/assets/main/images/ok.png");
                } else {
                    imageSelector.attr("src", "/assets/main/images/not_ok.png");
                }
                $("#loading-username").loading('stop');
                imageSelector.show();
            },
            error: function (response) {
                alert(response.error);
            }
        });
    });

    $("#email").on('focusout', function (e) {
        $("#loading-email").loading('start');
        $.ajax({
            type: "post",
            url: "/signup/checkEmail",
            cache: false,
            data: "email=" + $('#email').val(),
            success: function (response) {
                var imageSelector = $("#email-status");
                if (response.success) {
                    imageSelector.attr("src", "/assets/main/images/ok.png");
                } else {
                    imageSelector.attr("src", "/assets/main/images/not_ok.png");
                }
                $("#loading-email").loading('stop');
                imageSelector.show();
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