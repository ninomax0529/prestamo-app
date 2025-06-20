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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

/**
 *
 * @author Maximiliano
 */
@Route("/detallePrestamo")
@PageTitle("Detalle Préstamo")
@Menu(order = 2, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class DetallePrestamoView extends VerticalLayout implements HasUrlParameter<String> {

    private final PrestamoService prestamoService;
    private final PeriodoService periodoService;
    private final TipoPrestamoService tipoPrestamoService;
    private final ClienteService clienteService;
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
        setSpacing(true);
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

        add(botonera, grid);

    }

    private void configurarGrid() {

        grid.addColumn(DetallePrestamo::getNumeroCuota).setHeader("NumCuota").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getValorCuota).setHeader("Vaor Cuota").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getMontoPagado).setHeader("Monto Pagado").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getMontoPendiente).setHeader("Monto Pendiente").setAutoWidth(true);
        grid.addColumn(DetallePrestamo::getFecha).setHeader("Fecha").setAutoWidth(true);

    }

    @Override
    public void setParameter(BeforeEvent be, String t) {

        System.out.println("Parametro " + t);

        Integer codigo = Integer.valueOf(t);

        grid.setItems(prestamoService.getDetallePrestamo(codigo));
    }

}
