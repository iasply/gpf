package br.com.gpf.view.screen;

import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.data.LoadData;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public void setGenerateReportButtonListener(ActionListener listener) {
        generateReportButton.addActionListener(listener);
    }

    public void setLast1MonthButtonListener(ActionListener listener) {
        last1MonthButton.addActionListener(listener);
    }

    public void setLast3MonthsButtonListener(ActionListener listener) {
        last3MonthsButton.addActionListener(listener);
    }

    public void setLast6MonthsButtonListener(ActionListener listener) {
        last6MonthsButton.addActionListener(listener);
    }


    public String getStartDateFieldText() {
        return startDateField.getText();
    }

    public void setStartDateFieldText(String text) {
        startDateField.setText(text);
    }

    public String getEndDateFieldText() {
        return endDateField.getText();
    }


    public void setEndDateFieldText(String text) {
        endDateField.setText(text);
    }

    public void setTotalBalanceLabel(String text) {
        totalBalanceLabel.setText(LABEL_TOTAL_BALANCE + text);
    }

    public void setTotalIncomeLabel(String text) {
        totalIncomeLabel.setText(LABEL_TOTAL_INCOME + text);
    }

    public void setTotalExpensesLabel(String text) {
        totalExpensesLabel.setText(LABEL_TOTAL_EXPENSES + text);
    }

    public void setIncomeCategoriesLabel(String text) {
        incomeCategoriesLabel.setText("<html>" + text.replaceAll("\n", "<br/>") + "</html>");
    }

    public void setExpensesCategoriesLabel(String text) {
        expensesCategoriesLabel.setText("<html>" + text.replaceAll("\n", "<br/>") + "</html>");
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
