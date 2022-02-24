package com.erisnilton.controle.controller.workinday;

import com.erisnilton.controle.controller.workinday.requests.CreateWorkingdayRequest;
import com.erisnilton.controle.controller.workinday.responses.CreateWorkingdayResponse;
import com.erisnilton.controle.controller.workinday.responses.GetWorkingdayResponse;
import com.erisnilton.controle.controller.workinday.responses.GetWorkingdaysResponse;
import com.erisnilton.controle.model.Workingday;
import com.erisnilton.controle.service.WorkingdayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.erisnilton.controle.controller.workinday.responses.CreateWorkingdayResponse.fromWorkingday;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/workingdays")
@Api(value = "Jornada Trabalho controller")
public record WorkingdayController(WorkingdayService service) {

    @PostMapping
    @ApiOperation(value = "Criar uma jornada de trabalho")
    @ResponseStatus(CREATED)
    public CreateWorkingdayResponse createWorkingday(@RequestBody CreateWorkingdayRequest resquest) {
        var workingday = service.save(resquest.toWorkingday());
        return fromWorkingday(workingday);
    }

    @GetMapping
    @ApiOperation(value = "retorna todas as jornadas de trabalho")
    public List<GetWorkingdaysResponse> getAllJornadaTrabalho() {
        return service.findWorkinsdays()
                .stream()
                .map(GetWorkingdaysResponse::fromWorkingday)
                .toList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna uma jornada de trabaho pelo id")
    public GetWorkingdayResponse findWorkingdayByid(@PathVariable("id") Long id) {

        var workingday = service.findById(id).
                orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Jornada de trabalho não encontrada"));

        return GetWorkingdayResponse.fromWorkingday(workingday);

    }

    @PutMapping()
    @ApiOperation(value = "Atualiza uma jorndada de trabalho")
    public Workingday updateWorkingday(@RequestBody Workingday workingday) {
        return service.updateWorkingday(workingday);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta uma jornada de trabalho pelo id")
    public void deleteWorkingday(@PathVariable("id") Long id) {
        service.deleteWorkinday(id);
    }
}
