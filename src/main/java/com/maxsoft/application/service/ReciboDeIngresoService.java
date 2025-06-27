/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.DetalleReciboDeIngreso;
import com.maxsoft.application.modelo.ReciboDeIngreso;
import java.util.List;

/**
 *
 * @author Maximiliano
 */
public interface ReciboDeIngresoService {

    ReciboDeIngreso guardar(ReciboDeIngreso obj);

    List<ReciboDeIngreso> getLista();

    List<ReciboDeIngreso> getLista(boolean estado);

    Integer getCantidadPago(int op);

    List<DetalleReciboDeIngreso> getDetalleRecibo(int codRecibo);

    ReciboDeIngreso getReciboDeIngreso(int codRecibo);

}
