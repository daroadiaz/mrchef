package com.example.mrchefbackend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String descripcion;
    private String ingredientes;
    private String preparacion;

    //Getters
    public Integer getId(){
        return id;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getIngredientes(){
        return ingredientes;
    }

    public String getPreparacion(){
        return preparacion;
    }


    // Setters
    public void setId(Integer id){
        this.id = id;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setIngredientes(String ingredientes){
        this.ingredientes = ingredientes;
    }

    public void setPreparacion(String preparacion){
        this.preparacion = preparacion;
    }
}