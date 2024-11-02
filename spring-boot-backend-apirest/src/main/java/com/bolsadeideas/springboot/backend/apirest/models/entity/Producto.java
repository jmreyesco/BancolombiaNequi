package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "sucursal_id") // La columna que será la clave foránea en la tabla productos
    private Sucursal sucursal; // Añadir la relación con Sucursal

    public Producto() {}

    public Producto(String nombre, int stock, Sucursal sucursal) { // Modificar el constructor
        this.nombre = nombre;
        this.stock = stock;
        this.sucursal = sucursal; // Asignar sucursal en el constructor
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Sucursal getSucursal() { // Método para obtener la sucursal
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) { // Método para establecer la sucursal
        this.sucursal = sucursal;
    }

    private static final long serialVersionUID = 1L;
}
