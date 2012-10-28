package org.hooogle.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-28
 * Time: 下午6:36
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Catalog {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column
    private String name;
    @Column
    private Long parentId;
    @Column
    private Long operatorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
}
