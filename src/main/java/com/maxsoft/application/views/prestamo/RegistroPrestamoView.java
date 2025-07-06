/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.prestamo;

import com.maxsoft.application.modelo.Cliente;
import com.maxsoft.application.modelo.DetallePrestamo;
import com.maxsoft.application.modelo.Periodo;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.modelo.TipoPrestamo;
import com.maxsoft.application.service.ClienteService;
import com.maxsoft.application.service.PeriodoService;
import com.maxsoft.application.service.PrestamoService;
import com.maxsoft.application.service.TipoPrestamoService;
import com.maxsoft.application.util.ClaseUtil;
import com.maxsoft.application.util.NavigationContext;
import com.maxsoft.application.views.componente.ToolBarBotonera;
import com.maxsoft.application.views.dialogo.ClienteSelectorDialog;
import com.maxsoft.application.views.dialogo.ConfirmDialog;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

/**
 *
 * @author Maximiliano
 */
@Route("/registroPrestamos")
@PageTitle("Gestión de Préstamos")
@AnonymousAllowed()
@Menu(order = 2, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class RegistroPrestamoView extends VerticalLayout implements HasUrlParameter<String> {

    private final Grid<DetallePrestamo> gridDet = new Grid<>(DetallePrestamo.class, false);
    private final PrestamoService prestamoService;
    private final PeriodoService periodoService;
    private final TipoPrestamoService tipoPrestamoService;
    private final ClienteService clienteService;
    List<DetallePrestamo> listaDetPrestamo = new ArrayList<>();

    private final Grid<Prestamo> grid = new Grid<>(Prestamo.class, false);
    private final Binder<Prestamo> binder = new Binder<>(Prestamo.class);
    private Prestamo prestamo = new Prestamo();
    Integer canPeriodo;
    Double montoCuot = 0.00;
    // Componentes del formulario
    private final DatePicker fechaInicio = new DatePicker("Fecha");
    private final DatePicker fechaPrimerPago = new DatePicker("Fecha Primer Pago");
    private final TextField nombreCliente = new TextField("Cliente");
    private final NumberField montoPrestado = new NumberField("Monto Prestado");
    private final NumberField totalPrestamo = new NumberField("Total");
    private final NumberField montoIntere = new NumberField("Monto Interés");
    private final NumberField tasaDeIntere = new NumberField("Tasa de Interés %");
    private final NumberField montoCuota = new NumberField("Monto Cuota");
    TextField cantidadPeriodoField = new TextField("Cantidad de Periodo");

    private final ComboBox<Periodo> periodo = new ComboBox<>("Periodo");
    private final ComboBox<TipoPrestamo> tipoPrestamo = new ComboBox<>("Tipo de Préstamo");

    private final Button btnGuardar = new Button("Guardar", new Icon(VaadinIcon.CHECK));
    private final Button btnLimpiar = new Button("Limpiar", VaadinIcon.REFRESH.create());
    Button buscarClienteBtn = new Button(new Icon(VaadinIcon.SEARCH));
    private final Button btnCalcular = new Button("Calcular");
    ToolBarBotonera botonera = new ToolBarBotonera(false, true, true);

    public RegistroPrestamoView(PrestamoService prestamoService, PeriodoService periodoService, TipoPrestamoService tipoPrestamoService,
            ClienteService clienteService) {

        setPadding(true);
        setSpacing(false);
        this.prestamoService = prestamoService;
        this.periodoService = periodoService;
        this.tipoPrestamoService = tipoPrestamoService;
        this.clienteService = clienteService;
        btnGuardar.setEnabled(false);

        botonera.getGuardar().addClickListener(e -> {
            // lógica de guardar

//           if (binder.validate().isOk()) {
//                   Notification.show("Faltan datos por registrar  "
//                            , 3000, Notification.Position.TOP_CENTER);
//                 return;
//             }
            ConfirmDialog dialog = new ConfirmDialog(
                    "¿Estás seguro que quiere guardar el prestamo -> ",
                    () -> {
                        guardar();

                        Notification.show("Prestamo"
                                + ""
                                + " guardado exitosamente", 3000, Notification.Position.TOP_CENTER);

                    },
                    () -> {

                    }
            );
            dialog.open();

        });

        botonera.getCancelar().addClickListener(e -> {
            // lógica de cancelar
            UI.getCurrent().navigate(PrestamoView.class);
        });

        periodo.addValueChangeListener(e -> {

            if (e.getValue() != null) {
                cantidadPeriodoField.focus();
            }

        });

        addClassName("prestamo-view");
        buscarClienteBtn.addClassName("boton-buscar");
        btnCalcular.addClassName("boton-calcular");
//        setSizeFull();

        configurarFormulario();
        configurarEventos();
        configurarGrid();

        confiBinder();

        add(botonera, crearFormulario(), gridDet);

    }

    private void configurarFormulario() {

        periodo.setItems(periodoService.getLista());
        periodo.setItemLabelGenerator(Periodo::getNombre);

        tipoPrestamo.setItems(tipoPrestamoService.getLista());
        tipoPrestamo.setItemLabelGenerator(TipoPrestamo::getNombre);
//        binder.forField(totalPrestamo).bind("total");
        binder.bindInstanceFields(this);

    }

    private FormLayout crearFormulario() {

        totalPrestamo.setWidth("30%");
        montoCuota.setWidth("30%");
        tasaDeIntere.setWidth("30%");
        montoIntere.setWidth("35%");
        montoPrestado.setWidth("35%");

        HorizontalLayout hlValor = new HorizontalLayout(montoCuota, tasaDeIntere, totalPrestamo);
        HorizontalLayout hlCliente = new HorizontalLayout(nombreCliente, buscarClienteBtn);
        HorizontalLayout hlPeriodo = new HorizontalLayout(periodo, cantidadPeriodoField);
        HorizontalLayout hlMonto = new HorizontalLayout(montoPrestado, montoIntere, btnCalcular);
        HorizontalLayout hlFecha = new HorizontalLayout(fechaInicio, fechaPrimerPago);

        nombreCliente.setSizeFull();
        nombreCliente.setReadOnly(true);
        totalPrestamo.setReadOnly(true);
        tasaDeIntere.setReadOnly(true);
        montoCuota.setReadOnly(true);

//        hlPeriodo.setSizeFull();
        hlValor.setSpacing("1%");
        hlCliente.setSpacing("1%");
        hlMonto.setSpacing("1%");

        hlValor.setAlignItems(Alignment.BASELINE);
        hlCliente.setAlignItems(Alignment.BASELINE);
        hlPeriodo.setAlignItems(Alignment.BASELINE);
        hlMonto.setAlignItems(Alignment.BASELINE);
        hlFecha.setAlignItems(Alignment.BASELINE);

        FormLayout formLayout = new FormLayout(
                hlCliente,
                tipoPrestamo,
                hlPeriodo,
                hlFecha,
                hlMonto,
                hlValor
        );

        return formLayout;
    }

    private void configurarEventos() {

        btnGuardar.addClickListener(e -> {

            try {

                System.out.println("Guardando prestamo ...");

                prestamo.setNombrePeriodo(periodo.getValue().getNombre());
                prestamo.setNombreTipoPrestamo(tipoPrestamo.getValue().getNombre());
                prestamo.setCreadoPor("ADMIN");
                prestamo.setFechaCreacion(new Date());
//                prestamo.setTotal();

                binder.writeBean(prestamo);

                listaDetPrestamo.clear();
                listaDetPrestamo.addAll(crearDetalle(canPeriodo, montoCuot));

                listaDetPrestamo.forEach(d -> {

                    System.out.println("Det capital  " + d.getCapital());
                    d.setCodigo(null);
//                    d.setPrestamo(prestamo);
                });

                prestamo.setDetallePrestamoCollection(listaDetPrestamo);

                prestamoService.guardar(prestamo);

//                actualizarGrid();
                Notification.show("Préstamo guardado");
                limpiarFormulario();
            } catch (ValidationException ex) {
                Notification.show("Datos inválidos: " + ex.getMessage());
            }
        });

        buscarClienteBtn.addClickListener(e -> {

            List<Cliente> clientes = clienteService.getLista(true); // Ajusta según tu servicio real
            ClienteSelectorDialog dialog = new ClienteSelectorDialog(clientes, cliente -> {

                if (cliente != null) {
                    btnGuardar.setEnabled(true);
                } else {
                    btnGuardar.setEnabled(false);
                }
                nombreCliente.setValue(cliente.getNombre());
                prestamo.setCliente(cliente); // Enlazar al objeto Prestamo
            });

            dialog.open();
        });

        btnCalcular.addClickListener((ClickEvent<Button> e) -> {

            Double tasaIntere = 0.00, montoPrestad = 0.00, montoInter = 0.00;
            canPeriodo = Integer.valueOf(cantidadPeriodoField.getValue());

            montoPrestad = this.montoPrestado.getValue();
            montoInter = this.montoIntere.getValue();
            prestamo.setTotalPagado(0.00);
            prestamo.setTotalPendiente(montoPrestad);

            montoCuot = ClaseUtil.formatoNumeroSinComa((montoPrestad + montoInter) / canPeriodo);

            tasaIntere = ClaseUtil.FormatearDouble((montoInter / montoPrestad) * 100, 2);
            tasaDeIntere.setValue(tasaIntere);
            this.montoCuota.setValue(montoCuot);
            totalPrestamo.setValue(montoInter + montoPrestad);

            listaDetPrestamo.clear();

            listaDetPrestamo.addAll(crearDetalle(canPeriodo, montoCuot));

            gridDet.setItems(listaDetPrestamo);





        });

        btnLimpiar.addClickListener(e -> limpiarFormulario());
    }
//
//    private void actualizarGrid() {
//        grid.setItems(prestamoService.getLista());
//    }

    private void limpiarFormulario() {

        btnGuardar.setEnabled(false);
        prestamo = new Prestamo();
        binder.readBean(prestamo);
        listaDetPrestamo.clear();
        gridDet.getDataProvider().refreshAll();
    }

    private void confiBinder() {

// Cliente (solo texto)
        binder.forField(nombreCliente)
                .asRequired("El nombre del cliente es obligatorio")
                .bind(Prestamo::getNombreCliente, Prestamo::setNombreCliente);

// Monto prestado
        binder.forField(montoPrestado)
                .asRequired("El monto prestado es obligatorio")
                .bind(Prestamo::getMontoPrestado, Prestamo::setMontoPrestado);

// Total del préstamo
        binder.forField(totalPrestamo)
                .asRequired("El total del préstamo es obligatorio")
                .bind(Prestamo::getTotal, Prestamo::setTotal);

// Monto del interés
        binder.forField(montoIntere)
                .asRequired("El monto de interés es obligatorio")
                .bind(Prestamo::getMontoIntere, Prestamo::setMontoIntere);

// Tasa de interés
        binder.forField(tasaDeIntere)
                .asRequired("La tasa de interés es obligatoria")
                .bind(Prestamo::getTasaDeIntere, Prestamo::setTasaDeIntere);

// Monto de la cuota
        binder.forField(montoCuota)
                .asRequired("El monto de la cuota es obligatorio")
                .bind(Prestamo::getMontoCuota, Prestamo::setMontoCuota);

// Cantidad de periodos (como texto, puede convertirse si es numérico)
        binder.forField(cantidadPeriodoField)
                .withNullRepresentation("") // trata el string vacío como null
                .withConverter(
                        new StringToIntegerConverter("Debe ser un número entero")) // conversión segura
                .bind(Prestamo::getCantidadPeriodo, Prestamo::setCantidadPeriodo);

    }

    private void editar(Prestamo presta) {
        this.prestamo = presta;

        binder.setBean(presta);
    }

    @Override
    public void setParameter(BeforeEvent be, String parameter) {

        Prestamo entidad = NavigationContext.retrieve(parameter, Prestamo.class);
        if (entidad != null) {
            System.out.println("Art :" + entidad);
            editar(entidad);
        } else {
            System.out.println("Error Art :" + entidad);
            // manejar error si no se encuentra
        }
    }

    private void guardar() {

        try {

            System.out.println("Guardando prestamo ...");

            prestamo.setNombrePeriodo(periodo.getValue().getNombre());
            prestamo.setNombreTipoPrestamo(tipoPrestamo.getValue().getNombre());
            prestamo.setCreadoPor("ADMIN");
            prestamo.setFechaCreacion(new Date());
//                prestamo.setTotal();

            binder.writeBean(prestamo);

//            listaDetPrestamo.clear();
//            listaDetPrestamo.addAll(crearDetalle(canPeriodo, montoCuot));

            listaDetPrestamo.forEach(d -> {

                System.out.println("Det capital  " + d.getCapital());
                d.setCodigo(null);
//                    d.setPrestamo(prestamo);
            });

            prestamo.setDetallePrestamoCollection(listaDetPrestamo);

            prestamoService.guardar(prestamo);

//                actualizarGrid();
//            Notification.show("Préstamo guardado");
            limpiarFormulario();
        } catch (ValidationException ex) {
            Notification.show("Datos inválidos: " + ex.getMessage());
        }

    }

    private List<DetallePrestamo> crearDetalle(int canCuota, Double valorCuota) {

        List<DetallePrestamo> lista = new ArrayList<>();
        DetallePrestamo det;

        Date fecha = null;

        fecha = ClaseUtil.asDate(fechaPrimerPago.getValue());
        prestamo.setFechaPrimerPago(fecha);
        prestamo.setPeriodo(periodo.getValue());

        Double montoInteres, montoCapital;

        for (int i = 1; i <= canCuota; i++) {

            montoInteres = montoIntere.getValue() / canCuota;
            montoCapital = montoPrestado.getValue() / canCuota;
            det = new DetallePrestamo();
            det.setCodigo(i);

            det.setConcepto("pago");
            det.setValorCuota(valorCuota);
            det.setCapital(ClaseUtil.formatoNumeroSinComa(montoCapital));
            det.setInteres(ClaseUtil.formatoNumeroSinComa(montoInteres));
            det.setEstado(false);

            det.setFechaAplicado(new Date());
            det.setMontoPagado(0.00);
            det.setMontoPendiente(valorCuota);
            det.setPrestamo(prestamo);
            det.setNumeroCuota(i);
            det.setFecha(fecha);
            det.setEstado(false);

            if (null != prestamo.getPeriodo().getCodigo()) {

                switch (prestamo.getPeriodo().getCodigo()) {
                    case 5 -> {
                        fecha = ClaseUtil.Fechadiadespues(fecha, 1);
                        System.out.println("Fecha diario " + fecha);
                        break;
                    }
                    case 6 -> {
                        fecha = ClaseUtil.Fechadiadespues(fecha, 7);
                        System.out.println("Fecha semanal " + fecha);
                        break;
                    }
                    case 7 -> {
                        fecha = ClaseUtil.Fechadiadespues(fecha, 15);
                        System.out.println("Fecha quincenal " + fecha);
                        break;
                    }
                    case 8 -> {
                        fecha = ClaseUtil.Fechadiadespues(fecha, 30);
                        System.out.println("Fecha mensual " + fecha);
                        break;
                    }
                    default -> {
                    }
                }
            }

            lista.add(det);

        }

        return lista;

    }

    private void configurarGrid() {

        gridDet.addColumn(DetallePrestamo::getNumeroCuota).setHeader("Numero").setAutoWidth(true);
        gridDet.addColumn(DetallePrestamo::getValorCuota).setHeader("Cuota").setAutoWidth(true);
        gridDet.addColumn(DetallePrestamo::getCapital).setHeader("Capital").setAutoWidth(true);
        gridDet.addColumn(DetallePrestamo::getInteres).setHeader("Interes").setAutoWidth(true);
        
        gridDet.setItems(listaDetPrestamo);
//        gridDet.addColumn(DetallePrestamo::getMontoPagado).setHeader("Pagado").setAutoWidth(true);
//        gridDet.addColumn(DetallePrestamo::getMontoPendiente).setHeader("Pendiente").setAutoWidth(true);

    }
}
