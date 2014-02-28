$(document).ready(function() {
    $(".loading").loading({width:'25', text: 'Waiting...'});

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
                    imageSelector.attr("src", "/resources/main/images/ok.png");
                } else {
                    imageSelector.attr("src", "/resources/main/images/not_ok.png");
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
                    imageSelector.attr("src", "/resources/main/images/ok.png");
                } else {
                    imageSelector.attr("src", "/resources/main/images/not_ok.png");
                }
                $("#loading-email").loading('stop');
                imageSelector.show();
            },
            error: function (response) {
                alert(response.error);
            }
        });
    });

    $("#sign-up-btn").click(function(e) {
        $("#loading-sign-up").loading('start');

        var data = {};
        $('input').each(function() {
            data[$(this).attr('name')] = $(this).val();
        });

        $.ajax({
            type: "post",
            url: "/signup/register",
            cache: false,
            data: data,
            success: function (response) {
                if (response.success) {
                    window.location = "/profile";
                } else {
                    var a = window.location.url;
                    var b = window.location.href;
                    var c = window.location;
                    window.location = $.updateQueryStringParameter(b, "errors", response.errors);
                }
                $("#loading-sign-up").loading('stop');
            },
            error: function (response) {
                alert(response.error);
            }
        });

        e.preventDefault();
    });
});