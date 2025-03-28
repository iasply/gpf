package br.com.gpf.view.screen;

import br.com.gpf.Util;
import br.com.gpf.service.Controller;
import br.com.gpf.service.DataEnum;
import br.com.gpf.service.RequestStatusEnum;
import br.com.gpf.service.ResponseData;
import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.GpfScreenManager;
import br.com.gpf.view.LoadData;
import br.com.gpf.view.MessageDialogEnum;

import javax.swing.*;
import java.awt.*;

public class NewAccountScreen extends DefaultTemplateScreen {

    private final JTextField userName;
    private final JPasswordField password;
    private final JPasswordField confirmPassword;
    private final JButton register;
    private final JButton back;

    private final JLabel userNameLabel;
    private final JLabel passwordLabel;
    private final JLabel confirmPasswordLabel;

    private static final String LABEL_PASSWORD = "Password:";
    private static final String LABEL_CONFIRM_PASSWORD = "Confirm Password:";
    private static final String LABEL_USERNAME = "User Name:";
    private static final String LABEL_REGISTER = "REGISTER";
    private static final String LABEL_BACK_TO_LOGIN = "back to login";

    public NewAccountScreen() {
        this.userNameLabel = new JLabel(LABEL_USERNAME);
        this.passwordLabel = new JLabel(LABEL_PASSWORD);
        this.userName = new JTextField();
        this.password = new JPasswordField();
        this.register = new JButton(LABEL_REGISTER);
        this.back = new JButton(LABEL_BACK_TO_LOGIN);
        this.confirmPassword = new JPasswordField();
        this.confirmPasswordLabel = new JLabel(LABEL_CONFIRM_PASSWORD);
    }


    @Override
    public void onload(LoadData loadData) {

        register.addActionListener(e -> {
            try {
                onSave();
            } catch (DefaultScreenException ex) {
                MessageDialogEnum.ERROR.showMsg(ex.getMessage(),null);
                return;
            }

            ResponseData responseData = Controller.getInstance().getAccountService().createAccount(userName.getText(), new String(password.getPassword()));
            if (responseData.getValue() != RequestStatusEnum.SUCCESS) {
                String msg = DataEnum.decodeString(DataEnum.ERROR_MSG, responseData.getMapData().get(DataEnum.ERROR_MSG));
                MessageDialogEnum.ERROR.showMsg(msg,null);

                SwingUtilities.invokeLater(() -> {
                    GpfScreenManager instance = GpfScreenManager.getInstance();
                    instance.changeScreen(instance.loadScreenPanel(ScreenEnum.NEW_ACCOUNT), null);
                });
                return;
            }

            MessageDialogEnum.SUCCESS.showMsg("USER " + userName.getText() + " CREATED",null);

            SwingUtilities.invokeLater(() -> {
                GpfScreenManager instance = GpfScreenManager.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.LOGIN), null);
            });


        });

        back.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenManager instance = GpfScreenManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.LOGIN), null);
        }));

    }

    @Override
    public void onSave() throws DefaultScreenException {
        if (Util.isBlankNullOrEmpty(userName.getText()) ||
                Util.isBlankNullOrEmpty(new String(password.getPassword()))
                ||
                Util.isBlankNullOrEmpty(new String(confirmPassword.getPassword()))
        ) {
            throw new DefaultScreenException("FILL IN ALL FIELDS");
        }

        if (!new String(password.getPassword()).equals(new String(confirmPassword.getPassword()))) {
            throw new DefaultScreenException("PASSWORDS MUST BE THE SAME");
        }


    }

    @Override
    public String getTittle() {
        return ScreenEnum.NEW_ACCOUNT.getTitle();
    }


    @Override
    public JPanel getTopPanel() {
        return this.topPanel;
    }


    @Override
    public JPanel getMidPanel() {
        super.midPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        super.midPanel.add(userNameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        super.midPanel.add(userName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        super.midPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        super.midPanel.add(password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        super.midPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        super.midPanel.add(confirmPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        super.midPanel.add(register, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        super.midPanel.add(back, gbc);

        return super.midPanel;
    }


    @Override
    public JPanel getBottomPanel() {
        return this.bottomPanel;
    }
}
