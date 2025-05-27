/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.views.medicamento;

/**
 *
 * @author maximilianoalmonte
 */
import com.maxsoft.application.modelo.Medicamento;
import com.maxsoft.application.modelo.TratamientoMedico;
import com.maxsoft.application.repo.MedicamentoRepo;
import com.maxsoft.application.service.TratamientoMedicoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
//
//import java.sql.Date; 72503964 orden 
//import java.sql.Time;
import java.util.List;

@Route("TratamientoMedico")
@PageTitle("TratamientoMedico")
@PermitAll
public class TratamientoMedicoView extends VerticalLayout implements HasUrlParameter<String> {

    private final TratamientoMedicoService tratamientoService;
    private final MedicamentoRepo medicamentoRepo;

    private final Grid<TratamientoMedico> gridDetalle = new Grid<>(TratamientoMedico.class, false);
    List<TratamientoMedico> tratamientos;
    TabSheet tabs = new TabSheet();

    private Medicamento medicamento;
    TratamientoMedico tratamientoMedico;

    Integer codMedi;

    @Autowired
    public TratamientoMedicoView(TratamientoMedicoService tratamientoService, MedicamentoRepo medicamentoRepo) {
        this.tratamientoService = tratamientoService;
        this.medicamentoRepo = medicamentoRepo;
        setSizeFull();

//          tratamientos = this.tratamientoService.getHistorial(codMedi);
//           gridDetalle.setItems(tratamientos);
        configurarGridDetalle();

        tabs.setSizeFull();

        tabs.add("HISTORIAL", gridDetalle);

        add(tabs);

    }

    private void configurarGridDetalle() {

        gridDetalle.setHeight("620px");
        gridDetalle.setWidthFull();
//
//        gridDetalle.addColumn(TratamientoMedico::getNombreMedicamento)
//                .setHeader("Medicamento")
//                .setKey("medicamento")
//                .setFooter("TOTAL :");
        gridDetalle.addColumn(TratamientoMedico::getFechaBebida).setHeader("Fecha Bebida")
                .setFooter("TOTAL :");

        gridDetalle.addColumn(TratamientoMedico::getHora)
                .setHeader("Hora Bebida")
                .setKey("hora");

        gridDetalle.addColumn(TratamientoMedico::getDia)
                .setHeader("Dia")
                .setKey("dia");
        gridDetalle.addComponentColumn(obj -> {

            Button editar = new Button("Eliminar", event -> {

                ConfirmDialo dialog = new ConfirmDialo(
                        "¿Estás seguro de que deseas eliminar esta pastilla?",
                        () -> {

                            tratamientoService.eliminar(obj);
                            gridDetalle.getDataProvider().refreshAll();

                            Notification.show("Registro eliminado exitosamente", 2000, Notification.Position.MIDDLE);

                        },
                        () -> {
                            // Acción al cancelar (opcional)
//                        Notification.show("Accion Cancelada", 3000, Notification.Position.MIDDLE);
                        }
                );
                dialog.open();

            });

            return new HorizontalLayout(editar);
        }).setHeader("Acciones");

//        gridDetalle.getColumns().forEach(col -> {
//            col.setAutoWidth(true);
//            col.setSortable(true);
//        });
    }

    /**
     * @return the medicamento
     */
    public Medicamento getMedicamento() {
        return medicamento;
    }

    /**
     * @param medicamento the medicamento to set
     */
    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    @Override
    public void setParameter(BeforeEvent be, String t) {

        System.out.println("Parametro " + t);

        codMedi = Integer.valueOf(t);

        tratamientos = this.tratamientoService.getHistorial(codMedi);
        gridDetalle.setItems(tratamientos);
    }

}
