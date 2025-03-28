package br.com.gpf.view;

import javax.swing.*;
import java.awt.*;

public enum MessageDialogEnum {
    ERROR("Error", JOptionPane.ERROR_MESSAGE),
    SUCCESS("Sucess", JOptionPane.INFORMATION_MESSAGE),
    YES_OR_NOT("Are you sure?", JOptionPane.YES_NO_OPTION);

    private final String title;
    private final int option;

    MessageDialogEnum(String title, int option) {
        this.title = title;
        this.option = option;
    }

    public boolean showMsg(String msg, Component component) {

        if (this != YES_OR_NOT) {
            JOptionPane.showMessageDialog(component, msg, this.title, this.option);
            return true;
        } else {
            int response = JOptionPane.showConfirmDialog(component,
                    msg,
                    this.title,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            return response == JOptionPane.YES_OPTION;
        }
    }

}
