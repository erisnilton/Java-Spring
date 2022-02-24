package com.erisnilton.controle.service;

import com.erisnilton.controle.model.Workingday;
import com.erisnilton.controle.repository.WorkingdayRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record WorkingdayService (WorkingdayRepository repository) {

    public Workingday save(Workingday workingday) {
        return repository.save(workingday);
    }

    public List<Workingday> findWorkinsdays() {
        return repository.findAll();
    }

    public Optional<Workingday> findById(Long id) {
        return  repository.findById(id);
    }

    public Workingday updateWorkingday(Workingday workingday) {
        return repository.save(workingday);
    }

    public void deleteWorkinday(Long id) {
        repository.deleteById(id);
    }
}
