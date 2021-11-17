package edu.egg.tinder.web.servicios;

import edu.egg.tinder.web.excepciones.ErrorServicio;
import edu.egg.tinder.web.repositorios.ClienteRepositorio;
import edu.egg.tinder.web.repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import edu.egg.tinder.web.entidades.Cliente;
import edu.egg.tinder.web.entidades.Libro;
import edu.egg.tinder.web.entidades.Prestamo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author ignacio
 */
@Service
public class PrestamoServicios {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private NotificacionServicio notificacionServicio;
    @Transactional
    public void crearPrestamo(Integer id, Date fechaPrestamo, Date fechaDevolucion, Libro libro, Integer idcliente) throws ErrorServicio {

        validarPrestamo(id, fechaPrestamo, fechaDevolucion, libro);

        Cliente cliente = clienteRepositorio.findById(idcliente).get();
        Prestamo prestamo = new Prestamo();
        prestamo.setId(id);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setLibro(libro);
        prestamo.setCliente(cliente);

        prestamoRepositorio.save(prestamo);
        notificacionServicio.enviarMail("El prestamo ha sido creado", "Libreria de barrio", prestamo.getCliente().getEmail());
    }

    public void validarPrestamo(Integer id, Date fechaPrestamo, Date fechaDevolucion, Libro libro) throws ErrorServicio {

        if (fechaPrestamo == null) {
            throw new ErrorServicio("La fechaPrestamo de la prestamo no puede ser nulo");
        }
        if (fechaDevolucion == null) {
            throw new ErrorServicio("La fechaDevolucion de la prestamo no puede estar vacio ");
        }
        if (libro == null) {
            throw new ErrorServicio("El libro de la prestamo no puede estar vacio ");

        }

    }
    @Transactional
    public void modificarPrestamo(Integer id, Date fechaPrestamo, Date fechaDevolucion, Libro libro, String idcliente) throws ErrorServicio {
        validarPrestamo(id, fechaPrestamo, fechaDevolucion, libro);

        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Prestamo prestamo = prestamoRepositorio.findById(id).get();
            if (prestamo.getCliente().getId().equals(idcliente)) {
                prestamo.setId(id);
                prestamo.setFechaPrestamo(fechaPrestamo);
                prestamo.setFechaDevolucion(fechaDevolucion);
                prestamo.setLibro(libro);

                prestamoRepositorio.save(prestamo);

            } else {
                throw new ErrorServicio("No puede realizar la operacion ");
            }

        } else {
            throw new ErrorServicio("No se encontro el prestamo ");
        }
    }
}
