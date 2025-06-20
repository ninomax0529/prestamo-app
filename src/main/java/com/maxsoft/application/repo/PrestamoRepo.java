/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.repo;

import com.maxsoft.application.modelo.DetallePrestamo;
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
    
        String    strDetPrestamo = """
                      select * from  detalle_prestamo  d
                       where 
                       d.prestamo=:codPrestamo """;

    @Query(value = strDetPrestamo, nativeQuery = true)
    public List<DetallePrestamo> getDetallePrestamo(@Param("codPrestamo") int codPrestamo);

    String strMontPendiente = """
                               
                       select   (total-F_TOTAL_ABONO(p.codigo))
                       from  prestamo  p
                       where anulado=false
                       and codigo=:codPrestamo  """;

    @Query(value = strMontPendiente, nativeQuery = true)
    public Double getMontoPendiente(@Param("codPrestamo") int codPrestamo);

    String strPrestamoPendiente = """
                               
                       select   ifnull((total-F_TOTAL_ABONO(p.codigo)),0)
                       from  prestamo  p
                       where anulado=false
                       and cliente=:codCliente  """;

    @Query(value = strPrestamoPendiente, nativeQuery = true)
    public Double getPrestamoPendiente(@Param("codCliente") int codCliente);

    String strAbonado = """
                               
                       select F_TOTAL_ABONO(p.codigo)
                       from  prestamo  p
                       where anulado=false
                       and codigo=:codPrestamo  """;

    @Query(value = strAbonado, nativeQuery = true)
    public Double getMontoPagado(@Param("codPrestamo") int codPrestamo);

}
