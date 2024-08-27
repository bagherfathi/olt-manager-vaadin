package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.Utility.dialog.DialogModal;
import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.repository.FrameRepository;
import com.gohardani.oltmanager.service.*;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static com.gohardani.oltmanager.Utility.ONTID.FreeOntID.getBiggestFreeOntID;
import static com.gohardani.oltmanager.Utility.SSH.JavaTelnetsimulator.telnetConnection;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "ONTAdd", layout = MainLayout.class)
public class ONTAddView extends VerticalLayout {

    private int freeBiggestOntID=-1;

    private OltService oltService;
    private FrameService frameService;
    private SlotService slotService;
    private PortService portService;
    private OntService ontService;
    private SshService sshService;
    private CommandHistoryService commandHistoryService;
    private LineProfileService lineProfileService;
    private ServiceProfileService serviceProfileService;

    private ComboBox<Olt> oltComboBox;
    private ComboBox<Frame> frameComboBox;
    private ComboBox<Slot> slotComboBox;
    private ComboBox<Port> portComboBox;
    private ComboBox<Ont> ontComboBox;
    private ComboBox<LineProfile> lineProfileComboBox;
    private ComboBox<ServiceProfile> serviceProfileComboBox;

    private Button addOntButton;
    private Button servicePortButton;
    private Button tr069Button;
    private Button ipConfigButton;

    private Paragraph info;

    public ONTAddView(SshService sshServiceIN, OntService ontServiceIN, OltService oltServiceIN, CommandHistoryService commandHistoryServiceIN, FrameService frameServiceIN, SlotService slotServiceIN, PortService portServiceIN, LineProfileService lineProfileServiceIN, ServiceProfileService serviceProfileServiceIN) {
        oltService = oltServiceIN;
        frameService = frameServiceIN;
        slotService = slotServiceIN;
        portService = portServiceIN;
        ontService = ontServiceIN;
        sshService = sshServiceIN;
        commandHistoryService = commandHistoryServiceIN;
        lineProfileService = lineProfileServiceIN;
        serviceProfileService = serviceProfileServiceIN;

        oltComboBox = new ComboBox<Olt>("olt",this::OnOltCBClick);
        frameComboBox = new ComboBox<Frame>("frame",this::OnFrameCBClick);
        slotComboBox = new ComboBox<Slot>("slot",this::OnSlotCBClick);
        portComboBox = new ComboBox<Port>("port",this::OnPortCBClick);
        ontComboBox = new ComboBox<Ont>("ONT",this::OnOntCBClick);
        lineProfileComboBox = new ComboBox<LineProfile>("lineProfile",this::OnLineProfileCBClick);
        serviceProfileComboBox = new ComboBox<ServiceProfile>("serviceProfile",this::OnServiceProfileCBClick);
        oltComboBox.setItems(oltService.findAll());
        oltComboBox.setItemLabelGenerator(Olt::getName);

        lineProfileComboBox.setItems(lineProfileService.findAll());
        lineProfileComboBox.setItemLabelGenerator(LineProfile::getProfileName);

        serviceProfileComboBox.setItems(serviceProfileService.findAll());
        serviceProfileComboBox.setItemLabelGenerator(ServiceProfile::getProfileName);
        HorizontalLayout hl0 = new HorizontalLayout(oltComboBox, frameComboBox, slotComboBox, portComboBox, ontComboBox);
        HorizontalLayout hl1=new HorizontalLayout(lineProfileComboBox, serviceProfileComboBox);
        add(hl0,hl1);
        addOntButton = new Button("Add ONT",this::OnAddOntButton);
        servicePortButton = new Button("Service Port",this::OnServicePortButton);
        tr069Button = new Button("Tr069",this::OnTr069Button);
        ipConfigButton = new Button("IP Config",this::OnIPConfigButton);

        info = new Paragraph();
        info.getStyle().set("color" ,"#FF0000");
        add(info);
        add(addOntButton);
        info.setText("");
        HorizontalLayout hl2=new HorizontalLayout(addOntButton,servicePortButton,tr069Button,ipConfigButton);
        add(hl2);
    }

    private void OnServicePortButton(ClickEvent<Button> buttonClickEvent) {
        if (freeBiggestOntID==-1) {
            info.setText("First, You must run ont add command successfully");
            return;
        }
        else {
            info.setText("");
        }
        Olt olt=oltComboBox.getValue();
        CommandHistory ch=new CommandHistory();
        //code to run ssh
        freeBiggestOntID=getBiggestFreeOntID(ontService,portComboBox.getValue());
        String com="service-port  vlan 930 gpon " + portComboBox.getValue() + " ont " + freeBiggestOntID + " gemport 930 multi-service user-vlan 930 tag-transform translate";
        ArrayList<String> c=new ArrayList<>();
        c.add("enable");
        c.add("config");
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
    }

