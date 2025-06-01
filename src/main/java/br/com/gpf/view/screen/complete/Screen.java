package br.com.gpf.view.screen.complete;

import br.com.gpf.view.DefaultScreenException;
import br.com.gpf.view.data.LoadData;

import javax.swing.*;

public interface Screen {


    void onload(LoadData loadData);

    void onSave() throws DefaultScreenException;

    String getTittle();

    JPanel getTopPanel();

    JPanel getMidPanel();

    JPanel getBottomPanel();
}
