/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.Cliente;
import java.util.List;

/**
 *
 * @author Maximiliano
 */
public interface ClienteService {
    
    Cliente guardar(Cliente obj);
    List<Cliente> getLista();
}
