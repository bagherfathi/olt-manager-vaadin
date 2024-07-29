package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.Port;
import com.gohardani.oltmanager.entity.Slot;
import com.gohardani.oltmanager.entity.User;
import com.gohardani.oltmanager.service.FrameService;
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
@Route(value = "port", layout = MainLayout.class)
public class PortView extends VerticalLayout {
    private final PortService portService;

//    private final UserService userService;

    public PortView(UserService userService, SlotService slotService, PortService portService) {
        // crud instance
        GridCrud<Port> crud = new GridCrud<>(Port.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by Serial Number");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("id", "slot", "frameNo", "slotNo", "portNo", "ontID", "serialNumber","controlFlag","runState","configState","matchState", "protectSide", "user");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties( "slot", "frameNo","slotNo","portNo", "ontID","serialNumber","controlFlag","runState","configState","matchState", "protectSide","user");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, "slotid", "frameNo","slotNo","portNo", "ontID","serialNumber","controlFlag","runState","configState","matchState", "protectSide","user");

        crud.getCrudFormFactory().setFieldProvider("slot",
                new ComboBoxProvider<>(slotService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("slot",
                new ComboBoxProvider<>("SLOT", slotService.findAll(), new TextRenderer<>(Slot::getSlotidAsText), Slot::getSlotidAsText));

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
                () -> portService.findBySerialNumberContainingIgnoreCase(filter.getValue()),
                port -> portService.save(port),
                port -> portService.save(port),
                port -> portService.delete(port)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
//        this.userService = userService;
        this.portService = portService;
    }

}
