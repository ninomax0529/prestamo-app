/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.prestamo;

import com.maxsoft.application.modelo.DetallePrestamo;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.service.ClienteService;
import com.maxsoft.application.service.PeriodoService;
import com.maxsoft.application.service.PrestamoService;
import com.maxsoft.application.service.TipoPrestamoService;
import com.maxsoft.application.util.NavigationContext;
import com.maxsoft.application.views.componente.ToolBarBotonera;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

/**
 *
 * @author Maximiliano
 */
@Route("/detallePrestamo")
@PageTitle("Detalle Préstamo")
@AnonymousAllowed()
@Menu(order = 2, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class DetallePrestamoView extends VerticalLayout implements HasUrlParameter<String> {

    // Componentes del formulario
    private final TextField txtFechaInicio = new TextField("Fecha");
    private final TextField txtNombreCliente = new TextField("Cliente");
    private final TextField txtTipoPrestamo = new TextField("Tipo Prestamo");
    private final NumberField txtMontoPrestado = new NumberField("Monto Prestado");
    private final NumberField txtTotalPrestamo = new NumberField("Total");
    private final NumberField txtMontoIntere = new NumberField("Monto Interés");
    private final NumberField txtTasaDeIntere = new NumberField("Tasa de Interés %");
    TextField txtPeriodo = new TextField("Periodo");

    private final PrestamoService prestamoService;
    private final PeriodoService periodoService;
    private final TipoPrestamoService tipoPrestamoService;
    private final ClienteService clienteService;
    Prestamo prestamo;
    ToolBarBotonera botonera = new ToolBarBotonera(false, false, true);

    private final Grid<DetallePrestamo> grid = new Grid<>(DetallePrestamo.class, false);

    private final Button btnGuardar = new Button("Guardar", new Icon(VaadinIcon.CHECK));
    private final Button btnLimpiar = new Button("Limpiar", VaadinIcon.REFRESH.create());
    Button buscarClienteBtn = new Button(new Icon(VaadinIcon.SEARCH));
    private final Button btnCalcular = new Button("Calcular");

    public DetallePrestamoView(PrestamoService prestamoService, PeriodoService periodoService, TipoPrestamoService tipoPrestamoService,
            ClienteService clienteService) {

        setSizeFull();
        setPadding(true);
        setSpacing("8px");
        this.prestamoService = prestamoService;
        this.periodoService = periodoService;
        this.tipoPrestamoService = tipoPrestamoService;
        this.clienteService = clienteService;
        btnGuardar.setEnabled(false);

        botonera.getCancelar().addClickListener(e -> {
            // lógica de cancelar
            UI.getCurrent().navigate(PrestamoView.class);
        });

        botonera.getNuevo().addClickListener(e -> {
            // lógica de nuevo

            String key = NavigationContext.store(new Prestamo());
            UI.getCurrent().navigate(RegistroPrestamoView.class, key);

        });

        addClassName("prestamo-view");
        buscarClienteBtn.addClassName("boton-buscar");
        btnCalcular.addClassName("boton-calcular");

        configurarGrid();

        add(botonera, crearFormulario(), grid);

    }

    private void configurarGrid() {

        grid.addColumn(DetallePrestamo::getNumeroCuota).setHeader("Numero").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getValorCuota).setHeader("Cuota").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getCapital).setHeader("Capital").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getInteres).setHeader("Interes").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getMontoPagado).setHeader("Pagado").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getMontoPendiente).setHeader("Pendiente").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getFecha).setHeader("Fecha").setAutoWidth(true);
//        
//        grid.addComponentColumn(p -> {
//            Icon icon = p.getEstado() ? VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create();
//            icon.setColor(p.getEstado() ? "green" : "red");
//            return icon;
//        }).setHeader("Estado").setAutoWidth(true);

    }

    private FormLayout crearFormulario() {

//        totalPrestamo.setWidth("30%");
//        montoCuota.setWidth("30%");
//        tasaDeIntere.setWidth("30%");
//        montoIntere.setWidth("35%");
//        montoPrestado.setWidth("35%");
        HorizontalLayout hlValor = new HorizontalLayout(txtTasaDeIntere, txtTotalPrestamo);
        HorizontalLayout hlCliente = new HorizontalLayout(txtFechaInicio, txtNombreCliente);
        HorizontalLayout hlPeriodo = new HorizontalLayout(txtTipoPrestamo, txtPeriodo);
        HorizontalLayout hlMonto = new HorizontalLayout(txtMontoPrestado, txtMontoIntere);

//        nombreCliente.setSizeFull();
        txtNombreCliente.setReadOnly(true);
        txtTotalPrestamo.setReadOnly(true);
        txtFechaInicio.setReadOnly(true);
        txtTasaDeIntere.setReadOnly(true);
        txtTipoPrestamo.setReadOnly(true);
        txtPeriodo.setReadOnly(true);
        txtMontoPrestado.setReadOnly(true);
        txtMontoIntere.setReadOnly(true);

//        hlPeriodo.setSizeFull();
        hlValor.setSpacing("1%");
        hlCliente.setSpacing("1%");
        hlMonto.setSpacing("1%");

        hlValor.setAlignItems(Alignment.BASELINE);
        hlCliente.setAlignItems(Alignment.BASELINE);
        hlPeriodo.setAlignItems(Alignment.BASELINE);
        hlMonto.setAlignItems(Alignment.BASELINE);

        FormLayout formLayout = new FormLayout(
                hlCliente,
                hlPeriodo,
                hlMonto,
                hlValor
        );

        return formLayout;
    }

    @Override
    public void setParameter(BeforeEvent be, String t) {

        System.out.println("Parametro " + t);

        Integer codigo = Integer.valueOf(t);

        prestamo = prestamoService.getPrestamo(codigo);

        grid.setItems(prestamoService.getDetallePrestamo(codigo));

        if (prestamo != null) {

            txtNombreCliente.setValue(prestamo.getNombreCliente());
            txtPeriodo.setValue(prestamo.getNombrePeriodo());
            txtMontoIntere.setValue(prestamo.getMontoIntere());
            txtMontoPrestado.setValue(prestamo.getMontoPrestado());
            txtTipoPrestamo.setValue(prestamo.getNombreTipoPrestamo());
            txtTasaDeIntere.setValue(prestamo.getTasaDeIntere());
            txtTotalPrestamo.setValue(prestamo.getTotal());

            txtFechaInicio.setValue(prestamo.getFechaInicio().toString());

        } else {
            System.out.println("Prestamo null ");
        }
    }

}
