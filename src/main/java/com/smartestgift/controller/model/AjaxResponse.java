package com.smartestgift.controller.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikkini on 26.02.14.
 * Email: dikkini@gmail.com
 */
public class AjaxResponse {

    protected boolean success;

    protected List<String> errors = new ArrayList<>();

    protected List<String> warnings = new ArrayList<>();

    protected List<String> information = new ArrayList<>();

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

    public List<String> getInformation() {
        return information;
    }

    public void setInformation(List<String> information) {
        this.information = information;
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public void addWarning(String warning) {
        this.warnings.add(warning);
    }

    public void addInformation(String information) {
        this.information.add(information);
    }
}
