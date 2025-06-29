/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.reporte;

/**
 *
 * @author Maximiliano
 */
import com.maxsoft.application.reporte.RptPrestamo;
import com.maxsoft.application.util.ClaseUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Reporte Prestamo")
@Route("/rptPrestamo")
@Menu(order = 4, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class ReportView extends VerticalLayout {

    @Autowired
    DataSource dataSource;
    HorizontalLayout hl = new HorizontalLayout();
    HorizontalLayout h2 = new HorizontalLayout();
    StreamResource pdfResource;

    // Crear RadioButtonGroup
    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();

    private final DatePicker datePicker = new DatePicker("Fecha");

    public ReportView() {

        //        radioGroup.setLabel("Seleccione una Opcion :");
        radioGroup.setItems(List.of("Todos", "Pendiente", "Saldado"));
        radioGroup.setValue("Todos");
//        radioGroup.setLabel("Empaque");// Mostrar solo el nombre
        datePicker.setValue(LocalDate.now());

        Button generateReportButton = new Button("Generar Reporte", event -> {

            try (Connection conn = dataSource.getConnection()) {

                Date fecha = ClaseUtil.asDate(datePicker.getValue());
                RptPrestamo rpt = new RptPrestamo();

                if (radioGroup.getValue().equalsIgnoreCase("Pendiente")) {

                    pdfResource = rpt.prestamoPendienteAlCorte(fecha, conn);

                } else if (radioGroup.getValue().equalsIgnoreCase("Todos")) {

                    pdfResource = rpt.prestamoAlCorte(fecha, conn);

                }

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
        h2.add(datePicker, radioGroup);

        hl.add(generateReportButton);
        add(h2, hl);

    }

}
