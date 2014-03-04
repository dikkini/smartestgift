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
    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (errors.length > 0) {
        uri += separator + "errors=";
        for (var error in errors) {
            uri += error + ",";
        }
    }

    separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (information.length > 0) {
        uri += separator + "information=";
        for (var info in information) {
            uri += info + ",";
        }
    }

    separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (successes.length > 0) {
        uri += separator + "successes=";
        for (var success in successes) {
            uri += success + ",";
        }
    }

    return uri;
};