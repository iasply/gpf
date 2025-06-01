package br.com.gpf.controller.screenController;

import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.view.screen.complete.HomeScreen;
import br.com.gpf.view.screen.complete.Screen;
import br.com.gpf.view.screen.complete.ScreenEnum;

import javax.swing.*;

public class HomeScreenController implements ScreenController {

    private final HomeScreen homeScreen = new HomeScreen();

    public HomeScreenController() {
        homeScreen.setButtonTextExitAccountListener(e -> {

            ServiceLocator.getInstance().setSession(null);
            SwingUtilities.invokeLater(() -> {
                GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.LOGIN), null);
            });

        });

        homeScreen.setButtonTextAddTransactionListener(
                e -> SwingUtilities.invokeLater(() -> {
                    GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
                    instance.changeScreen(instance.loadScreenPanel(ScreenEnum.ADD_TRANSACTION), null);
                })
        );

        homeScreen.setButtonTextTransactionTypesListener(e -> SwingUtilities.invokeLater(() -> {
                    GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
                    instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_TYPES), null);
                })
        );

        homeScreen.setButtonTextTransactionHistoryListener(e -> SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_HISTORY), null);
        }));

        homeScreen.setButtonTextReportsListener(
                e -> SwingUtilities.invokeLater(() -> {
                    GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
                    instance.changeScreen(instance.loadScreenPanel(ScreenEnum.REPORTS), null);
                })
        );

    }

    @Override
    public Screen getScreen() {
        return homeScreen;
    }


}
