/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.reciboIngreso;

import com.maxsoft.application.modelo.Cliente;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.modelo.ReciboDeIngreso;
import com.maxsoft.application.reporte.RptReciboIngreso;
import com.maxsoft.application.service.PrestamoService;
import com.maxsoft.application.service.ReciboDeIngresoService;
import com.maxsoft.application.util.ClaseUtil;
import com.maxsoft.application.util.NavigationContext;
import com.maxsoft.application.views.componente.FiltroAvanzado;
import com.maxsoft.application.views.componente.ToolBarBotonera;
import com.maxsoft.application.views.dialogo.ConfirmDialog;
import com.maxsoft.application.views.dialogo.PrestamoDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import java.sql.Connection;
import javax.sql.DataSource;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@Route(value = "recibos")
@PageTitle("Recibos de Ingreso")
@Menu(order = 3, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@PermitAll
public class ReciboDeIngresoView extends VerticalLayout {

    @Autowired
    DataSource dataSource;
    private final ReciboDeIngresoService reciboService;
    private final PrestamoService prestamoService;

    private final Grid<ReciboDeIngreso> grid = new Grid<>(ReciboDeIngreso.class, false);
    private final Binder<ReciboDeIngreso> binder = new Binder<>(ReciboDeIngreso.class);

    private final DatePicker dpFecha = new DatePicker("Fecha");

    private final TextField nombreCliente = new TextField("Nombre del Cliente");
    private final TextField numeroPrestamo = new TextField("Numero Prestamo");
    private final NumberField txtValorCuota = new NumberField("Valor Cuota");
    private final NumberField montoPendiente = new NumberField("Monto Pendiente");
    private final NumberField montoPrestado = new NumberField("Monto Prestado");
    private final TextArea descripcionPago = new TextArea("Descripción del Pago");

    private final Button btnGuardar = new Button("Guardar");
    private final Button limpiar = new Button("Limpiar");
    ToolBarBotonera botonera = new ToolBarBotonera(true, false, false);

    Button buscarPrestamo = new Button(new Icon(VaadinIcon.SEARCH));
    private ReciboDeIngreso reciboActual;
    Cliente cliente;
    Prestamo prestamo;

    @Autowired
    public ReciboDeIngresoView(ReciboDeIngresoService reciboService, PrestamoService prestamoService) {
        this.reciboService = reciboService;
        this.prestamoService = prestamoService;

        setSpacing(false);
        setSizeFull();
//        grid.setSizeFull();
//        setSizeFull();
        configureGrid();
//        configureForm();

   
         GridListDataView<ReciboDeIngreso> dataView = grid.setItems(this.reciboService.getLista());

// Crear el filtro avanzado
        FiltroAvanzado<ReciboDeIngreso> filtro = new FiltroAvanzado<>(dataView);

// Agregar filtros por nombre y cédula
        filtro.addFiltro("Cliente", ReciboDeIngreso::getNombreCliente);
//        filtro.addFiltro("Prestamo", Prestamo::getCodigo().toStrin);

        buscarPrestamo.addClassName("boton-buscar");
        botonera.getNuevo().addClickListener(e -> {
            // lógica de nuevo

            String key = NavigationContext.store(new ReciboDeIngreso());
            UI.getCurrent().navigate(RegistroReciboDeIngresoView.class, key);

        });

        add(botonera,filtro, grid);
        actualizarGrid();

        buscarPrestamo.addClickListener(e -> {

            List<Prestamo> prestamos = prestamoService.getPrestamoPendiente(); // desde la capa de servicio
            PrestamoDialog dialog = new PrestamoDialog(prestamos, seleccionado -> {

                nombreCliente.setValue(seleccionado.getNombreCliente()); // ComboBox o campo vinculado
                numeroPrestamo.setValue(seleccionado.getCodigo().toString()); // ComboBox o campo vinculado
                txtValorCuota.setValue(seleccionado.getMontoCuota());
                cliente = seleccionado.getCliente();
                prestamo = seleccionado;
                btnGuardar.setEnabled(true);

                StringBuilder descripcion = new StringBuilder();
                int canPago = this.reciboService.getCantidadPago(prestamo.getCodigo()) + 1;

                descripcion.append("Pago ").append(canPago)
                        .append(" de ").append(prestamo.getCantidadPeriodo());

                descripcionPago.setValue(descripcion.toString());
            });
            dialog.open();
        });

    }

    private void configureGrid() {

        grid.addColumn(r -> r.getCodigo()).setHeader("Recibo").setAutoWidth(true);
        grid.addColumn(r -> r.getNombreCliente()).setHeader("Cliente");
        grid.addColumn(r -> r.getFecha()).setHeader("Fecha").setAutoWidth(true);
//        grid.addColumn(r -> r.getNombreCliente()).setHeader("Cliente").setAutoWidth(true);
//        grid.addColumn(r -> r.getTotal()).setHeader("Total").setAutoWidth(true);
//        grid.addColumn(r -> r.getMontoPendiente()).setHeader("Pendiente");
//        grid.addColumn(r -> r.getDescripcionPago()).setHeader("Descripcion Pago").setAutoWidth(true);

        grid.addComponentColumn(recibo -> {
            HorizontalLayout acciones = new HorizontalLayout();
            acciones.setSpacing("2px");

            // Botón Ver
            Button verBtn = new Button(VaadinIcon.EYE.create(), click -> {

                // Aquí puedes abrir un diálogo o navegar
                try (Connection conn = dataSource.getConnection()) {

                    RptReciboIngreso rec = new RptReciboIngreso();

                    if (recibo != null) {

                        StreamResource pdfResource = rec.reciboIngreso(recibo.getCodigo(), conn);

                        Anchor anchor = new Anchor(pdfResource, "");
                        anchor.getElement().setAttribute("download", false);
                        anchor.getElement().setAttribute("target", "_blank");

                        anchor.getElement().callJsFunction("click"); // dispara la apertura automática

                        add(anchor);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Notification.show("Error al generar el reporte");
                }

            });
            
            verBtn.getElement().setAttribute("title", "Ver");

            // Botón Detalle
            Button detalleBtn = new Button(VaadinIcon.INFO_CIRCLE.create(), click -> {

                // Aquí puedes abrir un diálogo de detalle
                try {

                    UI.getCurrent().navigate("detalleRecibo/" + recibo.getCodigo());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    //                    Notification.show("Error anulando el prestamo", 3000, Notification.Position.TOP_CENTER);
                }
            });
            detalleBtn.getElement().setAttribute("title", "Detalle");

            // Botón Anular
            Button anularBtn = new Button(VaadinIcon.CLOSE_CIRCLE.create(), click -> {

                try {

                    if (recibo != null) {

                        ConfirmDialog dialog = new ConfirmDialog(
                                "¿Estás seguro que quiere anular el recibo -> " + recibo.getCodigo(),
                                () -> {

                                    Double montoPend, montoPagado;
                                    recibo.setAnulado(true);
                                    recibo.setFechaAnulado(new Date());
                                    recibo.setAnuladoPor("ADMIN");

                                    prestamo = recibo.getPrestamo();
                                    reciboService.guardar(recibo);

                                    montoPend = prestamoService.getMontoPendiente(prestamo.getCodigo());
                                    montoPagado = prestamoService.getMontoPagado(prestamo.getCodigo());

                                    prestamo.setTotalPagado(montoPagado);
                                    prestamo.setTotalPendiente(montoPend);

                                    prestamoService.guardar(prestamo);

                                    actualizarGrid();
                                    Notification.show("Recibo anulado exitosamente", 3000, Notification.Position.MIDDLE);

                                },
                                () -> {

                                }
                        );
                        dialog.open();

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Notification.show("Error al generar el reporte");
                }

            });

            anularBtn.getElement().setAttribute("title", "Anular");
            anularBtn.getStyle().set("color", "red");

            acciones.add(verBtn, detalleBtn, anularBtn);
            return acciones;
        }).setHeader("Acciones").setAutoWidth(true);

        
        
        grid.asSingleSelect().addValueChangeListener(event -> {
            reciboActual = event.getValue();
            if (reciboActual != null) {

                buscarPrestamo.setEnabled(false);
                btnGuardar.setEnabled(false);
                cliente = reciboActual.getCliente();
                prestamo = reciboActual.getPrestamo();
                txtValorCuota.setValue(reciboActual.getTotal());
                numeroPrestamo.setValue(prestamo.getCodigo().toString());
                binder.readBean(reciboActual);
            }
        });
    }
//
//    private FormLayout crearFormulario() {
//
//        FormLayout form = new FormLayout();
//        btnGuardar.setEnabled(false);
//        dpFecha.setValue(LocalDate.now());
//        numeroPrestamo.setEnabled(false);
//        nombreCliente.setEnabled(false);
//        txtValorCuota.setWidth("30%");
//        montoPendiente.setWidth("30%");
//        montoPrestado.setWidth("30%");
//
//        HorizontalLayout botones = new HorizontalLayout(btnGuardar, limpiar);
//        HorizontalLayout hlcliente = new HorizontalLayout(numeroPrestamo, buscarPrestamo);

    ////        HorizontalLayout hlPrestamo = new HorizontalLayout(montoPrestado,txtValorCuota, montoPendiente);
//
//        hlcliente.setAlignItems(Alignment.BASELINE);
//        hlcliente.setSpacing(false);
////
////        hlPrestamo.setAlignItems(Alignment.BASELINE);
////        hlPrestamo.setSpacing(true);
////        hlPrestamo.setSizeFull();
//
//        btnGuardar.addClickListener(e -> guardar());
//
//        limpiar.addClickListener(e -> limpiarFormulario());
//
//        binder.bindInstanceFields(this);
//
//        form.add(dpFecha,
//                hlcliente,
//                nombreCliente,
//                txtValorCuota,
//                descripcionPago,
//                botones
//        );
//        return form;
//    }

    private void guardar() {

        try {

            if (reciboActual == null) {
                reciboActual = new ReciboDeIngreso();
            }

            Double montoPend, montoPagado;

            binder.writeBean(reciboActual);
            reciboActual.setFechaCreacion(new Date());
            reciboActual.setFecha(ClaseUtil.asDate(dpFecha.getValue()));
            reciboActual.setCliente(cliente);
            reciboActual.setPrestamo(prestamo);
            reciboActual.setTotal(txtValorCuota.getValue());

            montoPend = prestamoService.getMontoPendiente(prestamo.getCodigo());
            montoPend = montoPend - reciboActual.getTotal();

            reciboActual.setMontoPendiente(montoPend);

            reciboService.guardar(reciboActual);

            montoPagado = prestamoService.getMontoPagado(prestamo.getCodigo());
            prestamo.setTotalPagado(montoPagado);
            prestamo.setTotalPendiente(montoPend);

            prestamoService.guardar(prestamo);

            actualizarGrid();
            limpiarFormulario();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void limpiarFormulario() {

        btnGuardar.setEnabled(false);
        buscarPrestamo.setEnabled(true);
        reciboActual = null;
        txtValorCuota.clear();
        numeroPrestamo.clear();
        binder.readBean(new ReciboDeIngreso());

    }

    private void actualizarGrid() {
        List<ReciboDeIngreso> lista = reciboService.getLista(false);
        grid.setItems(lista);
    }

}
