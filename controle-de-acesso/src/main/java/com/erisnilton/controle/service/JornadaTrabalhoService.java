package com.erisnilton.controle.service;

import com.erisnilton.controle.model.JornadaTrabalho;
import com.erisnilton.controle.repository.JornadaTrabalhoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class JornadaTrabalhoService {

    JornadaTrabalhoRepository repository;

    @Autowired
    public JornadaTrabalhoService(JornadaTrabalhoRepository repository) {
        this.repository = repository;
    }

    public JornadaTrabalho save(JornadaTrabalho jornadaTrabalho) {
        return repository.save(jornadaTrabalho);
    }


    public List<JornadaTrabalho> findAll() {
        return repository.findAll();
    }

    public Optional<JornadaTrabalho> findPeloId(Long id) {
        return  repository.findById(id);
    }

    public JornadaTrabalho updateJornada(JornadaTrabalho jornada) {
        return repository.save(jornada);
    }

    public void deleteJornada(Long id) {
        repository.deleteById(id);
    }
}
