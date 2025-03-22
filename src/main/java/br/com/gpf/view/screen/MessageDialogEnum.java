package br.com.gpf.view.screen;

import javax.swing.*;

public enum MessageDialogEnum {
    ERROR("Error", JOptionPane.ERROR_MESSAGE),
    SUCCESS("Sucess",JOptionPane.INFORMATION_MESSAGE);

    private final String title;
    private final int option;

    MessageDialogEnum(String title, int option) {
        this.title = title;
        this.option =option;
    }

    public void showMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, this.title, this.option);
    }
}
