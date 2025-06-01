package br.com.gpf.view.screen.complete;

import br.com.gpf.model.entity.TransactionModel;
import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.controller.DataEnum;
import br.com.gpf.controller.RequestStatusEnum;
import br.com.gpf.controller.ResponseData;
import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.data.LoadData;
import br.com.gpf.view.screen.DefaultTemplateScreen;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.gpf.view.ConstValues.*;

public class ReportScreen extends DefaultTemplateScreen {
    private static final String LABEL_TOTAL_BALANCE = "Saldo Total: ";
    private static final String LABEL_TOTAL_INCOME = "Renda Total: ";
    private static final String LABEL_TOTAL_EXPENSES = "Despesas Totais: ";

    private final JLabel totalBalanceLabel;
    private final JLabel totalIncomeLabel;
    private final JLabel totalExpensesLabel;
    private final JTextField startDateField;
    private final JTextField endDateField;
    private final JButton generateReportButton;
    private final JButton last1MonthButton;
    private final JButton last3MonthsButton;
    private final JButton last6MonthsButton;
    private final JLabel expensesCategoriesLabel;
    private final JLabel incomeCategoriesLabel;

    public ReportScreen() {
        this.totalBalanceLabel = new JLabel(LABEL_TOTAL_BALANCE + "$0.00");
        this.totalIncomeLabel = new JLabel(LABEL_TOTAL_INCOME + "$0.00");
        this.totalExpensesLabel = new JLabel(LABEL_TOTAL_EXPENSES + "$0.00");
        this.startDateField = new JTextField(10);
        this.endDateField = new JTextField(10);
        this.generateReportButton = new JButton("Gerar Relatório");
        this.last1MonthButton = new JButton("Último 1 Mês");
        this.last3MonthsButton = new JButton("Últimos 3 Meses");
        this.last6MonthsButton = new JButton("Últimos 6 Meses");
        this.expensesCategoriesLabel = new JLabel();
        this.incomeCategoriesLabel = new JLabel();
    }

    @Override
    public void onload(LoadData loadData) {
        generateReportButton.addActionListener(e -> generateReport());
        last1MonthButton.addActionListener(e -> setDateRange(-1));
        last3MonthsButton.addActionListener(e -> setDateRange(-3));
        last6MonthsButton.addActionListener(e -> setDateRange(-6));
    }

    @Override
    public void onSave() throws DefaultScreenException {

    }

    @Override
    public String getTittle() {
        return ScreenEnum.REPORTS.getTitle();
    }

    @Override
    public JPanel getTopPanel() {
        super.topPanel.add(userNameLabel());
        return super.topPanel;
    }

    @Override
    public JPanel getMidPanel() {
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new BorderLayout(15, 15));
        midPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        datePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Selecionar Período", TitledBorder.LEFT, TitledBorder.TOP));
        datePanel.setBackground(new Color(240, 240, 240));
        datePanel.add(new JLabel("Data de Início " + DATE_FORMAT));
        datePanel.add(startDateField);
        datePanel.add(new JLabel("Data de Término " + DATE_FORMAT));
        datePanel.add(endDateField);

        JPanel resultPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Resumo do Relatório", TitledBorder.LEFT, TitledBorder.TOP));
        resultPanel.setBackground(new Color(245, 245, 245));
        resultPanel.add(totalBalanceLabel);
        resultPanel.add(totalIncomeLabel);
        resultPanel.add(totalExpensesLabel);

