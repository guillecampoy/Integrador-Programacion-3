package ar.edu.tup.programacion3.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Base {
    @EqualsAndHashCode.Include
    private Long id;
    private Boolean eliminado;
    private LocalDateTime createdAt;

}
