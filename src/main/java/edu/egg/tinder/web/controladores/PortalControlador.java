package edu.egg.tinder.web.controladores;

import edu.egg.tinder.web.entidades.Zona;
import edu.egg.tinder.web.excepciones.ErrorServicio;
import edu.egg.tinder.web.repositorios.ZonaRepositorio;
import edu.egg.tinder.web.servicios.ClienteServicios;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ignacio
 */
@Controller
@RequestMapping
public class PortalControlador {

    @Autowired
    private ClienteServicios clienteServicio;
    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o clave incorrectas");

        }
        if (logout != null) {
            model.put("logout", "Ha salido correctamente de su sesion");

        }
        return "login.html";
    }

    @GetMapping("/registro")
    public String registro(ModelMap modelo) {
        List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);
        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String documento, @RequestParam String clave, @RequestParam String clave2,@RequestParam Integer idZona) throws ErrorServicio {
        try {
            clienteServicio.crearCliente(archivo,nombre, apellido, documento, email, clave, clave2,idZona);
        } catch (ErrorServicio ex) {
            List<Zona> zonas = zonaRepositorio.findAll();
            modelo.put("zonas", zonas);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("documento", documento);
            modelo.put("archivo", archivo);
            modelo.put("email", email);
            modelo.put("clave", clave);
            modelo.put("clave", clave2);

            Logger.getLogger(PortalControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "registro.html";
        }
        modelo.put("titulo", "Bienvenido a la biblioteca virtual !");
        modelo.put("descripcion", "El usuario ha sido registrado con exito! ");

        return "exito.html";
    }
}
