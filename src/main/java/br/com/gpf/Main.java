package br.com.gpf;

import br.com.gpf.view.GpfScreenManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        UIManager.getDefaults().entrySet().stream().sorted((o1, o2) -> {
            return o1.getKey().toString().compareTo(o2.getKey().toString());
        }).forEach(entry -> {
            System.out.print(entry.getKey());
            System.out.print(" ---> ");
            System.out.println(entry.getValue());
        });
        GpfScreenManager gpfScreenManager = GpfScreenManager.getInstance();
        gpfScreenManager.start();

    }
}