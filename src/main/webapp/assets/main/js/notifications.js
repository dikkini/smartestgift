$.showNotifications = function (ajaxResultObj) {
    var successMsgs = ajaxResultObj.successes;
    var errorMsgs = ajaxResultObj.errors;
    var infoMsgs = ajaxResultObj.information;

    var stack_bar_top = {"dir1":"down", "dir2":"right", "push":"top"};

    var opts = {
        type: "error",
        title: "Title",
        text: "",
        addclass: "stack-bar-top",
        cornerclass: "",
        width: "50%",
        nonblock: {
            nonblock: true,
            nonblock_opacity: .2
        },
        buttons: {
            sticker: false
        },
        stack: stack_bar_top
    };

    opts.title = "Success!";
    opts.type = "success";
    successMsgs.forEach(function (entry) {
        opts.text = entry;
        new PNotify(opts);
    });

    opts.title = "Error!";
    opts.type = "error";
    errorMsgs.forEach(function (entry) {
        opts.text = entry;
        new PNotify(opts);
    });

    opts.title = "Information!";
    opts.type = "info";
    infoMsgs.forEach(function (entry) {
        opts.text = entry;
        new PNotify(opts);
    });
};