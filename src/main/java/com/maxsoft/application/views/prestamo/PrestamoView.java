/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.prestamo;

import com.maxsoft.application.modelo.Periodo;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.modelo.TipoPrestamo;
import com.maxsoft.application.service.ClienteService;
import com.maxsoft.application.service.PeriodoService;
import com.maxsoft.application.service.PrestamoService;
import com.maxsoft.application.service.TipoPrestamoService;
import com.maxsoft.application.util.NavigationContext;
import com.maxsoft.application.views.componente.ToolBarBotonera;
import com.maxsoft.application.views.dialogo.ConfirmDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Date;
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
    ToolBarBotonera botonera = new ToolBarBotonera(true, false, false);

    private final Grid<Prestamo> grid = new Grid<>(Prestamo.class, false);
    private final Binder<Prestamo> binder = new Binder<>(Prestamo.class);
    private Prestamo prestamo = new Prestamo();

    private final ComboBox<Periodo> periodo = new ComboBox<>("Periodo");
    private final ComboBox<TipoPrestamo> tipoPrestamo = new ComboBox<>("Tipo de Préstamo");

    private final Button btnGuardar = new Button("Guardar", new Icon(VaadinIcon.CHECK));
    private final Button btnLimpiar = new Button("Limpiar", VaadinIcon.REFRESH.create());
    Button buscarClienteBtn = new Button(new Icon(VaadinIcon.SEARCH));
    private final Button btnCalcular = new Button("Calcular");

    public PrestamoView(PrestamoService prestamoService, PeriodoService periodoService, TipoPrestamoService tipoPrestamoService,
            ClienteService clienteService) {

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        this.prestamoService = prestamoService;
        this.periodoService = periodoService;
        this.tipoPrestamoService = tipoPrestamoService;
        this.clienteService = clienteService;
        btnGuardar.setEnabled(false);

        botonera.getNuevo().addClickListener(e -> {
            // lógica de nuevo

            String key = NavigationContext.store(new Prestamo());
            UI.getCurrent().navigate(RegistroPrestamoView.class, key);

        });

        addClassName("prestamo-view");
        buscarClienteBtn.addClassName("boton-buscar");
        btnCalcular.addClassName("boton-calcular");
//        setSizeFull();

//        configurarFormulario();
        configurarGrid();
//        configurarEventos();

        add(botonera, grid);
        actualizarGrid();

    }

    private void configurarGrid() {

        grid.addColumn(p -> p.getCodigo()).setHeader("Prestamo").setAutoWidth(true);
        grid.addColumn(p -> p.getCliente() != null ? p.getCliente().getNombre() : "").setHeader("Cliente").setAutoWidth(true);
        grid.addColumn(Prestamo::getNombreTipoPrestamo).setHeader("Tipo Prestamo").setAutoWidth(true);
        grid.addColumn(Prestamo::getMontoPrestado).setHeader("Monto Prestado").setAutoWidth(true);
        grid.addColumn(Prestamo::getMontoIntere).setHeader("Monto Interes").setAutoWidth(true);
        grid.addColumn(Prestamo::getTotal).setHeader("Total Prestamo").setAutoWidth(true);
        grid.addColumn(Prestamo::getTotalPagado).setHeader("Total Pagado").setAutoWidth(true);
        grid.addColumn(Prestamo::getTotalPendiente).setHeader("Total Pendiente").setAutoWidth(true);
        grid.addColumn(Prestamo::getMontoCuota).setHeader("Valor Cuota").setAutoWidth(true);
        grid.addColumn(Prestamo::getFechaInicio).setHeader("Fecha Inicio").setAutoWidth(true);
        grid.addColumn(Prestamo::getFechaPrimerPago).setHeader("Fecha Primer Pago").setAutoWidth(true);
        grid.addColumn(Prestamo::getNombrePeriodo).setHeader("Periodo").setAutoWidth(true);
        grid.addColumn(Prestamo::getCantidadPeriodo).setHeader("Cantidad Periodo").setAutoWidth(true);

        grid.setItems(prestamoService.getLista());
//        grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(event -> {

            if (event.getValue() != null) {

                prestamo = event.getValue();

                Double montoPagado = prestamoService.getMontoPagado(prestamo.getCodigo());

                binder.readBean(prestamo);

                if (montoPagado > 0) {
                    btnGuardar.setEnabled(false);
                } else {
                    btnGuardar.setEnabled(true);
                }

            } else {
                btnGuardar.setEnabled(false);
            }

        });

        grid.addComponentColumn(prest -> {

            Button btnAnular = new Button(VaadinIcon.CLOSE.create(), event -> {

                try {

                    if (prest != null) {
                        prestamo = prest;
                        Double montoPagado;
                        montoPagado = prestamoService.getMontoPagado(prestamo.getCodigo());

                        if (montoPagado > 0) {
                            Notification.show("No puede anular un prestamo"
                                    + " que tenga pagos", 3000, Notification.Position.TOP_CENTER);
                            return;
                        }

                        ConfirmDialog dialog = new ConfirmDialog(
                                "¿Estás seguro que quiere anular el prestamo -> " + prest.getCodigo(),
                                () -> {

                                    prest.setAnulado(true);
                                    prest.setFechaAnulado(new Date());
                                    prest.setAnuladoPor("ADMIN");

                                    prestamoService.guardar(prestamo);

                                    actualizarGrid();
                                    Notification.show("Prestamo"
                                            + ""
                                            + " anulado exitosamente", 3000, Notification.Position.TOP_CENTER);

                                },
                                () -> {

                                }
                        );
                        dialog.open();

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Notification.show("Error anulando el prestamo", 3000, Notification.Position.TOP_CENTER);
                }
            });

            return btnAnular;
        }).setHeader("Anular");

        grid.addComponentColumn(prest -> {

            Button ver = new Button( VaadinIcon.EYE.create(), event -> {

                try {

                    UI.getCurrent().navigate("detallePrestamo/" + prest.getCodigo());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Notification.show("Error anulando el prestamo", 3000, Notification.Position.TOP_CENTER);
                }
            });

            return ver;
        }).setHeader("Detalle");

    }

    private void actualizarGrid() {
        grid.setItems(prestamoService.getLista());
    }

    private void limpiarFormulario() {
        
        btnGuardar.setEnabled(false);
        prestamo = new Prestamo();
        binder.readBean(prestamo);
    }

}
