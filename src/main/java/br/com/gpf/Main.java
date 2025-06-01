package br.com.gpf;

import br.com.gpf.controller.GpfScreenControllerManager;

public class Main {
    public static void main(String[] args) {

        GpfScreenControllerManager gpfScreenControllerManager = GpfScreenControllerManager.getInstance();
        gpfScreenControllerManager.start();

    }
}