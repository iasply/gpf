package br.com.gpf.view.screen.complete;

import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.view.data.LoadData;
import br.com.gpf.view.screen.DefaultTemplateScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

public class TransactionTypesScreen extends DefaultTemplateScreen {

    private static final String BUTTON_TEXT_REGISTER = "CADASTRAR";
    private static final String LABEL_CREATE_TYPE = "Criar Tipo";
    private static final String LABEL_NEW_TYPE = "Novo Tipo:";

    private final JLabel accountNameLabel;
    private final JTextField newTransactionTypeDescField;
    private final JButton registerTransactionTypeButton;
    private JTable table;
    private final JLabel labelCreateType;
    private final JLabel labelNewType;

    private ActionListener editButtonListener;
    private ActionListener deleteButtonListener;

    public TransactionTypesScreen() {
        this.accountNameLabel = new JLabel();
        this.newTransactionTypeDescField = new JTextField();
        this.registerTransactionTypeButton = new JButton(BUTTON_TEXT_REGISTER);
        this.labelCreateType = new JLabel(LABEL_CREATE_TYPE);
        this.labelNewType = new JLabel(LABEL_NEW_TYPE);
    }

    public void setRegisterTransactionTypeButtonListener(ActionListener listener) {
        registerTransactionTypeButton.addActionListener(listener);
    }

    public void setEditButtonListener(ActionListener listener) {
        this.editButtonListener = listener;
    }

    public void setDeleteButtonListener(ActionListener listener) {
        this.deleteButtonListener = listener;
    }

    public String getNewTransactionTypeDescFieldText() {
        return newTransactionTypeDescField.getText();
    }

    public void setNewTransactionTypeDescFieldText(String text) {
        newTransactionTypeDescField.setText(text);
    }


    @Override
    public void onload(LoadData loadData) {
        accountNameLabel.setText("Conta: " + ServiceLocator.getInstance().getSession().userName());
    }

    @Override
    public void onSave() {

    }

    @Override
    public String getTittle() {
        return ScreenEnum.TRANSACTION_TYPES.getTitle();
    }

    @Override
    public JPanel getTopPanel() {
        super.topPanel.add(accountNameLabel);
        return super.topPanel;
    }

    @Override
    public JPanel getMidPanel() {
        JPanel midPanel = new JPanel(new BorderLayout(20, 0));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        if (table != null) {
            tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        } else {
            tablePanel.add(new JScrollPane(new JTable()), BorderLayout.CENTER);
        }


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(350, formPanel.getPreferredSize().height));
        formPanel.setMaximumSize(new Dimension(350, Integer.MAX_VALUE));

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 0.3;

        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formGbc.gridwidth = 1;
        formPanel.add(labelCreateType, formGbc);

        formGbc.gridy = 1;
        formGbc.gridwidth = 1;
        formPanel.add(labelNewType, formGbc);

        formGbc.gridx = 1;
        formGbc.weightx = 0.7;
        newTransactionTypeDescField.setMinimumSize(new Dimension(200, 25));
        formPanel.add(newTransactionTypeDescField, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formGbc.gridwidth = 2;
        formPanel.add(registerTransactionTypeButton, formGbc);

        midPanel.add(tablePanel, BorderLayout.CENTER);
        midPanel.add(formPanel, BorderLayout.EAST);

        return midPanel;
    }

    @Override
    public JPanel getBottomPanel() {
        super.bottomPanel.add(super.defaultAddTransactionButton());
        super.bottomPanel.add(super.defaultTransactionHistoryButton());
        super.bottomPanel.add(super.defaultReportsButton());
        super.bottomPanel.add(super.defaultHomeButton());
        return super.bottomPanel;
    }

    public void loadTransactionTypesTable(DefaultTableModel model) {
        this.table = new JTable(model);
        if (model.getRowCount() > 0) {

            addButtonToColumn(table, 2, "EDITAR", editButtonListener);
            addButtonToColumn(table, 3, "EXCLUIR", deleteButtonListener);

            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(0).setWidth(0);
            table.getColumnModel().getColumn(0).setPreferredWidth(0);
        }

    }

    private void addButtonToColumn(JTable table, int columnIndex, String buttonText, ActionListener listener) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setCellRenderer(new ButtonRenderer(buttonText));
        column.setCellEditor(new ButtonEditor(new JCheckBox(), buttonText, columnIndex, listener, table));
    }


    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setFocusable(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private int clickedRow;
        private int clickedColumn;
        private final ActionListener delegateListener;
        private final JTable table;

        public ButtonEditor(JCheckBox checkBox, String buttonText, int columnIndex, ActionListener listener, JTable table) {
            super(checkBox);
            this.button = new JButton(buttonText);
            this.delegateListener = listener;
            this.table = table;
            this.button.setFocusPainted(false);
            this.button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            clickedRow = row;
            clickedColumn = column;
            return button;
        }

        @Override
        public Object getCellEditorValue() {

            if (delegateListener != null) {
                delegateListener.actionPerformed(new ActionEvent(table, ActionEvent.ACTION_PERFORMED, clickedColumn == 2 ? "EDIT_BUTTON_CLICKED" : "DELETE_BUTTON_CLICKED", clickedRow, clickedColumn));
            }
            return button.getText();
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            return true;
        }
    }
}