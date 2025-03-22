package br.com.gpf.view.screen;

import br.com.gpf.view.LoadData;

import javax.swing.*;

public interface Screen {


    void onload(LoadData loadData);
    void onSave();

    JPanel getTopPanel();
    JPanel getMidPanel();
    JPanel getBottomPanel();
}
