/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.DetallePrestamo;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.repo.PrestamoRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    PrestamoRepo repo;

    @Override
    public Prestamo guardar(Prestamo obj) {

        Prestamo p = repo.save(obj);
        return p;
    }

    @Override
    public List<Prestamo> getLista() {

        List<Prestamo> lista = repo.findAll();
        return lista;

    }

    @Override
    public List<Prestamo> getPrestamoPendiente() {

        List<Prestamo> lista = repo.getPrestamoPendiente();
        return lista;
    }

    @Override
    public Double getMontoPendiente(int codPrestamo) {

        Double monto = repo.getMontoPendiente(codPrestamo);
        return monto == null ? 0.00 : monto;
    }

    @Override
    public Double getMontoPagado(int codPrestamo) {

        Double monto = repo.getMontoPagado(codPrestamo);
        return monto == null ? 0.00 : monto;
    }

    @Override
    public Double getPrestamoPendiente(int codCliente) {

        Double monto = repo.getPrestamoPendiente(codCliente);
        return monto == null ? 0.00 : monto;
    }

    @Override
    public List<DetallePrestamo> getDetallePrestamo(int codPrestamo) {

        List<DetallePrestamo> lista = repo.getDetallePrestamo(codPrestamo);

        return lista;
    }

    @Override
    public List<DetallePrestamo> getDetalleCuotaPendiente(int codPrestamo) {

        List<DetallePrestamo> lista = repo.getDetalleCuotaPendiente(codPrestamo);

        System.out.println("cant " + lista.size());

        return lista;
    }

    @Override
    public Double getMontoPendienteCuota(int codPrestamo, int codCuota) {

        Double monto = repo.getMontoPendienteCuota(codPrestamo, codCuota);
        return monto == null ? 0.00 : monto;
    }

    @Override
    public Double getMontoPagadoCuota(int codPrestamo, int codCuota) {

        Double monto = repo.getMontoPagadoCuota(codPrestamo, codCuota);
        return monto == null ? 0.00 : monto;
    }

    @Override
    public Prestamo getPrestamo(int obj) {
       
        Prestamo p=repo.getPrestamo(obj);
        System.out.println("P : "+p);
        return p;
    }

}
