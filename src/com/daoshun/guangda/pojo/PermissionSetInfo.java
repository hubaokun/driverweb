package com.daoshun.guangda.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_permission_set")
public class PermissionSetInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8938615969898898340L;
	
	//权限id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permissionid", length = 10, nullable = false)
	private int permissionid;
	
	// 权限名称
	@Column(name = "name", nullable = false)
	private String name;

	
	public int getPermissionid() {
		return permissionid;
	}

	
	public void setPermissionid(int permissionid) {
		this.permissionid = permissionid;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
}
