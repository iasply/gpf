package br.com.gpf;

import br.com.gpf.view.GpfScreenManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        GpfScreenManager gpfScreenManager = GpfScreenManager.getInstance();
        gpfScreenManager.start();

    }
}