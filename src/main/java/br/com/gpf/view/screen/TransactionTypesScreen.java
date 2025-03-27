package br.com.gpf.view.screen;

import br.com.gpf.repository.model.TransactionTypesModel;
import br.com.gpf.service.Controller;
import br.com.gpf.service.ResponseData;
import br.com.gpf.service.RequestStatusEnum;
import br.com.gpf.service.DataEnum;
import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.GpfScreen;
import br.com.gpf.view.LoadData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class TransactionTypesScreen extends DefaultTemplateScreen {

    private JTable table;
    private final JLabel accountNameLabel;
    private final JTextField newTransactionTypeDescField;
    private final JButton registerTransactionTypeButton;
    private JLabel labelCreateType;
    private JLabel labelNewType;

    private static final String BUTTON_TEXT_REGISTER = "REGISTER";
    private static final String LABEL_CREATE_TYPE = "Create Type";
    private static final String LABEL_NEW_TYPE = "New Type:";



    public TransactionTypesScreen() {
        this.accountNameLabel = new JLabel();
        this.newTransactionTypeDescField = new JTextField();
        this.registerTransactionTypeButton = new JButton(BUTTON_TEXT_REGISTER);
        this.labelCreateType = new JLabel(LABEL_CREATE_TYPE);
        this.labelNewType = new JLabel(LABEL_NEW_TYPE);
    }

    @Override
    public void onload(LoadData loadData) {
        accountNameLabel.setText("Account: " + Controller.getInstance().getSession().userName());

        loadTransactionTypes();

        registerTransactionTypeButton.addActionListener(e -> {
            String newDesc = newTransactionTypeDescField.getText();
            if (newDesc.isEmpty()) {
                MessageDialogEnum.ERROR.showMsg("Please provide a description for the new transaction type.", null);
                return;
            }
            ResponseData responseData = Controller.getInstance().getTransactionTypeService().createType(
                    Controller.getInstance().getSession().id(), newDesc);

            if (responseData.getValue() == RequestStatusEnum.SUCCESS) {
                reload();
                return;
            }

            MessageDialogEnum.ERROR.showMsg("Failed to add the transaction type: " + responseData.getMapData().get(DataEnum.ERROR_MSG), null);
        });


    }

    @Override
    public void onSave() throws DefaultScreenException {
        // No save logic needed here for now
    }

    @Override
    public String getTittle() {
        return ScreenEnum.TRANSACTION_TYPES.getTitle();
    }

    @Override
    public JPanel getTopPanel() {
        super.topPanel.add(userNameLabel());
        return super.topPanel;
    }

    @Override
    public JPanel getMidPanel() {
        JPanel midPanel = new JPanel(new BorderLayout(20, 0));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

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

        super.bottomPanel.add(super.defaultHomeButton());

        return super.bottomPanel;

    }

    private void loadTransactionTypes() {
        ResponseData responseData = Controller.getInstance().getTransactionTypeService().getUserTypes(
                Controller.getInstance().getSession().id());


        if (responseData.getValue() == RequestStatusEnum.SUCCESS) {
            Vector<Vector<String>> data = new Vector<>();
            Vector<String> columnNames = new Vector<>();
            columnNames.add("ID");
            columnNames.add("TYPE");
            columnNames.add("EDIT");
            columnNames.add("DELETE");

            List<TransactionTypesModel> transactionTypes = DataEnum.decodeTransactionTypes(DataEnum.USER_TYPES, responseData.getMapData().get(DataEnum.USER_TYPES));

            for (TransactionTypesModel t : transactionTypes) {
                Vector<String> row = new Vector<>();
                row.add(t.getId().toString());
                row.add(t.getDesc());
                row.add("");
                row.add("");
                data.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 3 || column == 2;
                }
            };

            this.table = new JTable(model);
            if (!transactionTypes.isEmpty()) {
                addButtonToColumn(table, 2, "EDIT");
                addButtonToColumn(table, 3, "DELETE");

                table.getColumnModel().getColumn(0).setMinWidth(0);
                table.getColumnModel().getColumn(0).setMaxWidth(0);
                table.getColumnModel().getColumn(0).setWidth(0);
                table.getColumnModel().getColumn(0).setPreferredWidth(0);
            }
            return;

        }

        MessageDialogEnum.ERROR.showMsg("Failed to load transaction types: " + DataEnum.decodeString(DataEnum.ERROR_MSG, responseData.getMapData().get(DataEnum.ERROR_MSG)), null);
        SwingUtilities.invokeLater(() -> {
            GpfScreen instance = GpfScreen.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.HOME), null);
        });
    }


    private static void addButtonToColumn(JTable table, int columnIndex, String buttonText) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setCellRenderer(new ButtonRenderer(buttonText));
        column.setCellEditor(new ButtonEditor(new JCheckBox(), buttonText, columnIndex));
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
        private final String buttonText;
        private final int columnIndex;


        public ButtonEditor(JCheckBox checkBox, String buttonText, int columnIndex) {
            super(checkBox);
            this.buttonText = buttonText;
            this.columnIndex = columnIndex;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JButton button = new JButton(buttonText);

            button.addActionListener(e -> {
                if (columnIndex == 2) {
                    String id = table.getValueAt(row, 0).toString();
                    String desc = table.getValueAt(row, 1).toString();

                    boolean isConfirmed = MessageDialogEnum.YES_OR_NOT.showMsg("Are you sure you want to edit " + desc + "?", table);
                    if (isConfirmed) {
                        String userInput = JOptionPane.showInputDialog(null, "Please enter the new type description:", "Input Required", JOptionPane.QUESTION_MESSAGE);

                        if (userInput != null && !userInput.isEmpty()) {
                            Controller.getInstance().getTransactionTypeService().alterType(Controller.getInstance().getSession().id(), Integer.parseInt(id), userInput);
                            reload();
                            return;
                        }
                        MessageDialogEnum.ERROR.showMsg("Input invalid or operation cancelled", null);
                    }

                } else if (columnIndex == 3) {
                    String id = table.getValueAt(row, 0).toString();
                    String desc = table.getValueAt(row, 1).toString();
                    boolean isConfirmed = MessageDialogEnum.YES_OR_NOT.showMsg("Are you sure you want to delete " + desc + "?", table);
                    if (isConfirmed) {
                        Controller.getInstance().getTransactionTypeService().deleteType(Controller.getInstance().getSession().id(), Integer.parseInt(id));
                        reload();
                    }
                }
            });

            return button;
        }


    }

    private static void reload() {
        SwingUtilities.invokeLater(() -> {
            GpfScreen instance = GpfScreen.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_TYPES), null);
        });
    }

}
