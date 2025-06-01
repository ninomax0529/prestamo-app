/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.TratamientoMedico;
import com.maxsoft.application.repo.TratamientoMedicoRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TratamientoMedicoServiceImpl implements TratamientoMedicoService {

    @Autowired
    TratamientoMedicoRepo repo;

    @Override
    public TratamientoMedico guardar(TratamientoMedico obj) {

        return repo.save(obj);
    }

    @Override
    public List<TratamientoMedico> getLista() {

        List<TratamientoMedico> lista = null;
        lista = repo.findAll();
//        
        return lista;
    }

    @Override
    public List<TratamientoMedico> getHistorial(int med) {

        List<TratamientoMedico> lista = null;
        lista = repo.getHistorial(med);
//        
        return lista;
    }

    @Override
    public void eliminar(TratamientoMedico obj) {

        repo.delete(obj);
    }

    @Override
    public Integer getCantidadBebida(int op) {

        return repo.getCantidadBebida(op);
    }

    @Override
    public List<TratamientoMedico> getListaActiva(int op) {

        List<TratamientoMedico> lista = null;
        lista = repo.getListaActiva(op);
//        
        return lista;
    }

}
