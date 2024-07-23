package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.entity.OltType;
import com.gohardani.oltmanager.service.OltService;
import com.gohardani.oltmanager.service.OltTypeService;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.ComboBoxProvider;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "olt", layout = MainLayout.class)
public class OltView extends VerticalLayout {

    public OltView(OltService oltService, OltTypeService oltTypeService) {
        // crud instance
        GridCrud<Olt> crud = new GridCrud<>(Olt.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by name");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("id","name", "ip", "port","username","serialNumber","oltType","user");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("name", "ip", "port", "username","password", "serialNumber", "oltType", "user");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"name", "ip", "port", "username", "password", "serialNumber", "oltType", "user");

        crud.getCrudFormFactory().setFieldProvider("oltType",
                new ComboBoxProvider<>(oltTypeService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("oltType",
                new ComboBoxProvider<>("OLT Type", oltTypeService.findAll(), new TextRenderer<>(OltType::getName), OltType::getName));

        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> oltService.findByNameContainingIgnoreCase(filter.getValue()),
                olt -> oltService.save(olt),
                olt -> oltService.save(olt),
                olt -> oltService.delete(olt)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
    }

}
