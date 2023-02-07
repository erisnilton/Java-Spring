package erisnilton.dev.admin.catalogo.infraestrutura.api.controllers;

import erisnilton.dev.admin.catalogo.infraestrutura.api.CastmemberAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CastmemberController implements CastmemberAPI {

    @Override
    public ResponseEntity<?> create(final Object input) {
        return null;
    }
}
