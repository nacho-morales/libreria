/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.egg.tinder.web.repositorios;

import edu.egg.tinder.web.entidades.Cliente;
import edu.egg.tinder.web.entidades.Libro;
import edu.egg.tinder.web.entidades.Prestamo;
import edu.egg.tinder.web.entidades.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ignacio
 */
    @Repository
public interface ZonaRepositorio extends JpaRepository<Zona, Integer>{
    
    
     

} 

