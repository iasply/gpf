package br.com.gpf.view.screen.complete;

import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.view.data.LoadData;
import br.com.gpf.view.screen.DefaultTemplateScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeScreen extends DefaultTemplateScreen {

    private static final String BUTTON_TEXT_ADD_TRANSACTION = "ADICIONAR TRANSAÇÃO";
    private static final String BUTTON_TEXT_TRANSACTION_HISTORY = "HISTÓRICO DE TRANSAÇÕES";
    private static final String BUTTON_TEXT_TRANSACTION_CATEGORIES = "TIPOS DE TRANSAÇÕES";
    private static final String BUTTON_TEXT_REPORTS = "RELATÓRIOS";
    private static final String BUTTON_TEXT_EXIT_ACCOUNT = "SAIR DA CONTA";
    private final JButton addTransactionButton;
    private final JButton transactionHistoryButton;
    private final JButton transactionTypesButton;
    private final JButton reportsButton;
    private final JButton exitAccountButton;


    public HomeScreen() {

        this.addTransactionButton = new JButton(BUTTON_TEXT_ADD_TRANSACTION);
        this.transactionHistoryButton = new JButton(BUTTON_TEXT_TRANSACTION_HISTORY);
        this.transactionTypesButton = new JButton(BUTTON_TEXT_TRANSACTION_CATEGORIES);
        this.reportsButton = new JButton(BUTTON_TEXT_REPORTS);
        this.exitAccountButton = new JButton(BUTTON_TEXT_EXIT_ACCOUNT);
    }


    @Override
    public void onload(LoadData loadData) {


        exitAccountButton.addActionListener(e -> {

            ServiceLocator.getInstance().setSession(null);
            SwingUtilities.invokeLater(() -> {
                GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.LOGIN), null);
            });

        });

        addTransactionButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.ADD_TRANSACTION), null);
        }));
        transactionTypesButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_TYPES), null);
        }));
        transactionHistoryButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_HISTORY), null);
        }));
        reportsButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.REPORTS), null);
        }));


    }

    @Override
    public void onSave() {
        // :)
    }

    @Override
    public String getTittle() {
        return ScreenEnum.HOME.getTitle();
    }

    @Override
    public JPanel getTopPanel() {

        super.topPanel.add(userNameLabel());

        return super.topPanel;
    }


    @Override
    public JPanel getMidPanel() {
        super.midPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        super.midPanel.add(addTransactionButton, gbc);

        gbc.gridx = 1;
        super.midPanel.add(transactionHistoryButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        super.midPanel.add(transactionTypesButton, gbc);

        gbc.gridx = 1;
        super.midPanel.add(reportsButton, gbc);

        return super.midPanel;
    }

    @Override
    public JPanel getBottomPanel() {
        super.bottomPanel.add(exitAccountButton);
        return super.bottomPanel;
    }


    public void setButtonTextAddTransactionListener(ActionListener listener) {
        addTransactionButton.addActionListener(listener);
    }

    public void setButtonTextTransactionHistoryListener(ActionListener listener) {
        transactionHistoryButton.addActionListener(listener);
    }

    public void setButtonTextTransactionTypesListener(ActionListener listener) {
        transactionTypesButton.addActionListener(listener);
    }

    public void setButtonTextReportsListener(ActionListener listener) {
        reportsButton.addActionListener(listener);
    }

    public void setButtonTextExitAccountListener(ActionListener listener) {
        exitAccountButton.addActionListener(listener);
    }

}
