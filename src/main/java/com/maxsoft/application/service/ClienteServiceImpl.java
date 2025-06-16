/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.Cliente;
import com.maxsoft.application.repo.ClienteRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepo repo;
    @Override
    public Cliente guardar(Cliente obj) {
       
        Cliente c=repo.save(obj);
        
        return c;
        
    }

    @Override
    public List<Cliente> getLista() {
       
        List<Cliente>lista=repo.findAll();
        
        return lista;
    }
    
}
