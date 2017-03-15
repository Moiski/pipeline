package com.moiski.dao.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

import com.moiski.dao.enums.StatusType;

@Entity
@Table(name = "tasks")
@Proxy(lazy = false)
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long taskId;

	@Id
	@Column(name = "taskID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getTaskId() {
		return taskId;
	}

	private String name;
	@Column(name = "name")
	public String getName() {
		return name;
	}

	private String description;
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	private Pipeline pipeline;
	@ManyToOne
	@JoinColumn(name = "pipelineID")
	public Pipeline getPipeline() {
		return pipeline;
	}

	private Action action;
	@OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
	public Action getAction() {
		return action;
	}

	private StatusType statusType;
	@Enumerated(EnumType.STRING)
	@Column(name = "statusType", columnDefinition = "enum('PENDING','IN_PROGRESS', 'SKIPPED', 'FAILED', 'COMPLETED')")
	public StatusType getStatusType() {
		return statusType;
	}

	private Date endTime;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "endTime")
	public Date getEndTime() {
		return endTime;
	}

	private Date startTime;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "startTime")
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", name=" + name + ", description=" + description + ", action=" + action
				+ ", statusType=" + statusType + ", endTime=" + endTime + ", startTime=" + startTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((statusType == null) ? 0 : statusType.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
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
		Task other = (Task) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (statusType != other.statusType)
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
