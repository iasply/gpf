package br.com.gpf.view.screen;

import br.com.gpf.repository.model.TransactionTypesModel;
import br.com.gpf.service.Controller;
import br.com.gpf.service.DataEnum;
import br.com.gpf.service.RequestStatusEnum;
import br.com.gpf.service.ResponseData;
import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.GpfScreen;
import br.com.gpf.view.LoadData;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

import static br.com.gpf.view.ConstValues.*;

public class TransactionAddScreen extends DefaultTemplateScreen {

    private static final String LABEL_CREATE_TRANSACTION = "Create Transaction";
    private static final String LABEL_TYPE = "Type:";
    private static final String LABEL_VALUE = "Value:";
    private static final String LABEL_DATE = "Date:";
    private static final String LABEL_DESCRIPTION = "Description:";

    private static final String BUTTON_LABEL_SAVE = "Save";
    private static final String BUTTON_LABEL_TRANSACTION_HISTORY = "TRANSACTION HISTORY";
    private static final String BUTTON_LABEL_REPORTS = "REPORTS";

    private final JTextField valueField;
    private final JTextArea descriptionField;
    private final JRadioButton incomeRadioButton;
    private final JRadioButton expenseRadioButton;
    private final ButtonGroup transactionTypeGroup;
    private JComboBox<TransactionTypesModel> typeComboBox;
    private final JSpinner dateSpinner;
    private final JButton saveButton;
    private final JButton transactionHistoryButton;
    private final JButton reportsButton;

    private final JLabel createTransactionLabel;
    private final JLabel typeLabel;
    private final JLabel valueLabel;
    private final JLabel dateLabel;
    private final JLabel descriptionLabel;


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

        this.transactionHistoryButton = new JButton(BUTTON_LABEL_TRANSACTION_HISTORY);
        this.reportsButton = new JButton(BUTTON_LABEL_REPORTS);
    }

    @Override
    public void onload(LoadData loadData) {

        ResponseData userTypesResponseData = Controller.getInstance().getTransactionTypeService().getUserTypes(Controller.getInstance().getSession().id());
        if (userTypesResponseData.getValue() != RequestStatusEnum.SUCCESS) {
            MessageDialogEnum.ERROR.showMsg(DataEnum.decodeString(DataEnum.ERROR_MSG, userTypesResponseData.getMapData().get(DataEnum.ERROR_MSG)), null);
            SwingUtilities.invokeLater(() -> {
                GpfScreen instance = GpfScreen.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.HOME), null);
            });
            return;
        }
        List<TransactionTypesModel> transactionTypes = DataEnum.decodeTransactionTypes(DataEnum.USER_TYPES, userTypesResponseData.getMapData().get(DataEnum.USER_TYPES));

        TransactionTypesModel[] transactionTypesArray = transactionTypes.toArray(new TransactionTypesModel[0]);

        this.typeComboBox = new JComboBox<>(transactionTypesArray);

        saveButton.addActionListener(e -> {
            try {
                onSave();
                Integer transactionCategory = incomeRadioButton.isSelected() ? CONST_INCOME : CONST_EXPENSE;
                String valueText = valueField.getText();
                String descriptionText = descriptionField.getText().trim();
                Date selectedDate = (Date) dateSpinner.getValue();
                TransactionTypesModel transactionType = (TransactionTypesModel) typeComboBox.getSelectedItem();
                Double value = Double.parseDouble(valueText);
                ResponseData responseData = Controller.getInstance().getTransactionService().createTransaction(
                        value, transactionCategory, transactionType.getId(), selectedDate, descriptionText, Controller.getInstance().getSession().id());

                if (responseData.getValue() == RequestStatusEnum.SUCCESS) {
                    MessageDialogEnum.SUCCESS.showMsg("Transaction saved successfully!", null);

                    SwingUtilities.invokeLater(() -> {
                        GpfScreen instance = GpfScreen.getInstance();
                        instance.changeScreen(instance.loadScreenPanel(ScreenEnum.ADD_TRANSACTION), null);
                    });
                    return;
                }

                MessageDialogEnum.ERROR.showMsg("Failed to save the transaction: " + DataEnum.decodeString(DataEnum.ERROR_MSG, responseData.getMapData().get(DataEnum.ERROR_MSG)), null);

            } catch (DefaultScreenException ex) {
                MessageDialogEnum.ERROR.showMsg(ex.getMessage(), null);
            }

        });


        transactionHistoryButton.addActionListener(e -> {

            SwingUtilities.invokeLater(() -> {
                GpfScreen instance = GpfScreen.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_HISTORY), null);
            });
        });

        reportsButton.addActionListener(e ->{
            SwingUtilities.invokeLater(() -> {
                GpfScreen instance = GpfScreen.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.REPORTS), null);
            });
        });
    }

    @Override
    public void onSave() throws DefaultScreenException {
        String valueText = valueField.getText();
        String descriptionText = descriptionField.getText();
        Date selectedDate = (Date) dateSpinner.getValue();
        TransactionTypesModel transactionType = (TransactionTypesModel) typeComboBox.getSelectedItem();

        if (valueText.isEmpty() || selectedDate == null || transactionType == null || (!incomeRadioButton.isSelected() && !expenseRadioButton.isSelected())) {
            throw new DefaultScreenException("Please fill in all the required fields.");
        }

        try {
           Double.parseDouble(valueText);
        } catch (NumberFormatException ex) {
            throw new DefaultScreenException("Invalid value. Please enter a numeric value.");
        }
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

        super.bottomPanel.add(transactionHistoryButton);
        super.bottomPanel.add(reportsButton);
        super.bottomPanel.add(super.defaultHomeButton());

        return super.bottomPanel;
    }

}
