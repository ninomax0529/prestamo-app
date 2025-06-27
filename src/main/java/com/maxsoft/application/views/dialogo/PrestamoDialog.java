/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.dialogo;

import com.maxsoft.application.modelo.Prestamo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author Maximiliano
 */
public class PrestamoDialog extends Dialog {

    private final Grid<Prestamo> grid = new Grid<>(Prestamo.class, false);
    private final TextField filtroNombre = new TextField("Buscar por nombre");
    private final TextField filtroCodigo = new TextField("Buscar por Codigo");
    private Button seleccionarButton;
    private Prestamo prestamoSeleccionado;
    Consumer<Prestamo> onPrestamoSeleccionado;

    private final List<Prestamo> listaPrestamo;

    public PrestamoDialog(List<Prestamo> prestamos, Consumer<Prestamo> listener) {

        setHeaderTitle("Seleccionar Préstamo");
        this.listaPrestamo = prestamos;
        this.onPrestamoSeleccionado = listener;

        setSizeFull();
        setHeaderTitle("Seleccionar Prestamo");
        setModal(true);
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        filtroNombre.focus();

        configurarGrid();
        configurarFiltros();

        Button cancelar = new Button("Cancelar", e -> close());

        HorizontalLayout filtros = new HorizontalLayout(filtroNombre);
        filtros.setWidthFull();

//        HorizontalLayout botones = new HorizontalLayout(seleccionarButton, cancelar);
        add(new VerticalLayout(cancelar, filtros, grid));
    }

    private void configurarGrid() {

        grid.addComponentColumn(prestamo -> {

            seleccionarButton = new Button(VaadinIcon.CHECK.create(), e -> {

                if (prestamo != null) {
                    onPrestamoSeleccionado.accept(prestamo);
                    close();
                }
            });

            return seleccionarButton;
        }).setHeader("Selccionar");
        grid.addColumn(Prestamo::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Prestamo::getNombreCliente).setHeader("Nombre").setAutoWidth(true);

        grid.setItems(listaPrestamo);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

    }

    private void configurarFiltros() {

        filtroNombre.setPlaceholder("Ej: Juan");
        filtroNombre.setClearButtonVisible(true);
        filtroNombre.setValueChangeMode(ValueChangeMode.EAGER); // 👈 Aquí está la clave
        filtroNombre.addValueChangeListener(e -> aplicarFiltros());

        filtroCodigo.setPlaceholder("Ej: 1");
        filtroCodigo.setClearButtonVisible(true);
        filtroCodigo.setValueChangeMode(ValueChangeMode.EAGER); // 👈 Aquí también
        filtroCodigo.addValueChangeListener(e -> aplicarFiltros());
    }

    private void aplicarFiltros() {
        String nombreFiltro = filtroNombre.getValue().trim().toLowerCase();
        String codigoFiltro = filtroCodigo.getValue().trim().toLowerCase();

        List<Prestamo> filtrados = listaPrestamo.stream()
                .filter(cliente
                        -> (nombreFiltro.isEmpty() || cliente.getNombreCliente().toLowerCase().contains(nombreFiltro))
                && (codigoFiltro.isEmpty() || cliente.getCodigo().toString().toLowerCase().contains(codigoFiltro))
                )
                .collect(Collectors.toList());

        grid.setItems(filtrados);
    }
}
