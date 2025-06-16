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
import com.maxsoft.application.views.dialogo.PrestamoDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
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
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperRunManager;
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

    private final DatePicker fecha = new DatePicker("Fecha");
    private final TextField nombreCliente = new TextField("Nombre del Cliente");
    private final TextField numeroPrestamo = new TextField("Numero Prestamo");
    private final NumberField txtValorCuota = new NumberField("Valor Cuota");
    private final NumberField montoPendiente = new NumberField("Monto Pendiente");
    private final TextField creadoPor = new TextField("Creado Por");
    private final TextArea descripcionPago = new TextArea("Descripción del Pago");

    private final Button guardar = new Button("Guardar");
    private final Button limpiar = new Button("Limpiar");
    Button buscarPrestamo = new Button(new Icon(VaadinIcon.SEARCH));   
    private ReciboDeIngreso reciboActual;
    Cliente cliente;
    Prestamo prestamo;

    @Autowired
    public ReciboDeIngresoView(ReciboDeIngresoService reciboService, PrestamoService prestamoService) {
        this.reciboService = reciboService;
        this.prestamoService = prestamoService;
   
        setSpacing(false);
//        setSizeFull();
        configureGrid();
//        configureForm();
        buscarPrestamo.addClassName("boton-buscar");
        add(new H3("Gestión de Cobros"), crearFormulario(), grid);
        actualizarGrid();


        buscarPrestamo.addClickListener(e -> {

            List<Prestamo> prestamos = prestamoService.getLista(); // desde la capa de servicio
            PrestamoDialog dialog = new PrestamoDialog(prestamos, seleccionado -> {
                nombreCliente.setValue(seleccionado.getNombreCliente()); // ComboBox o campo vinculado
                numeroPrestamo.setValue(seleccionado.getCodigo().toString()); // ComboBox o campo vinculado
                txtValorCuota.setValue(seleccionado.getMontoCuota());
                cliente = seleccionado.getCliente();
                prestamo = seleccionado;

                StringBuilder descripcion = new StringBuilder();
                int canPago = this.reciboService.getCantidadPago(cliente.getCodigo()) + 1;

                descripcion.append("Pago ").append(canPago)
                        .append(" de ").append(prestamo.getCantidadPeriodo());

                descripcionPago.setValue(descripcion.toString());
            });
            dialog.open();
        });

    }

    private void configureGrid() {

        grid.addColumn(r -> r.getCodigo()).setHeader("Num.Recibo");
        grid.addColumn(r -> r.getPrestamo() != null ? r.getPrestamo().getCodigo() : "").setHeader("Num.Préstamo");
        grid.addColumn(r -> r.getFecha()).setHeader("Fecha");
        grid.addColumn(r -> r.getNombreCliente()).setHeader("Cliente");
        grid.addColumn(r -> r.getTotal()).setHeader("Total");
//        grid.addColumn(r -> r.getMontoPendiente()).setHeader("Pendiente");
        grid.addColumn(r -> r.getDescripcionPago()).setHeader("Descripcion Pago");

        grid.addComponentColumn(articulo -> {
            
            Button btnVere = new Button(VaadinIcon.EYE.create(), event -> {

                try (Connection conn = dataSource.getConnection()) {
                    RptReciboIngreso rec = new RptReciboIngreso();

                    StreamResource pdfResource = rec.reciboIngreso(1, conn);

                    Anchor anchor = new Anchor(pdfResource, "");
                    anchor.getElement().setAttribute("download", false);
                    anchor.getElement().setAttribute("target", "_blank");

                    anchor.getElement().callJsFunction("click"); // dispara la apertura automática

                    add(anchor);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Notification.show("Error al generar el reporte");
                }
            });

            return btnVere;
        }).setHeader("Ver");

        grid.asSingleSelect().addValueChangeListener(event -> {
            reciboActual = event.getValue();
            if (reciboActual != null) {
                cliente = reciboActual.getCliente();
                prestamo = reciboActual.getPrestamo();
                txtValorCuota.setValue(reciboActual.getTotal());
                numeroPrestamo.setValue(prestamo.getCodigo().toString());
                binder.readBean(reciboActual);
            }
        });
    }

    private VerticalLayout crearFormulario() {

        numeroPrestamo.setEnabled(false);
        nombreCliente.setEnabled(false);
        HorizontalLayout botones = new HorizontalLayout(guardar, limpiar);
        HorizontalLayout hlcliente = new HorizontalLayout(numeroPrestamo, buscarPrestamo);
        hlcliente.setAlignItems(Alignment.BASELINE);
        hlcliente.setSpacing(false);

        guardar.addClickListener(e -> guardar());
        limpiar.addClickListener(e -> limpiarFormulario());

        binder.bindInstanceFields(this);

        VerticalLayout formulario = new VerticalLayout(
                fecha,
                hlcliente,
                nombreCliente,
                txtValorCuota,
                descripcionPago,
                botones
        );
        return formulario;
    }

    private void guardar() {
        try {
            if (reciboActual == null) {
                reciboActual = new ReciboDeIngreso();
            }

            binder.writeBean(reciboActual);
            reciboActual.setFechaCreacion(new Date());
            reciboActual.setCliente(cliente);
            reciboActual.setPrestamo(prestamo);
            reciboActual.setMontoPendiente(0.00);
            reciboActual.setTotal(txtValorCuota.getValue());

            System.out.println("reciboActual" + reciboActual);
            reciboService.guardar(reciboActual);
            actualizarGrid();
            limpiarFormulario();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void limpiarFormulario() {
        reciboActual = null;
        txtValorCuota.clear();
        numeroPrestamo.clear();
        binder.readBean(new ReciboDeIngreso());

    }

    private void actualizarGrid() {
        List<ReciboDeIngreso> lista = reciboService.getLista();
        grid.setItems(lista);
    }

}
