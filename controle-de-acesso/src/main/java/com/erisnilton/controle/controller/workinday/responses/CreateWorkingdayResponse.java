package com.erisnilton.controle.controller.workinday.responses;

import com.erisnilton.controle.model.Workingday;

public record CreateWorkingdayResponse(
        Long id, String description) {

    public static CreateWorkingdayResponse fromWorkingday(Workingday workingday) {

        return new CreateWorkingdayResponse(workingday.id(), workingday.description());

    }

}
