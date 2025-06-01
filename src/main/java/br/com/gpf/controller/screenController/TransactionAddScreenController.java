package br.com.gpf.controller.screenController;

import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.model.dao.TransactionDao;
import br.com.gpf.model.dao.TransactionTypesDao;
import br.com.gpf.model.dao.UserDao;
import br.com.gpf.model.entity.TransactionModel;
import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.model.entity.UserModel;
import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.MessageDialogEnum;
import br.com.gpf.view.screen.complete.TransactionAddScreen;
import br.com.gpf.view.screen.complete.Screen;
import br.com.gpf.view.screen.complete.ScreenEnum;

import javax.swing.*;
import java.util.Date;
import java.util.List;

import static br.com.gpf.view.ConstValues.CONST_EXPENSE;
import static br.com.gpf.view.ConstValues.CONST_INCOME;

public class TransactionAddScreenController implements ScreenController {

    private final TransactionAddScreen transactionAddScreen = new TransactionAddScreen();
    private final UserDao userDao = ServiceLocator.getInstance().getUserDao();
    private final TransactionDao transactionDao = ServiceLocator.getInstance().getTransactionDao();
    private final TransactionTypesDao transactionTypesDao = ServiceLocator.getInstance().getTransactionTypesDao();

    public TransactionAddScreenController() {
        List<TransactionTypesModel> userTypes = transactionTypesDao.getUserTypes(ServiceLocator.getInstance().getSession().id());
        transactionAddScreen.loadTransactionType(userTypes);
        transactionAddScreen.setSaveButtonListener(e -> onSaveClick(transactionAddScreen.getDescriptionText(), transactionAddScreen.getTransactionType(), transactionAddScreen.getValueText(), transactionAddScreen.getSelectedDate(), transactionAddScreen.isIncome(), transactionAddScreen.isExpense()));
    }

    private void onSaveClick(String descriptionText, TransactionTypesModel transactionType, String valueText, Date selectedDate, boolean income, boolean expense) {
        try {

            if (valueText.isEmpty() || selectedDate == null || transactionType == null || (!income && !expense)) {
                throw new DefaultScreenException("Por favor, preencha todos os campos obrigatórios.");
            }
            double value;
            try {
                  value = Double.parseDouble(valueText);
            } catch (NumberFormatException ex) {
                throw new DefaultScreenException("Valor inválido. Por favor, insira um valor numérico.");
            }

            Integer transactionCategory = income ? CONST_INCOME : CONST_EXPENSE;

            UserModel userById = userDao.getUserById(ServiceLocator.getInstance().getSession().id());
            TransactionTypesModel typeById = transactionTypesDao.getTypeById(transactionType.getId());

            TransactionModel transaction = new TransactionModel();
            transaction.setUser(userById);
            transaction.setValue(value);
            transaction.setTransactionType(typeById);
            transaction.setTransactionClassification(transactionCategory);
            transaction.setDate(selectedDate);
            transaction.setDescriptionText(descriptionText);

            boolean success = transactionDao.createTransaction(transaction);

            if(success){
                MessageDialogEnum.SUCCESS.showMsg("Transação salva com sucesso!", null);
                SwingUtilities.invokeLater(() -> {
                    GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
                    instance.changeScreen(instance.loadScreenPanel(ScreenEnum.ADD_TRANSACTION), null);
                });
                return;
            }

            MessageDialogEnum.ERROR.showMsg("Falha ao criar transação.",null);

        } catch (Exception ex) {
            MessageDialogEnum.ERROR.showMsg(ex.getMessage(), null);

        }

    }

    @Override
    public Screen getScreen() {
        return transactionAddScreen ;
    }
}
