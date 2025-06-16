/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.reporte;

import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Maximiliano
 */
public class RptReciboIngreso {

  
  
    public RptReciboIngreso() {
    }

    public  StreamResource reciboIngreso(Integer codREcibo, Connection dataSource) {

        StreamResource pdfResource = null;

        try {

//             Cargar el archivo .jasper desde el classpath
            InputStream reportStream = getClass().getResourceAsStream("/recibo/RptReciboDeIngreso.jasper");

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
}
