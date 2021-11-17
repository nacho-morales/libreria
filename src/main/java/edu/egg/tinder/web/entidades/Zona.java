/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.egg.tinder.web.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ignacio
 */
@Entity
public class Zona implements Serializable {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idZona",unique=true, nullable = false)
    private Integer idZona;
    private String nombre;

    public Zona() {
    }

    public Zona(Integer idZona, String nombre) {
        this.idZona = idZona;
        this.nombre = nombre;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Zona{" + "idZona=" + idZona + ", nombre=" + nombre + '}';
    }
    
    
    
    
    
}
