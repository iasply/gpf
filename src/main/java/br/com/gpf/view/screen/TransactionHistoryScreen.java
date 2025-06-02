package br.com.gpf.view.screen;

import br.com.gpf.controller.DataEnum;
import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.controller.RequestStatusEnum;
import br.com.gpf.controller.ResponseData;
import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.model.entity.TransactionModel;
import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.view.ConstValues;
import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.MessageDialogEnum;
import br.com.gpf.view.data.LoadData;
import br.com.gpf.view.screen.complete.FilterColumnEnum;
import br.com.gpf.view.screen.complete.ScreenEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import static br.com.gpf.view.ConstValues.DATE_FORMAT;
import static br.com.gpf.view.screen.complete.FilterColumnEnum.*;

public class TransactionHistoryScreen extends DefaultTemplateScreen {

    private static final String LABEL_FILTER = "Filtrar por Tipo:";
    private static final String LABEL_SEARCH = "Pesquisar:";
    private static final String LABEL_CLEAR_SELECTION = "Limpar Seleção";
    private final JComboBox<String> filterComboBox;
    private final JTextField searchField;
    private final JButton searchButton;
    private final JButton clearSelectionButton;
    private final Vector<String> columnNames;
    private final StringBuilder filterString;
    private JTable table;
    private LoadData loadData;
    private List<TransactionModel> transactionModels;
    private List<TransactionTypesModel> transactionTypesModels;

    public TransactionHistoryScreen() {
        this.searchField = new JTextField(15);
        this.searchButton = new JButton("Filtrar");
        this.clearSelectionButton = new JButton(LABEL_CLEAR_SELECTION);

        columnNames = new Vector<>();
        columnNames.add(CLASSIFICATION.getName());
        columnNames.add(VALUE.getName());
        columnNames.add(TYPE.getName());
        columnNames.add(DESCRIPTION.getName());
        columnNames.add(DATE.getName());
        filterString = new StringBuilder();
        filterString.append("/ FILTROS?");
        this.filterComboBox = new JComboBox<>(columnNames);
    }

    public void setSearchButtonListener(ActionListener lickListener) {
        searchButton.addActionListener(lickListener);
    }

    public LoadData getLoadData() {
        return loadData;
    }

    public void setClearSelectionButtonListener(ActionListener lickListener) {
        clearSelectionButton.addActionListener(lickListener);
    }

    public void setHistory(List<TransactionModel> transactionModels, List<TransactionTypesModel> transactionTypesModels) {
        this.transactionModels = transactionModels;
        this.transactionTypesModels = transactionTypesModels;
    }


    @Override
    public void onload(LoadData loadData) {
        this.loadData = loadData;

        loadTransactions(loadData);
    }

