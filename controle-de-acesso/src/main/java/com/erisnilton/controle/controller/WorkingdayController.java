package com.erisnilton.controle.controller;

import com.erisnilton.controle.model.Workingday;
import com.erisnilton.controle.service.WorkingdayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jornada")
@Api(value = "Jornada Trabalho controller")
public record WorkingdayController (WorkingdayService service) {

    @PostMapping
    @ApiOperation(value = "Criar uma jornada de trabalho")
    public ResponseEntity<Workingday> createWorkingday( @RequestBody Workingday workingday, HttpServletResponse response){

        Workingday workingdaySaved = service.save(workingday);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(workingdaySaved.getId()).toUri();
        return ResponseEntity.created(uri).body(workingdaySaved);
    }

    @GetMapping
    @ApiOperation(value = "retorna todas as jornadas de trabalho")
    public List<Workingday> getAllJornadaTrabalho(){
        return  service.findWorkinsdays();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna uma jornada de trabaho pelo id")
    public ResponseEntity<Workingday> findWorkingdayByid(@PathVariable("id") Long id) throws Exception {
        Optional<Workingday> workingday = service.findById(id);
        return workingday.isPresent() ? ResponseEntity.ok(workingday.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping()
    @ApiOperation(value = "Atualiza uma jorndada de trabalho")
    public  Workingday updateWorkingday(@RequestBody Workingday workingday){
        return service.updateWorkingday(workingday);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta uma jornada de trabalho pelo id")
    public void deleteWorkingday(@PathVariable("id") Long id) {
        service.deleteWorkinday(id);
    }
}
