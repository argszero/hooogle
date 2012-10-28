package org.hooogle.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hooogle.dao.ServerDao;
import org.hooogle.model.Server;
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
public class ServerDaoImpl implements ServerDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<Server> queryAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Server.class).list();
    }
}
