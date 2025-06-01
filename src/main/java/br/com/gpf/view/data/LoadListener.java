package br.com.gpf.view.data;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoadListener {
    private final Map<ListenerEnum, ActionListener> mapData;

    public LoadListener() {
        this.mapData = new HashMap<>();

    }

    public Map<ListenerEnum, ActionListener> getMapData() {
        return mapData;
    }
}
