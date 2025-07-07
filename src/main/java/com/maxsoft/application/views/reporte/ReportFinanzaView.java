/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.reporte;

/**
 *
 * @author Maximiliano
 */
import com.maxsoft.application.reporte.RptFinanza;
import com.maxsoft.application.util.ClaseUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@AnonymousAllowed()
@PageTitle("Reportes Financiero")
@Route("/rptFinanza")
@Menu(order = 7)
public class ReportFinanzaView extends VerticalLayout {

    @Autowired
    DataSource dataSource;
    HorizontalLayout hl = new HorizontalLayout();
    HorizontalLayout h2 = new HorizontalLayout();
    StreamResource pdfResource;

    // Crear RadioButtonGroup
    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();

    private final DatePicker datePicker = new DatePicker("Fecha");

    public ReportFinanzaView() {

        radioGroup.setItems(List.of("Pendiente", "Todos"));
        radioGroup.setValue("Pendiente");
//        radioGroup.setLabel("Empaque");// Mostrar solo el nombre
        datePicker.setValue(LocalDate.now());

        Button generateReportButton = new Button("Estado de Resultado", event -> {

            try (Connection conn = dataSource.getConnection()) {

                Date fecha = ClaseUtil.asDate(datePicker.getValue());
                RptFinanza rpt = new RptFinanza();

                pdfResource = rpt.estadoResultadoGerencial(fecha, conn);

                Anchor anchor = new Anchor(pdfResource, "");
                anchor.getElement().setAttribute("download", false);
                anchor.getElement().setAttribute("target", "_blank");

                anchor.getElement().callJsFunction("click"); // dispara la apertura automática

                add(anchor);

            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Error al generar el reporte");
            }

        });

        h2.setAlignItems(FlexComponent.Alignment.BASELINE);
        h2.add(datePicker);

        hl.add(generateReportButton);
        add(h2, hl);

    }

}
