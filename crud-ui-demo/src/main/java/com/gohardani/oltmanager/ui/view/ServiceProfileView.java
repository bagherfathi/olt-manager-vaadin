package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.User;
import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.entity.ServiceProfile;
import com.gohardani.oltmanager.service.OltService;
import com.gohardani.oltmanager.service.ServiceProfileService;
import com.gohardani.oltmanager.service.UserService;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.ClickEvent;
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
import static com.gohardani.oltmanager.Utility.sshOutputProcessor.SSHOutputProcessor.getServiceProfileList;
import static com.gohardani.oltmanager.Utility.sshOutputProcessor.SSHOutputProcessor.testGetServiceProfileList;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "serviceprofile", layout = MainLayout.class)
public class ServiceProfileView extends VerticalLayout {
    ComboBox<Olt> oltComboBox;
    ServiceProfileService serviceProfileService;
    UserService userService;
    GridCrud<ServiceProfile> crud;

    public ServiceProfileView(ServiceProfileService serviceProfileService, OltService oltService,UserService userService) {
        this.serviceProfileService = serviceProfileService;
        this.userService = userService;
        crud = new GridCrud<>(ServiceProfile.class);
        oltComboBox = new ComboBox<>();
        //olt filter
        oltComboBox.setItems(oltService.findAll());
        oltComboBox.setItemLabelGenerator(Olt::getName);
        oltComboBox.setPlaceholder("select OLT");
        oltComboBox.setClearButtonVisible(true);
        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by profile name");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);
        //import button
        Button button = new Button("Import",this::importButtonClickListener);
        crud.getCrudLayout().addToolbarComponent(oltComboBox);
        crud.getCrudLayout().addToolbarComponent(button);
        // grid configuration
        crud.getGrid().setColumns("olt","profileID", "profileName", "bindingTimes");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
		    crud.getCrudFormFactory().setVisibleProperties("olt","profileID", "profileName", "bindingTimes");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"olt","profileID", "profileName", "bindingTimes");

        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>(oltService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>("OLT", oltService.findAll(), new TextRenderer<>(Olt::getName), Olt::getName));

        crud.getCrudFormFactory().setFieldProvider("user",
                new ComboBoxProvider<>(userService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("user",
                new ComboBoxProvider<>("USER", userService.findAll(), new TextRenderer<>(User::getName), User::getName));
        // layout configuration
        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> serviceProfileService.findByProfileNameContainingIgnoreCase(filter.getValue()),
                serviceProfile  -> serviceProfileService.save(serviceProfile),
                serviceProfile  -> serviceProfileService.save(serviceProfile),
                serviceProfile  -> serviceProfileService.delete(serviceProfile)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
    }
    private void importButtonClickListener(ClickEvent<Button> buttonClickEvent) {
        if (oltComboBox.getValue() == null) {
            confirmDialog("Please Select an Olt");
            return;
        }
        Olt olt = oltComboBox.getValue();
        if (olt.getIp().trim().contains("1.1.1.8")) {
            ArrayList<ServiceProfile> serviceProfiles=testGetServiceProfileList();
            for (ServiceProfile serviceProfile : serviceProfiles) {
                serviceProfile.setOlt(olt);
                serviceProfileService.save(serviceProfile);
            }
            crud.refreshGrid();
            return;
        }
        ArrayList<String> c=new ArrayList<>();
        c.add("enable");
        c.add("display ont-lineprofile gpon all");
        for(int i=0;i<10;i++)
            c.add("\t");
        c.add("\n\n\n");
        c.add("quit");
        c.add("y");
        try {
            String s= telnetConnection(c,olt.getUsername().trim(),olt.getPassword().trim(), olt.getIp().trim(),olt.getPort() );
            ArrayList<ServiceProfile> serviceProfiles=getServiceProfileList(s);
            if(serviceProfiles.size()==0) {
                confirmDialog("No service profile found on this olt");
                return;
            }
            serviceProfileService.deleteByOlt(olt);
            for (ServiceProfile serviceProfile : serviceProfiles) {
                serviceProfile.setOlt(olt);
                serviceProfileService.save(serviceProfile);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
