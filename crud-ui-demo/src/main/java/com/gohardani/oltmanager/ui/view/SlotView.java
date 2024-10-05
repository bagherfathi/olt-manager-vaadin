package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.repository.SlotRepository;
import com.gohardani.oltmanager.service.OltService;
import com.gohardani.oltmanager.service.SlotService;
import com.gohardani.oltmanager.service.FrameService;
import com.gohardani.oltmanager.service.UserService;
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
import static com.gohardani.oltmanager.Utility.sshOutputProcessor.SSHOutputProcessor.getSlotList;
import static com.gohardani.oltmanager.Utility.sshOutputProcessor.SSHOutputProcessor.testGetSlotList;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "slot", layout = MainLayout.class)
public class SlotView extends VerticalLayout {

//    private final UserService userService;

    public SlotView(OltService oltService, FrameService frameService, UserService userService, SlotService slotService) {
        // crud instance
        GridCrud<Slot> crud = new GridCrud<>(Slot.class);

        ComboBox<Olt> oltComboBox = new ComboBox<>();
        ComboBox<Frame> frameComboBox = new ComboBox<>();

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
            crud.refreshGrid();
        });
        frameComboBox.setPlaceholder("select Frame");
        frameComboBox.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(frameComboBox);
        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by Board Name");
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
                    }
                    Olt olt = oltComboBox.getValue();
                    Frame frame = frameComboBox.getValue();
                    if (olt.getIp().trim().contains("1.1.1.8")) {
                        ArrayList<Slot> slots=testGetSlotList();
                        for (Slot slot : slots) {
                            slot.setFrame(frame);
                            slotService.save(slot);
                        }
                    }
                    ArrayList<String> c=new ArrayList<>();
                    c.add("enable");
                    c.add("display board "+ frame.getFrameNumber());
                    for(int i=0;i<10;i++)
                        c.add("\t");
                    c.add("\n\n\n");
                    c.add("quit");
                    c.add("y");
//                    c.add("exit");
            try {
//                slotService.deleteByFrame(frame);
                String s= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
                ArrayList<Slot> slots=getSlotList(s);
                slotService.deleteByFrame(frame);
                for (Slot slot : slots) {
                    slot.setFrame(frame);
                    slotService.save(slot);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            crud.refreshGrid();
                });



        crud.getCrudLayout().addToolbarComponent(button);


        // grid configuration
        crud.getGrid().setColumns("frame","slotid", "boardName","status","subType0","subType1","onOff","user");
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
                () -> slotService.findByNameContainingIgnoreCaseOrEqualFrame(filter.getValue(),(Frame) frameComboBox.getValue()),
                slot -> slotService.save(slot),
                slot -> slotService.save(slot),
                slot -> slotService.delete(slot)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
//        this.userService = userService;
    }

}
