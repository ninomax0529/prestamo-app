/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.prestamo;

import com.maxsoft.application.modelo.Cliente;
import com.maxsoft.application.modelo.Periodo;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.modelo.TipoPrestamo;
import com.maxsoft.application.service.ClienteService;
import com.maxsoft.application.service.PeriodoService;
import com.maxsoft.application.service.PrestamoService;
import com.maxsoft.application.service.TipoPrestamoService;
import com.maxsoft.application.util.ClaseUtil;
import com.maxsoft.application.views.dialogo.ClienteSelectorDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Date;
import java.util.List;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

/**
 *
 * @author Maximiliano
 */
@Route("prestamos")
@PageTitle("Gestión de Préstamos")
@Menu(order = 2, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class PrestamoView extends VerticalLayout {

    private final PrestamoService prestamoService;
    private final PeriodoService periodoService;
    private final TipoPrestamoService tipoPrestamoService;
    private final ClienteService clienteService;

    private final Grid<Prestamo> grid = new Grid<>(Prestamo.class, false);
    private final Binder<Prestamo> binder = new Binder<>(Prestamo.class);
    private Prestamo prestamo = new Prestamo();

    // Componentes del formulario
    private final DatePicker fechaInicio = new DatePicker("Fecha");
    private final DatePicker fechaFinal = new DatePicker("Fecha Final");
    private final DatePicker fechaPrimerPago = new DatePicker("Fecha Primer Pago");
    private final TextField nombreCliente = new TextField("Cliente");
    private final NumberField montoPrestado = new NumberField("Monto Prestado");
    private final NumberField totalPrestamo = new NumberField("Total Prestamo");
    private final NumberField montoIntere = new NumberField("Monto Interés");
    private final NumberField tasaDeIntere = new NumberField("Tasa de Interés %");
    private final NumberField montoCuota = new NumberField("Monto Cuota");
    TextField cantidadPeriodoField = new TextField("Cantidad de Periodo");

    private final ComboBox<Periodo> periodo = new ComboBox<>("Periodo");
    private final ComboBox<TipoPrestamo> tipoPrestamo = new ComboBox<>("Tipo de Préstamo");

    private final Button guardar = new Button("Guardar", new Icon(VaadinIcon.CHECK));
    private final Button limpiar = new Button("Limpiar");
    Button buscarClienteBtn = new Button(new Icon(VaadinIcon.SEARCH));
    private final Button btnCalcular = new Button("Calcular");

    public PrestamoView(PrestamoService prestamoService, PeriodoService periodoService, TipoPrestamoService tipoPrestamoService,
            ClienteService clienteService) {
        
        this.prestamoService = prestamoService;
        this.periodoService = periodoService;
        this.tipoPrestamoService = tipoPrestamoService;
        this.clienteService = clienteService;
        guardar.setEnabled(false);
        periodo.addValueChangeListener(e -> {

            if (e.getValue() != null) {
                cantidadPeriodoField.focus();
            }

        });

        addClassName("prestamo-view");
          buscarClienteBtn.addClassName("boton-buscar");
//        setSizeFull();

        configurarFormulario();
        configurarGrid();
        configurarEventos();

        add(new H3("Gestión de Préstamos"), crearFormulario(), grid);
        actualizarGrid();

        binder.forField(cantidadPeriodoField)
                .withNullRepresentation("") // trata el string vacío como null
                .withConverter(
                        new StringToIntegerConverter("Debe ser un número entero")) // conversión segura
                .bind(Prestamo::getCantidadPeriodo, Prestamo::setCantidadPeriodo);

    }

    private void configurarFormulario() {

        periodo.setItems(periodoService.getLista());
        periodo.setItemLabelGenerator(Periodo::getNombre);

        tipoPrestamo.setItems(tipoPrestamoService.getLista());
        tipoPrestamo.setItemLabelGenerator(TipoPrestamo::getNombre);

        binder.bindInstanceFields(this);
    }

    private FormLayout crearFormulario() {

        nombreCliente.setEnabled(false);

        HorizontalLayout hlValor = new HorizontalLayout(montoCuota, tasaDeIntere);
        HorizontalLayout hlCliente = new HorizontalLayout(nombreCliente, buscarClienteBtn);
        HorizontalLayout hlPeriodo = new HorizontalLayout(periodo, cantidadPeriodoField);
        HorizontalLayout hlMonto = new HorizontalLayout(montoPrestado, montoIntere);
        HorizontalLayout hlFecha = new HorizontalLayout(fechaInicio, fechaPrimerPago);

        nombreCliente.setSizeFull();
//        hlPeriodo.setSizeFull();

        hlCliente.setSpacing(false);
        hlCliente.setAlignItems(Alignment.BASELINE);
        hlPeriodo.setAlignItems(Alignment.BASELINE);
        hlMonto.setAlignItems(Alignment.BASELINE);
        hlFecha.setAlignItems(Alignment.BASELINE);
        hlValor.setAlignItems(Alignment.BASELINE);

        FormLayout formLayout = new FormLayout(
                hlCliente,
                tipoPrestamo,
                hlPeriodo,
                hlMonto,
                hlValor,
//                totalPrestamo,
                hlFecha,
                new HorizontalLayout(guardar, limpiar, btnCalcular)
        );
        return formLayout;
    }

    private void configurarGrid() {

//        grid.setSizeFull();
        grid.addColumn(p -> p.getCliente() != null ? p.getCliente().getNombre() : "").setHeader("Cliente").setAutoWidth(true);
        grid.addColumn(Prestamo::getNombreTipoPrestamo).setHeader("Tipo Prestamo").setAutoWidth(true);
        grid.addColumn(Prestamo::getMontoPrestado).setHeader("Monto Prestado").setAutoWidth(true);
        grid.addColumn(Prestamo::getTotal).setHeader("Total Prestamo").setAutoWidth(true);
        grid.addColumn(Prestamo::getFechaInicio).setHeader("Fecha Inicio").setAutoWidth(true);
        grid.addColumn(Prestamo::getFechaPrimerPago).setHeader("Fecha Primer Pago").setAutoWidth(true);
        grid.addColumn(Prestamo::getNombrePeriodo).setHeader("Periodo").setAutoWidth(true);
        grid.addColumn(Prestamo::getCantidadPeriodo).setHeader("Cantidad Periodo").setAutoWidth(true);

        grid.setItems(prestamoService.getLista());
//        grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(event -> {

            if (event.getValue() != null) {
                
                prestamo = event.getValue();
                
                binder.readBean(prestamo);
                guardar.setEnabled(true);
                
            } else {
                guardar.setEnabled(false);
            }

        });
    }

    private void configurarEventos() {

        guardar.addClickListener(e -> {
            try {
                prestamo.setNombrePeriodo(periodo.getValue().getNombre());
                prestamo.setNombreTipoPrestamo(tipoPrestamo.getValue().getNombre());
                prestamo.setCreadoPor("ADMIN");
                prestamo.setFechaCreacion(new Date());
//                prestamo.setTotal();

                binder.writeBean(prestamo);

                prestamoService.guardar(prestamo);

                actualizarGrid();
                Notification.show("Préstamo guardado");
                limpiarFormulario();
            } catch (ValidationException ex) {
                Notification.show("Datos inválidos: " + ex.getMessage());
            }
        });

        buscarClienteBtn.addClickListener(e -> {

            List<Cliente> clientes = clienteService.getLista(); // Ajusta según tu servicio real
            ClienteSelectorDialog dialog = new ClienteSelectorDialog(clientes, cliente -> {

                if (cliente != null) {
                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }
                nombreCliente.setValue(cliente.getNombre());
                prestamo.setCliente(cliente); // Enlazar al objeto Prestamo
            });

            dialog.open();
        });

        btnCalcular.addClickListener(e -> {

            Double montoCuota = 0.00, tasaIntere = 0.00, montoPrestado = 0.00, montoIntere = 0.00;
            Integer canPeriodo = Integer.valueOf(cantidadPeriodoField.getValue());

            montoPrestado = this.montoPrestado.getValue();
            montoIntere = this.montoIntere.getValue();

            montoCuota = ClaseUtil.FormatearDouble((montoPrestado + montoIntere) / canPeriodo, 2);

            tasaIntere = ClaseUtil.FormatearDouble((montoIntere / montoPrestado) * 100, 2);
            tasaDeIntere.setValue(tasaIntere);
            this.montoCuota.setValue(montoCuota);
            totalPrestamo.setValue(montoIntere + montoPrestado);

        });

        limpiar.addClickListener(e -> limpiarFormulario());
    }

    private void actualizarGrid() {
        grid.setItems(prestamoService.getLista());
    }

    private void limpiarFormulario() {
        guardar.setEnabled(false);
        prestamo = new Prestamo();
        binder.readBean(prestamo);
    }
}
