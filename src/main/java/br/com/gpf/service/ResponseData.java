package br.com.gpf.service;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {
    private RequestStatusEnum value;
    private Map<DataEnum, Object> mapData;

    public ResponseData() {
        this.value = RequestStatusEnum.DEFAULT;
        this.mapData = new HashMap<>();
        mapData.put(DataEnum.NULL, "null");
    }

    public ResponseData(RequestStatusEnum value, Map<DataEnum, Object> mapData) {
        this.value = value;
        this.mapData = mapData;
    }

    public RequestStatusEnum getValue() {
        return value;
    }

    public void setValue(RequestStatusEnum value) {
        this.value = value;
    }

    public Map<DataEnum, Object> getMapData() {
        return mapData;
    }

    public void setMapData(Map<DataEnum, Object> mapData) {
        this.mapData = mapData;
    }
}
