/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.service;

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
        return monto;
    }

    @Override
    public Double getMontoPagado(int codPrestamo) {

        Double monto = repo.getMontoPagado(codPrestamo);
        return monto;
    }

    @Override
    public Double getPrestamoPendiente(int codCliente) {

        Double monto = repo.getPrestamoPendiente(codCliente);
        return monto;
    }

}
