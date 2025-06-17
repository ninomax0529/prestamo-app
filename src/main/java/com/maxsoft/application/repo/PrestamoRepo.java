/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.repo;

import com.maxsoft.application.modelo.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Maximiliano
 */
public interface PrestamoRepo extends JpaRepository<Prestamo, Integer> {

    String strPrestamo = """
                       select  * from  prestamo  p
                       where anulado=false
                       and  (monto_prestado-F_TOTAL_ABONO(p.codigo))>0 """;

    @Query(value = strPrestamo, nativeQuery = true)
    public List<Prestamo> getPrestamoPendiente();
    
           String strAbonado = """
                               
                       select   (monto_prestado-F_TOTAL_ABONO(p.codigo))
                       from  prestamo  p
                       where anulado=false
                       and codigo=:codPrestamo  """;
       
    @Query(value = strAbonado, nativeQuery = true)
    public Double getMontoPendiente(@Param("codPrestamo") int codPrestamo);
    
        
}
