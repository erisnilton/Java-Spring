package com.erisnilton.controle.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Audited
public class NivelAccess {
    @Id
    private Long id;
    private String descricao;
}
