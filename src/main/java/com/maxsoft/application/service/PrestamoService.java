/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.DetallePrestamo;
import com.maxsoft.application.modelo.Prestamo;
import java.util.List;

/**
 *
 * @author Maximiliano
 */
public interface PrestamoService {

    Prestamo guardar(Prestamo obj);

    Prestamo getPrestamo(int obj);

    List<Prestamo> getLista();
    List<Prestamo> getPrestamoSaldado();

    List<Prestamo> getPrestamoPendiente();

    Double getMontoPendiente(int codPrestamo);

    Double getMontoPagado(int codPrestamo);

    Double getPrestamoPendiente(int codCliente);

    List<DetallePrestamo> getDetallePrestamo(int codPrestamo);

    List<DetallePrestamo> getDetalleCuotaPendiente(int codPrestamo);

    Double getMontoPendienteCuota(int codPrestamo, int codCuota);

    Double getMontoPagadoCuota(int codPrestamo, int codCuota);
    
    Double getInteresPagadoCuota(int codPrestamo, int codCuota);
}
