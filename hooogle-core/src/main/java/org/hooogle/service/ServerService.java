package org.hooogle.service;

import org.hooogle.model.Server;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-28
 * Time: 上午9:58
 * To change this template use File | Settings | File Templates.
 */
public interface ServerService {
    List<Server> queryAll();
}
