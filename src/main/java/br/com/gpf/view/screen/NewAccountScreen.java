package br.com.gpf.view.screen;

import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.data.LoadData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NewAccountScreen extends DefaultTemplateScreen {

    private static final String LABEL_PASSWORD = "Senha:";
    private static final String LABEL_CONFIRM_PASSWORD = "Confirmar Senha:";
    private static final String LABEL_USERNAME = "Nome de Usuário:";
    private static final String LABEL_REGISTER = "Cadastrar";
    private final JTextField userName;
    private final JPasswordField password;
    private final JPasswordField confirmPassword;
    private final JButton register;
    private final JLabel userNameLabel;
    private final JLabel passwordLabel;
    private final JLabel confirmPasswordLabel;


    public NewAccountScreen() {
        this.userNameLabel = new JLabel(LABEL_USERNAME);
        this.passwordLabel = new JLabel(LABEL_PASSWORD);
        this.userName = new JTextField();
        this.password = new JPasswordField();
        this.register = new JButton(LABEL_REGISTER);
        this.confirmPassword = new JPasswordField();
        this.confirmPasswordLabel = new JLabel(LABEL_CONFIRM_PASSWORD);
    }


    @Override
    public void onload(LoadData loadData) {
    }

    @Override
    public void onSave() throws DefaultScreenException {
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


        return super.midPanel;
    }


    @Override
    public JPanel getBottomPanel() {

        super.bottomPanel.add(super.defaultLoginButton());

        return this.bottomPanel;
    }

    public String getUserName() {
        return userName.getText();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPassword.getPassword());
    }

    public void setRegisterListener(ActionListener listener) {
        register.addActionListener(listener);
    }
}
