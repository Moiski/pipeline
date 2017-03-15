package com.moiski.dao.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.moiski.dao.enums.Type;

@Entity
@Table(name="actions")
@Proxy(lazy = false)
public class Action implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long actionId;
	@Id
	@Column(name = "actionID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getActionId() {
		return actionId;
	}
	
	private Type type;
	@Enumerated(EnumType.STRING)
	@Column(name = "actionType", columnDefinition = "enum('PRINT','RANDOM', 'COMPLETED', 'DELAYED')")
	public Type getType() {
		return type;
	}
	
	private Task task;
	@OneToOne
	@JoinColumn(name="taskID")
	public Task getTask() {
		return task;
	}
	
	@Override
	public String toString() {
		return "Action [actionId=" + actionId + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionId == null) ? 0 : actionId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (actionId == null) {
			if (other.actionId != null)
				return false;
		} else if (!actionId.equals(other.actionId))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	
}
