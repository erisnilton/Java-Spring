package com.erisnilton.controle.controller;

import com.erisnilton.controle.model.JornadaTrabalho;
import com.erisnilton.controle.service.JornadaTrabalhoService;
import org.springframework.beans.factory.annotation.Autowired;
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

public class JornadaTrabalhoController {

    @Autowired
    JornadaTrabalhoService service;

    @PostMapping
    public ResponseEntity<JornadaTrabalho> createJornadaTrabalho(
            @RequestBody JornadaTrabalho jornadaTrabalho,
            HttpServletResponse response){
        JornadaTrabalho jornadaSalva = service.save(jornadaTrabalho);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(jornadaSalva.getId()).toUri();
        return ResponseEntity.created(uri).body(jornadaSalva);
    }

    @GetMapping
    public List<JornadaTrabalho> getAllJornadaTrabalho(){
        return  service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JornadaTrabalho> findByidJornadaTrabalho(@PathVariable("id") Long id) throws Exception {
        Optional<JornadaTrabalho> jornadaTrabalho = service.findPeloId(id);
        return jornadaTrabalho.isPresent() ? ResponseEntity.ok(jornadaTrabalho.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping()
    public  JornadaTrabalho updateJornadaTrabalho(@RequestBody JornadaTrabalho jornadaTrabalho) {
        return service.updateJornada(jornadaTrabalho);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJornadaTrabalho(@PathVariable("id") Long id) {
        service.deleteJornada(id);
    }
}
