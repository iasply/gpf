package br.com.gpf.view.screen;

import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.controller.Controller;
import br.com.gpf.controller.DataEnum;
import br.com.gpf.controller.RequestStatusEnum;
import br.com.gpf.controller.ResponseData;
import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.GpfScreenManager;
import br.com.gpf.view.LoadData;
import br.com.gpf.view.MessageDialogEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

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


    public TransactionTypesScreen() {
        this.accountNameLabel = new JLabel();
        this.newTransactionTypeDescField = new JTextField();
        this.registerTransactionTypeButton = new JButton(BUTTON_TEXT_REGISTER);
        this.labelCreateType = new JLabel(LABEL_CREATE_TYPE);
        this.labelNewType = new JLabel(LABEL_NEW_TYPE);
    }

    private static void addButtonToColumn(JTable table, int columnIndex, String buttonText) {
        TableColumn column = table.getColumnModel().getColumn(columnIndex);
        column.setCellRenderer(new ButtonRenderer(buttonText));
        column.setCellEditor(new ButtonEditor(new JCheckBox(), buttonText, columnIndex));
    }

    private static void reload() {
        SwingUtilities.invokeLater(() -> {
            GpfScreenManager instance = GpfScreenManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_TYPES), null);
        });
    }

    @Override
    public void onload(LoadData loadData) {
        accountNameLabel.setText("Conta: " + Controller.getInstance().getSession().userName());

        loadTransactionTypes();

        registerTransactionTypeButton.addActionListener(e -> {
            String newDesc = newTransactionTypeDescField.getText();
            if (newDesc.isEmpty()) {
                MessageDialogEnum.ERROR.showMsg("Por favor, forneça uma descrição para o novo tipo de transação.", null);
                return;
            }

            ResponseData responseData = Controller.getInstance().getTransactionTypeService().createType(
                    Controller.getInstance().getSession().id(), newDesc);

            if (responseData.getValue() == RequestStatusEnum.SUCCESS) {
                reload();
                return;
            }

            MessageDialogEnum.ERROR.showMsg("Falha ao adicionar o tipo de transação: " + responseData.getMapData().get(DataEnum.ERROR_MSG), null);
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
        super.bottomPanel.add(super.defaultAddTransactionButton());
        super.bottomPanel.add(super.defaultTransactionHistoryButton());
        super.bottomPanel.add(super.defaultReportsButton());
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
            columnNames.add("TIPO");
            columnNames.add("EDITAR");
            columnNames.add("EXCLUIR");
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
                addButtonToColumn(table, 2, "EDITAR");
                addButtonToColumn(table, 3, "EXCLUIR");

                table.getColumnModel().getColumn(0).setMinWidth(0);
                table.getColumnModel().getColumn(0).setMaxWidth(0);
                table.getColumnModel().getColumn(0).setWidth(0);
                table.getColumnModel().getColumn(0).setPreferredWidth(0);
            }
            return;

        }

        MessageDialogEnum.ERROR.showMsg("Falha ao carregar os tipos de transação: " + DataEnum.decodeString(DataEnum.ERROR_MSG, responseData.getMapData().get(DataEnum.ERROR_MSG)), null);
        SwingUtilities.invokeLater(() -> {
            GpfScreenManager instance = GpfScreenManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.HOME), null);
        });
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
                    handleEditTransaction(table, row);
                } else if (columnIndex == 3) {
                    handleDeleteTransaction(table, row);
                }
            });

            return button;
        }

        private void handleEditTransaction(JTable table, int row) {
            String id = table.getValueAt(row, 0).toString();
            String desc = table.getValueAt(row, 1).toString();

            boolean isConfirmed = MessageDialogEnum.YES_OR_NOT.showMsg("Você tem certeza que deseja editar " + desc + "?", table);

            if (isConfirmed) {
                String userInput = JOptionPane.showInputDialog(null, "Por favor, insira a nova descrição do tipo:", "Entrada Necessária", JOptionPane.QUESTION_MESSAGE);

                if (userInput != null && !userInput.isEmpty()) {
                    Controller.getInstance().getTransactionTypeService().alterType(Controller.getInstance().getSession().id(), Integer.parseInt(id), userInput);
                    reload();
                } else {
                    MessageDialogEnum.ERROR.showMsg("Entrada inválida ou operação cancelada", null);
                }
            }
        }

        private void handleDeleteTransaction(JTable table, int row) {
            String desc = table.getValueAt(row, 1).toString();
            boolean isConfirmed = MessageDialogEnum.YES_OR_NOT.showMsg("Você tem certeza que deseja excluir " + desc + "?", table);

            if (isConfirmed) {
                ResponseData availableTransactionTypes = Controller.getInstance().getTransactionTypeService()
                        .getUserTypes(Controller.getInstance().getSession().id());
                List<TransactionTypesModel> transactionTypes = DataEnum.decodeTransactionTypes(DataEnum.USER_TYPES, availableTransactionTypes.getMapData().get(DataEnum.USER_TYPES));

                if (transactionTypes != null && !transactionTypes.isEmpty()) {
                    Vector<String> transactionTypeNames = getAvailableTransactionTypes(desc, transactionTypes);

                    JComboBox<String> typeSelectionComboBox = new JComboBox<>(transactionTypeNames);
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            typeSelectionComboBox,
                            "Escolha um novo tipo para as transações",
                            JOptionPane.OK_CANCEL_OPTION
                    );

                    if (result == JOptionPane.OK_OPTION) {
                        String selectedTypeName = (String) typeSelectionComboBox.getSelectedItem();
                        moveTransactionsAndDeleteType(desc, selectedTypeName, transactionTypes);
                        reload();
                    }
                }
            }
        }

        private Vector<String> getAvailableTransactionTypes(String desc, List<TransactionTypesModel> transactionTypes) {
            return transactionTypes.stream()
                    .filter(t -> !t.getDesc().equals(desc))
                    .map(TransactionTypesModel::getDesc)
                    .collect(Collectors.toCollection(Vector::new));
        }

        private void moveTransactionsAndDeleteType(String desc, String selectedTypeName, List<TransactionTypesModel> transactionTypes) {
            TransactionTypesModel oldType = findTransactionTypeByDesc(transactionTypes, desc);
            TransactionTypesModel newType = findTransactionTypeByDesc(transactionTypes, selectedTypeName);

            if (newType != null && oldType != null) {
                Controller.getInstance().getTransactionService().alterTransactionType(
                        Controller.getInstance().getSession().id(),
                        oldType.getId(),
                        newType.getId()
                );
                Controller.getInstance().getTransactionTypeService()
                        .deleteType(Controller.getInstance().getSession().id(), oldType.getId());
                reload();
            }
        }

        private TransactionTypesModel findTransactionTypeByDesc(List<TransactionTypesModel> transactionTypes, String desc) {
            return transactionTypes.stream()
                    .filter(t -> t.getDesc().equals(desc))
                    .findFirst()
                    .orElse(null);
        }
    }


}
