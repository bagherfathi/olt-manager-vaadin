package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.Utility.dialog.DialogModal;
import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.service.*;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.gohardani.oltmanager.Utility.ONTID.FreeOntID.getBiggestFreeOntID;
import static com.gohardani.oltmanager.Utility.SSH.JavaTelnetsimulator.telnetConnection;
import static com.gohardani.oltmanager.Utility.dialog.DialogModal.confirmDialog;
import static com.gohardani.oltmanager.Utility.sshOutputProcessor.SSHOutputProcessor.getUnregisterdONTList;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "UnregisteredONT", layout = MainLayout.class)
public class UnregisteredONTView extends VerticalLayout {

    private OntUnregisteredService ontUnregisteredService;
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
    private TextField frameText;
    private TextField slotText;
    private TextField portText;
    private ComboBox<OntUnregistered> ontUnregisteredComboBox;
    private ComboBox<LineProfile> lineProfileComboBox;
    private ComboBox<ServiceProfile> serviceProfileComboBox;

    private Button addOntButton;
    private Button servicePortButton;
    private Button tr069Button;
    private Button ipConfigButton;
    private Button filterButton;
    private Button reImportButton;

    private Paragraph info;

    public UnregisteredONTView(SshService sshServiceIN, OntService ontServiceIN, OltService oltServiceIN, CommandHistoryService commandHistoryServiceIN, FrameService frameServiceIN, SlotService slotServiceIN, PortService portServiceIN, LineProfileService lineProfileServiceIN, ServiceProfileService serviceProfileServiceIN, OntUnregisteredService ontUnregisteredServiceIN) {
        oltService = oltServiceIN;
        frameService = frameServiceIN;
        slotService = slotServiceIN;
        portService = portServiceIN;
        ontService = ontServiceIN;
        sshService = sshServiceIN;
        commandHistoryService = commandHistoryServiceIN;
        lineProfileService = lineProfileServiceIN;
        serviceProfileService = serviceProfileServiceIN;
        ontUnregisteredService = ontUnregisteredServiceIN;
        oltComboBox = new ComboBox<Olt>("olt",this::OnOltCBClick);
        frameText = new TextField("frame");
        frameText.addValueChangeListener(this::OnFrameTextChanged);
        slotText = new TextField("slot");
        slotText.addValueChangeListener(this::OnSlotTextChanged);
        portText = new TextField("port");
        portText.addValueChangeListener(this::OnPortTextChanged);
        frameText.setClearButtonVisible(true);
        slotText.setClearButtonVisible(true);
        portText.setClearButtonVisible(true);
        ontUnregisteredComboBox = new ComboBox<OntUnregistered> ("ONT",this::OnOntCBClick);
        lineProfileComboBox = new ComboBox<LineProfile>("lineProfile");
        serviceProfileComboBox = new ComboBox<ServiceProfile>("serviceProfile");
        oltComboBox.setItems(oltService.findAll());
        oltComboBox.setItemLabelGenerator(Olt::getName);

        filterButton = new Button("Filter",this::onFilterClick);
        reImportButton = new Button("ReImport",this::onReImportClick);
        HorizontalLayout hl0 = new HorizontalLayout(oltComboBox,reImportButton,frameText, slotText, portText,filterButton, ontUnregisteredComboBox);
        HorizontalLayout hl1=new HorizontalLayout(lineProfileComboBox, serviceProfileComboBox);
        add(hl0,hl1);
        addOntButton = new Button("Add ONT",this::OnAddOntButton);
        servicePortButton = new Button("Service Port",this::OnServicePortButton);
        tr069Button = new Button("Tr069",this::OnTr069Button);
        ipConfigButton = new Button("IP Config",this::OnIPConfigButton);

        info = new Paragraph();
        info.getStyle().set("color" ,"#FF0000");
//        add(addOntButton);
        info.setText("");
        HorizontalLayout hl2=new HorizontalLayout(addOntButton,servicePortButton,tr069Button,ipConfigButton);
        add(hl2);
        add(info);
    }

