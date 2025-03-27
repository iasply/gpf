package br.com.gpf.view.screen;

import br.com.gpf.service.Controller;
import br.com.gpf.view.GpfScreen;

import javax.swing.*;
import java.awt.*;
import java.lang.ref.PhantomReference;

public abstract class DefaultTemplateScreen implements Screen {
    protected JPanel topPanel;
    protected JPanel midPanel;
    protected JPanel bottomPanel;
    private static final String BUTTON_TEXT_HOME = "HOME";


    protected DefaultTemplateScreen() {
        defaultTopPanel();
        defaultMidPanel();
        defaultBottomPanel();
    }


    protected JButton defaultHomeButton() {
        JButton homeButton = new JButton(BUTTON_TEXT_HOME);
        homeButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreen instance = GpfScreen.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.HOME), null);
        }));
        return homeButton;
    }
    protected JLabel userNameLabel(){
        JLabel accountName = new JLabel();
        accountName.setText("/ USUARIO "+Controller.getInstance().getSession().userName());
        return accountName;
    }

    private void defaultTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.add(new JLabel(getTittle()));
        //topPanel.setBackground(Color.red);
    }

    private void defaultMidPanel() {
        midPanel = new JPanel();
        // midPanel.setBackground(Color.GREEN);
    }

    private void defaultBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.blue);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    }

    @Override
    public String getTittle() {
        return "";
    }

    @Override
    public JPanel getTopPanel() {
        return topPanel;
    }

    @Override
    public JPanel getMidPanel() {
        return midPanel;
    }

    @Override
    public JPanel getBottomPanel() {
        return bottomPanel;
    }
}
