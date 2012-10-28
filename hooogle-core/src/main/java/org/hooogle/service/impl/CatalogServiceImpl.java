package org.hooogle.service.impl;

import org.hooogle.dao.CatalogDao;
import org.hooogle.model.Catalog;
import org.hooogle.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-28
 * Time: 上午9:59
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private CatalogDao catalogDao;

    @Override
    public Long save(Catalog catalog) {
        return catalogDao.save(catalog);
    }

    @Override
    public List<Catalog> queryByParentIdAndOperatorId(Long parentId, Long operatorId) {
        return catalogDao.queryByParentIdAndOperatorId(parentId, operatorId);
    }

    @Override
    public void deleteByIdAndOperator(Long catalogId, Long operatorId) {
        catalogDao.deleteByIdAndOperator(catalogId, operatorId);
    }
}
