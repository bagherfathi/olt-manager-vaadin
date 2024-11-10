package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.Area;
import com.gohardani.oltmanager.entity.Center;
import com.gohardani.oltmanager.service.AreaService;
import com.gohardani.oltmanager.service.CenterService;
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
@Route(value = "center", layout = MainLayout.class)
public class CenterView extends VerticalLayout {

    public CenterView(CenterService centerService, AreaService areaService) {
        // crud instance
        GridCrud<Center> crud = new GridCrud<>(Center.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by name");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("area","name");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("area","name");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"area","name");

        crud.getCrudFormFactory().setFieldProvider("area",
                new ComboBoxProvider<>(areaService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("area",
                new ComboBoxProvider<>("Area", areaService.findAll(), new TextRenderer<>(Area::getName), Area::getName));
        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> centerService.findByNameContainingIgnoreCase(filter.getValue()),
                center -> centerService.save(center),
                center -> centerService.save(center),
                center -> centerService.delete(center)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
    }

}
