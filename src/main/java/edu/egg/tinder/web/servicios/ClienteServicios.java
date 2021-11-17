package edu.egg.tinder.web.servicios;

import edu.egg.tinder.web.excepciones.ErrorServicio;

import edu.egg.tinder.web.repositorios.ClienteRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import edu.egg.tinder.web.entidades.Cliente;
import edu.egg.tinder.web.entidades.Foto;
import edu.egg.tinder.web.entidades.Zona;
import edu.egg.tinder.web.repositorios.ZonaRepositorio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteServicios implements UserDetailsService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private NotificacionServicio notificacionServicio;
    @Autowired
    private FotoServicio fotoServicio;
    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @Transactional
    public void crearCliente(MultipartFile archivo, String nombre, String apellido, String documento, String email, String clave, String clave2, Integer idZona) throws ErrorServicio {
        Zona zona = zonaRepositorio.getOne(idZona);
        validarCliente(nombre, apellido, documento, email, clave, clave2, zona);

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);

        cliente.setDocumento(documento);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setZona(zona);
        String enciptada = new BCryptPasswordEncoder().encode(clave);
        cliente.setClave(enciptada);

        Foto foto = fotoServicio.guardar(archivo);
        cliente.setFoto(foto);

        clienteRepositorio.save(cliente);
//        notificacionServicio.enviarMail("Bienvenidos a la libreria", "Libreria de barrio", cliente.getEmail());

    }

    public void validarCliente(String nombre, String apellido, String documento, String email, String clave, String clave2, Zona zona) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del cliente no puede ser nulo");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del cliente no puede ser nulo");
        }
        if (documento == null || documento.isEmpty()) {
            throw new ErrorServicio("El documento del cliente no puede ser nulo");
        }
        if (email == null || email.isEmpty()) {
            throw new ErrorServicio("El email del cliente no puede ser nulo");
        }
        if (clave == null || clave.isEmpty() || clave.length() <= 6) {
            throw new ErrorServicio("La clave del cliente no puede ser nulo o menor a 6 caracteres");
        }
        if (!clave.equals(clave2)) {
            throw new ErrorServicio("La claves deben ser iguales");
        }
        if (zona == null) {
            throw new ErrorServicio("No se encontro la zona solictida");
        }

    }

    @Transactional
    public void modificarCliente(Integer id, String documento, String nombre, String apellido, String email,MultipartFile archivo, String clave, String clave2, Integer idZona) throws ErrorServicio {
        Zona zona = zonaRepositorio.getOne(idZona);
        validarCliente(nombre, apellido, documento, email, clave, clave2, zona);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Cliente cliente = clienteRepositorio.findById(id).get();
            cliente.setNombre(nombre);
            cliente.setId(id);
            cliente.setDocumento(documento);
            cliente.setApellido(apellido);
            cliente.setEmail(email);
            cliente.setZona(zona);

            String enciptada = new BCryptPasswordEncoder().encode(clave);
            cliente.setClave(enciptada);

            
            Integer idFoto = null;
            if(cliente.getFoto() != null) {
                idFoto = cliente.getFoto().getidFoto();
            }
            Foto foto = fotoServicio.actualizar(idFoto,archivo);
            cliente.setFoto(foto);
            clienteRepositorio.save(cliente);

        } else {
            throw new ErrorServicio("No se encontro el cliente ");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepositorio.buscarClienteXEmail(email);
        if (cliente != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);

            ServletRequestAttributes atrr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = atrr.getRequest().getSession(true);
            session.setAttribute("usuariosession", cliente);

            User user = new User(cliente.getEmail(), cliente.getClave(), permisos);

            return user;
        } else {
            return null;
        }
    }

    public Cliente buscarXId(Integer id) {
        try {
        Cliente cliente = clienteRepositorio.buscarClienteXId(id);
        return cliente;
        } catch (Exception e ) {
                    System.err.println(e.getMessage());
                        
                        }
        return null;

    }
}
