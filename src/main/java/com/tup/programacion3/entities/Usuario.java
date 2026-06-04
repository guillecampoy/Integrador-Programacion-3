package com.tup.programacion3.entities;

import com.tup.programacion3.enums.Rol;

import java.util.HashSet;

public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String password;
    private Rol rol;
    private HashSet<Pedido> pedidos;
    public Usuario() {}
}
