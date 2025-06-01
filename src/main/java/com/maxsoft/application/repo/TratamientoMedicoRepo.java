/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.repo;

import com.maxsoft.application.modelo.TratamientoMedico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author maximilianoalmonte
 */
public interface TratamientoMedicoRepo extends JpaRepository<TratamientoMedico, Integer> {

    String strDet = "  select * from  tratamiento_medico o where medicamento=:obj ";

    @Query(value = strDet, nativeQuery = true)
    public List<TratamientoMedico> getHistorial(@Param("obj") int op);

    String strActivo = "  select * from  tratamiento_medico o "
            + " where historial=false and "
            + " medicamento=:obj ";

    @Query(value = strActivo, nativeQuery = true)
    public List<TratamientoMedico> getListaActiva(@Param("obj") int op);

    String strCant = "  select count(*) from  tratamiento_medico o "
            + "  where historial=false and "
            + "  medicamento=:obj ";

    @Query(value = strCant, nativeQuery = true)
    public Integer getCantidadBebida(@Param("obj") int op);
}
