package com.gohardani.oltmanager.ui.view;

import com.gohardani.oltmanager.entity.Frame;
import com.gohardani.oltmanager.entity.Olt;
import com.gohardani.oltmanager.entity.OltParameters;
import com.gohardani.oltmanager.entity.OltType;
import com.gohardani.oltmanager.service.OltParametersService;
import com.gohardani.oltmanager.service.OltService;
import com.gohardani.oltmanager.service.OltTypeService;
import com.gohardani.oltmanager.ui.MainLayout;
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
import java.util.Collections;

@RolesAllowed({"ADMIN","USER"})
@Route(value = "oltParameters", layout = MainLayout.class)
public class OltParametersView extends VerticalLayout {

    public OltParametersView(OltParametersService oltParametersService, OltService oltService) {
        // crud instance
        GridCrud<OltParameters> crud = new GridCrud<>(OltParameters.class);
        ComboBox<Olt> oltComboBox = new ComboBox<>();

        //olt filter
        oltComboBox.setItems(oltService.findAll());
        oltComboBox.setItemLabelGenerator(Olt::getName);

        oltComboBox.setPlaceholder("select OLT");
        oltComboBox.setClearButtonVisible(true);
        crud.getCrudLayout().addFilterComponent(oltComboBox);

        // grid configuration
        crud.getGrid().setColumns("olt","vlanid", "gemport", "userVlanID","ipIndex","priority","tr069ProfileID","user");
        crud.getGrid().setColumnReorderingAllowed(true);

        // form configuration
        crud.getCrudFormFactory().setUseBeanValidation(true);
		    crud.getCrudFormFactory().setVisibleProperties("olt","vlanid", "gemport", "userVlanID","ipIndex","priority","tr069ProfileID","user");
        crud.getCrudFormFactory().setVisibleProperties(CrudOperation.ADD,"olt","vlanid", "gemport", "userVlanID","ipIndex","priority","tr069ProfileID","user");

        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>(oltService.findAll()));
        crud.getCrudFormFactory().setFieldProvider("olt",
                new ComboBoxProvider<>("OLT", oltService.findAll(), new TextRenderer<>(Olt::getName), Olt::getName));

        // layout configuration
        setSizeFull();
        add(crud);
        // logic configuration
        crud.setOperations(
                () -> oltParametersService.findOltParametersByOlt(oltComboBox.getValue()),
                oltParameters -> oltParametersService.save(oltParameters),
                oltParameters -> oltParametersService.save(oltParameters),
                oltParameters -> oltParametersService.delete(oltParameters)
        );

        oltComboBox.addValueChangeListener(e -> crud.refreshGrid());
    }

}
