/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.egg.tinder.web.servicios;

import edu.egg.tinder.web.entidades.Foto;
import edu.egg.tinder.web.excepciones.ErrorServicio;
import edu.egg.tinder.web.repositorios.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ignacio
 */
@Service
public class FotoServicio {
    @Autowired
    private FotoRepositorio  fotoRepositorio;
    @Transactional
    public Foto guardar(MultipartFile archivo) throws ErrorServicio {
        if ( archivo != null) {
            try {
            Foto foto = new Foto();
            foto.setMime(archivo.getContentType());
            foto.setNombre(archivo.getName());
            foto.setContenido(archivo.getBytes());
            
            
            return fotoRepositorio.save(foto);
            } catch(Exception e ) {
                    System.err.println(e.getMessage());
                    }
      
    }
    return null; 
}
   @Transactional 
 public Foto actualizar(Integer idFoto, MultipartFile archivo) throws ErrorServicio {
        if ( archivo != null) {
            try {
            Foto foto = new Foto();
            if (idFoto != null){
                Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                if(respuesta.isPresent()) {
                    foto = respuesta.get();
                }
            }
            foto.setMime(archivo.getContentType());
            foto.setNombre(archivo.getName());
            foto.setContenido(archivo.getBytes());
            
            
            return fotoRepositorio.save(foto);
            } catch(Exception e ) {
                    System.err.println(e.getMessage());
                    }
      
    }
    return null;    
    
}
}

