package com.erisnilton.controle.repository;

import com.erisnilton.controle.model.Workingday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingdayRepository extends JpaRepository<Workingday, Long> {
}
