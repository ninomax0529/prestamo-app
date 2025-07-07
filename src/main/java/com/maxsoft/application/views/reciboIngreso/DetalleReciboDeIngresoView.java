/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.reciboIngreso;

import com.maxsoft.application.views.prestamo.*;
import com.maxsoft.application.modelo.DetalleReciboDeIngreso;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.modelo.ReciboDeIngreso;
import com.maxsoft.application.service.ReciboDeIngresoService;
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
@AnonymousAllowed()
@Route("/detalleRecibo")
@PageTitle("Detalle Recibo")
@Menu(order = 2, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class DetalleReciboDeIngresoView extends VerticalLayout implements HasUrlParameter<String> {

    private ReciboDeIngresoService reciboService;
    ReciboDeIngreso recibo;

    private final TextField txtNombreCliente = new TextField("Nombre del Cliente");
    private final TextField txtRecibo = new TextField("Recibo");
    private final TextField txtFecha = new TextField("Fecha");
    private final NumberField txtTotal = new NumberField("Monto Cobrado");

    ToolBarBotonera botonera = new ToolBarBotonera(false, false, true);

    private final Grid<DetalleReciboDeIngreso> grid = new Grid<>(DetalleReciboDeIngreso.class, false);

    Button buscarClienteBtn = new Button(new Icon(VaadinIcon.SEARCH));
    private final Button btnCalcular = new Button("Calcular");

    public DetalleReciboDeIngresoView(ReciboDeIngresoService reciboService) {

        setSizeFull();
        setPadding(true);
        setSpacing("8px");

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

        add(botonera, crearFormulario(), grid);

    }

    private void configurarGrid() {

        grid.addColumn(DetalleReciboDeIngreso::getNumeroCuota).setHeader("Cuota").setAutoWidth(true);
        grid.addColumn(DetalleReciboDeIngreso::getTotal).setHeader("Total").setAutoWidth(true);
        grid.addColumn(DetalleReciboDeIngreso::getMontoCapital).setHeader("Capital").setAutoWidth(true);
        grid.addColumn(DetalleReciboDeIngreso::getMontoInteres).setHeader("Interes").setAutoWidth(true);
        grid.addColumn(DetalleReciboDeIngreso::getMontoPendiente).setHeader("Pemdiente").setAutoWidth(true);
        grid.addColumn(DetalleReciboDeIngreso::getConcepto).setHeader("Concepto").setAutoWidth(true);

    }

    private FormLayout crearFormulario() {

        FormLayout form = new FormLayout();
//        form.setHeight("15%");

        txtRecibo.setWidth("30%");
        txtNombreCliente.setWidth("40%");
        txtFecha.setWidth("30%");

        txtNombreCliente.setReadOnly(true);
        txtTotal.setReadOnly(true);
        txtFecha.setReadOnly(true);
        txtRecibo.setReadOnly(true);

        HorizontalLayout hlcliente = new HorizontalLayout(txtFecha, txtRecibo);
        HorizontalLayout hlDato = new HorizontalLayout(txtTotal, txtNombreCliente);

        hlcliente.setAlignItems(Alignment.BASELINE);
        hlcliente.setSpacing("1%");
        hlDato.setAlignItems(Alignment.BASELINE);
        hlDato.setSpacing("1%");

        form.add(hlcliente,
                hlcliente,
                hlDato
        );
        return form;
    }

    @Override
    public void setParameter(BeforeEvent be, String t) {

        System.out.println("Parametro " + t);

        Integer codigo = Integer.valueOf(t);
        recibo = reciboService.getReciboDeIngreso(codigo);

        grid.setItems(reciboService.getDetalleRecibo(codigo));

        if (recibo != null) {

            txtNombreCliente.setValue(recibo.getNombreCliente());
            txtRecibo.setValue(recibo.getCodigo().toString());
            txtFecha.setValue(recibo.getFecha().toString());
            txtTotal.setValue(recibo.getTotal());

        } else {
            System.out.println("Recibo null ");
        }
    }

}
