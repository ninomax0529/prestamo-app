package com.maxsoft.application.views.registrar;

import com.maxsoft.application.modelo.Medicamento;
import com.maxsoft.application.modelo.TipoMedicamento;
import com.maxsoft.application.repo.MedicamentoRepo;
import com.maxsoft.application.service.MedicamentoDaoService;
import com.maxsoft.application.util.ClaseUtil;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Registrar Medicina")
@Route("RegistrarMedicina")
@Menu(order = 1, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class RegistrarView extends VerticalLayout {

    private Grid<Medicamento> grid = new Grid<>(Medicamento.class, false);
    private Binder<Medicamento> binder = new Binder<>(Medicamento.class);
    private TextField txtNombre;
    private IntegerField txtCantidad;
    private TextField txtExistencia;
    private TextField txtCantBebidad;
    private Button btnGuardar;
    private Button nuevo = new Button("Nuevo");
    @Autowired
    private MedicamentoRepo mediRepo;
    Medicamento medicamento;
    MedicamentoDaoService medicamentoDaoService;
    DatePicker dpFecha = new DatePicker("Fecha de Compra");

    MemoryBuffer buffer = new MemoryBuffer();
    Upload upload = new Upload(buffer);

    byte[] bytes;

    public RegistrarView(MedicamentoRepo mediRepoArg, MedicamentoDaoService medicamentoDaoArg) {

        this.mediRepo = mediRepoArg;
        this.medicamentoDaoService = medicamentoDaoArg;

        txtNombre = new TextField("Nombre Medicamento");
        txtCantidad = new IntegerField("Cantidad Medicamento");
        btnGuardar = new Button("Guardar");
        dpFecha.setValue(LocalDate.now());
        setSizeFull();

        btnGuardar.addClickListener(e -> {

            if (txtNombre.getValue().isEmpty()) {

                Notification.show("Tiene que digitar el nombre ");
                txtNombre.focus();
                return;
            }

            if (txtCantidad.getValue() <= 0) {

                Notification.show("Tiene que digitar la cantidad ");
                txtCantidad.focus();
                return;
            }

            guardar();

        });

        btnGuardar.addClickShortcut(Key.ENTER);
        setMargin(true);
        setSpacing(false);
//        setHorizontalComponentAlignment(Alignment.END, txtNombre, btnGuardar);

        upload.setAcceptedFileTypes("image/jpeg", "image/png");

        Image preview = new Image();
        preview.setMaxWidth("300px");

        upload.addSucceededListener(event -> {

            try {

                String fileName = event.getFileName();
                bytes = buffer.getInputStream().readAllBytes();

                StreamResource resource = new StreamResource(fileName, () -> new ByteArrayInputStream(bytes));
                preview.setSrc(resource);

                add(new Paragraph("Imagen cargada con éxito."), preview);
            } catch (IOException e) {
                e.printStackTrace();
                add(new Paragraph("Error al procesar la imagen."));
            }
        });

//        add(new H3("Subir imagen"), upload);
//        add(dpFecha, txtNombre, txtCantidad, upload, nuevo, btnGuardar, grid);
        configurarFormulario();
        configurarGrid();
    }

    private void configurarGrid() {

        grid.addColumn(Medicamento::getNombre);
        grid.setSizeFull();

        grid.addComponentColumn(medic -> {
                       
            Button editar = new Button("Actualizar", event -> editarMedicamento(medic));

            return new HorizontalLayout(editar);
        }).setHeader("Acciones");

        nuevo.addClickListener(event -> nuevoMedicamento());

        actualizarLista();
    }

    private void editarMedicamento(Medicamento medicamento) {
       
        this.medicamento = medicamento;
        bytes = medicamento.getImagen();
        this.medicamentoDaoService.setMedicamento(medicamento);
        binder.setBean(medicamento);
    }

    private void nuevoMedicamento() {

        editarMedicamento(new Medicamento());
    }

    private void actualizarLista() {
        grid.setItems(mediRepo.findAll());
    }

    private void guardar() {

        System.out.println("medicamento " + medicamento);
//         medicamento = new Medicamento();
        medicamento.setNombre(txtNombre.getValue());
        medicamento.setCantidadComprada(txtCantidad.getValue());
        medicamento.setCantidadBebida(0);
        medicamento.setExistencia(txtCantidad.getValue());
        medicamento.setNota("Norta");
        medicamento.setFechaCompra(ClaseUtil.asDate(dpFecha.getValue()));
        medicamento.setFechaCreacion(new Date());
        medicamento.setTipoMedicamento(new TipoMedicamento(1));
        medicamento.setImagen(bytes);
        medicamento.setHora(ClaseUtil.getFormatoHora(new Date()));
        mediRepo.save(medicamento);

        Notification.show("Registro guardado exitosamente ", 2000, Notification.Position.TOP_CENTER);
//        limpiarFormulario();
        actualizarLista();

    }

    private void configurarFormulario() {

        HorizontalLayout hbBotones = new HorizontalLayout(nuevo, btnGuardar);

        FormLayout formLayout = new FormLayout(dpFecha, txtNombre, txtCantidad, upload, hbBotones);

        add(formLayout, grid);

        binder.bind(txtNombre, Medicamento::getNombre, Medicamento::setNombre);
        binder.bind(txtCantidad, Medicamento::getExistencia, Medicamento::setExistencia);

        binder.forField(dpFecha)
                .withConverter(
                        localDate -> {
                            if (localDate == null) {
                                return null;
                            }
                            return ClaseUtil.asDate(localDate); // usa java.sql.Date, que hereda de java.util.Date
                        },
                        utilDate -> {
                            if (utilDate == null) {
                                return null;
                            }
                            return ClaseUtil.convertToLocalDateViaMilisecond(utilDate);
                        },
                        "Fecha inválida")
                .bind(Medicamento::getFechaCompra, Medicamento::setFechaCompra);

        btnGuardar.addClickListener(event -> guardar());
//        cancelar.addClickListener(event -> limpiarFormulario());

        limpiarFormulario();
    }

    private void limpiarFormulario() {
        editarMedicamento(new Medicamento());
        dpFecha.setValue(LocalDate.now());
    }

}
