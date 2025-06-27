/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.DetalleReciboDeIngreso;
import com.maxsoft.application.modelo.ReciboDeIngreso;
import com.maxsoft.application.repo.ReciboDeIngresoRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReciboDeIngresoServiceImpl implements ReciboDeIngresoService {

    @Autowired
    ReciboDeIngresoRepo repo;

    @Override
    public ReciboDeIngreso guardar(ReciboDeIngreso obj) {

        ReciboDeIngreso reci = repo.save(obj);
        return reci;
    }

    @Override
    public List<ReciboDeIngreso> getLista() {

        List<ReciboDeIngreso> lista = repo.findAll();

        return lista;

    }

    @Override
    public Integer getCantidadPago(int cliente) {

        return repo.getCantidadPago(cliente);
    }

    @Override
    public List<ReciboDeIngreso> getLista(boolean estado) {

        List<ReciboDeIngreso> lista = repo.getLista(estado);

        return lista;

    }

    @Override
    public List<DetalleReciboDeIngreso> getDetalleRecibo(int codRecibo) {

        List<DetalleReciboDeIngreso> lista = repo.getDetalleRecibo(codRecibo);

        return lista;
    }

    @Override
    public ReciboDeIngreso getReciboDeIngreso(int codRecibo) {

        ReciboDeIngreso r = repo.getReciboDeIngreso(codRecibo);
        return r;
    }

}
