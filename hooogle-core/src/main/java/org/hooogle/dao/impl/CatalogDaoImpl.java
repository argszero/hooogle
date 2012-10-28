package org.hooogle.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hooogle.dao.CatalogDao;
import org.hooogle.model.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-28
 * Time: 下午6:30
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CatalogDaoImpl implements CatalogDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Long save(Catalog catalog) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(catalog);
    }

    @Override
    public List<Catalog> queryByParentIdAndOperatorId(Long parentId, Long operatorId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria =session.createCriteria(Catalog.class);
        criteria.add(parentId == null ? Restrictions.isNull("parentId") : Restrictions.eq("parentId", parentId));
        criteria.add(operatorId == null ? Restrictions.isNull("operatorId") : Restrictions.eq("operatorId", parentId));
        return criteria.list();
    }

    @Override
    public void deleteByIdAndOperator(Long catalogId, Long operatorId) {
        Session session = sessionFactory.getCurrentSession();
        Catalog catalog = (Catalog) session.get(Catalog.class,catalogId);
        if(catalog.getOperatorId() == operatorId){
            List<Catalog> catalogs =  queryByParentIdAndOperatorId(catalogId, operatorId);
            for(Catalog subCatalog:catalogs){
                subCatalog.setParentId(null);
                session.save(subCatalog);
            }
            session.delete(catalog);
        }
    }
}
