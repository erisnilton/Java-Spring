package com.erisnilton.controle.controller.workinday.requests;

import com.erisnilton.controle.model.Workingday;

import static com.erisnilton.controle.model.Workingday.create;

public record CreateWorkingdayRequest(String description) {

    public Workingday toWorkingday() {

        return create().description(description);

    }

}
