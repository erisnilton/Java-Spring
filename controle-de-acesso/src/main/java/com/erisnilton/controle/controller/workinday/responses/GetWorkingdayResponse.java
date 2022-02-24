package com.erisnilton.controle.controller.workinday.responses;

import com.erisnilton.controle.model.Workingday;

public record GetWorkingdayResponse(Long id, String description) {


    public static GetWorkingdayResponse fromWorkingday(Workingday workingday) {
        return new GetWorkingdayResponse(workingday.id(), workingday.description());
    }
}
