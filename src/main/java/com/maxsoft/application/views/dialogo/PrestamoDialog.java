/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.dialogo;

import com.maxsoft.application.modelo.Prestamo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.List;

/**
 *
 * @author Maximiliano
 */
public class PrestamoDialog extends Dialog {

    private final Grid<Prestamo> grid = new Grid<>(Prestamo.class, false);
    private final TextField filtro = new TextField("Buscar cliente");
    private final Button seleccionar = new Button("Seleccionar");
    private Prestamo prestamoSeleccionado;

    public interface PrestamoSeleccionadoListener {
        void alSeleccionar(Prestamo prestamo);
    }

    public PrestamoDialog(List<Prestamo> prestamos, PrestamoSeleccionadoListener listener) {
        setHeaderTitle("Seleccionar Préstamo");

        filtro.setPlaceholder("Filtrar por cliente...");
        filtro.setWidthFull();
        filtro.addValueChangeListener(e -> {
            String filtroTexto = e.getValue().trim().toLowerCase();
            grid.setItems(prestamos.stream()
                .filter(p -> p.getNombreCliente() != null &&
                             p.getNombreCliente().toLowerCase().contains(filtroTexto))
                .toList());
        });

        grid.addColumn(Prestamo::getCodigo).setHeader("Código");
        grid.addColumn(Prestamo::getNombreCliente).setHeader("Cliente");
        grid.addColumn(Prestamo::getMontoPrestado).setHeader("Monto");
        grid.setItems(prestamos);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        seleccionar.addClickListener(e -> {
            prestamoSeleccionado = grid.asSingleSelect().getValue();
            if (prestamoSeleccionado != null) {
                listener.alSeleccionar(prestamoSeleccionado);
                close();
            }
        });

        VerticalLayout layout = new VerticalLayout(filtro, grid, seleccionar);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }
}

