/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.reciboIngreso;

import com.maxsoft.application.modelo.Cliente;
import com.maxsoft.application.modelo.DetallePrestamo;
import com.maxsoft.application.modelo.DetalleReciboDeIngreso;
import com.maxsoft.application.modelo.Prestamo;
import com.maxsoft.application.modelo.ReciboDeIngreso;
import com.maxsoft.application.service.PrestamoService;
import com.maxsoft.application.service.ReciboDeIngresoService;
import com.maxsoft.application.util.ClaseUtil;
import com.maxsoft.application.util.NavigationContext;
import com.maxsoft.application.views.componente.ToolBarBotonera;
import com.maxsoft.application.views.dialogo.PrestamoDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
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
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import com.vaadin.flow.router.Route;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@Route(value = "/registroRecibos")
@PageTitle("Regsitro Recibos de Ingreso")
@Menu(order = 3, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@PermitAll
public class RegistroReciboDeIngresoView extends VerticalLayout implements HasUrlParameter<String> {

    private final ReciboDeIngresoService reciboService;
    private final PrestamoService prestamoService;

    private final Binder<ReciboDeIngreso> binder = new Binder<>(ReciboDeIngreso.class);

    private final DatePicker dpFecha = new DatePicker("Fecha");

    private final TextField nombreCliente = new TextField("Nombre del Cliente");
    private final TextField numeroPrestamo = new TextField("Prestamo");
    private final NumberField txtValorCuota = new NumberField("Valor Cuota");
    private final NumberField montoPendiente = new NumberField("Monto Pendiente");
    private final NumberField montoPrestado = new NumberField("Monto Prestado");
    private final TextArea descripcionPago = new TextArea("Descripción del Pago");

    private final Button btnGuardar = new Button("Guardar");
    private final Button limpiar = new Button("Limpiar");
    Button buscarPrestamo = new Button(new Icon(VaadinIcon.SEARCH));
    ToolBarBotonera botonera = new ToolBarBotonera(false, true, true);
    private final Grid<DetallePrestamo> gridDetPrestamo = new Grid<>(DetallePrestamo.class, false);
    ListDataProvider<DetallePrestamo> dataProvider;

    private ReciboDeIngreso reciboActual;
    Cliente cliente;
    Prestamo prestamo;

    @Autowired
    public RegistroReciboDeIngresoView(ReciboDeIngresoService reciboService, PrestamoService prestamoService) {
        this.reciboService = reciboService;
        this.prestamoService = prestamoService;
        addClassName("prestamo-view");

        setSizeFull();
        setPadding(true);
        setSpacing(false);
        configurarGridDetPrestamo();

        buscarPrestamo.addClassName("boton-buscar");

        botonera.getGuardar().addClickListener(e -> {
            // lógica de guardar
            guardar();
        });

        botonera.getCancelar().addClickListener(e -> {
            // lógica de cancelar
            UI.getCurrent().navigate(ReciboDeIngresoView.class);
        });

        buscarPrestamo.addClickListener(e -> {

            buscarPrestamo();
        });

        add(botonera, crearFormulario(), gridDetPrestamo);

    }

    private void configurarGridDetPrestamo() {

// Editor
        Binder biderDet = new Binder<>(DetallePrestamo.class);
        Editor<DetallePrestamo> editor = gridDetPrestamo.getEditor();
        editor.setBinder(biderDet);
//        editor.setBuffered(true);

        NumberField pendiente = new NumberField();
        pendiente.setStep(0.01);
        pendiente.setWidth("100px");

        gridDetPrestamo.addColumn(DetallePrestamo::getFecha).setHeader("Fecha").setWidth("120px");
        gridDetPrestamo.addColumn(DetallePrestamo::getNumeroCuota).setHeader("Numero").setWidth("88px");
        gridDetPrestamo.addColumn(DetallePrestamo::getValorCuota).setHeader("Cuota").setWidth("95px");
        gridDetPrestamo.addColumn(DetallePrestamo::getMontoPagado).setHeader("Pagado");
        gridDetPrestamo.addColumn(DetallePrestamo::getMontoPendiente)
                .setEditorComponent(pendiente)
                .setHeader("Pendiente");

        gridDetPrestamo.addItemClickListener(event -> {
//            if (editor.isOpen()) {
//                editor.cancel(); // cierra el editor actual
//            }
            editor.editItem(event.getItem());
            pendiente.focus();
        });

        gridDetPrestamo.addComponentColumn(p -> {

            Checkbox checkbox = new Checkbox(false);

            checkbox.addValueChangeListener(event -> {

                if (editor.isOpen()) {
                    editor.cancel(); // cierra el editor actual
                }
                boolean nuevoEstado = event.getValue();

                p.setEstado(nuevoEstado);

                crearDetalle();
            });

//            checkbox.setReadOnly(true);
            return checkbox;
        }).setHeader("Pagar");

        // Detectar ENTER
        pendiente.getElement().addEventListener("keydown", e -> {

            editor.getItem().setMontoPendiente(pendiente.getValue());

//            editor.save(); // guarda el registro actual
        }).setFilter("event.key === 'Enter'");

        gridDetPrestamo.setSizeFull();

    }

    private FormLayout crearFormulario() {

        FormLayout form = new FormLayout();
        btnGuardar.setEnabled(false);
        dpFecha.setValue(LocalDate.now());
        numeroPrestamo.setWidth("30%");
        numeroPrestamo.setReadOnly(true);

        nombreCliente.setReadOnly(true);
        txtValorCuota.setReadOnly(true);
        nombreCliente.setReadOnly(true);
        txtValorCuota.setWidth("30%");
        montoPendiente.setWidth("30%");
        montoPrestado.setWidth("30%");

        HorizontalLayout hlcliente = new HorizontalLayout(dpFecha, numeroPrestamo, buscarPrestamo);
//        HorizontalLayout hlPrestamo = new HorizontalLayout(montoPrestado,txtValorCuota, montoPendiente);

        hlcliente.setAlignItems(Alignment.BASELINE);
        hlcliente.setSpacing("1%");

        btnGuardar.addClickListener(e -> {

            guardar();

        });

        limpiar.addClickListener(e -> limpiarFormulario());

        binder.bindInstanceFields(this);

        form.add(hlcliente,
                nombreCliente,
                txtValorCuota,
                descripcionPago
        );
        return form;
    }

    private void guardar() {

        try {

            if (reciboActual == null) {
                reciboActual = new ReciboDeIngreso();
            }

            Double montoPend, montoPagado;

            binder.writeBean(reciboActual);
            reciboActual.setFechaCreacion(new Date());
            reciboActual.setCreadoPor("ADMIN");
            reciboActual.setFecha(ClaseUtil.asDate(dpFecha.getValue()));
            reciboActual.setCliente(cliente);
            reciboActual.setPrestamo(prestamo);
            reciboActual.setTotal(txtValorCuota.getValue());

            montoPend = prestamoService.getMontoPendiente(prestamo.getCodigo());
            System.out.println("montoPend actual "+montoPend);
              montoPend = montoPend - reciboActual.getTotal();
                 System.out.println("montoPend posterior menos  "+ reciboActual.getTotal()+" = "+montoPend);

            reciboActual.setMontoPendiente(montoPend);
            reciboActual.setDetalleReciboDeIngresoCollection(crearDetalle());

            reciboService.guardar(reciboActual);

            montoPagado = prestamoService.getMontoPagado(prestamo.getCodigo());
            prestamo.setTotalPagado(montoPagado);
            prestamo.setTotalPendiente(montoPend);

            actualizarDetallePresramo();

            prestamo.setDetallePrestamoCollection(dataProvider.getItems());

            prestamoService.guardar(prestamo);

            limpiarFormulario();
            dataProvider.getItems().clear();
            dataProvider.refreshAll();
            Notification.show("Recibo guardado exitosamente ", 2000, Notification.Position.TOP_CENTER);
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

    private void editar(ReciboDeIngreso recibo) {
        this.reciboActual = recibo;

        binder.setBean(recibo);
    }

    @Override
    public void setParameter(BeforeEvent be, String parameter) {

        ReciboDeIngreso entidad = NavigationContext.retrieve(parameter, ReciboDeIngreso.class);
        if (entidad != null) {
            System.out.println("Art :" + entidad);
            editar(entidad);
        } else {
            System.out.println("Error Art :" + entidad);
            // manejar error si no se encuentra
        }
    }

    private void buscarPrestamo() {

        List<Prestamo> prestamos = prestamoService.getPrestamoPendiente(); // desde la capa de servicio
        PrestamoDialog dialog = new PrestamoDialog(prestamos, seleccionado -> {

            nombreCliente.setValue(seleccionado.getNombreCliente()); // ComboBox o campo vinculado
            numeroPrestamo.setValue(seleccionado.getCodigo().toString()); // ComboBox o campo vinculado
            txtValorCuota.setValue(seleccionado.getMontoCuota());
            cliente = seleccionado.getCliente();
            prestamo = seleccionado;
            btnGuardar.setEnabled(true);
//
//            StringBuilder descripcion = new StringBuilder();
//            int canPago = this.reciboService.getCantidadPago(prestamo.getCodigo()) + 1;
//
//            descripcion.append("Pago ").append(canPago)
//                    .append(" de ").append(prestamo.getCantidadPeriodo());

            gridDetPrestamo.setItems(prestamoService.getDetalleCuotaPendiente(prestamo.getCodigo()));

//            descripcionPago.setValue(descripcion.toString());
        });
        dialog.open();

    }

    private List<DetalleReciboDeIngreso> crearDetalle() {

        List<DetalleReciboDeIngreso> lista = new ArrayList<>();

        DetalleReciboDeIngreso det;

        dataProvider = (ListDataProvider<DetallePrestamo>) gridDetPrestamo.getDataProvider();

        String descPago = "";
        Double monPendiente, montoPagado=0.00;

        reciboActual.setTotal(0.00);
        for (DetallePrestamo detP : dataProvider.getItems()) {

            if (detP.getEstado() == true) {

                det = new DetalleReciboDeIngreso();

                monPendiente = prestamoService.getMontoPendienteCuota(detP.getPrestamo().getCodigo(), detP.getCodigo());
//                montoPagado = prestamoService.getMontoPagadoCuota(detP.getPrestamo().getCodigo(), detP.getCodigo());

                det.setTotal(detP.getMontoPendiente());
                det.setMontoPendiente(monPendiente - det.getTotal());
                det.setRecibo(reciboActual);
                det.setNumeroCuota(detP.getNumeroCuota());
                det.setCuota(detP.getCodigo());
                
                montoPagado+=det.getTotal();
                
//                reciboActual.setTotal(reciboActual.getTotal()+det.getTotal());

                StringBuilder builderDescripcion = new StringBuilder();

                int canPago = detP.getNumeroCuota();//this.reciboService.getCantidadPago(prestamo.getCodigo()) + 1;

                if (det.getTotal() < detP.getValorCuota()) {

                    builderDescripcion.append("Abono a cuota numero ").append(canPago);
                    // .append(" de ").append(prestamo.getCantidadPeriodo());

                } else {

                    builderDescripcion.append("Pago cuota ").append(canPago)
                            .append(" de ").append(prestamo.getCantidadPeriodo());

                }

                det.setConcepto(builderDescripcion.toString());

                System.out.println("Concepto " + det.getConcepto());
                descPago += det.getConcepto() + "\n";

                lista.add(det);

            }

            descripcionPago.setValue(descPago);
        }
        
        txtValorCuota.setValue(montoPagado);

        return lista;
    }

    private void actualizarDetallePresramo() {

        dataProvider = (ListDataProvider<DetallePrestamo>) gridDetPrestamo.getDataProvider();

        Double monPendiente, montoPagado;

        for (DetallePrestamo detP : dataProvider.getItems()) {

            monPendiente = prestamoService.getMontoPendienteCuota(detP.getPrestamo().getCodigo(), detP.getCodigo());
            montoPagado = prestamoService.getMontoPagadoCuota(detP.getPrestamo().getCodigo(), detP.getCodigo());

            detP.setMontoPagado(montoPagado);
            detP.setMontoPendiente(monPendiente);

        }

    }

}
