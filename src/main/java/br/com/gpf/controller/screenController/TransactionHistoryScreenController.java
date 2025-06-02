package br.com.gpf.controller.screenController;

import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.model.dao.TransactionDao;
import br.com.gpf.model.dao.TransactionTypesDao;
import br.com.gpf.model.dao.UserDao;
import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.MessageDialogEnum;
import br.com.gpf.view.data.LoadData;
import br.com.gpf.view.screen.TransactionHistoryScreen;
import br.com.gpf.view.screen.complete.FilterColumnEnum;
import br.com.gpf.view.screen.complete.Screen;
import br.com.gpf.view.screen.complete.ScreenEnum;

import javax.swing.*;

import static br.com.gpf.view.ConstValues.DATE_FORMAT;

public class TransactionHistoryScreenController implements ScreenController {
    private final TransactionHistoryScreen transactionHistoryScreen;
    private final UserDao userDao = ServiceLocator.getInstance().getUserDao();
    private final TransactionDao transactionDao = ServiceLocator.getInstance().getTransactionDao();
    private final TransactionTypesDao transactionTypesDao = ServiceLocator.getInstance().getTransactionTypesDao();


    public TransactionHistoryScreenController() {
        this.transactionHistoryScreen = new TransactionHistoryScreen();
        loadHistory();
        transactionHistoryScreen.setSearchButtonListener(e -> onSearchClick(transactionHistoryScreen.getSearchText(), transactionHistoryScreen.getSelectedFilter()));
        transactionHistoryScreen.setClearSelectionButtonListener(e -> onClearSelectionClick());
    }

    private void loadHistory() {
        Integer id = ServiceLocator.getInstance().getSession().id();
        transactionHistoryScreen.setHistory(transactionDao.getUserTransaction(id),transactionTypesDao.getUserTypes(id));
    }

    private void onClearSelectionClick() {
        SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_HISTORY), null);
        });
    }

    private void onSearchClick(String searchText, String selectedFilter) {
        try {

            if (selectedFilter == null || selectedFilter.isEmpty()) {
                throw new DefaultScreenException("Por favor, selecione um filtro válido.");
            }

            if (searchText.isEmpty()) {
                throw new DefaultScreenException("Por favor, insira um termo de busca.");
            }

            if (selectedFilter.equals("Date") && !transactionHistoryScreen.validateDateFormat(searchText)) {
                throw new DefaultScreenException("Por favor, insira uma data válida no formato " + DATE_FORMAT + ".");
            }

            LoadData loadData1 = transactionHistoryScreen.getLoadData() != null ? transactionHistoryScreen.getLoadData() : new LoadData();

            loadData1.getMapData().put(FilterColumnEnum.toEnum(selectedFilter).getDataEnum(), searchText);

            SwingUtilities.invokeLater(() -> {
                GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
                instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_HISTORY), loadData1);
            });

        } catch (Exception ex) {
            MessageDialogEnum.ERROR.showMsg(ex.getMessage(), null);
        }
    }


    @Override
    public Screen getScreen() {
        return transactionHistoryScreen;
    }


}