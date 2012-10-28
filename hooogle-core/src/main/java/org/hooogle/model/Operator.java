package org.hooogle.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-29
 * Time: 上午8:49
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Operator {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column
    private String name;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
