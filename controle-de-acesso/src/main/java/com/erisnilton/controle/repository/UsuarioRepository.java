package com.erisnilton.controle.repository;

import com.erisnilton.controle.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
