package br.com.gpf.view.screen;

import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.view.screen.complete.Screen;
import br.com.gpf.view.screen.complete.ScreenEnum;

import javax.swing.*;
import java.awt.*;

public abstract class DefaultTemplateScreen implements Screen {
    protected JPanel topPanel;
    protected JPanel midPanel;
    protected JPanel bottomPanel;


    protected DefaultTemplateScreen() {
        defaultTopPanel();
        defaultMidPanel();
        defaultBottomPanel();
    }


    protected JButton defaultHomeButton() {
        JButton homeButton = new JButton(ScreenEnum.HOME.getTitle());
        homeButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.HOME), null);
        }));
        return homeButton;
    }

    protected JButton defaultLoginButton() {
        JButton loginButton = new JButton(ScreenEnum.LOGIN.getTitle());
        loginButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.LOGIN), null);
        }));
        return loginButton;
    }

    protected JButton defaultNewAccountButton() {
        JButton newAccountButton = new JButton(ScreenEnum.NEW_ACCOUNT.getTitle());
        newAccountButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.NEW_ACCOUNT), null);
        }));
        return newAccountButton;
    }

    protected JButton defaultAddTransactionButton() {
        JButton addTransactionButton = new JButton(ScreenEnum.ADD_TRANSACTION.getTitle());
        addTransactionButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.ADD_TRANSACTION), null);
        }));
        return addTransactionButton;
    }

    protected JButton defaultTransactionHistoryButton() {
        JButton transactionHistoryButton = new JButton(ScreenEnum.TRANSACTION_HISTORY.getTitle());
        transactionHistoryButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_HISTORY), null);
        }));
        return transactionHistoryButton;
    }

    protected JButton defaultTransactionTypesButton() {
        JButton transactionTypesButton = new JButton(ScreenEnum.TRANSACTION_TYPES.getTitle());
        transactionTypesButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_TYPES), null);
        }));
        return transactionTypesButton;
    }

    protected JButton defaultReportsButton() {
        JButton reportsButton = new JButton(ScreenEnum.REPORTS.getTitle());
        reportsButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.REPORTS), null);
        }));
        return reportsButton;
    }

    protected JLabel userNameLabel() {
        JLabel accountName = new JLabel();
        accountName.setText("/ USUARIO " + ServiceLocator.getInstance().getSession().userName());
        return accountName;
    }

    private void defaultTopPanel() {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.add(new JLabel(getTittle()));
    }

    private void defaultMidPanel() {
        midPanel = new JPanel();
    }

    private void defaultBottomPanel() {
        bottomPanel = new JPanel();
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
