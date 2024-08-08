package com.gohardani.oltmanager.Utility.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Span;

public class DialogModal {
    public static void confirmDialog(String message){
        Span status = new Span();
        status.setVisible(false);

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Information");
        dialog.setText(message);

        dialog.setCancelable(false);
        dialog.addCancelListener(event ->
        // canceled code here
                setStatus("Canceled")
        );

        dialog.setRejectable(false);
        dialog.setRejectText("Discard");
        dialog.addRejectListener(event ->
                //discarded code here
                setStatus("Discarded"));

        dialog.setConfirmText("OK");
        dialog.addConfirmListener(event ->
                // saved code here
                setStatus("Saved"));
        dialog.open();
        status.setVisible(false);
//        Button button = new Button("Open confirm dialog");
//        button.addClickListener(event -> {
//            dialog.open();
//            status.setVisible(false);
//        });
    }

    private static void setStatus(String saved) {
    }
}
