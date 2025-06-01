package br.com.gpf.controller.screenController;

import br.com.gpf.controller.DataEnum;
import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.controller.RequestStatusEnum;
import br.com.gpf.controller.ResponseData;
import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.model.dao.TransactionDao;
import br.com.gpf.model.dao.TransactionTypesDao;
import br.com.gpf.model.dao.UserDao;
import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.model.entity.UserModel;
import br.com.gpf.view.MessageDialogEnum;
import br.com.gpf.view.screen.TransactionTypesScreen;
import br.com.gpf.view.screen.complete.Screen;
import br.com.gpf.view.screen.complete.ScreenEnum;

import javax.swing.*;
import java.util.List;

public class TransactionTypesScreenController implements ScreenController {
    private final TransactionTypesScreen transactionTypesScreen = new TransactionTypesScreen();
    private final UserDao userDao = ServiceLocator.getInstance().getUserDao();
    private final TransactionDao transactionDao = ServiceLocator.getInstance().getTransactionDao();
    private final TransactionTypesDao transactionTypesDao = ServiceLocator.getInstance().getTransactionTypesDao();

    public TransactionTypesScreenController() {
        List<TransactionTypesModel> userTypes = transactionTypesDao.getUserTypes(
                ServiceLocator.getInstance().getSession().id());

        transactionTypesScreen.loadTransactionTypes(userTypes);
        transactionTypesScreen.setRegisterTransactionTypeButtonListanner(e -> onRegisterTransactionTypeButtonClick(transactionTypesScreen.getNewTransactionTypeDescField()));
    }

    private void onRegisterTransactionTypeButtonClick(String newDesc) {
        try {
            if (newDesc.isEmpty()) {
                MessageDialogEnum.ERROR.showMsg("Por favor, forneça uma descrição para o novo tipo de transação.", null);
                return;
            }

            UserModel userById = userDao.getUserById(ServiceLocator.getInstance().getSession().id());
            transactionTypesDao.createType(new TransactionTypesModel(newDesc, userById));
        } catch (Exception ex) {
            MessageDialogEnum.ERROR.showMsg("Falha ao adicionar o tipo de transação: " + ex.getMessage(), null);
        }
        reload();
    }

    @Override
    public Screen getScreen() {
        return transactionTypesScreen;
    }

    private static void reload() {
        SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_TYPES), null);
        });
    }
}
