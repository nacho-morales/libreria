/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.egg.tinder.web.servicios;

import edu.egg.tinder.web.excepciones.ErrorServicio;
import edu.egg.tinder.web.repositorios.AutorRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import edu.egg.tinder.web.entidades.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ignacio
 */
@Service
public class AutorServicio {
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Transactional     
    public void crearAutor(Integer id, String nombre, boolean alta) throws ErrorServicio {
        
        validarAutor(id,nombre,alta);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setId(id);
        autor.setAlta(alta);
        
        autorRepositorio.save(autor);
        
    }
    
    public void validarAutor(Integer id, String nombre, boolean alta) throws ErrorServicio  {
        
        if ( nombre == null || nombre.isEmpty() ) {
            throw new ErrorServicio("El nombre del autor no puede ser nulo");
        }
         if ( id == null ) {
            throw new ErrorServicio("El id del autor no puede estar vacio ");
          
    }
    }
    @Transactional
    public void modificarAutor(Integer id, String nombre, boolean alta) throws ErrorServicio {
        validarAutor(id,nombre,alta);
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
                
        Autor autor = autorRepositorio.findById(id).get();
        autor.setNombre(nombre);
        autor.setId(id);
        autor.setAlta(alta);
        
        autorRepositorio.save(autor);
        
        } else {
                throw new ErrorServicio("No se encontro el autor ");
                }
        
         }
}

