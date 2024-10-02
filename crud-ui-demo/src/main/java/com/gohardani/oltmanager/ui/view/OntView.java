package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.service.*;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import java.util.ArrayList;

import static com.gohardani.oltmanager.Utility.SSH.JavaTelnetsimulator.telnetConnection;
import static com.gohardani.oltmanager.Utility.dialog.DialogModal.confirmDialog;
import static com.gohardani.oltmanager.Utility.sshOutputProcessor.SSHOutputProcessor.*;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "ont", layout = MainLayout.class)
public class OntView extends VerticalLayout {
    private final OntService ontService;
    private final PortService portService;

//    private final UserService userService;

    public OntView(UserService userService, SlotService slotService, OntService ontService, PortService portService, OltService oltService,FrameService frameService) {
        // crud instance
        GridCrud<Ont> crud = new GridCrud<>(Ont.class);

        ComboBox<Olt> oltComboBox = new ComboBox<>();
        ComboBox<Frame> frameComboBox = new ComboBox<>();
        ComboBox<Slot> slotComboBox = new ComboBox<>();
        ComboBox<Port> portComboBox = new ComboBox<>();
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
        frameComboBox.setItems(frameService.findByOltEquals((Olt) oltComboBox.getValue()));
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
        slotComboBox.setItems(slotService.findByFrame((Frame) frameComboBox.getValue()));
        slotComboBox.setItemLabelGenerator(Slot::getSlotidAsText);
        slotComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                Slot slot = (Slot) e.getValue();
//                confirmDialog(String.valueOf(portService.findBySlotEquals(slot).size()));
                portComboBox.setItems(portService.findBySlotEquals(slot));
                portComboBox.setItemLabelGenerator(Port::getFsp);
            }
            crud.refreshGrid();
        });
        slotComboBox.setPlaceholder("select Slot");
        slotComboBox.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(slotComboBox);


        //slot filter
        portComboBox.setItems(portService.findBySlotEquals((Slot) slotComboBox.getValue()));
        portComboBox.setItemLabelGenerator(Port::getFsp);
        portComboBox.addValueChangeListener(e -> {
            crud.refreshGrid();
        });
        portComboBox.setPlaceholder("select Port");
        portComboBox.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(portComboBox);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by Serial Number");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        //import button
        Button button = new Button("Import");
        button.addClickListener(clickEvent -> {
            if (oltComboBox.getValue() == null) {
                confirmDialog("Please Select an Olt");
                return;
            } else if(frameComboBox.getValue() == null){
                confirmDialog("Please Select a Frame");
                return;
            } else if(slotComboBox.getValue() == null){
                confirmDialog("Please Select a Slot");
                return;
            } else if(portComboBox.getValue() == null){
                confirmDialog("Please Select a Port");
            }
            Olt olt = oltComboBox.getValue();
            Frame frame = frameComboBox.getValue();
            Slot slot = slotComboBox.getValue();
            Port port = portComboBox.getValue();
            if (olt.getIp().trim().contains("1.1.1.8")) {
                ArrayList<Ont> onts=testGetOntList();
                for (Ont ont : onts) {
                    ont.setPort(port);
                    ontService.save(ont);
                }
            }
            ArrayList<String> c=new ArrayList<>();
            c.add("enable");
            c.add("config");
            c.add("interface gpon " +frame.getFrameNumberAsText() + "/" + slot.getSlotidAsText());
            c.add("display ont info " + port.getPortNumberAsString()  + " all");
            for(int i=0;i<30;i++)
                c.add("\t");
            c.add("\n\n\n");
            c.add("quit");
            c.add("quit");
            c.add("quit");
            c.add("y");
            try {
                String s= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
                ArrayList<Ont> onts=getOntList(s);
                for (Ont ont : onts) {
                    ont.setPort(port);
                    ontService.save(ont);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            crud.refreshGrid();
        });


        crud.getCrudLayout().addToolbarComponent(button);


        // grid configuration
        crud.getGrid().setColumns("port", "frameNo", "slotNo", "portNo", "ontID", "serialNumber","controlFlag","runState","configState","matchState", "protectSide", "user");
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
                () -> ontService.findBySerialNumberContainingIgnoreCaseAndPortEquals(filter.getValue(),(Port) portComboBox.getValue()),
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
