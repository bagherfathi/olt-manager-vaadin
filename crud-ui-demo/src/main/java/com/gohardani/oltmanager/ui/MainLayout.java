package com.gohardani.oltmanager.ui;

import com.gohardani.oltmanager.DemoUtils;
import com.gohardani.oltmanager.service.SecurityService;
import com.gohardani.oltmanager.ui.view.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.gohardani.oltmanager.ui.view.*;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;
import java.util.Map;

public class MainLayout extends AppLayout implements BeforeEnterObserver, AfterNavigationObserver {

    private SecurityService securityService;
    private Tabs tabs = new Tabs();
    private Map<Tab, Class<? extends HasComponents>> tabToView = new HashMap<>();
    private Map<Class<? extends HasComponents>, Tab> viewToTab = new HashMap<>();

    public MainLayout(@Autowired SecurityService securityService) {
        this.securityService = securityService;
        AppLayout appLayout = new AppLayout();
//        Image img = new Image();
        // Find the application directory
//        String basepath = VaadinService.getCurrent()
//                .getBaseDirectory().getAbsolutePath();

// Image as a file resource
//        FileResource resource = new FileResource();

//        img.setSrc(new ClassResource("tct.png"));
        Image img = new Image("https://i.imgur.com/GPpnszs.png", "TCT OLT Manager");
        img.setHeight("44px");
   //     addToNavbar(img);
        //logout
        H1 logo = new H1("OLT Manager");
        logo.addClassName("logo");
        HorizontalLayout header;
        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Logout", click ->
                    securityService.logout());
            header = new HorizontalLayout(logo, logout);
        } else {
            header = new HorizontalLayout(logo);
        }

        // Other page components omitted.

        addToNavbar(header);
        //logout

        tabs.addSelectedChangeListener(event -> tabsSelectionChanged(event));
        addToNavbar(tabs);

        addTab(HomeView.class);
//        addTab(SimpleCrudView.class);
//        addTab(CrudWithSplitLayoutView.class);
//        addTab(CrudWithFilterView.class);
//        addTab(CrudWithLazyLoadingView.class);
//        addTab(SimpleTreeCrudView.class);
        addTab(OltTypeView.class);
        addTab(OltView.class);
        addTab(FrameView.class);
        addTab(SlotView.class);
        addTab(SSHCommandsView.class);
        addTab(CommandsHistoryView.class);
        addTab(RunCommandsView.class);
    }

    private void tabsSelectionChanged(Tabs.SelectedChangeEvent event) {
        if (event.isFromClient()) {
            UI.getCurrent().navigate((Class<? extends Component>) tabToView.get(event.getSelectedTab()));
        }
    }

    private void addTab(Class<? extends HasComponents> clazz) {
        Tab tab = new Tab(DemoUtils.getViewName(clazz));
        tabs.add(tab);
        tabToView.put(tab, clazz);
        viewToTab.put(clazz, tab);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        selectTabByCurrentView(event);
    }

    public void selectTabByCurrentView(BeforeEnterEvent event) {
        Class<?> viewClass = event.getNavigationTarget();
        tabs.setSelectedTab(viewToTab.get(viewClass));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        updatePageTitle();
        addSourceCodeAnchorToCurrentView();
    }

    public void updatePageTitle() {
        Class<? extends HasComponents> viewClass = tabToView.get(tabs.getSelectedTab());
        UI.getCurrent().getPage().setTitle(DemoUtils.getViewName(viewClass) + " - " + "OLT Manager Application");
    }

    public void addSourceCodeAnchorToCurrentView() {
        Class<? extends HasComponents> viewClass = tabToView.get(tabs.getSelectedTab());
        if (!HomeView.class.equals(viewClass)) {
            HorizontalLayout footer = new HorizontalLayout(new Anchor("https://www.oltmanager.com", "Powered By IranSign"));
            footer.setMargin(true);
            ((HasComponents) getContent()).add(footer);
        }
    }

}
