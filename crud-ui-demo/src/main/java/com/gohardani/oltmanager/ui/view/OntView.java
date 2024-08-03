package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.Ont;
import com.gohardani.oltmanager.entity.Port;
import com.gohardani.oltmanager.entity.User;
import com.gohardani.oltmanager.service.OntService;
import com.gohardani.oltmanager.service.PortService;
import com.gohardani.oltmanager.service.SlotService;
import com.gohardani.oltmanager.service.UserService;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "ont", layout = MainLayout.class)
public class OntView extends VerticalLayout {
    private final OntService ontService;
    private final PortService portService;

//    private final UserService userService;

    public OntView(UserService userService, SlotService slotService, OntService ontService, PortService portService) {
        // crud instance
        GridCrud<Ont> crud = new GridCrud<>(Ont.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by Serial Number");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("id", "port", "frameNo", "slotNo", "portNo", "ontID", "serialNumber","controlFlag","runState","configState","matchState", "protectSide", "user");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties( "port", "frameNo","slotNo","portNo", "ontID","serialNumber","controlFlag","runState","configState","matchState", "protectSide","user");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "port", "frameNo","slotNo","portNo", "ontID","serialNumber","controlFlag","runState","configState","matchState", "protectSide","user");

        crud.getCrudFormFactory().setFieldProvider("port",
                new ComboBoxProvider<>(slotService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("port",
                new ComboBoxProvider<>("PORT", portService.findAll(), new TextRenderer<>(Port::getFsp), Port::getFsp));

//
        crud.getCrudFormFactory().setFieldProvider("user",
                new ComboBoxProvider<>(userService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("user",
                new ComboBoxProvider<>("USER", userService.findAll(), new TextRenderer<>(User::getName), User::getName));
        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> ontService.findBySerialNumberContainingIgnoreCase(filter.getValue()),
                ont -> ontService.save(ont),
                ont -> ontService.save(ont),
                ont -> ontService.delete(ont)
        );
        filter.addValueChangeListener(e -> crud.refreshGrid());
//        this.userService = userService;
        this.ontService = ontService;
        this.portService = portService;
    }

}
