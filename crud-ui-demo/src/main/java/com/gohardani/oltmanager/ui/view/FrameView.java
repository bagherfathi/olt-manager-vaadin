package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.Frame;
import com.gohardani.oltmanager.entity.User;
import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.service.FrameService;
import com.gohardani.oltmanager.service.OltService;
import com.gohardani.oltmanager.service.UserService;
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
@Route(value = "frame", layout = MainLayout.class)
public class FrameView extends VerticalLayout {

//    private final UserService userService;

    public FrameView(FrameService frameService, UserService userService, OltService oltService) {
        // crud instance
        GridCrud<Frame> crud = new GridCrud<>(Frame.class);

        // additional components
        TextField filter = new TextField();
        filter.setPlaceholder("Filter by frame number");
        filter.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(filter);

        // grid configuration
        crud.getGrid().setColumns("id", "olt","frameNumber", "description","user");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
        crud.getCrudFormFactory().setVisibleProperties("olt", "frameNumber", "description","user");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"olt", "frameNumber", "description","user");

        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>(oltService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>("OLT", oltService.findAll(), new TextRenderer<>(Olt::getName), Olt::getName));


        crud.getCrudFormFactory().setFieldProvider("user",
                new ComboBoxProvider<>(userService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("user",
                new ComboBoxProvider<>("USER", userService.findAll(), new TextRenderer<>(User::getName), User::getName));
        // layout configuration
        setSizeFull();
        add(crud);

        // logic configuration
        crud.setOperations(
                () -> frameService.findByFrameNumberEquals(filter.getValue()),
                frame -> frameService.save(frame),
                frame -> frameService.save(frame),
                frame -> frameService.delete(frame)
        );

        filter.addValueChangeListener(e -> crud.refreshGrid());
//        this.userService = userService;
    }

}
