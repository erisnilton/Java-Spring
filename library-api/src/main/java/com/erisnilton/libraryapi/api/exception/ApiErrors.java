package com.erisnilton.libraryapi.api.exception;

import com.erisnilton.libraryapi.exception.BussinessException;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ApiErrors {
    private List<String> errors;

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));

    }

    public ApiErrors(BussinessException bussinessException) {
        this.errors = Arrays.asList(bussinessException.getMessage());
    }
}
