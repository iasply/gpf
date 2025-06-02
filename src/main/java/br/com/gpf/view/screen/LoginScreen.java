package br.com.gpf.view.screen;

import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.data.LoadData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

    public String getUserName() {
        return userName.getText();
    }

    public void setLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public String getPassword() {
        return new String(password.getPassword());
    }
}
