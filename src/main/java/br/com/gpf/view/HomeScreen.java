package br.com.gpf.view;

import br.com.gpf.service.Controller;
import br.com.gpf.view.screen.DefaultTemplateScreen;
import br.com.gpf.view.screen.Screen;
import br.com.gpf.view.screen.ScreenEnum;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends DefaultTemplateScreen {

    private final JLabel accountName;
    private final JButton addTransactionButton;
    private final JButton transactionHistoryButton;
    private final JButton transactionTypesButton;
    private final JButton reportsButton;
    private final JButton exitAccountButton;

    private static final String BUTTON_TEXT_ADD_TRANSACTION = "ADD TRANSACTION";
    private static final String BUTTON_TEXT_TRANSACTION_HISTORY = "TRANSACTION HISTORY";
    private static final String BUTTON_TEXT_TRANSACTION_CATEGORIES = "TRANSACTION TYPES";
    private static final String BUTTON_TEXT_REPORTS = "REPORTS";
    private static final String BUTTON_TEXT_EXIT_ACCOUNT = "EXIT ACCOUNT";


    public HomeScreen() {
        this.accountName = new JLabel();
        this.addTransactionButton = new JButton(BUTTON_TEXT_ADD_TRANSACTION);
        this.transactionHistoryButton = new JButton(BUTTON_TEXT_TRANSACTION_HISTORY);
        this.transactionTypesButton = new JButton(BUTTON_TEXT_TRANSACTION_CATEGORIES);
        this.reportsButton = new JButton(BUTTON_TEXT_REPORTS);
        this.exitAccountButton = new JButton(BUTTON_TEXT_EXIT_ACCOUNT);
    }


    @Override
    public void onload(LoadData loadData) {
        accountName.setText(Controller.getInstance().getSession().userName());

        exitAccountButton.addActionListener(e -> {

            Controller.getInstance().setSession(null);
            SwingUtilities.invokeLater(() -> {
                GpfScreen instance = GpfScreen.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.LOGIN), null);
            });

        });

        addTransactionButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreen instance = GpfScreen.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.ADD_TRANSACTION), null);
        }));
        transactionTypesButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreen instance = GpfScreen.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_TYPES), null);
        }));
        transactionHistoryButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreen instance = GpfScreen.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_HISTORY), null);
        }));
        reportsButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreen instance = GpfScreen.getInstance();
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
        super.topPanel.setLayout(new BorderLayout());

        super.topPanel.add(accountName, BorderLayout.CENTER);

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

        super.bottomPanel.add(exitAccountButton );

        return super.bottomPanel;
    }

}
