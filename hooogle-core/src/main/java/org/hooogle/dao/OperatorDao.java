package org.hooogle.dao;

import org.hooogle.model.Operator;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-28
 * Time: 下午6:29
 * To change this template use File | Settings | File Templates.
 */
public interface OperatorDao {
    Operator findByName(String name);
}
