package edu.egg.tinder.web.servicios;

import edu.egg.tinder.web.excepciones.ErrorServicio;

import edu.egg.tinder.web.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;


import edu.egg.tinder.web.entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;



@Service
public class ClienteServicios implements UserDetailsService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private NotificacionServicio notificacionServicio;
     @Transactional
    public void crearCliente(Integer id, String documento, String nombre, String apellido, String email,String clave) throws ErrorServicio {

        validarCliente(id, documento, nombre, apellido, email);
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setId(id);
        cliente.setDocumento(documento);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        String enciptada  = new BCryptPasswordEncoder().encode(clave);
            cliente.setClave(enciptada);

        clienteRepositorio.save(cliente);
        notificacionServicio.enviarMail("Bienvenidos a la libreria", "Libreria de barrio", cliente.getEmail());

    }

    public void validarCliente(Integer id, String documento, String nombre, String apellido, String email) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del cliente no puede ser nulo");
        }
        if (id == null) {
            throw new ErrorServicio("El id del cliente no puede estar vacio ");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El nombre del cliente no puede ser nulo");
        }
        if (email == null || email.isEmpty()) {
            throw new ErrorServicio("El nombre del cliente no puede ser nulo");
        }

    }
     @Transactional
    public void modificarCliente(Integer id, String documento, String nombre, String apellido, String email,String clave) throws ErrorServicio {
        validarCliente(id, documento, nombre, apellido, email);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Cliente cliente = clienteRepositorio.findById(id).get();
            cliente.setNombre(nombre);
            cliente.setId(id);
            cliente.setDocumento(documento);
            cliente.setApellido(apellido);
            cliente.setEmail(email);
            String enciptada  = new BCryptPasswordEncoder().encode(clave);
            cliente.setClave(enciptada);
            clienteRepositorio.save(cliente);

        } else {
            throw new ErrorServicio("No se encontro el cliente ");
        }

    }
    @Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {
        Cliente cliente = clienteRepositorio.buscarClienteXDocumento(documento);
        if ( cliente != null ) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_AUTOR");
            permisos.add(p1);
            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_PRESTAMO");
            permisos.add(p2);
            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_EDITORIAL");
            permisos.add(p3);
            
            
            User user = new User(cliente.getDocumento(), cliente.getClave(), permisos);
            
            
            
            
            return user;
    } else {
            return null;
        }
}
    } 


    