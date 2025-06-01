package br.com.gpf.controller.screenController;

import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.controller.Session;
import br.com.gpf.model.dao.UserDao;
import br.com.gpf.model.entity.UserModel;
import br.com.gpf.view.MessageDialogEnum;
import br.com.gpf.view.screen.complete.LoginScreen;
import br.com.gpf.view.screen.complete.Screen;
import br.com.gpf.view.screen.complete.ScreenEnum;

import javax.swing.*;

public class LoginScreenController implements ScreenController {

    private final LoginScreen loginScreen = new LoginScreen();
    private final UserDao userDao = ServiceLocator.getInstance().getUserDao();


    public LoginScreenController() {
        loginScreen.setLoginListener(e -> onLoginClick(loginScreen.getUserName(), loginScreen.getPassword()));
    }

    private void onLoginClick(String userName, String password) {

        boolean validUser = userDao.isValidUser(userName, password);

        if (validUser) {
            UserModel userByName = userDao.getUserByName(userName);
            ServiceLocator.getInstance().setSession(new Session(userByName.getId(), userByName.getUserName()));

            SwingUtilities.invokeLater(() -> {
                GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.HOME), null);
            });
            return;
        }

        MessageDialogEnum.ERROR.showMsg("FALHA NO LOGIN, VERIFIQUE AS CREDENCIAIS", null);

        SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.LOGIN), null);
        });

    }

    @Override
    public Screen getScreen() {
        return loginScreen;
    }


}
