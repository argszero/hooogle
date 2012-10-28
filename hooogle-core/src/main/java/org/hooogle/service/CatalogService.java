package org.hooogle.service;

import org.hooogle.model.Catalog;
import org.hooogle.model.Server;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-28
 * Time: 上午9:58
 * To change this template use File | Settings | File Templates.
 */
public interface CatalogService {

    Long save(Catalog catalog);

    List<Catalog> queryByParentIdAndOperatorId(Long parentId, Long operatorId);

    void deleteByIdAndOperator(Long catalogId, Long operatorId);
}
