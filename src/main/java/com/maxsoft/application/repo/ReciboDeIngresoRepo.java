/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.repo;

import com.maxsoft.application.modelo.DetalleReciboDeIngreso;
import com.maxsoft.application.modelo.ReciboDeIngreso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Maximiliano
 */
public interface ReciboDeIngresoRepo extends JpaRepository<ReciboDeIngreso, Integer> {

    String strLista = """
                       select * from  recibo_de_ingreso 
                        where 
                        anulado=:estado
                      
                       """;

    @Query(value = strLista, nativeQuery = true)
    public List<ReciboDeIngreso> getLista(@Param("estado") boolean estado);

    String strCant = """
                       select  count(*)  from  recibo_de_ingreso 
                      where 
                      anulado=false
                      and prestamo=:codPrestamo """;

    @Query(value = strCant, nativeQuery = true)
    public Integer getCantidadPago(@Param("codPrestamo") int codPrestamo);

    String strDetRecibo = """
                       select * from  detalle_recibo_de_ingreso  d
                       where 
                       d.recibo=:codRecibo """;

    @Query(value = strDetRecibo, nativeQuery = true)
    List<DetalleReciboDeIngreso> getDetalleRecibo(@Param("codRecibo") int codRecibo);
    
        String strRecibo = """
                               
                       select *
                       from  recibo_de_ingreso  r
                       where codigo=:codRecibo 
                         """;

    @Query(value = strRecibo, nativeQuery = true)
    public ReciboDeIngreso getReciboDeIngreso(@Param("codRecibo") int codRecibo);

}
