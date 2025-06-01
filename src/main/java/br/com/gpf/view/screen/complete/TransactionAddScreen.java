package br.com.gpf.view.screen.complete;

import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.view.*;
import br.com.gpf.view.data.LoadData;
import br.com.gpf.view.screen.DefaultTemplateScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import static br.com.gpf.view.ConstValues.*;

public class TransactionAddScreen extends DefaultTemplateScreen {

    private static final String LABEL_CREATE_TRANSACTION = "Criar Transação";
    private static final String LABEL_TYPE = "Tipo:";
    private static final String LABEL_VALUE = "Valor:";
    private static final String LABEL_DATE = "Data:";
    private static final String LABEL_DESCRIPTION = "Descrição:";
    private static final String BUTTON_LABEL_SAVE = "Salvar";


    private final JTextField valueField;
    private final JTextArea descriptionField;
    private final JRadioButton incomeRadioButton;
    private final JRadioButton expenseRadioButton;
    private final ButtonGroup transactionTypeGroup;
    private final JSpinner dateSpinner;
    private final JButton saveButton;
    private final JLabel createTransactionLabel;
    private final JLabel typeLabel;
    private final JLabel valueLabel;
    private final JLabel dateLabel;
    private final JLabel descriptionLabel;
    private JComboBox<TransactionTypesModel> typeComboBox;


    public TransactionAddScreen() {
        this.createTransactionLabel = new JLabel(LABEL_CREATE_TRANSACTION);
        this.typeLabel = new JLabel(LABEL_TYPE);
        this.valueLabel = new JLabel(LABEL_VALUE);
        this.dateLabel = new JLabel(LABEL_DATE);
        this.descriptionLabel = new JLabel(LABEL_DESCRIPTION);

        this.valueField = new JTextField();
        this.descriptionField = new JTextArea(3, 20);
        this.descriptionField.setLineWrap(true);

        this.incomeRadioButton = new JRadioButton(LABEL_INCOME);
        this.expenseRadioButton = new JRadioButton(LABEL_EXPENSE);

        this.transactionTypeGroup = new ButtonGroup();
        this.transactionTypeGroup.add(incomeRadioButton);
        this.transactionTypeGroup.add(expenseRadioButton);


        this.dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, DATE_FORMAT);
        dateSpinner.setEditor(editor);

        this.saveButton = new JButton(BUTTON_LABEL_SAVE);

    }

    @Override
    public void onload(LoadData loadData) {



    }

    public String getValueText() {
        return valueField.getText();
    }
    public String getDescriptionText() {
        return descriptionField.getText();
    }
    public Date getSelectedDate() {
        return (Date) dateSpinner.getValue();
    }
    public TransactionTypesModel getTransactionType() {
        return (TransactionTypesModel) typeComboBox.getSelectedItem();
    }
    public boolean isIncome() {
        return incomeRadioButton.isSelected();
    }
    public boolean isExpense() {
        return expenseRadioButton.isSelected();
    }
    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void loadTransactionType(   List<TransactionTypesModel>  transactionTypes) {
        TransactionTypesModel[] transactionTypesArray = transactionTypes.toArray(new TransactionTypesModel[0]);
        this.typeComboBox = new JComboBox<>(transactionTypesArray);
    }

    @Override
    public void onSave() throws DefaultScreenException {

    }

    @Override
    public String getTittle() {
        return ScreenEnum.ADD_TRANSACTION.getTitle();
    }

    @Override
    public JPanel getTopPanel() {
        super.topPanel.add(userNameLabel());
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
        gbc.gridwidth = 2;
        super.midPanel.add(createTransactionLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        super.midPanel.add(typeLabel, gbc);

        gbc.gridx = 1;
        super.midPanel.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        super.midPanel.add(incomeRadioButton, gbc);

        gbc.gridx = 1;
        super.midPanel.add(expenseRadioButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        super.midPanel.add(valueLabel, gbc);

        gbc.gridx = 1;
        super.midPanel.add(valueField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        super.midPanel.add(dateLabel, gbc);

        gbc.gridx = 1;
        super.midPanel.add(dateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        super.midPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        super.midPanel.add(new JScrollPane(descriptionField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        super.midPanel.add(saveButton, gbc);

        return super.midPanel;
    }

    @Override
    public JPanel getBottomPanel() {
        super.bottomPanel.add(super.defaultTransactionTypesButton());
        super.bottomPanel.add(super.defaultReportsButton());
        super.bottomPanel.add(super.defaultTransactionHistoryButton());
        super.bottomPanel.add(super.defaultHomeButton());

        return super.bottomPanel;
    }







}
