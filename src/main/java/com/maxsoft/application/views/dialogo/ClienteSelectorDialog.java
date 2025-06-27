/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.dialogo;

import com.maxsoft.application.modelo.Cliente;
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
public class ClienteSelectorDialog extends Dialog {

    private final Grid<Cliente> grid = new Grid<>(Cliente.class, false);
    private final TextField filtroNombre = new TextField("Buscar por nombre");
    private final TextField filtroCedula = new TextField("Buscar por cédula");
    private Button seleccionarButton;
    private Cliente clienteSeleccionado;
    Consumer<Cliente> onClienteSeleccionado;

    private final List<Cliente> clientes;

    public ClienteSelectorDialog(List<Cliente> clientes, Consumer<Cliente> onClienteSeleccionado) {
        this.clientes = clientes;
        this.onClienteSeleccionado = onClienteSeleccionado;

        setSizeFull();
        setHeaderTitle("Seleccionar Cliente");
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

        grid.addComponentColumn(cliente -> {

            seleccionarButton = new Button(VaadinIcon.CHECK.create(), e -> {

                if (cliente != null) {
                    onClienteSeleccionado.accept(cliente);
                    close();
                }
            });

            return seleccionarButton;
        }).setHeader("Selccionar");
        grid.addColumn(Cliente::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Cliente::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Cliente::getCedula).setHeader("Cédula").setAutoWidth(true);
        grid.setItems(clientes);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

//        
    }

    private void configurarFiltros() {

        filtroNombre.setPlaceholder("Ej: Juan");
        filtroNombre.setClearButtonVisible(true);
        filtroNombre.setValueChangeMode(ValueChangeMode.EAGER); // 👈 Aquí está la clave
        filtroNombre.addValueChangeListener(e -> aplicarFiltros());

        filtroCedula.setPlaceholder("Ej: 00112345678");
        filtroCedula.setClearButtonVisible(true);
        filtroCedula.setValueChangeMode(ValueChangeMode.EAGER); // 👈 Aquí también
        filtroCedula.addValueChangeListener(e -> aplicarFiltros());
    }

    private void aplicarFiltros() {
        String nombreFiltro = filtroNombre.getValue().trim().toLowerCase();
        String cedulaFiltro = filtroCedula.getValue().trim().toLowerCase();

        List<Cliente> filtrados = clientes.stream()
                .filter(cliente
                        -> (nombreFiltro.isEmpty() || cliente.getNombre().toLowerCase().contains(nombreFiltro))
                && (cedulaFiltro.isEmpty() || cliente.getCedula().toLowerCase().contains(cedulaFiltro))
                )
                .collect(Collectors.toList());

        grid.setItems(filtrados);
    }
}
