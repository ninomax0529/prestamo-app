/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.repo;

import com.maxsoft.application.modelo.ReciboDeIngreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Maximiliano
 */
public interface ReciboDeIngresoRepo extends JpaRepository<ReciboDeIngreso, Integer>{
    
    
    String strCant = """
                       select  count(*)  from  recibo_de_ingreso 
                      where 
                      anulado=false
                      and cliente=:codClint """;

    @Query(value = strCant, nativeQuery = true)
    public Integer getCantidadPago(@Param("codClint") int op);

}
