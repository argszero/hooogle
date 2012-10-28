package org.hooogle.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hooogle.dao.OperatorDao;
import org.hooogle.model.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-28
 * Time: 下午6:30
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OperatorDaoImpl implements OperatorDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Operator findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Operator o where o.name = :name");
        query.setParameter("name", name);
        return (Operator) query.uniqueResult();
    }
}
