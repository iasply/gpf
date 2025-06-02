package br.com.gpf.controller.screenController;

import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.model.dao.TransactionTypesDao;
import br.com.gpf.model.dao.UserDao;
import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.model.entity.UserModel;
import br.com.gpf.view.MessageDialogEnum;
import br.com.gpf.view.screen.NewAccountScreen;
import br.com.gpf.view.screen.Screen;
import br.com.gpf.view.screen.ScreenEnum;

import javax.swing.*;

public class NewAccountScreenController implements ScreenController {
    private final NewAccountScreen newAccountScreen = new NewAccountScreen();

    private final UserDao userDao = ServiceLocator.getInstance().getUserDao();
    private final TransactionTypesDao transactionTypesDao = ServiceLocator.getInstance().getTransactionTypesDao();

    public NewAccountScreenController() {
        newAccountScreen.setRegisterListener(e -> onRegisterClick(newAccountScreen.getUserName(), newAccountScreen.getPassword(), newAccountScreen.getConfirmPassword()));
    }

    private void onRegisterClick(String userName, String password, String confirmPassword) {

        if (isBlankNullOrEmpty(userName) ||
                isBlankNullOrEmpty(password)
                ||
                isBlankNullOrEmpty(confirmPassword)
        ) {
            MessageDialogEnum.ERROR.showMsg("PREENCHA TODOS OS CAMPOS", null);
            return;
        }

        if (!password.equals(confirmPassword)) {
            MessageDialogEnum.ERROR.showMsg("AS SENHAS DEVEM SER IGUAIS", null);
            return;

        }

        boolean userCreated = userDao.createUser(userName, password);

        if (userCreated) {
            UserModel userByName = userDao.getUserByName(userName);

            transactionTypesDao.createType(new TransactionTypesModel("Salario", userByName));
            transactionTypesDao.createType(new TransactionTypesModel("Cartão", userByName));

            MessageDialogEnum.SUCCESS.showMsg("USUÁRIO " + userName + " CRIADO", null);

            SwingUtilities.invokeLater(() -> {
                GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.LOGIN), null);
            });

            return;
        }

        MessageDialogEnum.ERROR.showMsg("FALHA AO CRIAR O USUARIO", null);

        SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.NEW_ACCOUNT), null);
        });

    }

    @Override
    public Screen getScreen() {
        return newAccountScreen;
    }


    private static boolean isBlankNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
