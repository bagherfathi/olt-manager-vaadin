package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.OltType;
import com.gohardani.oltmanager.entity.SshCommand;
import com.gohardani.oltmanager.service.SshService;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import com.gohardani.oltmanager.service.OltTypeService;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "sshcommands", layout = MainLayout.class)
public class SSHCommandsView extends VerticalLayout {

    public SSHCommandsView(SshService sshService, OltTypeService oltTypeService) {
        // crud instance
        GridCrud<SshCommand> crud = new GridCrud<>(SshCommand.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by name");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("name", "fixPart", "varPart","oltType","user");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name","fixPart", "varPart","oltType","user");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"name", "fixPart", "varPart","oltType","user");

        crud.getCrudFormFactory().setFieldProvider("oltType",
                new ComboBoxProvider<>(oltTypeService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("oltType",
                new ComboBoxProvider<>("OLT Type", oltTypeService.findAll(), new TextRenderer<>(OltType::getName), OltType::getName));

        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> sshService.findByNameContainingIgnoreCase(filter.getValue()),
                sc -> sshService.save(sc),
                sc -> sshService.save(sc),
                sc -> sshService.delete(sc)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
    }

}
