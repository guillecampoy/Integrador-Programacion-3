package ar.edu.tup.programacion3.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public abstract class Base {
    @Id
    private Long id;
    private Boolean eliminado;
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Base base = (Base) object;
        return id != null && Objects.equals(id, base.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
