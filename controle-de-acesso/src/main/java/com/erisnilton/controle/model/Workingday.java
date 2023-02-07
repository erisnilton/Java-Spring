package com.erisnilton.controle.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Accessors(chain = true, fluent = true)
@Entity
@Audited
public class Workingday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    public static Workingday create() {
        return new Workingday();
    }

}
