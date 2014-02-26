package com.smartestgift.controller.model;

import java.util.List;

/**
 * Created by dikkini on 26.02.14.
 * Email: dikkini@gmail.com
 */
public class AjaxResponse {

    protected boolean success;

    protected List<String> errors;

    protected List<String> warnings;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
