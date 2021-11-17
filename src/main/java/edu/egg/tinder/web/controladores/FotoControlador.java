/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.egg.tinder.web.controladores;

import edu.egg.tinder.web.entidades.Cliente;
import edu.egg.tinder.web.excepciones.ErrorServicio;
import edu.egg.tinder.web.servicios.ClienteServicios;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ignacio
 */
@Controller
@RequestMapping("/foto")
public class FotoControlador {
    @Autowired
    private ClienteServicios clienteServicios;
    
    @GetMapping("/cliente/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable Integer id) throws ErrorServicio {
        
        try {
        Cliente cliente = clienteServicios.buscarXId(id);
        
        if(cliente.getFoto() == null) {
            throw new ErrorServicio("El usuario no tiene foto asignada");
        }
       byte[] foto = cliente.getFoto().getContenido();
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.IMAGE_JPEG);
       return new ResponseEntity<>(foto,headers,HttpStatus.OK);
        } catch(ErrorServicio ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE,null,ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    
    
}
