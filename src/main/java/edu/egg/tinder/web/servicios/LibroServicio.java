package edu.egg.tinder.web.servicios;

import edu.egg.tinder.web.excepciones.ErrorServicio;

import edu.egg.tinder.web.repositorios.LibroRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;

import edu.egg.tinder.web.entidades.Autor;
import edu.egg.tinder.web.entidades.Cliente;
import edu.egg.tinder.web.entidades.Editorial;
import edu.egg.tinder.web.entidades.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ignacio
 */
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
@Transactional
    public void crearLibro(Integer isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial) throws ErrorServicio {

        validarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, alta, autor, editorial);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAlta(alta);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);

    }

    public void validarLibro(Integer isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial) throws ErrorServicio {

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El nombre del libro no puede ser nulo");
        }
        if (autor == null) {
            throw new ErrorServicio("El autor del libro no puede estar vacio ");
        }
        if (editorial == null) {
            throw new ErrorServicio("La editoiral del libro no puede ser nula");
        }

    }
@Transactional
    public void modificarLibro(Integer isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Boolean alta, Autor autor, Editorial editorial) throws ErrorServicio {
        validarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, alta, autor, editorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);

        if (respuesta.isPresent()) {

            Libro libro = libroRepositorio.findById(isbn).get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            libro.setAlta(alta);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepositorio.save(libro);

        } else {
            throw new ErrorServicio("No se encontro el libro ");
        }

    }
}
