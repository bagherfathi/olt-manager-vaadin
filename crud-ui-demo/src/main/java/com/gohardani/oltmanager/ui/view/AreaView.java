package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.Area;
import com.gohardani.oltmanager.service.AreaService;
import com.gohardani.oltmanager.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "area", layout = MainLayout.class)
public class AreaView extends VerticalLayout {

    public AreaView(AreaService areaService) {
        // crud instance
        GridCrud<Area> crud = new GridCrud<>(Area.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by name");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("name");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
		    crud.getCrudFormFactory().setVisibleProperties("name");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"name");


        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> areaService.findByNameContainingIgnoreCase(filter.getValue()),
                area -> areaService.save(area),
                area -> areaService.save(area),
                area -> areaService.delete(area)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
    }

}
