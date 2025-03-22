package br.com.gpf.view.screen;

import javax.swing.*;
import java.awt.*;

public abstract class DefaultTemplateScreen {
    protected JPanel topPanel;
    protected JPanel midPanel;
    protected JPanel bottomPanel;

    public DefaultTemplateScreen() {
        getTopPanel();
        getMidPanel();
        getBottomPanel();
    }

    private void getTopPanel() {
        topPanel = new JPanel();
        topPanel.setBackground(Color.red);
    }

    private void getMidPanel() {
        midPanel = new JPanel();
       // midPanel.setBackground(Color.GREEN);
    }

    private void getBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.blue);
    }


}
