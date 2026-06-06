package com.utn.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Base {
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    protected Base() {
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Base base)) return false;
        return eliminado == base.eliminado && Objects.equals(id, base.id) && Objects.equals(createdAt, base.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eliminado, createdAt);
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", createdAt=" + createdAt +
                '}';
    }
}
