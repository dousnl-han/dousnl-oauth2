package com.dousnl.domain;

import java.io.Serializable;

public class Role implements Serializable {
    private Integer id;

    private String roleName;

    private static final long serialVersionUID = 1L;

    public Role(Integer id, String roleName) {
    	this.id=id;
    	this.roleName=roleName;
	}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Role other = (Role) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoleName() == null ? other.getRoleName() == null : this.getRoleName().equals(other.getRoleName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoleName() == null) ? 0 : getRoleName().hashCode());
        return result;
    }
    
    
    public static void main(String[] args) {
		Role role1=new Role(1,"1");
		Role role2=new Role(1,"1");
		System.out.println(role1.getRoleName().equals(role2.getRoleName()));
	}
}
