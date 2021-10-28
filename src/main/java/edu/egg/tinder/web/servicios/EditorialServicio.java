package edu.egg.tinder.web.servicios;

import edu.egg.tinder.web.excepciones.ErrorServicio;

import edu.egg.tinder.web.repositorios.EditorialRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;

import edu.egg.tinder.web.entidades.Editorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ignacio
 */
@Service
public class EditorialServicio {
    @Autowired
    private EditorialRepositorio editorialRepositorio;
            @Transactional
    public void crearEditorial(Integer id, String nombre, boolean alta) throws ErrorServicio {
        
        validarEditorial(id,nombre,alta);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setId(id);
        editorial.setAlta(alta);
        
        editorialRepositorio.save(editorial);
        
    }
    
    public void validarEditorial(Integer id, String nombre, boolean alta) throws ErrorServicio  {
        
        if ( nombre == null || nombre.isEmpty() ) {
            throw new ErrorServicio("El nombre de la editorial no puede ser nulo");
        }
         if ( id == null ) {
            throw new ErrorServicio("El id de la editorial no puede estar vacio ");
          
    }
    }
    @Transactional
    public void modificarEditorial(Integer id, String nombre, boolean alta) throws ErrorServicio {
        validarEditorial(id,nombre,alta);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
                
        Editorial editorial = editorialRepositorio.findById(id).get();
        editorial.setNombre(nombre);
        editorial.setId(id);
        editorial.setAlta(alta);
        
        editorialRepositorio.save(editorial);
        
        } else {
                throw new ErrorServicio("No se encontro la editorial ");
                }
        
         }
}
