package com.erisnilton.controle.repository;

import com.erisnilton.controle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
