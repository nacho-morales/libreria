
package edu.egg.tinder.web.repositorios;


import edu.egg.tinder.web.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer>{
    @Query("SELECT c FROM Cliente c WHERE c.email = :email")
    public Cliente buscarClienteXEmail(@Param("email") String email);
    
    @Query("SELECT c FROM Cliente c WHERE c.id = :id")
    public Cliente buscarClienteXId(@Param("id") Integer id);
} 

    

