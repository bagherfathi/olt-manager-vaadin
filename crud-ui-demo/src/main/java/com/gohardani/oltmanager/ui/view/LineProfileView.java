package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.LineProfile;
import com.gohardani.oltmanager.service.LineProfileService;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "lineprofile", layout = MainLayout.class)
public class LineProfileView extends VerticalLayout {

    public LineProfileView(LineProfileService lineProfileService) {
        // crud instance
        GridCrud<LineProfile> crud = new GridCrud<>(LineProfile.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by profile name");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("id","profileID", "profileName", "bindingTimes");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
		    crud.getCrudFormFactory().setVisibleProperties("profileID", "profileName", "bindingTimes");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"profileID", "profileName", "bindingTimes");

        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> lineProfileService.findByProfileNameContainingIgnoreCase(filter.getValue()),
                lineProfile -> lineProfileService.save(lineProfile),
                lineProfile -> lineProfileService.save(lineProfile),
                lineProfile -> lineProfileService.delete(lineProfile)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
    }

}
