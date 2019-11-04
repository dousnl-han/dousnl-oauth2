package com.dousnl.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/11/1 13:55
 */
@Entity
@Table(name = "t_user")
public class TUserEntity {
    private int id;
    private String username;
    private String password;
    private int roleId;
    private String orderId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "order_id")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public TUserEntity() {
    }

    public TUserEntity(int id, int roleId) {
        this.id = id;
        this.roleId = roleId;
    }
}
