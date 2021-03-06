
$(document).ready(function () {

    // validation messages
    $.extend($.validator.messages, {
        required: validationMessages['validate.required'],
        equalTo: validationMessages['validate.equalTo'],
        email: validationMessages['validate.email'],
        maxlength: $.validator.format(validationMessages['validate.maxlength']),
        minlength: $.validator.format(validationMessages['validate.minlength'])
    });

    $( function() {
        $(".datepicker").datetimepicker({
            minDate: 0,
            format: jsLocaleStrings['datetimeformat'],
            formatTime: jsLocaleStrings['timeformat']
        });
        $.datetimepicker.setLocale(language);

        var tz = jstz.determine();
        var timeZone = tz.name();

        $("input#client-timezone").val(timeZone);
    });

    var $createGoalForm = $('#goal-create-form');

    $createGoalForm.validate();

    $createGoalForm.ajaxForm({
            success : function (response) {
                console.log("$createGoalForm success : " + JSON.stringify(response));
                if (response.success) {
                    var dataJson = response.data;
                    window.location = "/goal/view?uuid=" + dataJson.uuid;
                } else {
                    var errorsJson = response.data;
                    for (var i in errorsJson) {
                        if (errorsJson.hasOwnProperty(i)) {
                            var e = errorsJson[i];
                            if (e.code == "GoalDatesConstraint") {
                                // TODO process goal dates error
                                console.log("error: " + e.defaultMessage)
                                continue
                            }
                            console.log("field: " + e.field);
                            console.log("error message: " + e.defaultMessage);
                        }
                    }
                }
            },
            error: function(response) {
                console.log("$createGoalForm success : " + JSON.stringify(response));
            }
        });
});


