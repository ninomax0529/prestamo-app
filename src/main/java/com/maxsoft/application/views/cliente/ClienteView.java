/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.cliente;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.flow.component.notification.Notification;

import com.maxsoft.application.modelo.Cliente;
import com.maxsoft.application.service.ClienteService;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Clientes")
@Route("/")
@PermitAll
@Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class ClienteView extends VerticalLayout {

    private final ClienteService clienteService;
    private final Grid<Cliente> grid = new Grid<>(Cliente.class, false);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final Binder<Cliente> binder = new Binder<>(Cliente.class);

    private final TextField nombre = new TextField("Nombre y Apellidos");
    private final TextField cedula = new TextField("No.Cédula");
    private final TextField celular = new TextField("No.Celular");
    private final TextField direccion = new TextField("Dirección");
    private final TextField lugarDeTrabajo = new TextField("Lugar de Trabajo");
    private final TextField referencia = new TextField("Referencia");
    private final Checkbox habilitado = new Checkbox("Habilitado");

    private final Button guardar = new Button("Guardar", new Icon(VaadinIcon.CHECK));
    private final Button limpiar = new Button("Limpiar", new Icon(VaadinIcon.ERASER));
    Button exportarPdf = new Button("Exportar a PDF", VaadinIcon.FILE_PRESENTATION.create());

    private Cliente clienteActual = new Cliente();

    @Autowired
    public ClienteView(ClienteService clienteService) {
        this.clienteService = clienteService;

        add(new H3("Gestión de Clientes"));

        configurarGrid();
        configurarFormulario();
        actualizarLista();
//        exportarPdf.addClickListener(e -> exportarClientesPDF());

        add(grid);
    }

    private void configurarGrid() {

        grid.addColumn(Cliente::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Cliente::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Cliente::getCedula).setHeader("Cédula").setAutoWidth(true);
        grid.addColumn(Cliente::getCelular).setHeader("Celular").setAutoWidth(true);
        grid.addColumn(Cliente::getDireccion).setHeader("Dirección").setAutoWidth(true);
        grid.addColumn(Cliente::getLugarDeTrabajo).setHeader("Lugar de Trabajo").setAutoWidth(true);
        grid.addColumn(Cliente::getReferencia).setHeader("Referencia").setAutoWidth(true);
        grid.addColumn(cliente -> dateFormat.format(cliente.getFechaCreacion())).setHeader("Fecha de Creación").setAutoWidth(true);
        grid.addColumn(Cliente::getCreadoPor).setHeader("Creado Por").setAutoWidth(true);
//        grid.addColumn(cliente -> cliente.getFechaActualizacion() != null ? dateFormat.format(cliente.getFechaActualizacion()) : "").setHeader("Fecha de Actualización").setAutoWidth(true);
//        grid.addColumn(Cliente::getActualizadoPor).setHeader("Actualizado Por").setAutoWidth(true);
        grid.addColumn(cliente -> cliente.getHabilitado() ? "Sí" : "No").setHeader("Habilitado").setAutoWidth(true);

        grid.asSingleSelect().addValueChangeListener(event -> editarCliente(event.getValue()));
    }

    private void configurarFormulario() {
        
        FormLayout form = new FormLayout();
        habilitado.setValue(Boolean.TRUE);
        form.add(nombre, cedula, celular, direccion, lugarDeTrabajo, referencia, habilitado);

        binder.bindInstanceFields(this);

        guardar.addClickListener(e -> guardarCliente());

        limpiar.addClickListener(e -> limpiarFormulario());

        HorizontalLayout botones = new HorizontalLayout(guardar, limpiar);
        add(form, botones);

    }

    private void limpiarFormulario() {

        clienteActual = new Cliente();
        binder.readBean(clienteActual);
        habilitado.setValue(Boolean.TRUE);
    }

    private void editarCliente(Cliente cliente) {
        if (cliente != null) {
            this.clienteActual = cliente;
            binder.readBean(cliente);
        }
    }

    private void guardarCliente() {

        try {

            binder.writeBean(clienteActual);
            clienteActual.setFechaActualizacion(new Date());
            clienteActual.setFechaCreacion(new Date());
            clienteService.guardar(clienteActual);
            actualizarLista();
            Notification.show("Cliente guardado", 2000, Notification.Position.TOP_CENTER);
            clienteActual = new Cliente();
            binder.readBean(clienteActual);
        } catch (ValidationException e) {
            Notification.show("Error al guardar cliente: " + e.getMessage(), 2000, Notification.Position.BOTTOM_START);
        }
    }

    private void actualizarLista() {
        grid.setItems(clienteService.getLista());
    }

  
}
