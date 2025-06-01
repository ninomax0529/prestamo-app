/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.TratamientoMedico;
import java.util.List;

/**
 *
 * @author maximilianoalmonte
 */
public interface TratamientoMedicoService {

    public TratamientoMedico guardar(TratamientoMedico obj);

    public void eliminar(TratamientoMedico obj);

    public List<TratamientoMedico> getLista();

    public List<TratamientoMedico> getHistorial(int op);
    public List<TratamientoMedico> getListaActiva(int op);
    
     public Integer getCantidadBebida(int op);

}
