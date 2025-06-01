package br.com.gpf.controller.service;

import br.com.gpf.controller.ResponseData;

public interface TransactionTypeService {

    ResponseData createType(Integer userId, String desc);

    ResponseData getUserTypes(Integer userId);

    ResponseData alterType(Integer userId, Integer typeId, String newDesc);

    ResponseData deleteType(Integer userId, Integer typeId);

}
