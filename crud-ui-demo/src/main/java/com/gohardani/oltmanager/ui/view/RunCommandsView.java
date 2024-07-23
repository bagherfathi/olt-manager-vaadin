package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.SSH.SSHUtility;
import com.gohardani.oltmanager.entity.CommandHistory;
import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.entity.SshCommand;
import com.gohardani.oltmanager.service.CommandHistoryService;
import com.gohardani.oltmanager.service.OltService;
import com.gohardani.oltmanager.service.OltTypeService;
import com.gohardani.oltmanager.service.SshService;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "runcommand", layout = MainLayout.class)
public class RunCommandsView extends VerticalLayout {

    public RunCommandsView(SshService sshService, OltTypeService oltTypeService, OltService oltService, CommandHistoryService commandHistoryService) {
        ComboBox<Olt> oltComboBox = new ComboBox<>("olt");
        ComboBox<SshCommand> sshCommandComboBox = new ComboBox<>("sshCommand");
        oltComboBox.setItems(oltService.findAll());
        oltComboBox.setItemLabelGenerator(Olt::getName);
        oltComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
             Olt olt = e.getValue();
             sshCommandComboBox.setItems(sshService.findSshCommandsByOltType(olt.getOltType()));
             sshCommandComboBox.setItemLabelGenerator(SshCommand::getName);
             System.out.println(olt.getName());
            }
            });
        sshCommandComboBox.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                SshCommand sshCommand = e.getValue();
                System.out.println(sshCommand.getName());
            }
        });
        add(oltComboBox);
        add(sshCommandComboBox);
        Button button = new Button("Run command");
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
            else {
                info.setText("");
            }
            if (sshCommandComboBox.getValue()==null) {
                info.setText("Please select a Ssh Command");
                return;
            }
            else {
                info.setText("");
            }
            Olt olt=oltComboBox.getValue();
            SshCommand sshCommand=sshCommandComboBox.getValue();
            CommandHistory ch=new CommandHistory();
            //code to run ssh
            String com=null;
            if(sshCommand.getVarPart()!=null)
                com=sshCommand.getFixPart() + " " + sshCommand.getVarPart();
            else
                com=sshCommand.getFixPart();
            SSHUtility ssh=new SSHUtility();
            String result= ssh.runCommand(olt.getIp(),olt.getPort(),olt.getUsername(),olt.getPassword(),com);
            info.getStyle().set("color" ,"#00FF00");
            info.setText("result: " + result);
            ch.setCommandText(com);
            ch.setResult(result);
            ch.setOlt(olt);
            ch.setOltType(olt.getOltType());
            ch.setExcectionTime(ZonedDateTime.now(ZoneId.of("Asia/Tehran")));
            ch.setSshCommand(sshCommand);
            ch = commandHistoryService.save(ch);
            //end of my code
        });



    }


}
