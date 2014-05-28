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

function isValidDate(d) {
    if ( Object.prototype.toString.call(d) !== "[object Date]" )
        return false;
    return !isNaN(d.getTime());
};


/**
 * token:     description:             example:
 * #YYYY#     4-digit year             1999
 * #YY#       2-digit year             99
 * #MMMM#     full month name          February
 * #MMM#      3-letter month name      Feb
 * #MM#       2-digit month number     02
 * #M#        month number             2
 * #DDDD#     full weekday name        Wednesday
 * #DDD#      3-letter weekday name    Wed
 * #DD#       2-digit day number       09
 * #D#        day number               9
 * #th#       day ordinal suffix       nd
 * #hhh#      military/24-based hour   17
 * #hh#       2-digit hour             05
 * #h#        hour                     5
 * #mm#       2-digit minute           07
 * #m#        minute                   7
 * #ss#       2-digit second           09
 * #s#        second                   9
 * #ampm#     "am" or "pm"             pm
 * #AMPM#     "AM" or "PM"             PM
 */
Date.prototype.customFormat = function(formatString){
    var YYYY,YY,MMMM,MMM,MM,M,DDDD,DDD,DD,D,hhh,hh,h,mm,m,ss,s,ampm,AMPM,dMod,th;
    var dateObject = this;
    YY = ((YYYY=dateObject.getFullYear())+"").slice(-2);
    MM = (M=dateObject.getMonth()+1)<10?('0'+M):M;
    MMM = (MMMM=["January","February","March","April","May","June","July","August","September","October","November","December"][M-1]).substring(0,3);
    DD = (D=dateObject.getDate())<10?('0'+D):D;
    DDD = (DDDD=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"][dateObject.getDay()]).substring(0,3);
    th=(D>=10&&D<=20)?'th':((dMod=D%10)==1)?'st':(dMod==2)?'nd':(dMod==3)?'rd':'th';
    formatString = formatString.replace("#YYYY#",YYYY).replace("#YY#",YY).replace("#MMMM#",MMMM).replace("#MMM#",MMM).replace("#MM#",MM).replace("#M#",M).replace("#DDDD#",DDDD).replace("#DDD#",DDD).replace("#DD#",DD).replace("#D#",D).replace("#th#",th);

    h=(hhh=dateObject.getHours());
    if (h==0) h=24;
    if (h>12) h-=12;
    hh = h<10?('0'+h):h;
    AMPM=(ampm=hhh<12?'am':'pm').toUpperCase();
    mm=(m=dateObject.getMinutes())<10?('0'+m):m;
    ss=(s=dateObject.getSeconds())<10?('0'+s):s;
    return formatString.replace("#hhh#",hhh).replace("#hh#",hh).replace("#h#",h).replace("#mm#",mm).replace("#m#",m).replace("#ss#",ss).replace("#s#",s).replace("#ampm#",ampm).replace("#AMPM#",AMPM);
};