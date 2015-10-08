package com.daoshun.guangda.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_permission_set")
public class PermissionSetInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8938615969898898340L;
	
	//权限id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",length=10,nullable=false)
	private int id;
	
	
	@Column(name = "permissionid", length = 10, nullable = false)
	private int permissionid;
	
	//父权限id root权限的父权限为0
	@Column(name="parentPermissionId")
	private int parentPermissionId;

	// 权限名称
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name="mappedAction")
	private String mappedAction;
	
	@Transient
	private boolean checked=false;
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getMappedAction() {
		return mappedAction;
	}

	public void setMappedAction(String mappedAction) {
		this.mappedAction = mappedAction;
	}

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
	
	public int getParentPermissionId() {
		return parentPermissionId;
	}

	public void setParentPermissionId(int parentPermissionId) {
		this.parentPermissionId = parentPermissionId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
