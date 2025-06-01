package com.maxsoft.application.views.medicamento;

import com.maxsoft.application.modelo.Medicamento;
import com.maxsoft.application.repo.MedicamentoRepo;
import com.maxsoft.application.service.TratamientoMedicoService;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Registrar Bebidas")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.TH_LIST_SOLID)
public class MedicamentoView extends Main implements HasComponents, HasStyle {

    @Autowired
    private TratamientoMedicoService tratamientoService;
    @Autowired
    private MedicamentoRepo mediRepo;

    private OrderedList imageContainer;

    public MedicamentoView(MedicamentoRepo mediRepoArg, TratamientoMedicoService tratamientoServiceArg) {
        constructUI();

        this.mediRepo = mediRepoArg;
        this.tratamientoService = tratamientoServiceArg;

        crearTarjeta();

    }

    private void constructUI() {

        addClassNames("medicamento-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H3 header = new H3("Registro Bebida de Medicamentos");
//        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);

        headerContainer.add(header);

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer);
        add(container, imageContainer);

    }

    private void crearTarjeta() {

        List<Medicamento> lista = this.mediRepo.findAll();

        Integer canBebida , existencia ;
        
        for (Medicamento med : lista) {

            canBebida = this.tratamientoService.getCantidadBebida(med.getCodigo());
            existencia = med.getCantidadComprada() - canBebida;
            med.setCantidadBebida(canBebida);
            med.setExistencia(existencia);
//            
//            if(existencia==0){
//                this.tratamientoService.guardar(obj);
//            }
//            
            imageContainer.add(new MedicamentoViewCard(med, this.tratamientoService, mediRepo));
        }

    }
}
