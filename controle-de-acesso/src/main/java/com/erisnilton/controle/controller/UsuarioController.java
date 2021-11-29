package com.erisnilton.controle.controller;

import com.erisnilton.controle.model.Usuario;
import com.erisnilton.controle.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        return service.criarUsuario(usuario);
    }

}
