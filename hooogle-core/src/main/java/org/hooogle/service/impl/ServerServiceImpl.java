package org.hooogle.service.impl;

import org.hooogle.dao.ServerDao;
import org.hooogle.model.Server;
import org.hooogle.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-28
 * Time: 上午9:59
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ServerServiceImpl implements ServerService {
    @Autowired
    private ServerDao serverDao;

    @Override
    public List<Server> queryAll() {
        return serverDao.queryAll();
    }
}
