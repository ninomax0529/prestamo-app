/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.reciboIngreso;

import com.maxsoft.application.views.prestamo.*;
import com.maxsoft.application.modelo.DetallePrestamo;
import com.maxsoft.application.modelo.DetalleReciboDeIngreso;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.service.ClienteService;
import com.maxsoft.application.service.PeriodoService;
import com.maxsoft.application.service.PrestamoService;
import com.maxsoft.application.service.ReciboDeIngresoService;
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
@Route("/detalleRecibo")
@PageTitle("Detalle Recibo")
@Menu(order = 2, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class DetalleReciboDeIngresoView extends VerticalLayout implements HasUrlParameter<String> {

    private  ReciboDeIngresoService reciboService;

    ToolBarBotonera botonera = new ToolBarBotonera(false, false, true);

    private final Grid<DetalleReciboDeIngreso> grid = new Grid<>(DetalleReciboDeIngreso.class, false);

    Button buscarClienteBtn = new Button(new Icon(VaadinIcon.SEARCH));
    private final Button btnCalcular = new Button("Calcular");

    public DetalleReciboDeIngresoView(ReciboDeIngresoService reciboService) {

        setSizeFull();
        setPadding(true);
        setSpacing(true);
     
        this.reciboService = reciboService;
    
        botonera.getCancelar().addClickListener(e -> {
            // lógica de cancelar
            UI.getCurrent().navigate(ReciboDeIngresoView.class);
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

        grid.addColumn(DetalleReciboDeIngreso::getNumeroCuota).setHeader("Cuota").setAutoWidth(true);
        grid.addColumn(DetalleReciboDeIngreso::getTotal).setHeader("Total").setAutoWidth(true);
        grid.addColumn(DetalleReciboDeIngreso::getMontoPendiente).setHeader("Pemdiente").setAutoWidth(true);
        grid.addColumn(DetalleReciboDeIngreso::getConcepto).setHeader("Concepta").setAutoWidth(true);


    }

    @Override
    public void setParameter(BeforeEvent be, String t) {

        System.out.println("Parametro " + t);

        Integer codigo = Integer.valueOf(t);

        grid.setItems(reciboService.getDetalleRecibo(codigo));
    }

}