    private void OnTr069Button(ClickEvent<Button> buttonClickEvent) {
        if (freeBiggestOntID==-1) {
            info.setText("First, You must run ont add command successfully");
            return;
        }
        else {
            info.setText("");
        }
        Olt olt=oltComboBox.getValue();
        CommandHistory ch=new CommandHistory();
        //code to run ssh
        freeBiggestOntID=getBiggestFreeOntID(ontService,portComboBox.getValue());
        String com="ont tr069-server-config " + portComboBox.getValue().getPortNumberAsString() + "  " + freeBiggestOntID + " profile-id 20";
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
    }

    private void OnIPConfigButton(ClickEvent<Button> buttonClickEvent) {
        if (freeBiggestOntID==-1) {
            info.setText("First, You must run ont add command successfully");
            return;
        }
        else {
            info.setText("");
        }
        Olt olt=oltComboBox.getValue();
        CommandHistory ch=new CommandHistory();
        //code to run ssh
        freeBiggestOntID=getBiggestFreeOntID(ontService,portComboBox.getValue());
        String com="ont ipconfig " + portComboBox.getValue().getPortNumberAsString() + "  " +  freeBiggestOntID + " ip-index 0 dhcp vlan 930 priority 0";
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

    }

    private void cancel(ClickEvent<Button> buttonClickEvent) {
        DialogModal.confirmDialog("you clicked on cancel");
    }

    private void OnAddOntButton(ClickEvent<Button> buttonClickEvent) {
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
        freeBiggestOntID=getBiggestFreeOntID(ontService,portComboBox.getValue());
        String com="ont add " + portComboBox.getValue().getPortNumberAsString() +" "+ freeBiggestOntID + "  sn-auth " + ontComboBox.getValue().getSerialNumber() + " omci ont-lineprofile-id " + lineProfileComboBox.getValue().getProfileID() + " ont-srvprofile-id " + serviceProfileComboBox.getValue().getProfileID() + "desc OltManager";
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
            DialogModal.confirmDialog("if every thing done successfully, dont change your selections for next operations such as service-port & tr069 ...");
        } catch (Exception e) {
            info.getStyle().set("color" ,"#FF0000");
            info.setText("result: " + e.getMessage() + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,portComboBox.getValue()));
            ch.setCommandText(com);
            ch.setResult(e.getMessage());
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch = commandHistoryService.save(ch);
            DialogModal.confirmDialog("if every thing done successfully, dont change your selections for next operations such as service-port & tr069 ...");
            throw new RuntimeException(e);
        }
    }

    private void OnPortCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<Port>, Port> comboBoxPortComponentValueChangeEvent) {
        if (comboBoxPortComponentValueChangeEvent.getValue() != null) {
            ontComboBox.setItems(ontService.findByPortEquals(comboBoxPortComponentValueChangeEvent.getValue()));
            ontComboBox.setItemLabelGenerator(Ont::getSerialNumber);
        }
    }

    private void OnServiceProfileCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<ServiceProfile>, ServiceProfile> comboBoxServiceProfileComponentValueChangeEvent) {
        
    }

    private void OnSlotCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<Slot>, Slot> comboBoxSlotComponentValueChangeEvent) {
        if (comboBoxSlotComponentValueChangeEvent.getValue() != null) {
            portComboBox.setItems(portService.findBySlotEquals(comboBoxSlotComponentValueChangeEvent.getValue()));
            portComboBox.setItemLabelGenerator(Port::getFsp);
        }
    }

    private void OnFrameCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<Frame>, Frame> comboBoxFrameComponentValueChangeEvent) {
        if (comboBoxFrameComponentValueChangeEvent.getValue() != null) {
            slotComboBox.setItems(slotService.findByFrame(comboBoxFrameComponentValueChangeEvent.getValue()));
            slotComboBox.setItemLabelGenerator(Slot::getSlotidAsText);
        }
    }

    private void OnOltCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<Olt>, Olt> comboBoxOltComponentValueChangeEvent) {
        if (comboBoxOltComponentValueChangeEvent.getValue() != null) {
            frameComboBox.setItems(frameService.findByOltEquals(comboBoxOltComponentValueChangeEvent.getValue()));
            frameComboBox.setItemLabelGenerator(Frame::getFrameNumberAsText);
        }
    }

    private void ok(ClickEvent<Button> buttonClickEvent) {
        DialogModal.confirmDialog("you clicked on OK!:::" + buttonClickEvent.getButton());
    }
    private void OnLineProfileCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<LineProfile>, LineProfile> comboBoxLineProfileComponentValueChangeEvent) {
    }

    private void OnOntCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<Ont>, Ont> comboBoxOntComponentValueChangeEvent) {

    }
}
