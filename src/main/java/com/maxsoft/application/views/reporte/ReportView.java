/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.reporte;

/**
 *
 * @author Maximiliano
 */
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import net.sf.jasperreports.engine.JasperRunManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

@PageTitle("Reporete Empaque")
@Route("empaque/RptEmcadora")
public class ReportView extends VerticalLayout {

    @Autowired
    DataSource dataSource;
    HorizontalLayout hl = new HorizontalLayout();
    HorizontalLayout h2 = new HorizontalLayout();

    // Crear RadioButtonGroup
    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();

    private final DatePicker datePicker = new DatePicker("Fecha");

    private final IFrame pdfViewer = new IFrame();

    public ReportView() {

        radioGroup.setLabel("Seleccione una Opcion :");
        radioGroup.setItems(List.of("Operacion  Empacadora", "Resumen Empaque"));
//        radioGroup.setLabel("Empaque");// Mostrar solo el nombre
        datePicker.setValue(LocalDate.now());
        Button generateReportButton = new Button("Generar Reporte", event -> {

            try {

                StreamResource pdfResource = null;
                LocalDate selectedDate = datePicker.getValue();
                if (selectedDate == null) {

                    Notification.show("Tiene que seleccionar una fecha ", 3000, Notification.Position.MIDDLE);
                    return;
                }

                pdfResource = reciboIngreso(1);

                Anchor pdfAnchor = new Anchor(pdfResource, "Abrir Reporte");
                hl.getComponentAt(0).removeFromParent();

                pdfAnchor.setTarget("_blank");

                hl.add(pdfAnchor);

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        h2.setAlignItems(FlexComponent.Alignment.BASELINE);
        h2.add(datePicker, radioGroup);

        hl.add(generateReportButton);
        add(h2, hl);

    }

    private StreamResource reporteEmpacadora(Date fecha) {

        StreamResource pdfResource = null;
        Anchor linkDescarga = new Anchor();
        linkDescarga.setVisible(false);

        try {

//             Cargar el archivo .jasper desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/reporte/empaque/opEmpacadora/RptOpEmpacadora_v1.jasper");

            String subreportPath = new ClassPathResource("/reporte/empaque/opEmpacadora/").getFile().getAbsolutePath();

//            Date fecha = new Date("2025/03/12");
            // Parámetros
//        Map<String, Object> parameters = new HashMap<>();
            if (reportStream == null) {
                throw new RuntimeException("El archivo de reporte no se encontró en la ruta ." + subreportPath);
//                return pdfResource;

            }

            System.out.println("reportStream : " + reportStream);
            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fecha", fecha);
            parameters.put("SUBREPORT_DIR", subreportPath + "/");
//            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, dataSource.getConnection());
            System.out.println("jasperPrint : ");
//            parameters.put("Titulo", "Informe de Ejemplo");

//             Llenar el informe con datos (en este caso, sin fuente de datos)
//            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, new JREmptyDataSource());
//             Exportar el informe a PDF
            System.out.println(" dataSource.getConnection() : " + dataSource.getConnection());
            byte[] pdfContent = JasperRunManager.runReportToPdf(reportStream, parameters, dataSource.getConnection());
//            byte[] pdfContent = JasperRunManager.runReportToPdf(jasperPrint,null,null);

//             Crear un recurso de Vaadin para el PDF
            pdfResource = new StreamResource("informe.pdf", () -> new ByteArrayInputStream(pdfContent));

//              JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, dataSource.getConnection() );
//            // 3. Exportar a Word (DOCX)
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            JRDocxExporter exporter = new JRDocxExporter();
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
//            exporter.exportReport();
//
//            // 4. Descargar
//            StreamResource recurso = new StreamResource("nota.docx", () -> new ByteArrayInputStream(outputStream.toByteArray()));
//            recurso.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//
//            linkDescarga.setHref(recurso);
//            linkDescarga.getElement().setAttribute("download", true);
//            linkDescarga.setText("Descargar Word generado");
//            linkDescarga.setVisible(true);
//            add(linkDescarga);
//
//            Notification.show("Documento Word generado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfResource;
    }

    private StreamResource reciboIngreso(Integer codREcibo) {

        StreamResource pdfResource = null;

        try {

//             Cargar el archivo .jasper desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/reporte/recibo/RptReciboDeIngreso.jasper");

//            Date fecha = new Date("2025/03/12");
            // Parámetros
//        Map<String, Object> parameters = new HashMap<>();
            if (reportStream == null) {
                throw new RuntimeException("El archivo de reporte no se encontró en la ruta .");
//                return pdfResource;

            }

            System.out.println("reportStream : " + reportStream);
            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("cod_recibo", codREcibo);

            System.out.println("jasperPrint : ");

//             Exportar el informe a PDF
            System.out.println(" dataSource.getConnection() : " + dataSource.getConnection());
            byte[] pdfContent = JasperRunManager.runReportToPdf(reportStream, parameters, dataSource.getConnection());

            pdfResource = new StreamResource("informe.pdf", () -> new ByteArrayInputStream(pdfContent));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfResource;
    }

    private StreamResource exportarADocx(Date fecha) {

        StreamResource docxResource = null;
        Anchor linkDescarga = new Anchor();
        linkDescarga.setVisible(false);

        try {

//             Cargar el archivo .jasper desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/reporte/empaque/opEmpacadora/RptOpEmpacadora_v1.jasper");

            String subreportPath = new ClassPathResource("/reporte/empaque/opEmpacadora/").getFile().getAbsolutePath();

            if (reportStream == null) {
                throw new RuntimeException("El archivo de reporte no se encontró en la ruta ." + subreportPath);

            }

            System.out.println("reportStream : " + reportStream);
            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fecha", fecha);
            parameters.put("SUBREPORT_DIR", subreportPath + "/");
            System.out.println("jasperPrint : ");

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, dataSource.getConnection());
            // 3. Exportar a Word (DOCX)
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();

            // 4. Descargar
            docxResource = new StreamResource("Plantilla.docx", () -> new ByteArrayInputStream(outputStream.toByteArray()));
            docxResource.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

            Notification.show("Documento Word generado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return docxResource;
    }

}
