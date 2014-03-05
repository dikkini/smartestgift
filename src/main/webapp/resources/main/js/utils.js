$.updateQueryStringParameter = function(uri, key, value) {
    var re = new RegExp("([?|&])" + key + "=.*?(&|$)", "i");
    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (uri.match(re)) {
        return uri.replace(re, '$1' + key + "=" + value + '$2');
    } else {
        return uri + separator + key + "=" + value;
    }
};

$.updateNotifyBlockRequest = function(uri, successes, errors, information) {
    uri = $.clearRequestFromNotifications(uri);

    var errorsBlock = "errors=";
    var successesBlock = "successes=";
    var informationBlock = "information=";

    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (errors.length > 0) {
        uri += separator + errorsBlock;
        errors.forEach(function(error) {
            uri += error + ",";
        });
    }

    separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (information.length > 0) {
        uri += separator + informationBlock;
        information.forEach(function(info) {
            uri += info + ",";
        });
    }

    separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (successes.length > 0) {
        uri += separator + successesBlock;
        successes.forEach(function(success) {
            uri += success + ",";
        });
    }

    return uri;
};

$.clearRequestFromNotifications = function(uri) {
    var rtn = uri.split("?")[0],
        param,
        params_arr = [],
        queryString = (uri.indexOf("?") !== -1) ? uri.split("?")[1] : "";
    if (queryString !== "") {
        params_arr = queryString.split("&");
        for (var i = params_arr.length - 1; i >= 0; i -= 1) {
            param = params_arr[i].split("=")[0];
            if (param === "errors" || param === "information" || param === "successes") {
                params_arr.splice(i, 1);
            }
        }
        rtn = rtn + "?" + params_arr.join("&");
    }
    return rtn;
};