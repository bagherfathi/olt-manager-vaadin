package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.CommandHistory;
import com.gohardani.oltmanager.entity.OltType;
import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.service.CommandHistoryService;
import com.gohardani.oltmanager.service.SshService;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import com.gohardani.oltmanager.service.OltService;
import com.gohardani.oltmanager.service.OltTypeService;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "commandshistory", layout = MainLayout.class)
public class CommandsHistoryView extends VerticalLayout {

    public CommandsHistoryView(SshService sshService, OltTypeService oltTypeService, OltService oltService,CommandHistoryService commandHistoryService) {
        // crud instance
        GridCrud<CommandHistory> crud = new GridCrud<>(CommandHistory.class);

        // additional components
//        TextField filter = new TextField();
//        filter.setPlaceholder("Filter by name");
//        filter.setClearButtonVisible(true);
//        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration                              excecutionTime
        crud.getGrid().setColumns("id","olt","sshCommand", "commandText", "result");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("olt","sshCommand","commandText","result");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"olt","sshCommand","commandText","result");
        // show olts in combo
        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>(oltService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>("OLT Type", oltService.findAll(), new TextRenderer<>(Olt::getName), Olt::getName));
        // show related sshcommand after resolving oltType in combo
//        OltType ot=crud.getCrudFormFactory().
        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>(oltService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>("OLT Type", oltService.findAll(), new TextRenderer<>(Olt::getName), Olt::getName));

        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
//                () -> commandHistoryService.findByNameContainingIgnoreCase(filter.getValue()),
                () -> commandHistoryService.findAll(),
                sc -> commandHistoryService.save(sc),
                sc -> commandHistoryService.save(sc),
                sc -> commandHistoryService.delete(sc)
        );

//        filter.addValueChangeListener(e -> crud.refreshGrid());
    }

}
