/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.egg.tinder.web.repositorios;


import edu.egg.tinder.web.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Integer>{
    @Query("SELECT c FROM Libro c WHERE c.titulo = :titulo")
    public Libro buscarLibroXNombre(@Param("titulo") String titulo);

} 
