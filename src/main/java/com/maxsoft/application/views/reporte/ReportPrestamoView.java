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
import com.vaadin.flow.component.checkbox.Checkbox;
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

@PageTitle("Reporte Prestamo")
@Route("/rptPrestamo")
@AnonymousAllowed()
@Menu(order = 4, icon = LineAwesomeIconUrl.GLOBE_SOLID)
public class ReportPrestamoView extends VerticalLayout {

    @Autowired
    DataSource dataSource;
    HorizontalLayout hl = new HorizontalLayout();
    HorizontalLayout h2 = new HorizontalLayout();
    StreamResource pdfResource;
    Date fechaIni, fechaFin;

    // Crear RadioButtonGroup
    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();

    private final DatePicker datePickerIni = new DatePicker("Fecha Desde");
    private final DatePicker datePickerFin = new DatePicker("Fecha Hasta");

    Checkbox chAlCorte = new Checkbox("A la Fecha");

    public ReportPrestamoView() {

        //        radioGroup.setLabel("Seleccione una Opcion :");
        radioGroup.setItems(List.of("Todos", "Pendiente", "Saldado"));
        radioGroup.setValue("Todos");
//        radioGroup.setLabel("Empaque");// Mostrar solo el nombre
        datePickerIni.setValue(LocalDate.now());
        datePickerFin.setValue(LocalDate.now());

        Button generateReportButton = new Button("Generar Reporte", event -> {

            try (Connection conn = dataSource.getConnection()) {

                RptPrestamo rpt = new RptPrestamo();

                fechaIni = ClaseUtil.asDate(datePickerIni.getValue());
                fechaFin = ClaseUtil.asDate(datePickerFin.getValue());

                StringBuilder stringBuilder = new StringBuilder();

                String strDescripcion, estado = "Todos";

                if (chAlCorte.getValue()) {

                    stringBuilder.append("where fecha_inicio<='")
                            .append(ClaseUtil.formatoFecha(fechaFin))
                            .append("'");

                    strDescripcion = "Al corte: " + ClaseUtil.formatoFecha(fechaFin);

                } else {

                    stringBuilder.append(" where fecha_inicio between '")
                            .append(ClaseUtil.formatoFecha(fechaIni))
                            .append("' and '")
                            .append(ClaseUtil.formatoFecha(fechaFin))
                            .append("'");

                    strDescripcion = "Desde: " + ClaseUtil.formatoFecha(fechaIni)
                            + "  Hasta: " + ClaseUtil.formatoFecha(fechaFin);
                }

                if (radioGroup.getValue().equalsIgnoreCase("Pendiente")) {

                    stringBuilder.append(" and ")
                            .append(" total_pendiente>0 ");

                    estado = "Pendiente";

                }

                if (radioGroup.getValue().equalsIgnoreCase("Saldado")) {

                    stringBuilder.append(" and ")
                            .append(" total_pendiente<=0 ");
                    estado = "Saldado";
                }

                pdfResource = rpt.prestamo(stringBuilder.toString(),strDescripcion,estado, conn);

                Anchor anchor = new Anchor(pdfResource, "");
                anchor.getElement().setAttribute("download", false);
                anchor.getElement().setAttribute("target", "_blank");

                anchor.getElement().callJsFunction("click"); // dispara la apertura automática

                add(anchor);

            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Error al generar el reporte");
            }

        }
        );

        h2.setAlignItems(FlexComponent.Alignment.BASELINE);
        h2.add(datePickerIni, datePickerFin, radioGroup);
        chAlCorte.addClickListener(e -> {

            if (chAlCorte.getValue()) {

                datePickerIni.setEnabled(false);
            } else {

                datePickerIni.setEnabled(true);
            }

        });

        hl.add(generateReportButton);
        add(chAlCorte, h2, hl);

    }

}
