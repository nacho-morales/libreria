/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.egg.tinder.web.controladores;

import edu.egg.tinder.web.entidades.Cliente;
import edu.egg.tinder.web.entidades.Zona;
import edu.egg.tinder.web.excepciones.ErrorServicio;
import edu.egg.tinder.web.repositorios.ClienteRepositorio;
import edu.egg.tinder.web.repositorios.ZonaRepositorio;
import edu.egg.tinder.web.servicios.ClienteServicios;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ignacio
 */
@Controller
@RequestMapping
public class UsuarioControlador {

    @Autowired
    private ClienteServicios clienteServicios;
    @Autowired
    private ZonaRepositorio zonaRepositorio;
    
@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/editar-perfil")
    public String editarPerfil(HttpSession session, @RequestParam Integer id, ModelMap model) {
        
        Cliente login = (Cliente) session.getAttribute("usuariosession");
        if ( login == null  || !login.getId().equals(id)) {
            return "redirect:/inicio";
            
        }
        List<Zona> zonas = zonaRepositorio.findAll();
        model.put("zonas", zonas);

        Cliente cliente = clienteServicios.buscarXId(id);
        model.addAttribute("perfil", cliente);
      return "perfil.html";
    }

    /**
     *
     * @param archivo
     * @param modelo
     * @param session
     * @param id
     * @param nombre
     * @param apellido
     * @param email
     * @param documento
     * @param clave
     * @param clave2
     * @param idZona
     * @return
     */
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @PostMapping("/actualizar-perfil")
    public String registrar (ModelMap modelo, HttpSession session, @RequestParam Integer id,  @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String documento,MultipartFile archivo, @RequestParam String clave, @RequestParam String clave2,@RequestParam Integer idZona){
        Cliente cliente =  null;
        Cliente login = (Cliente) session.getAttribute("usuariosession");
        if ( login == null  || !login.getId().equals(id)) {
            return "redirect:/inicio";
        }
        try {
        cliente = clienteServicios.buscarXId(id);
        clienteServicios.modificarCliente( id, documento, nombre, apellido, email,archivo, clave, clave2,idZona);
        session.setAttribute("usuariosession", cliente);
        return "redirect:/inicio";
        } catch (ErrorServicio ex) {
            List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);
        modelo.put("error", ex.getMessage());
        modelo.put("perfil", cliente);
        return "perfil.html";
        }
    }
    }
