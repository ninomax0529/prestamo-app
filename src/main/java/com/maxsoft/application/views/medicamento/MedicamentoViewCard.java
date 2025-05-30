package com.maxsoft.application.views.medicamento;

import com.maxsoft.application.modelo.Medicamento;
import com.maxsoft.application.modelo.TratamientoMedico;
import com.maxsoft.application.repo.MedicamentoRepo;
import com.maxsoft.application.service.TratamientoMedicoService;
import com.maxsoft.application.util.ClaseUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import java.io.ByteArrayInputStream;
import java.time.LocalTime;
import java.util.Date;

public class MedicamentoViewCard extends ListItem {

    private final TratamientoMedicoService tratamientoService = null;
    private final MedicamentoRepo medicamentoRepo = null;

    public MedicamentoViewCard(String text, String url) {

        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);

        div.setHeight("160px");

        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(url);
        image.setAlt(text);

        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText("Title");

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText("Card subtitle");

        Paragraph description = new Paragraph(
                "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.");
        description.addClassName(Margin.Vertical.MEDIUM);

        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");
        badge.setText("Label");

        add(div, header, subtitle, description, badge);

    }

    public MedicamentoViewCard(Medicamento medic, TratamientoMedicoService tratamientoService, MedicamentoRepo mediRepoArg) {

        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);

        div.setHeight("160px");

        Image imagen;

        HorizontalLayout hlBotones = new HorizontalLayout();
//        Button btnRegistrar = new Button("Registrar");
       

        Button btnVerRegistro = new Button("Ver Registro", e -> {
            
          UI.getCurrent().navigate("TratamientoMedico/"+medic.getCodigo());
     
        } );

        Button btnRegistrar = new Button("Registrar", event -> {

            ConfirmDialo dialog = new ConfirmDialo(
                    "¿Estás seguro de que deseas registrar esta pastilla?",
                    () -> {

                        guardar(medic, tratamientoService);

                        Notification.show("Pastilla Registrada exitosamente", 3000, Notification.Position.MIDDLE);

                        medic.setFechaCreacion(new Date());
                        medic.setHora(ClaseUtil.getFormatoHora(new Date()));
                        mediRepoArg.save(medic);

                        // Acción al confirmar
//                    System.out.println("Elemento eliminado");
                    },
                    () -> {
                        // Acción al cancelar (opcional)
//                        Notification.show("Accion Cancelada", 3000, Notification.Position.MIDDLE);
                    }
            );
            dialog.open();
        });

        hlBotones.add(btnRegistrar, btnVerRegistro);

        StreamResource resource = new StreamResource(
                "imagen", // nombre lógico
                () -> new ByteArrayInputStream(medic.getImagen()) // input stream
        );
        imagen = new Image(resource, medic.getNombre());
//        imagen.setHeight("80px");
//        imagen.setWidth("80px");

        imagen.setWidth("100%");
//        image.set
        imagen.setAlt("Medicamento");

        div.add(imagen);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText("Fecha Ultima bebida ");

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);

        H3 fecha = new H3(medic.getHora());
        fecha.setText(medic.getFechaCreacion().toString());

        H3 h3 = new H3("Hora Ultima bebida");
        H3 hora = new H3(medic.getHora());
        h3.addClassName(Margin.Vertical.MEDIUM);

//        Span badge = new Span();
//        badge.getElement().setAttribute("theme", "badge");
//        badge.setText("Label");
        add(hlBotones, div, header, fecha, h3, hora);

    }

    private void guardar(Medicamento medic, TratamientoMedicoService tratService) {

        TratamientoMedico t = new TratamientoMedico();
        t.setNombreMedicamento(medic.getNombre());
//            t.setMedicamento(seleccionado); // ID manual si necesario
        t.setMedicamento(medic); // Objeto relacionado

        LocalTime lt = ClaseUtil.getHora();

        t.setFechaBebida(new Date());
        t.setHoraBebida(new Date());
        t.setHora(lt.getHour() + ":" + lt.getMinute());

        String dia = ClaseUtil.getNombreDia(new Date());
        String mes = ClaseUtil.getNombreMes(new Date());

        t.setDia(dia);
        t.setMes(mes);
        int anio = ClaseUtil.getAno(new Date());
        t.setAnio(Integer.toString(anio));

        tratService.guardar(t);

//            actualizarGrid();
    }

    // Método auxiliar para crear un RouterLink con icono y texto
    private RouterLink createLink(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        Icon linkIcon = icon.create();
        linkIcon.getStyle().set("marginRight", "10px");
        RouterLink link = new RouterLink();
        link.add(linkIcon, new Span(text));
        link.setRoute(navigationTarget);
//        link.setHighlightCondition(HighlightCondition.sameLocation());
        return link;
    }
}
