package com.erisnilton.controle.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class Location {
    @Id
    private Long id;
    @ManyToOne
    private NivelAccess nivelAccess;
    private String descricao;
}