        JPanel categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));
        categoriesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Divisão de Categorias", TitledBorder.LEFT, TitledBorder.TOP));
        categoriesPanel.setBackground(new Color(245, 245, 245));
        categoriesPanel.setPreferredSize(new Dimension(350, 400));
        JScrollPane categoriesScrollPane = new JScrollPane(categoriesPanel);
        categoriesScrollPane.setPreferredSize(new Dimension(350, 400));

        JLabel incomeCategoriesTitle = new JLabel("Categorias de Renda:");
        incomeCategoriesLabel.setText("");
        JLabel expenseCategoriesTitle = new JLabel("Categorias de Despesa:");
        expensesCategoriesLabel.setText("");

        categoriesPanel.add(incomeCategoriesTitle);
        categoriesPanel.add(incomeCategoriesLabel);
        categoriesPanel.add(Box.createVerticalStrut(10));
        categoriesPanel.add(expenseCategoriesTitle);
        categoriesPanel.add(expensesCategoriesLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Filtros Rápidos", TitledBorder.LEFT, TitledBorder.TOP));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(last1MonthButton);
        buttonPanel.add(last3MonthsButton);
        buttonPanel.add(last6MonthsButton);
        buttonPanel.add(generateReportButton);

        midPanel.add(datePanel, BorderLayout.NORTH);
        midPanel.add(resultPanel, BorderLayout.CENTER);
        midPanel.add(categoriesPanel, BorderLayout.WEST);
        midPanel.add(buttonPanel, BorderLayout.SOUTH);

        return midPanel;
    }

    private void generateReport() {
        String startDate = startDateField.getText();
        String endDate = endDateField.getText();

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date start, end;
        try {
            start = getStartDate(dateFormat.parse(startDate));
            end = getEndDate(dateFormat.parse(endDate));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Formato de data inválido. Por favor, use o formato " + DATE_FORMAT);
            return;
        }

        ResponseData responseDataTransaction = ServiceLocator.getInstance().getTransactionService().getUserTransaction(
                ServiceLocator.getInstance().getSession().id());
        ResponseData responseDataTransactionType = ServiceLocator.getInstance().getTransactionTypeService().getUserTypes(
                ServiceLocator.getInstance().getSession().id());

        if (responseDataTransaction.getValue() == RequestStatusEnum.SUCCESS &&
                responseDataTransactionType.getValue() == RequestStatusEnum.SUCCESS) {

            List<TransactionModel> transactions = DataEnum.decodeTransactionModel(DataEnum.USER_TRANSACTIONS,
                    responseDataTransaction.getMapData().get(DataEnum.USER_TRANSACTIONS));
            List<TransactionTypesModel> transactionTypes = DataEnum.decodeTransactionTypes(DataEnum.USER_TYPES,
                    responseDataTransactionType.getMapData().get(DataEnum.USER_TYPES));

            Map<Integer, Long> incomeTypeCount = transactions.stream()
                    .filter(transaction -> transaction.getTransactionClassification().equals(CONST_INCOME) &&
                            !transaction.getDate().before(start) && !transaction.getDate().after(end))
                    .collect(Collectors.groupingBy(transaction -> transaction.getTransactionType().getId(),Collectors.counting()));

            Map<Integer, Long> expenseTypeCount = transactions.stream()
                    .filter(transaction -> transaction.getTransactionClassification().equals(CONST_EXPENSE) &&
                            !transaction.getDate().before(start) && !transaction.getDate().after(end))
                    .collect(Collectors.groupingBy(
                            transaction -> transaction.getTransactionType().getId(),
                            Collectors.counting()));


            double totalIncome = transactions.stream()
                    .filter(transaction -> transaction.getTransactionClassification().equals(CONST_INCOME) &&
                            !transaction.getDate().before(start) && !transaction.getDate().after(end))
                    .mapToDouble(TransactionModel::getValue)
                    .sum();

            double totalExpenses = transactions.stream()
                    .filter(transaction -> transaction.getTransactionClassification().equals(CONST_EXPENSE) &&
                            !transaction.getDate().before(start) && !transaction.getDate().after(end))
                    .mapToDouble(TransactionModel::getValue)
                    .sum();

            double totalBalance = totalIncome - totalExpenses;

            totalBalanceLabel.setText(LABEL_TOTAL_BALANCE + String.format("$%,.2f", totalBalance));
            totalIncomeLabel.setText(LABEL_TOTAL_INCOME + String.format("$%,.2f", totalIncome));
            totalExpensesLabel.setText(LABEL_TOTAL_EXPENSES + String.format("$%,.2f", totalExpenses));

            String incomeCategories = transactionTypes.stream()
                    .filter(type -> incomeTypeCount.containsKey(type.getId()))
                    .map(type -> " " + type.getDesc() + ": " + incomeTypeCount.get(type.getId()) + "\n")
                    .collect(Collectors.joining());

            incomeCategoriesLabel.setText(incomeCategories);

            String expenseCategories = transactionTypes.stream()
                    .filter(type -> expenseTypeCount.containsKey(type.getId()))
                    .map(type -> " " + type.getDesc() + ": " + expenseTypeCount.get(type.getId()) + "\n")
                    .collect(Collectors.joining());

            expensesCategoriesLabel.setText(expenseCategories);
        } else {
            JOptionPane.showMessageDialog(null, "Falha ao gerar o relatório");
        }
    }

    private Date getStartDate(Date parsedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndDate(Date parsedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    @Override
    public JPanel getBottomPanel() {
        super.bottomPanel.add(super.defaultTransactionTypesButton());
        super.bottomPanel.add(super.defaultAddTransactionButton());
        super.bottomPanel.add(super.defaultTransactionHistoryButton());
        super.bottomPanel.add(super.defaultHomeButton());
        return super.bottomPanel;
    }

    private void setDateRange(int monthsAgo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();

        endDateField.setText(dateFormat.format(calendar.getTime()));
        calendar.add(Calendar.MONTH, monthsAgo);
        startDateField.setText(dateFormat.format(calendar.getTime()));
    }


}
