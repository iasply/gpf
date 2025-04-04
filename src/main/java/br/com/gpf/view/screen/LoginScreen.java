package br.com.gpf.view.screen;

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

public class LoginScreen extends DefaultTemplateScreen {


    private static final String LABEL_PASSWORD = "Senha:";
    private static final String LABEL_USERNAME = "Nome de Usuário:";
    private static final String LABEL_LOGIN = "Entrar";
    private final JTextField userName;
    private final JButton loginButton;
    private final JPasswordField password;
    private final JLabel userNameLabel;
    private final JLabel passwordLabel;

    public LoginScreen() {
        this.userNameLabel = new JLabel(LABEL_USERNAME);
        this.passwordLabel = new JLabel(LABEL_PASSWORD);
        this.userName = new JTextField();
        this.password = new JPasswordField();
        this.loginButton = new JButton(LABEL_LOGIN);
    }


    @Override
    public void onload(LoadData loadData) {
        loginButton.addActionListener(e -> {
            ResponseData responseData = Controller.getInstance().getAccountService().login(userName.getText(), new String(password.getPassword()));

            if (responseData.getValue() != RequestStatusEnum.SUCCESS) {

                String msg = DataEnum.decodeString(DataEnum.ERROR_MSG, responseData.getMapData().get(DataEnum.ERROR_MSG));
                MessageDialogEnum.ERROR.showMsg(msg, null);

                SwingUtilities.invokeLater(() -> {
                    GpfScreenManager instance = GpfScreenManager.getInstance();
                    instance.changeScreen(instance.loadScreenPanel(ScreenEnum.LOGIN), null);
                });
                return;
            }


            SwingUtilities.invokeLater(() -> {
                GpfScreenManager instance = GpfScreenManager.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.HOME), null);
            });

        });


    }

    @Override
    public void onSave() throws DefaultScreenException {
        // :)
    }

    @Override
    public String getTittle() {
        return ScreenEnum.LOGIN.getTitle();
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

        gbc.gridx = 1;
        gbc.gridy = 2;
        super.midPanel.add(loginButton, gbc);


        return super.midPanel;
    }

    @Override
    public JPanel getBottomPanel() {
        super.bottomPanel.add(super.defaultNewAccountButton());
        return this.bottomPanel;
    }
}
