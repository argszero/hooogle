package org.hooogle.service.impl;

import org.hooogle.dao.OperatorDao;
import org.hooogle.model.Operator;
import org.hooogle.service.OperatorService;
import org.hooogle.util.TimeCacheMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-29
 * Time: 上午8:51
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OperatorServiceImpl implements OperatorService {
    @Autowired
    private OperatorDao operatorDao;
    private ThreadLocal<Long> operatorId = new ThreadLocal<Long>();//登录用户
    private TimeCacheMap<String, Long> idNameMap = new TimeCacheMap<String, Long>(3 * 1000);

    @Override
    public Long login(String name) {
        if (name == null) return null;
        Long id = idNameMap.get(name);
        if (id == null) {
            Operator operator = operatorDao.findByName(name);
            if (operator != null) {
                id = operator.getId();
                idNameMap.put(name, id);
            }
        }
        operatorId.set(id);
        return id;
    }

    @Override
    public Long getLoginOperatorId() {
        return operatorId.get();
    }
}
