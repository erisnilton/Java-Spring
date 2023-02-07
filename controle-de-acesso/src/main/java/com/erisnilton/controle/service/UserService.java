package com.erisnilton.controle.service;

import com.erisnilton.controle.model.User;
import com.erisnilton.controle.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record UserService(UserRepository repository) {


    public User createUser(User user) {
        return repository.save(user);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return repository.findById(id);
    }

    public User updateUsuario(User usuario) {
        return repository.save(usuario);
    }

    public void deleteUsuer(Long id) {
        repository.deleteById(id);
    }
}
