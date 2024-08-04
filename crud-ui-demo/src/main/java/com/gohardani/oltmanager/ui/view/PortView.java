package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.service.*;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
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

    public PortView(UserService userService, SlotService slotService, PortService portService, OltService oltService, FrameService frameService) {
        // crud instance
        GridCrud<Port> crud = new GridCrud<>(Port.class);
        ComboBox<Olt> oltComboBox = new ComboBox<>();
        ComboBox<Frame> frameComboBox = new ComboBox<>();
        ComboBox<Slot> slotComboBox = new ComboBox<>();

        //olt filter
        oltComboBox.setItems(oltService.findAll());
        oltComboBox.setItemLabelGenerator(Olt::getName);
        oltComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                Olt olt = e.getValue();
                frameComboBox.setItems(frameService.findByOltEquals(olt));
                frameComboBox.setItemLabelGenerator(Frame::getFrameNumberAsText);
            }
            crud.refreshGrid();
        });
        oltComboBox.setPlaceholder("select OLT");
        oltComboBox.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(oltComboBox);
        //frame filter
        frameComboBox.setItems(frameService.findAll());
        frameComboBox.setItemLabelGenerator(Frame::getFrameNumberAsText);
        frameComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                Frame frame = e.getValue();
                slotComboBox.setItems(slotService.findByFrame(frame));
                slotComboBox.setItemLabelGenerator(Slot::getSlotidAsText);
            }
            crud.refreshGrid();
        });
        frameComboBox.setPlaceholder("select Frame");
        frameComboBox.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(frameComboBox);
        //slot filter
        slotComboBox.setItems(slotService.findAll());
        slotComboBox.setItemLabelGenerator(Slot::getSlotidAsText);
        slotComboBox.addValueChangeListener(e -> {
            crud.refreshGrid();
        });
        slotComboBox.setPlaceholder("select Slot");
        slotComboBox.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(slotComboBox);
        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by FSP");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("id", "slot", "fsp", "opticalModuleStatus", "portState", "laserState", "availableBandwidth","temperature","TXBiasCurrent","supplyVoltage","TXPower", "illegalRogueONT","maxDistance","waveLength","fiberType","length", "user");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("slot", "fsp", "opticalModuleStatus", "portState", "laserState", "availableBandwidth","temperature","TXBiasCurrent","supplyVoltage","TXPower", "illegalRogueONT","maxDistance","waveLength","fiberType","length", "user");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"slot", "fsp", "opticalModuleStatus", "portState", "laserState", "availableBandwidth","temperature","TXBiasCurrent","supplyVoltage","TXPower", "illegalRogueONT","maxDistance","waveLength","fiberType","length", "user");

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
                () -> portService.findByFspContainingIgnoreCaseAndSlotEquals(filter.getValue(),(Slot) slotComboBox.getValue()),
                port -> portService.save(port),
                port -> portService.save(port),
                port -> portService.delete(port)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
//        this.userService = userService;
        this.portService = portService;
    }

}
