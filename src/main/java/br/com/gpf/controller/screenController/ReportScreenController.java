package br.com.gpf.controller.screenController;

import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.model.dao.TransactionDao;
import br.com.gpf.model.dao.TransactionTypesDao;
import br.com.gpf.model.dao.UserDao;
import br.com.gpf.model.entity.TransactionModel;
import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.view.MessageDialogEnum;
import br.com.gpf.view.screen.ReportScreen;
import br.com.gpf.view.screen.Screen;

import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.gpf.view.ConstValues.CONST_EXPENSE;
import static br.com.gpf.view.ConstValues.CONST_INCOME;
import static br.com.gpf.view.ConstValues.DATE_FORMAT;

public class ReportScreenController implements ScreenController {
    private final ReportScreen reportScreen;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private final UserDao userDao;
    private final TransactionDao transactionDao;
    private final TransactionTypesDao transactionTypesDao;

    public ReportScreenController() {
        this.userDao = ServiceLocator.getInstance().getUserDao();
        this.transactionDao = ServiceLocator.getInstance().getTransactionDao();
        this.transactionTypesDao = ServiceLocator.getInstance().getTransactionTypesDao();
        this.reportScreen = new ReportScreen();
        setupListeners();

    }

    private void setupListeners() {
        reportScreen.setGenerateReportButtonListener(this::onGenerateReportButtonClick);
        reportScreen.setLast1MonthButtonListener(e -> setDateRangeAndGenerateReport(-1));
        reportScreen.setLast3MonthsButtonListener(e -> setDateRangeAndGenerateReport(-3));
        reportScreen.setLast6MonthsButtonListener(e -> setDateRangeAndGenerateReport(-6));
    }


    private void onGenerateReportButtonClick(ActionEvent e) {
        String startDateText = reportScreen.getStartDateFieldText();
        String endDateText = reportScreen.getEndDateFieldText();

        Date start, end;
        try {
            start = getStartDate(dateFormat.parse(startDateText));
            end = getEndDate(dateFormat.parse(endDateText));
        } catch (ParseException ex) {
            MessageDialogEnum.ERROR.showMsg("Formato de data inválido. Por favor, use o formato " + DATE_FORMAT, null);
            return;
        }

        generateReport(start, end);
    }

    private void setDateRangeAndGenerateReport(int monthsAgo) {
        Calendar calendar = Calendar.getInstance();
        reportScreen.setEndDateFieldText(dateFormat.format(calendar.getTime()));

        calendar.add(Calendar.MONTH, monthsAgo);
        reportScreen.setStartDateFieldText(dateFormat.format(calendar.getTime()));


        try {
            Date start = getStartDate(dateFormat.parse(reportScreen.getStartDateFieldText()));
            Date end = getEndDate(dateFormat.parse(reportScreen.getEndDateFieldText()));
            generateReport(start, end);
        } catch (ParseException ex) {
            MessageDialogEnum.ERROR.showMsg("Erro ao definir datas do relatório. Verifique o formato.", null);
        }
    }

    private void generateReport(Date startDate, Date endDate) {
        List<TransactionModel> transactions = transactionDao.getUserTransaction(ServiceLocator.getInstance().getSession().id());
        List<TransactionTypesModel> transactionTypes = transactionTypesDao.getUserTypes(ServiceLocator.getInstance().getSession().id());

        List<TransactionModel> filteredTransactions = transactions.stream().filter(transaction -> !transaction.getDate().before(startDate) && !transaction.getDate().after(endDate)).collect(Collectors.toList());

        double totalIncome = filteredTransactions.stream().filter(transaction -> CONST_INCOME.equals(transaction.getTransactionClassification())).mapToDouble(TransactionModel::getValue).sum();

        double totalExpenses = filteredTransactions.stream().filter(transaction -> CONST_EXPENSE.equals(transaction.getTransactionClassification())).mapToDouble(TransactionModel::getValue).sum();

        double totalBalance = totalIncome - totalExpenses;

        reportScreen.setTotalBalanceLabel(String.format("$%,.2f", totalBalance));
        reportScreen.setTotalIncomeLabel(String.format("$%,.2f", totalIncome));
        reportScreen.setTotalExpensesLabel(String.format("$%,.2f", totalExpenses));
        Map<Integer, Long> incomeTypeCount = filteredTransactions.stream().filter(transaction -> CONST_INCOME.equals(transaction.getTransactionClassification())).collect(Collectors.groupingBy(transaction -> transaction.getTransactionType().getId(), Collectors.counting()));

        Map<Integer, Long> expenseTypeCount = filteredTransactions.stream().filter(transaction -> CONST_EXPENSE.equals(transaction.getTransactionClassification())).collect(Collectors.groupingBy(transaction -> transaction.getTransactionType().getId(), Collectors.counting()));

        String incomeCategories = transactionTypes.stream().filter(type -> incomeTypeCount.containsKey(type.getId())).map(type -> type.getDesc() + ": " + incomeTypeCount.get(type.getId())).collect(Collectors.joining("\n"));

        String expenseCategories = transactionTypes.stream().filter(type -> expenseTypeCount.containsKey(type.getId())).map(type -> type.getDesc() + ": " + expenseTypeCount.get(type.getId())).collect(Collectors.joining("\n"));

        reportScreen.setIncomeCategoriesLabel(incomeCategories.isEmpty() ? "Nenhuma categoria de renda no período." : incomeCategories);
        reportScreen.setExpensesCategoriesLabel(expenseCategories.isEmpty() ? "Nenhuma categoria de despesa no período." : expenseCategories);


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
    public Screen getScreen() {
        return reportScreen;
    }


}