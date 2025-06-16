/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.maxsoft.application.service;

import com.maxsoft.application.modelo.Prestamo;
import java.util.List;

/**
 *
 * @author Maximiliano
 */
public interface PrestamoService {

    Prestamo guardar(Prestamo obj);
    List<Prestamo> getLista();
}
