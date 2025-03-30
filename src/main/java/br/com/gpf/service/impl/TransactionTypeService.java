package br.com.gpf.service.impl;

import br.com.gpf.service.ResponseData;

public interface TransactionTypeService {

    ResponseData createType(Integer userId, String desc);

    ResponseData getUserTypes(Integer userId);

    ResponseData alterType(Integer userId, Integer typeId, String newDesc);

    ResponseData deleteType(Integer userId, Integer typeId);

}