    private void loadTransactions(LoadData loadData) {
        Vector<Vector<String>> data = new Vector<>();
        if (loadData != null) {
            String classificationFilter = DataEnum.decodeString(DataEnum.FILTER_CLASSIFICATION, loadData.getMapData().get(DataEnum.FILTER_CLASSIFICATION));
            if (classificationFilter != null) {
                filterString.append(" ");
                filterString.append(CLASSIFICATION.getName());
                filterString.append("=");
                filterString.append(classificationFilter);
                transactionModels = transactionModels.stream()
                        .filter(transaction -> {
                            Integer transactionClassification = transaction.getTransactionClassification();
                            Integer classificationValue = null;

                            if (ConstValues.LABEL_INCOME.equals(classificationFilter)) {
                                classificationValue = ConstValues.CONST_INCOME;
                            } else if (ConstValues.LABEL_EXPENSE.equals(classificationFilter)) {
                                classificationValue = ConstValues.CONST_EXPENSE;
                            }

                            return transactionClassification != null && transactionClassification.equals(classificationValue);
                        })
                        .collect(Collectors.toList());
            }

            String valueFilter = DataEnum.decodeString(DataEnum.FILTER_VALUE, loadData.getMapData().get(DataEnum.FILTER_VALUE));
            if (valueFilter != null) {
                filterString.append(" ");
                filterString.append(VALUE.getName());
                filterString.append("=");
                filterString.append(valueFilter);
                transactionModels = transactionModels.stream()
                        .filter(transaction -> transaction.getValue().toString().equals(valueFilter))
                        .collect(Collectors.toList());
            }

            String typeFilter = DataEnum.decodeString(DataEnum.FILTER_TYPE, loadData.getMapData().get(DataEnum.FILTER_TYPE));
            if (typeFilter != null) {
                filterString.append(" ");
                filterString.append(TYPE.getName());
                filterString.append("=");
                filterString.append(typeFilter);
                transactionModels = transactionModels.stream()
                        .filter(transaction -> {
                            TransactionTypesModel typeModel = transactionTypesModels.stream()
                                    .filter(type -> type.getId().equals(transaction.getTransactionType().getId()))
                                    .findFirst()
                                    .orElse(null);
                            return typeModel != null && typeModel.getDesc().equals(typeFilter);
                        })
                        .collect(Collectors.toList());
            }

            String descriptionFilter = DataEnum.decodeString(DataEnum.FILTER_DESCRIPTION, loadData.getMapData().get(DataEnum.FILTER_DESCRIPTION));
            if (descriptionFilter != null) {
                filterString.append(" ");
                filterString.append(DESCRIPTION.getName());
                filterString.append("=");
                filterString.append(descriptionFilter);
                transactionModels = transactionModels.stream()
                        .filter(transaction -> transaction.getDescriptionText() != null && transaction.getDescriptionText().contains(descriptionFilter))
                        .collect(Collectors.toList());
            }

            String dateFilter = DataEnum.decodeString(DataEnum.FILTER_DATE, loadData.getMapData().get(DataEnum.FILTER_DATE));
            if (dateFilter != null) {
                filterString.append(" ");
                filterString.append(DATE.getName());
                filterString.append("=");
                filterString.append(dateFilter);
                transactionModels = transactionModels.stream()
                        .filter(transaction -> {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                            return dateFormat.format(transaction.getDate()).equals(dateFilter);
                        })
                        .collect(Collectors.toList());
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        for (TransactionModel transaction : transactionModels) {
            Vector<String> row = new Vector<>();
            row.add(transaction.getTransactionClassification().equals(ConstValues.CONST_INCOME) ? ConstValues.LABEL_INCOME : ConstValues.LABEL_EXPENSE);
            row.add(transaction.getValue().toString());

            TransactionTypesModel typeModel = transactionTypesModels.stream()
                    .filter(type -> type.getId().equals(transaction.getTransactionType().getId()))
                    .findFirst()
                    .orElse(null);
            row.add(typeModel != null ? typeModel.getDesc() : "");
            row.add(transaction.getDescriptionText() != null ? transaction.getDescriptionText() : "");
            row.add(transaction.getDate() != null ? dateFormat.format(transaction.getDate()) : "");

            data.add(row);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);

        this.table.revalidate();
        this.table.repaint();

    }

    @Override
    public JPanel getTopPanel() {
        super.topPanel.add(userNameLabel());
        super.topPanel.add(new JLabel(filterString.toString()));
        return super.topPanel;
    }

    @Override
    public JPanel getMidPanel() {
        super.midPanel.setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel(LABEL_FILTER));
        filterPanel.add(filterComboBox);
        filterPanel.add(new JLabel(LABEL_SEARCH));
        filterPanel.add(searchField);
        filterPanel.add(searchButton);
        filterPanel.add(clearSelectionButton);

        super.midPanel.add(filterPanel, BorderLayout.NORTH);
        super.midPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        return super.midPanel;
    }

    public String getSearchText() {
        return searchField.getText().trim();
    }

    public String getSelectedFilter() {
        return (String) filterComboBox.getSelectedItem();
    }

    @Override
    public void onSave() throws DefaultScreenException {


    }

    @Override
    public String getTittle() {
        return ScreenEnum.TRANSACTION_HISTORY.getTitle();
    }

    @Override
    public JPanel getBottomPanel() {
        super.bottomPanel.add(super.defaultTransactionTypesButton());
        super.bottomPanel.add(super.defaultReportsButton());
        super.bottomPanel.add(super.defaultAddTransactionButton());
        super.bottomPanel.add(super.defaultHomeButton());
        return super.bottomPanel;
    }

    public boolean validateDateFormat(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
