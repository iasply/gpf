package br.com.gpf.controller;

import br.com.gpf.controller.screenController.HomeScreenController;
import br.com.gpf.controller.screenController.LoginScreenController;
import br.com.gpf.controller.screenController.NewAccountScreenController;
import br.com.gpf.controller.screenController.ReportScreenController;
import br.com.gpf.controller.screenController.TransactionAddScreenController;
import br.com.gpf.controller.screenController.TransactionHistoryScreenController;
import br.com.gpf.controller.screenController.TransactionTypesScreenController;
import br.com.gpf.view.data.LoadData;
import br.com.gpf.view.screen.*;
import br.com.gpf.view.screen.complete.Screen;
import br.com.gpf.view.screen.complete.ScreenEnum;

import javax.swing.*;
import java.awt.*;

public class GpfScreenControllerManager extends JFrame {
    private static final String FRAME_TITLE = "Gerenciador Pessoal de Finanças";
    private static final Integer FRAME_WIDTH = 900;
    private static final Integer FRAME_HEIGHT = 600;
    private static GpfScreenControllerManager instance;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel bottomPanel;
    private GridBagConstraints gbc;


    private GpfScreenControllerManager() {
        setTitle(FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setResizable(false);
    }

    public static GpfScreenControllerManager getInstance() {
        if (instance == null) {
            instance = new GpfScreenControllerManager();
        }
        return instance;
    }

    public void start() {
        loadLayout();
        changeScreen(loadScreenPanel(ScreenEnum.LOGIN), null);
        setVisible(true);
    }

    private void loadLayout() {
        Font font = new Font("Arial", Font.BOLD, 14);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("TitledBorder.font", font);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        UIManager.put("Button.font", buttonFont);

        UIManager.put("Button.foreground", Color.WHITE);

        UIManager.put("Button.background", new Color(0, 123, 255));

        UIManager.put("Button.border", BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2), BorderFactory.createEmptyBorder(10, 20, 10, 20)));


        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        topPanel = new JPanel();
        gbc.gridy = 0;
        gbc.weighty = 100;
        add(topPanel, gbc);
        topPanel.setBackground(Color.red);


        midPanel = new JPanel();
        gbc.gridy = 1;
        gbc.weighty = 550;
        add(midPanel, gbc);
        midPanel.setBackground(Color.GREEN);

        bottomPanel = new JPanel();
        gbc.gridy = 2;
        gbc.weighty = 50;
        add(bottomPanel, gbc);
        bottomPanel.setBackground(Color.blue);

    }


    public Screen loadScreenPanel(ScreenEnum screenEnum) {
        switch (screenEnum) {
            case ScreenEnum.LOGIN -> {
                return new LoginScreenController().getScreen();
            }
            case ScreenEnum.NEW_ACCOUNT -> {
                return new NewAccountScreenController().getScreen();
            }
            case ScreenEnum.HOME -> {
                return new HomeScreenController().getScreen();
            }
            case ScreenEnum.TRANSACTION_TYPES -> {
                return new TransactionTypesScreenController().getScreen();
            }
            case ScreenEnum.ADD_TRANSACTION -> {
                return new TransactionAddScreenController().getScreen();
            }
            case TRANSACTION_HISTORY -> {
                return new TransactionHistoryScreenController().getScreen();
            }
            case REPORTS -> {
                return new ReportScreenController().getScreen();
            }
        }
        return null;
    }

    public void changeScreen(Screen screen, LoadData loadData) {
        if (screen == null) {
            return;
        }
        screen.onload(loadData);

        remove(topPanel);
        remove(midPanel);
        remove(bottomPanel);

        topPanel = screen.getTopPanel();
        midPanel = screen.getMidPanel();
        bottomPanel = screen.getBottomPanel();

        render(topPanel, midPanel, bottomPanel);
    }

    private void render(JPanel top, JPanel mid, JPanel bottom) {

        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;


        gbc.gridy = 0;
        gbc.weighty = 100;
        add(top, gbc);


        gbc.gridy = 1;
        gbc.weighty = 550;
        add(mid, gbc);


        gbc.gridy = 2;
        gbc.weighty = 50;
        add(bottom, gbc);

        revalidate();
        repaint();


    }


}

