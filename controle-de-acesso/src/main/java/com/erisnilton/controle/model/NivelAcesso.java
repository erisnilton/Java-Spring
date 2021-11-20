package com.erisnilton.controle.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class NivelAcesso {
    @Id
    private Long id;
    private String descricao;
}
