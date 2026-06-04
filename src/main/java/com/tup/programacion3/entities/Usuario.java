package com.tup.programacion3.entities;

import com.tup.programacion3.enums.Rol;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contraseña;
    private Rol rol;
    private Set<Pedido> pedidos;

    public Usuario() {
        this.pedidos = new HashSet<>();
    }

    public Usuario(String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contraseña = contraseña;
        this.rol = rol;
        this.pedidos = new HashSet<>();
    }

    public void addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(mail, usuario.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", mail='" + mail + '\'' +
                ", celular='" + celular + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", rol=" + rol +
                ", pedidos=" + pedidos.size() +
                '}';
    }
}
