package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.Utility.dialog.DialogModal;
import com.gohardani.oltmanager.entity.*;
import com.gohardani.oltmanager.service.*;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static com.gohardani.oltmanager.Utility.ONTID.FreeOntID.getBiggestFreeOntID;
import static com.gohardani.oltmanager.Utility.SSH.JavaTelnetsimulator.telnetConnection;
import static java.lang.System.lineSeparator;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "CommandSet", layout = MainLayout.class)
public class CommandsSetView extends VerticalLayout {

    private int freeBiggestOntID=-1;

    private OltService oltService;
    private CommandHistoryService commandHistoryService;
    private ComboBox<Olt> oltComboBox;
    private TextArea ta;
    private Button runButton;
    private Paragraph info;

    public CommandsSetView(OltService oltServiceIN, CommandHistoryService commandHistoryServiceIN) {
        oltService = oltServiceIN;
        commandHistoryService=commandHistoryServiceIN;
        oltComboBox = new ComboBox<Olt>("olt",this::OnOltCBClick);
        oltComboBox.setItems(oltService.findAll());
        oltComboBox.setItemLabelGenerator(Olt::getName);
        ta=new TextArea();
        ta.setLabel("Command Set:");
        ta.setWidth("100%");
        ta.setHeight("100%");
        HorizontalLayout hl0 = new HorizontalLayout(oltComboBox);
        add(hl0);
        add(ta);
        runButton = new Button("Run",this::OnRunButton);
        info = new Paragraph();
        info.getStyle().set("color" ,"#FF0000");
        add(info);
        add(runButton);
        info.setText("");
        HorizontalLayout hl2=new HorizontalLayout(runButton);
        add(hl2);
    }

    private void OnRunButton(ClickEvent<Button> buttonClickEvent) {
        if (oltComboBox.getValue() == null) {
            info.setText("Please Select an Olt");
            return;
        } else if (ta.getValue()==null) {
            info.setText("command set is null");
        } else if (ta.getValue().length()==0) {
            info.setText("command set is empty");
        } else {
            info.setText("");
        }
        Olt olt=oltComboBox.getValue();
        CommandHistory ch=new CommandHistory();
        //code to run ssh
        String[] s=ta.getValue().split(lineSeparator());
        ArrayList<String> c=new ArrayList<>(Arrays.asList(s));
        try {
            String result= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
            info.getStyle().set("color" ,"#00FF00");
            info.setText("result: " + result);
            ch.setCommandText(ta.getValue());
            ch.setResult(result);
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch = commandHistoryService.save(ch);
//            DialogModal.confirmDialog("if every thing done successfully, dont change your selections for next operations such as service-port & tr069 ...");
        } catch (Exception e) {
            info.getStyle().set("color" ,"#FF0000");
            info.setText("result: " + e.getMessage());
            ch.setCommandText(ta.getValue());
            ch.setResult(e.getMessage());
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch = commandHistoryService.save(ch);
//            DialogModal.confirmDialog("if every thing done successfully, dont change your selections for next operations such as service-port & tr069 ...");
            throw new RuntimeException(e);
        }
    }

    private void OnOltCBClick(AbstractField.ComponentValueChangeEvent<ComboBox<Olt>, Olt> comboBoxOltComponentValueChangeEvent) {
        if (comboBoxOltComponentValueChangeEvent.getValue() != null) {
            info.setText("OLT Selected: " + comboBoxOltComponentValueChangeEvent.getValue());
        }
    }
}
