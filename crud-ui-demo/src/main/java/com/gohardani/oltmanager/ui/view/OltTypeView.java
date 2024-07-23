package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.OltType;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import com.gohardani.oltmanager.service.OltTypeService;
import com.gohardani.oltmanager.ui.MainLayout;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "olttype", layout = MainLayout.class)
public class OltTypeView extends VerticalLayout {

    public OltTypeView(OltTypeService oltTypeService) {
        // crud instance
        GridCrud<OltType> crud = new GridCrud<>(OltType.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by name");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("id","name", "company", "model");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
		    crud.getCrudFormFactory().setVisibleProperties("name", "company", "model");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"name", "company", "model");


        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> oltTypeService.findByNameContainingIgnoreCase(filter.getValue()),
                oltType -> oltTypeService.save(oltType),
                oltType -> oltTypeService.save(oltType),
                oltType -> oltTypeService.delete(oltType)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
    }

}
