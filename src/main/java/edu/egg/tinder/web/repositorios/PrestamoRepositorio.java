/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.egg.tinder.web.repositorios;

import edu.egg.tinder.web.entidades.Cliente;
import edu.egg.tinder.web.entidades.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Integer>{
    @Query("SELECT c FROM Prestamo c WHERE c.cliente = :cliente")
    public Prestamo buscarPrestamoXCliente(@Param("cliente") Cliente cliente);

} 
