package com.erisnilton.controle.controller.workinday.responses;

import com.erisnilton.controle.model.Workingday;

public record GetWorkingdaysResponse(Long id, String description) {

    public static GetWorkingdaysResponse fromWorkingday(Workingday workingday) {
        return new GetWorkingdaysResponse(workingday.id(), workingday.description());
    }
}
