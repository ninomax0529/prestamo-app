/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.Periodo;
import com.maxsoft.application.repo.PeriodoRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodoServiceImpl implements PeriodoService {

    @Autowired
    PeriodoRepo  repo;
    
    @Override
    public List<Periodo> getLista() {
       
        List<Periodo> lista=repo.findAll();
        
        return lista;
        
    }
    
}
