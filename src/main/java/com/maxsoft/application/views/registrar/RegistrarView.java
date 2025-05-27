package com.maxsoft.application.views.registrar;

import com.maxsoft.application.modelo.Medicamento;
import com.maxsoft.application.modelo.TipoMedicamento;
import com.maxsoft.application.repo.MedicamentoRepo;
import com.maxsoft.application.util.ClaseUtil;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Registrar Medicina")
@Route("RegistrarMedicina")
@Menu(order = 1, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class RegistrarView extends HorizontalLayout {

    private TextField txtNombre;
    private Button btnGuardar;
    @Autowired
    private MedicamentoRepo mediRepo;
    DatePicker dpFecha = new DatePicker("Fecha");
    byte[] bytes;

    public RegistrarView(MedicamentoRepo mediRepoArg) {

        this.mediRepo = mediRepoArg;
        txtNombre = new TextField("Nombre Medicamento");
        btnGuardar = new Button("Guardar");

        btnGuardar.addClickListener(e -> {

            guardar();
            Notification.show("Guarda " + txtNombre.getValue());
        });
        btnGuardar.addClickShortcut(Key.ENTER);
        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, txtNombre, btnGuardar);

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");

        Image preview = new Image();
        preview.setMaxWidth("300px");

        upload.addSucceededListener(event -> {

            try {

                String fileName = event.getFileName();
                bytes = buffer.getInputStream().readAllBytes();

//                Medicamento entidad = new Medicamento();
//                entidad.setNombre(fileName);
//                entidad.setImagen(bytes);
//                mediRepo.save(entidad);
                // Mostrar imagen subida
                StreamResource resource = new StreamResource(fileName, () -> new ByteArrayInputStream(bytes));
                preview.setSrc(resource);

                add(new Paragraph("Imagen guardada con éxito."), preview);
            } catch (IOException e) {
                e.printStackTrace();
                add(new Paragraph("Error al procesar la imagen."));
            }
        });

//        add(new H3("Subir imagen"), upload);
        add(txtNombre, dpFecha, upload, btnGuardar);
    }

    private void guardar() {

        if (txtNombre.getValue().isEmpty()) {

            Notification.show("Tiene que digitar el nombre ");
            return;
        }

        Medicamento medicamento = new Medicamento();

        medicamento.setNombre(txtNombre.getValue());
        medicamento.setNota("Norta");
        medicamento.setFechaCompra(ClaseUtil.asDate(dpFecha.getValue()));
        medicamento.setFechaCreacion(new Date());
        medicamento.setTipoMedicamento(new TipoMedicamento(1));
        medicamento.setImagen(bytes);
        medicamento.setHora(ClaseUtil.getFormatoHora(new Date()));
        mediRepo.save(medicamento);
        Notification.show("Registro guardado exitosamente ");

    }
}
