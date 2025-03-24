package br.com.gpf.view;

import br.com.gpf.service.DataEnum;

import java.util.HashMap;
import java.util.Map;

public class LoadData {

    private Map<DataEnum,Object> mapData;

    public LoadData() {
        this.mapData = new HashMap<>();

    }

    public Map<DataEnum, Object> getMapData() {
        return mapData;
    }
}
