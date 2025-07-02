/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.reporte;

import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Maximiliano
 */
public class RptReciboIngreso {

    public RptReciboIngreso() {
    }

    public StreamResource reciboIngreso(Integer codREcibo, Connection dataSource) {

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
            System.out.println(" dataSource.getConnection() : " + dataSource);
            byte[] pdfContent = JasperRunManager.runReportToPdf(reportStream, parameters, dataSource);

            pdfResource = new StreamResource("informe.pdf", () -> new ByteArrayInputStream(pdfContent));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfResource;
    }

    public StreamResource listaReciboAlCorte(Date fecha, Connection dataSource) {

        StreamResource pdfResource = null;

        try {

//             Cargar el archivo .jasper desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/reporte/recibo/RptListaRecibo.jasper");

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
            parameters.put("fecha_param", fecha);

            System.out.println("jasperPrint : ");

//             Exportar el informe a PDF
            System.out.println(" dataSource.getConnection() : " + dataSource);
            byte[] pdfContent = JasperRunManager.runReportToPdf(reportStream, parameters, dataSource);

            pdfResource = new StreamResource("informe.pdf", () -> new ByteArrayInputStream(pdfContent));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfResource;
    }

    public StreamResource recibo(String StringParam, String descripcion, Connection dataSource) {

        StreamResource pdfResource = null;

        try {

            System.out.println("StringParam " + StringParam);
//             Cargar el archivo .jasper desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/reporte/recibo/RptListaRecibo.jasper");

//            Date fecha = new Date("2025/03/12");
            // Parámetros
//        Map<String, Object> parameters = new HashMap<>();
            if (reportStream == null) {
                throw new RuntimeException("El archivo de reporte no se encontró en la ruta .");
//                return pdfResource;

            }

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("str_param", StringParam);
            parameters.put("str_descripcion", descripcion);


            System.out.println("jasperPrint : ");

//             Exportar el informe a PDF
            System.out.println(" dataSource.getConnection() : " + dataSource);
            byte[] pdfContent = JasperRunManager.runReportToPdf(reportStream, parameters, dataSource);

            pdfResource = new StreamResource("informe.pdf", () -> new ByteArrayInputStream(pdfContent));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfResource;
    }
    
    
    public StreamResource prestamo(String StringParam, String descripcion, Connection dataSource) {

        StreamResource pdfResource = null;

        try {

            System.out.println("StringParam " + StringParam);
//             Cargar el archivo .jasper desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/reporte/prestamo/RptListadoPrestamo.jasper");

//            Date fecha = new Date("2025/03/12");
            // Parámetros
//        Map<String, Object> parameters = new HashMap<>();
            if (reportStream == null) {
                throw new RuntimeException("El archivo de reporte no se encontró en la ruta .");
//                return pdfResource;

            }

            // Parámetros para el informe
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("str_param", StringParam);
            parameters.put("str_descripcion", descripcion);
          

            System.out.println("jasperPrint : ");

//             Exportar el informe a PDF
            System.out.println(" dataSource.getConnection() : " + dataSource);
            byte[] pdfContent = JasperRunManager.runReportToPdf(reportStream, parameters, dataSource);

            pdfResource = new StreamResource("informe.pdf", () -> new ByteArrayInputStream(pdfContent));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pdfResource;
    }
}
