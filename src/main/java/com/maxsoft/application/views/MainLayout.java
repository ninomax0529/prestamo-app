package com.maxsoft.application.views;

import com.itextpdf.io.font.otf.LanguageTags;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.util.List;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed

public class MainLayout extends AppLayout {

    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        HorizontalLayout header = new HorizontalLayout(toggle, viewTitle);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.getStyle().set("padding", "var(--lumo-space-m)");
        header.add(createLogoutButton());

        addToNavbar(true, header);
    }

//
//    private void addHeaderContent() {
//        
//        DrawerToggle toggle = new DrawerToggle();
//        toggle.setAriaLabel("Menu toggle");
//
//        viewTitle = new H1();
//        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
//
//        addToNavbar(true, toggle, viewTitle);
//    }
    private void addDrawerContent() {
        Span appName = new Span("AppPrestamo");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
        menuEntries.forEach(entry -> {
            if (entry.icon() != null) {
                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
            } else {
                nav.addItem(new SideNavItem(entry.title(), entry.path()));
            }
        });

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }

    private Button createLogoutButton() {
        Button logout = new Button("Cerrar sesión", VaadinIcon.SIGN_OUT.create());

        logout.addClickListener(e -> {

            UI.getCurrent().getPage().setLocation("/logout");

            var request = VaadinServletRequest.getCurrent().getHttpServletRequest();

            // Logout seguro de Spring Security
            new SecurityContextLogoutHandler().logout(request, null, null);

            // Cerrar sesión de Vaadin solo si existe
            VaadinSession session = VaadinSession.getCurrent();
            if (session != null && session.getSession() != null) {
                session.getSession().invalidate();
                session.close();
            }

            // Redirigir a login
            UI.getCurrent().getPage().setLocation("/login");
        });

        return logout;
    }
}
