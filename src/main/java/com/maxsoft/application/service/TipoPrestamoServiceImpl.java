/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.TipoPrestamo;
import com.maxsoft.application.repo.TipoPrestamoRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoPrestamoServiceImpl implements TipoPrestamoService {

    @Autowired
    TipoPrestamoRepo repo;
    
    @Override
    public List<TipoPrestamo> getLista() {
        
        List<TipoPrestamo> lista=repo.findAll();
        
        return lista;
        
    }
    
}
