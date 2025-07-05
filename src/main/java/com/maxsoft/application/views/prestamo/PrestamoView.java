/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.prestamo;

import com.maxsoft.application.modelo.Periodo;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.modelo.TipoPrestamo;
import com.maxsoft.application.reporte.RptPrestamo;
import com.maxsoft.application.service.ClienteService;
import com.maxsoft.application.service.PeriodoService;
import com.maxsoft.application.service.PrestamoService;
import com.maxsoft.application.service.TipoPrestamoService;
import com.maxsoft.application.util.NavigationContext;
import com.maxsoft.application.views.componente.FiltroAvanzado;
import com.maxsoft.application.views.componente.ToolBarBotonera;
import com.maxsoft.application.views.dialogo.ConfirmDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.PermitAll;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

/**
 *
 * @author Maximiliano
 */
@Route("prestamos")
@PageTitle("Gestión de Préstamos")
@Menu(order = 2, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@PermitAll
public class PrestamoView extends VerticalLayout {

    @Autowired
    DataSource dataSource;
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
    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();

    public PrestamoView(PrestamoService prestamoService, PeriodoService periodoService, TipoPrestamoService tipoPrestamoService,
            ClienteService clienteService) {

        setSizeFull();
        setPadding(true);
        setSpacing(false);
        this.prestamoService = prestamoService;
        this.periodoService = periodoService;
        this.tipoPrestamoService = tipoPrestamoService;
        this.clienteService = clienteService;

        //        radioGroup.setLabel("Seleccione una Opcion :");
        radioGroup.setItems(List.of("Todos", "Pendiente", "Saldado"));
        radioGroup.setValue("Todos");

        radioGroup.addValueChangeListener(e -> {

            String seleccion = e.getValue();
            Notification.show("Seleccionaste: " + seleccion);
            actualizarGrid();
        });

        GridListDataView<Prestamo> dataView = grid.setItems(this.prestamoService.getLista());

// Crear el filtro avanzado
        FiltroAvanzado<Prestamo> filtro = new FiltroAvanzado<>(dataView);

// Agregar filtros por nombre y cédula
        filtro.addFiltro("Cliente", Prestamo::getNombreCliente);
//        filtro.addFiltro("Prestamo", Prestamo::getCodigo().toStrin);

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

        add(botonera, radioGroup, filtro, grid);
        actualizarGrid();

    }

    private void configurarGrid() {

        grid.addColumn(p -> p.getCodigo()).setHeader("Prestamo").setAutoWidth(true);
        grid.addColumn(p -> p.getCliente().getNombre()).setHeader("Cliente").setAutoWidth(true);
//        grid.addColumn(Prestamo::getNombreTipoPrestamo).setHeader("Tipo Prestamo").setAutoWidth(true);
//        grid.addColumn(Prestamo::getMontoPrestado).setHeader("Monto Prestado").setAutoWidth(true);
//        grid.addColumn(Prestamo::getMontoIntere).setHeader("Monto Interes").setAutoWidth(true);
//        grid.addColumn(Prestamo::getTotal).setHeader("Total Prestamo").setAutoWidth(true);
//        grid.addColumn(Prestamo::getTotalPagado).setHeader("Total Pagado").setAutoWidth(true);
//        grid.addColumn(Prestamo::getTotalPendiente).setHeader("Total Pendiente").setAutoWidth(true);
//        grid.addColumn(Prestamo::getFechaInicio).setHeader("Fecha Inicio").setAutoWidth(true); 
//        grid.addColumn(Prestamo::getNombrePeriodo).setHeader("Periodo").setAutoWidth(true);
//        grid.addColumn(Prestamo::getCantidadPeriodo).setHeader("Cant.Periodo").setAutoWidth(true);

        grid.setItems(prestamoService.getLista());
        grid.setSizeFull();

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

            HorizontalLayout acciones = new HorizontalLayout();

            Button verBtn = new Button(VaadinIcon.EYE.create(), event -> {

                // Aquí puedes abrir un diálogo o navegar
                try (Connection conn = dataSource.getConnection()) {

                    RptPrestamo rptP = new RptPrestamo();

                    if (rptP != null) {

                        StreamResource pdfResource = rptP.reciboIngreso(prest.getCodigo(), conn);

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

                    UI.getCurrent().navigate("detallePrestamo/" + prest.getCodigo());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Notification.show("Error anulando el prestamo", 3000, Notification.Position.TOP_CENTER);
                }
            });
            detalleBtn.getElement().setAttribute("title", "Detalle");

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

            btnAnular.getElement().setAttribute("title", "Anular");
            btnAnular.getStyle().set("color", "red");
//            btnAnular.setWidth("80px");
            acciones.setSizeFull();
            acciones.setSpacing("2px");

            acciones.add(verBtn, detalleBtn, btnAnular);

            return acciones;
        }).setHeader("Acciones").setAutoWidth(true);

    }

    private void actualizarGrid() {

        if (radioGroup.getValue().equalsIgnoreCase("Todos")) {

            grid.setItems(prestamoService.getLista());

        } else if (radioGroup.getValue().equalsIgnoreCase("Pendiente")) {

            grid.setItems(prestamoService.getPrestamoPendiente());

        } else if (radioGroup.getValue().equalsIgnoreCase("Saldado")) {

            grid.setItems(prestamoService.getPrestamoSaldado());
        }

  
    }

    private void limpiarFormulario() {

        btnGuardar.setEnabled(false);
        prestamo = new Prestamo();
        binder.readBean(prestamo);
    }

}
