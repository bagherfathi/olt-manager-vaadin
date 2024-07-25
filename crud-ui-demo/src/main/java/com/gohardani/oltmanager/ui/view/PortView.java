package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.Port;
import com.gohardani.oltmanager.entity.User;
import com.gohardani.oltmanager.service.FrameService;
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
@Route(value = "port", layout = MainLayout.class)
public class PortView extends VerticalLayout {

//    private final UserService userService;

    public PortView(FrameService frameService, UserService userService, SlotService slotService) {
        // crud instance
        GridCrud<Port> crud = new GridCrud<>(Port.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by Serial Number");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("id", "F","S","P", "slotid", "boardName","status","subType0","subType1","onOff","user");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties( "frame","slotid", "boardName","status","subType0","subType1","onOff","user");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "frame","slotid", "boardName","status","subType0","subType1","onOff","user");

        crud.getCrudFormFactory().setFieldProvider("frame",
                new ComboBoxProvider<>(frameService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("frame",
                new ComboBoxProvider<>("FRAME", frameService.findAll(), new TextRenderer<>(Frame::getFrameNumberAsText), Frame::getFrameNumberAsText));


        crud.getCrudFormFactory().setFieldProvider("user",
                new ComboBoxProvider<>(userService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("user",
                new ComboBoxProvider<>("USER", userService.findAll(), new TextRenderer<>(User::getName), User::getName));
        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> slotService.findByNameContainingIgnoreCase(filter.getValue()),
                slot -> slotService.save(slot),
                slot -> slotService.save(slot),
                slot -> slotService.delete(slot)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
//        this.userService = userService;
    }

}
