package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.service.*;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static com.gohardani.oltmanager.Utility.ONTID.FreeOntID.getBiggestFreeOntID;
import static com.gohardani.oltmanager.Utility.SSH.JavaTelnetsimulator.telnetConnection;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "ServicePort", layout = MainLayout.class)
public class ServicePortView extends VerticalLayout {

    public ServicePortView(SshService sshService, OntService ontService, OltService oltService, CommandHistoryService commandHistoryService, FrameService frameService, SlotService slotService, PortService portService, LineProfileService lineProfileService, ServiceProfileService serviceProfileService) {
        ComboBox<Olt> oltComboBox = new ComboBox<>("olt");
        ComboBox<Frame> frameComboBox = new ComboBox<>("frame");
        ComboBox<Slot> slotComboBox = new ComboBox<>("slot");
        ComboBox<Port> portComboBox = new ComboBox<>("port");
        ComboBox<Ont> ontComboBox = new ComboBox<>("ONT");
        ComboBox<LineProfile> lineProfileComboBox = new ComboBox<>("lineProfile");
        ComboBox<ServiceProfile> serviceProfileComboBox = new ComboBox<>("serviceProfile");
        oltComboBox.setItems(oltService.findAll());
        oltComboBox.setItemLabelGenerator(Olt::getName);
        oltComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
             frameComboBox.setItems(frameService.findByOltEquals(e.getValue()));
             frameComboBox.setItemLabelGenerator(Frame::getFrameNumberAsText);
            }
            });

        frameComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                slotComboBox.setItems(slotService.findByFrame(e.getValue()));
                slotComboBox.setItemLabelGenerator(Slot::getSlotidAsText);
            }
        });

        slotComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                portComboBox.setItems(portService.findBySlotEquals(e.getValue()));
                portComboBox.setItemLabelGenerator(Port::getFsp);
            }
        });

        portComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                ontComboBox.setItems(ontService.findByPortEquals(e.getValue()));
                ontComboBox.setItemLabelGenerator(Ont::getSerialNumber);
            }
        });

        lineProfileComboBox.setItems(lineProfileService.findAll());
        lineProfileComboBox.setItemLabelGenerator(LineProfile::getProfileName);

        serviceProfileComboBox.setItems(serviceProfileService.findAll());
        serviceProfileComboBox.setItemLabelGenerator(ServiceProfile::getProfileName);

        add(oltComboBox);
        add(frameComboBox);
        add(slotComboBox);
        add(portComboBox);
        add(ontComboBox);
        add(lineProfileComboBox);
        add(serviceProfileComboBox);
        Button button = new Button("Add ONT");
        Paragraph info = new Paragraph();
        info.getStyle().set("color" ,"#FF0000");
        add(info);
        add(button);
        info.setText("");
        button.addClickListener(clickEvent -> {
            if (oltComboBox.getValue() == null) {
                info.setText("Please Select an Olt");
                return;
            }
            else if (frameComboBox.getValue()==null) {
                info.setText("Please select a Frame");
                return;
            }
            else if (slotComboBox.getValue()==null) {
                info.setText("Please select a Slot");
                return;
            }
            else if (portComboBox.getValue()==null) {
                info.setText("Please select a Port");
                return;
            }
            else if (ontComboBox.getValue()==null) {
                info.setText("Please select an ONT");
                return;
            }
            else if (lineProfileComboBox.getValue()==null) {
                info.setText("Please select a Line Profile");
                return;
            }
            else if (serviceProfileComboBox.getValue()==null) {
                info.setText("Please select a Service Profile");
                return;
            }
            else {
                info.setText("");
            }
            Olt olt=oltComboBox.getValue();
            CommandHistory ch=new CommandHistory();
            //code to run ssh
            String com="ont add " + portComboBox.getValue().getPortNumberAsString() +" "+ getBiggestFreeOntID(ontService,portComboBox.getValue()) + "  sn-auth " + ontComboBox.getValue().getSerialNumber() + " omci ont-lineprofile-id " + lineProfileComboBox.getValue().getProfileID() + " ont-srvprofile-id " + serviceProfileComboBox.getValue().getProfileID() + "desc OltManager";
            ArrayList<String> c=new ArrayList<>();
            c.add("enable");
            c.add("config");
            c.add("interface gpon " + frameComboBox.getValue().getFrameNumberAsText() + " " + slotComboBox.getValue().getSlotid());
            c.add(com);
            c.add("quit");
            c.add("exit");
            try {
                String result= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
                info.getStyle().set("color" ,"#00FF00");
                info.setText("result: " + result + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,portComboBox.getValue()));
                ch.setCommandText(com);
                ch.setResult(result);
                ch.setOlt(olt);
                ch.setOltType(olt.getOltType());
                ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
                ch = commandHistoryService.save(ch);
            } catch (Exception e) {
                info.getStyle().set("color" ,"#FF0000");
                info.setText("result: " + e.getMessage() + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,portComboBox.getValue()));
                ch.setCommandText(com);
                ch.setResult(e.getMessage());
                ch.setOlt(olt);
                ch.setOltType(olt.getOltType());
                ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
                ch = commandHistoryService.save(ch);
                throw new RuntimeException(e);
            }
        });
    }
}
