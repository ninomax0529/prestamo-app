/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.repo;

import com.maxsoft.application.modelo.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Maximiliano
 */
public interface ClienteRepo extends JpaRepository<Cliente, Integer> {
    
    String strLista = """
                       select * from  cliente 
                        where 
                        habilitado=:estado                      
                       """;     
    
    @Query(value = strLista, nativeQuery = true)
    public List<Cliente> getLista(@Param("estado") boolean estado);
    
}
