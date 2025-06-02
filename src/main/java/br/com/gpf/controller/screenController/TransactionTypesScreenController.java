package br.com.gpf.controller.screenController;

import br.com.gpf.controller.GpfScreenControllerManager;
import br.com.gpf.controller.ServiceLocator;
import br.com.gpf.model.DefaultRepositoryException;
import br.com.gpf.model.dao.TransactionDao;
import br.com.gpf.model.dao.TransactionTypesDao;
import br.com.gpf.model.dao.UserDao;
import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.model.entity.UserModel;
import br.com.gpf.view.MessageDialogEnum;
import br.com.gpf.view.screen.complete.TransactionTypesScreen;
import br.com.gpf.view.screen.complete.Screen;
import br.com.gpf.view.screen.complete.ScreenEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class TransactionTypesScreenController implements ScreenController {
    private final TransactionTypesScreen transactionTypesScreen;
    private final UserDao userDao;
    private final TransactionDao transactionDao;
    private final TransactionTypesDao transactionTypesDao;

    public TransactionTypesScreenController() {
        this.transactionTypesScreen = new TransactionTypesScreen();
        this.userDao = ServiceLocator.getInstance().getUserDao();
        this.transactionDao = ServiceLocator.getInstance().getTransactionDao();
        this.transactionTypesDao = ServiceLocator.getInstance().getTransactionTypesDao();

        this.transactionTypesScreen.setRegisterTransactionTypeButtonListener(this::onRegisterTransactionTypeButtonClick);
        this.transactionTypesScreen.setEditButtonListener(this::onEditButtonClick);
        this.transactionTypesScreen.setDeleteButtonListener(this::onDeleteButtonClick);

        loadInitialData();
    }

    private void loadInitialData() {
        populateTransactionTypesTable();
    }

    private void populateTransactionTypesTable() {
        List<TransactionTypesModel> userTypes = transactionTypesDao.getUserTypes(ServiceLocator.getInstance().getSession().id());

        Vector<Vector<String>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("TIPO");
        columnNames.add("EDITAR");
        columnNames.add("EXCLUIR");

        for (TransactionTypesModel t : userTypes) {
            Vector<String> row = new Vector<>();
            row.add(t.getId().toString());
            row.add(t.getDesc());
            row.add("");
            row.add("");
            data.add(row);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3;
            }
        };
        transactionTypesScreen.loadTransactionTypesTable(model);
    }

    private void onRegisterTransactionTypeButtonClick(ActionEvent e) {
        String newDesc = transactionTypesScreen.getNewTransactionTypeDescFieldText();
        if (newDesc.isEmpty()) {
            MessageDialogEnum.ERROR.showMsg("Por favor, forneça uma descrição para o novo tipo de transação.", null);
            return;
        }

        try {
            UserModel userById = userDao.getUserById(ServiceLocator.getInstance().getSession().id());
            transactionTypesDao.createType(new TransactionTypesModel(newDesc, userById));
            MessageDialogEnum.SUCCESS.showMsg("Tipo de transação '" + newDesc + "' adicionado com sucesso!", null);
            transactionTypesScreen.setNewTransactionTypeDescFieldText("");
            reloadScreen();
        } catch (DefaultRepositoryException ex) {
            MessageDialogEnum.ERROR.showMsg("Falha ao adicionar o tipo de transação: " + ex.getMessage(), null);
        }
    }

    private void onEditButtonClick(ActionEvent e) {
        int row = (int) e.getWhen();
        JTable table = (JTable) e.getSource();

        String id = table.getValueAt(row, 0).toString();
        String desc = table.getValueAt(row, 1).toString();

        boolean isConfirmed = MessageDialogEnum.YES_OR_NOT.showMsg("Você tem certeza que deseja editar '" + desc + "'?", null);

        if (isConfirmed) {
            String userInput = JOptionPane.showInputDialog(null, "Por favor, insira a nova descrição do tipo:", "Entrada Necessária", JOptionPane.QUESTION_MESSAGE);

            if (userInput != null && !userInput.isEmpty()) {
                if (transactionTypesDao.alterType(ServiceLocator.getInstance().getSession().id(), Integer.parseInt(id), userInput)) {
                    MessageDialogEnum.SUCCESS.showMsg("Tipo de transação '" + desc + "' alterado para '" + userInput + "' com sucesso!", null);
                    reloadScreen();
                } else {
                    MessageDialogEnum.ERROR.showMsg("Falha ao alterar o tipo de transação.", null);
                }
            } else {
                MessageDialogEnum.ERROR.showMsg("Entrada inválida ou operação cancelada.", null);
            }
        }
    }


    private void onDeleteButtonClick(ActionEvent e) {

        int row = (int) e.getWhen();
        JTable table = (JTable) e.getSource();

        String id = table.getValueAt(row, 0).toString();
        String desc = table.getValueAt(row, 1).toString();

        boolean isConfirmed = MessageDialogEnum.YES_OR_NOT.showMsg("Você tem certeza que deseja excluir " + desc + "?", table);

        if (isConfirmed) {

            List<TransactionTypesModel> transactionTypes = transactionTypesDao.getUserTypes(ServiceLocator.getInstance().getSession().id());
            if (transactionTypes != null && !transactionTypes.isEmpty()) {

                Vector<String> transactionTypeNames = getAvailableTransactionTypes(desc, transactionTypes);
                JComboBox<String> typeSelectionComboBox = new JComboBox<>(transactionTypeNames);

                int result = JOptionPane.showConfirmDialog(null, typeSelectionComboBox, "Escolha um novo tipo para as transações", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String selectedTypeName = (String) typeSelectionComboBox.getSelectedItem();
                    moveTransactionsAndDeleteType(desc, selectedTypeName, transactionTypes);
                    reloadScreen();

                }
            }
        }
    }


    private Vector<String> getAvailableTransactionTypes(String desc, List<TransactionTypesModel> transactionTypes) {

        return transactionTypes.stream().filter(t -> !t.getDesc().equals(desc)).map(TransactionTypesModel::getDesc).collect(Collectors.toCollection(Vector::new));

    }


    private void moveTransactionsAndDeleteType(String desc, String selectedTypeName, List<TransactionTypesModel> transactionTypes) {
        TransactionTypesModel oldType = findTransactionTypeByDesc(transactionTypes, desc);
        TransactionTypesModel newType = findTransactionTypeByDesc(transactionTypes, selectedTypeName);

        if (newType != null && oldType != null) {
            transactionDao.alterTransactionType(ServiceLocator.getInstance().getSession().id(), oldType.getId(), newType.getId()

            );
            transactionTypesDao.deleteType(ServiceLocator.getInstance().getSession().id(), oldType.getId());
            reloadScreen();
        }
    }


    private TransactionTypesModel findTransactionTypeByDesc(List<TransactionTypesModel> transactionTypes, String desc) {
        return transactionTypes.stream().filter(t -> t.getDesc().equals(desc)).findFirst().orElse(null);
    }


    private void reloadScreen() {
        SwingUtilities.invokeLater(() -> {
            GpfScreenControllerManager instance = GpfScreenControllerManager.getInstance();
            instance.changeScreen(instance.loadScreenPanel(ScreenEnum.TRANSACTION_TYPES), null);
        });
    }

    @Override
    public Screen getScreen() {
        return transactionTypesScreen;
    }
}