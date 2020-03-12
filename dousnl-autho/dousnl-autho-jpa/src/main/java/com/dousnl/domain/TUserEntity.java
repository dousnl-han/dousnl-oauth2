package com.dousnl.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2019/11/1 13:55
 */
@Entity
@Table(name = "t_user")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.dousnl.domain")
public class TUserEntity {

    @ApiModelProperty(value = "id")
    private int id;

    @ApiModelProperty(value = "名称")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色id")
    private int roleId;

    @ApiModelProperty(value = "订单id")
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

    public TUserEntity(int id, String username) {
        this.id = id;
        this.username = username;
    }
}