    private void onFilterClick(ClickEvent<Button> buttonClickEvent) {
        if(ontUnregisteredComboBox.isEmpty()) {
            confirmDialog("Please Select Olt to filter");
            return;
        }
            ontUnregisteredComboBox.setItems(ontUnregisteredService.findByOltEquals(oltComboBox.getValue()));
            ontUnregisteredComboBox.setItemLabelGenerator(OntUnregistered::getSerialNumber);
            return;
        }
//        ontUnregisteredComboBox.clear();
//        ontUnregisteredComboBox.setItems(ontUnregisteredService.findByFspContainingIgnoreCase(frameText.getValue()+"/"+slotText.getValue()+"/"+portText.getValue()));
//        ontUnregisteredComboBox.setItemLabelGenerator(OntUnregistered::getSerialNumber);




    private void OnPortTextChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textFieldStringComponentValueChangeEvent) {
//        confirmDialog(textFieldStringComponentValueChangeEvent.getValue());
    }

    private void OnSlotTextChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textFieldStringComponentValueChangeEvent) {
//        confirmDialog(textFieldStringComponentValueChangeEvent.getValue());
    }

    private void OnFrameTextChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textFieldStringComponentValueChangeEvent) {
//        confirmDialog(textFieldStringComponentValueChangeEvent.getValue());
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
        Port p=getPortByOLTandFSP(olt);
        freeBiggestOntID=getBiggestFreeOntID(ontService,p);
        String freeBigONTIDstr=Integer.toString(freeBiggestOntID);
        String com="service-port vlan "+ olt.getOltParameters().getVlanid() +" gpon " + frameText.getValue()+"/"+slotText.getValue()+"/" +portText.getValue() + " ont " + freeBigONTIDstr +    " gemport "+ olt.getOltParameters().getGemport() +" multi-service user-vlan "+ olt.getOltParameters().getUserVlanID() +" tag-transform  translate \n";
        ArrayList<String> c=new ArrayList<>();
        c.add("enable");
        c.add("config");
        c.add(com);
        for(int i=0;i<5;i++)
            c.add("\t");
        c.add("\n\n\n");
        c.add("quit");
        c.add("quit");
        c.add("y");
        try {
            String result= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
            info.getStyle().set("color" ,"#00FF00");
            info.setText("result: " + result + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,p));
            ch.setCommandText(com);
            ch.setResult(result);
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch = commandHistoryService.save(ch);
        } catch (Exception e) {
            info.getStyle().set("color" ,"#FF0000");
            info.setText("result: " + e.getMessage() + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,p));
            ch.setCommandText(com);
            ch.setResult(e.getMessage());
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch = commandHistoryService.save(ch);
            throw new RuntimeException(e);
        }
    }

    private Port getPortByOLTandFSP(Olt olt) {
        Frame f=frameService.findByFrameNumberAndOltEquals2(frameText.getValue(),olt);
        Slot s=slotService.findByFrameAndSlotID(f,Long.parseLong(slotText.getValue()));
        return portService.findByFspContainingIgnoreCase(ontUnregisteredComboBox.getValue().getFsp().trim()).getFirst();
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
        Port p=getPortByOLTandFSP(olt);
        freeBiggestOntID=getBiggestFreeOntID(ontService,p);
        String freeBigONTIDstr=Integer.toString(freeBiggestOntID);
        String com="ont tr069-server-config " + portText.getValue() + " " + freeBigONTIDstr + " profile-id " + olt.getOltParameters().getTr069ProfileID();
        ArrayList<String> c=new ArrayList<>();
        c.add("enable");
        c.add("config");
        c.add("interface gpon " + frameText.getValue() + "/" + slotText.getValue());
        c.add(com);
        for(int i=0;i<5;i++)
            c.add("\t");
        c.add("\n\n\n");
        c.add("quit");
        c.add("quit");
        c.add("quit");
        c.add("y");
        try {
            String result= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
            info.getStyle().set("color" ,"#00FF00");
            info.setText("result: " + result + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,p));
            ch.setCommandText(com);
            ch.setResult(result);
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch = commandHistoryService.save(ch);
        } catch (Exception e) {
            info.getStyle().set("color" ,"#FF0000");
            info.setText("result: " + e.getMessage() + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,p));
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
        Port p=getPortByOLTandFSP(olt);
        freeBiggestOntID=getBiggestFreeOntID(ontService,p);
        String freeBigONTIDstr=Integer.toString(freeBiggestOntID);
        String com="ont  ipconfig  " + portText.getValue() + "  " +  freeBigONTIDstr + "  ip-index  "+ olt.getOltParameters().getIpIndex() +"  dhcp  vlan  "+ olt.getOltParameters().getVlanid() +"  priority   " + olt.getOltParameters().getPriority();
        ArrayList<String> c=new ArrayList<>();
        c.add("enable");
        c.add("config");
        c.add("interface gpon " + frameText.getValue() + "/" + slotText.getValue());
        c.add(com);
        for(int i=0;i<5;i++)
            c.add("\t");
        c.add("\n\n\n");
        c.add("quit");
        c.add("quit");
        c.add("quit");
        c.add("y");
        try {
            String result= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
            info.getStyle().set("color" ,"#00FF00");
            info.setText("result: " + result + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,p));
            ch.setCommandText(com);
            ch.setResult(result);
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch = commandHistoryService.save(ch);
        } catch (Exception e) {
            info.getStyle().set("color" ,"#FF0000");
            info.setText("result: " + e.getMessage() + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,p));
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
        confirmDialog("you clicked on cancel");
    }

    private void OnAddOntButton(ClickEvent<Button> buttonClickEvent) {
        if (oltComboBox.getValue() == null) {
            info.setText("Please Select an Olt");
            return;
        }
        else if (frameText.getValue()==null) {
            info.setText("Please select a Frame");
            return;
        }
        else if (slotText.getValue()==null) {
            info.setText("Please select a Slot");
            return;
        }
        else if (portText.getValue()==null) {
            info.setText("Please select a Port");
            return;
        }
        else if (ontUnregisteredComboBox.getValue()==null) {
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
        Port p=getPortByOLTandFSP(olt);
        freeBiggestOntID=getBiggestFreeOntID(ontService,p);
        String freeBigONTIDstr=Integer.toString(freeBiggestOntID);
        String addOntCommand="ont add " + portText.getValue() +" " + freeBigONTIDstr + " sn-auth " + ontUnregisteredComboBox.getValue().getSerialNumber() + " omci ont-lineprofile-id " + lineProfileComboBox.getValue().getProfileID() + " ont-srvprofile-id " + serviceProfileComboBox.getValue().getProfileID() + " desc OltManager";
        String servicePortCommand2="service-port vlan "+ olt.getOltParameters().getVlanid() +" gpon " + frameText.getValue()+"/"+slotText.getValue()+"/" +portText.getValue() + " ont " + freeBigONTIDstr +    " gemport "+ olt.getOltParameters().getGemport() +" multi-service user-vlan "+ olt.getOltParameters().getUserVlanID() +" tag-transform  translate \n";
        String iPConfigCommand="ont  ipconfig  " + portText.getValue() + "  " +  freeBigONTIDstr + "  ip-index  "+ olt.getOltParameters().getIpIndex() +"  dhcp  vlan  "+ olt.getOltParameters().getVlanid() +"  priority   " + olt.getOltParameters().getPriority();
        String tr069ConfigCommand="ont tr069-server-config " + portText.getValue() + " " + freeBigONTIDstr + " profile-id " + olt.getOltParameters().getTr069ProfileID();

//        System.out.println("command is:" +com);
        ArrayList<String> c=new ArrayList<>();
        c.add("enable");
        c.add("config");
        c.add("interface gpon " + frameText.getValue() + "/" + slotText.getValue());
        c.add(addOntCommand);
        c.add("\n");
        c.add("quit");
        c.add("\n");
        c.add(servicePortCommand2);
        c.add("\n");
        c.add("\n");
        c.add("interface gpon " + frameText.getValue() + "/" + slotText.getValue());
        c.add("\n");
        c.add("\n");
        c.add(tr069ConfigCommand);
        c.add("\n");
        c.add(iPConfigCommand);
        for(int i=0;i<5;i++)
            c.add("\t");
        c.add("\n\n\n");
        c.add("quit");
        c.add("quit");
        c.add("quit");
        c.add("y");
        try {
            String result= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
            info.getStyle().set("color" ,"#00FF00");
            info.setText("result: " + result + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,p));
            ch.setCommandText(addOntCommand);
            ch.setResult(result);
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch = commandHistoryService.save(ch);
            confirmDialog("if every thing done successfully, dont change your selections for next operations such as service-port & tr069 ...");
        } catch (Exception e) {
            info.getStyle().set("color" ,"#FF0000");
            info.setText("result: " + e.getMessage() + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,p));
            ch.setCommandText(addOntCommand);
            ch.setResult(e.getMessage());
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch = commandHistoryService.save(ch);
            confirmDialog("if every thing done successfully, dont change your selections for next operations such as service-port & tr069 ...");
            throw new RuntimeException(e);
        }
    }

   private void OnOltCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<Olt>, Olt> comboBoxOltComponentValueChangeEvent) {
       serviceProfileComboBox.setItems(serviceProfileService.findByOltEquals(comboBoxOltComponentValueChangeEvent.getValue()));
       serviceProfileComboBox.setItemLabelGenerator(ServiceProfile::getProfileName);
        lineProfileComboBox.setItems(lineProfileService.findByOltEquals(comboBoxOltComponentValueChangeEvent.getValue()));
       lineProfileComboBox.setItemLabelGenerator(LineProfile::getProfileName);
        ontUnregisteredComboBox.setItems(ontUnregisteredService.findByOltEquals(comboBoxOltComponentValueChangeEvent.getValue()));
       ontUnregisteredComboBox.setItemLabelGenerator(OntUnregistered::getSerialNumber);
    }
    private void onReImportClick(ClickEvent<Button> buttonClickEvent){
        if (oltComboBox.getValue()==null) {
            confirmDialog("please select olt");
        }
        else {
//            frameText.setItems(frameService.findByOltEquals(comboBoxOltComponentValueChangeEvent.getValue()));
//            frameText.setItemLabelGenerator(Frame::getFrameNumberAsText);
            Olt olt=oltComboBox.getValue();
            CommandHistory ch=new CommandHistory();
            //code to run ssh
            ArrayList<String> c=new ArrayList<>();
            c.add("enable");
            c.add("config");
            c.add("display ont autofind all");
            for(int i=0;i<20;i++)
                c.add("\t");
            c.add("\n\n\n");
            c.add("quit");
            c.add("quit");
            c.add("y");
            try {
                String result= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
                List<OntUnregistered> ontUnregistereds=getUnregisterdONTList(result);
                for(OntUnregistered ontUnregistered:ontUnregistereds)
                    ontUnregistered.setOlt(olt);
                ontUnregisteredService.deleteByOlt(olt);
                ontUnregisteredService.saveAll(ontUnregistereds);
                ontUnregisteredComboBox.setItems(ontUnregisteredService.findByOltEquals(olt));
                ontUnregisteredComboBox.setItemLabelGenerator(OntUnregistered::getSerialNumber);
                info.getStyle().set("color" ,"#00FF00");
                info.setText("result: " + result);
                ch.setCommandText("display ont autofind all");
                ch.setResult(result);
                ch.setOlt(olt);
                ch.setOltType(olt.getOltType());
                ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
                ch = commandHistoryService.save(ch);
            } catch (Exception e) {
                info.getStyle().set("color" ,"#FF0000");
//                Port p=getPortByOLTandFSP(olt);
//                info.setText("result: " + e.getMessage() + " getBiggestFreeOntID:" +getBiggestFreeOntID(ontService,p));
                ch.setCommandText("display ont autofind all");
                ch.setResult(e.getMessage());
                ch.setOlt(olt);
                ch.setOltType(olt.getOltType());
                ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
                ch = commandHistoryService.save(ch);
                throw new RuntimeException(e);
            }

        }
    }
    private void ok(ClickEvent<Button> buttonClickEvent) {
        confirmDialog("you clicked on OK!:::" + buttonClickEvent.getButton());
    }


    private void OnOntCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<OntUnregistered>, OntUnregistered> comboBoxOntComponentValueChangeEvent) {
        if (comboBoxOntComponentValueChangeEvent.getValue() != null) {
//            confirmDialog(comboBoxOntComponentValueChangeEvent.getValue().getFsp());
            String[] fsps = comboBoxOntComponentValueChangeEvent.getValue().getFsp().trim().split("/");
            frameText.setValue(fsps[0]);
            slotText.setValue(fsps[1]);
            portText.setValue(fsps[2]);
        }
    }
}
