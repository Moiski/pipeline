package com.moiski.dao.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import com.moiski.dao.enums.StatusType;

@Entity
@Table(name = "pipelines")
@Proxy(lazy = false)
public class Pipeline implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long pipelineId;

	@Id
	@Column(name = "pipelineID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getPipelineId() {
		return pipelineId;
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
	
	private StatusType statusType;
	@Enumerated(EnumType.STRING)
	@Column(name = "statusType", columnDefinition = "enum('PENDING','IN_PROGRESS', 'SKIPPED', 'FAILED', 'COMPLETED')")
	public StatusType getStatusType() {
		return statusType;
	}
	
	private List<Task> tasks;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL)
	public List<Task> getTasks() {
		return tasks;
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
		return "Pipeline [pipelineId=" + pipelineId + ", name=" + name + ", description=" + description
				+ ", statusType=" + statusType + ", tasks=" + tasks + ", endTime=" + endTime + ", startTime="
				+ startTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pipelineId == null) ? 0 : pipelineId.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((statusType == null) ? 0 : statusType.hashCode());
		result = prime * result + ((tasks == null) ? 0 : tasks.hashCode());
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
		Pipeline other = (Pipeline) obj;
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
		if (pipelineId == null) {
			if (other.pipelineId != null)
				return false;
		} else if (!pipelineId.equals(other.pipelineId))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (statusType != other.statusType)
			return false;
		if (tasks == null) {
			if (other.tasks != null)
				return false;
		} else if (!tasks.equals(other.tasks))
			return false;
		return true;
	}

	public void setPipelineId(Long pipelineId) {
		this.pipelineId = pipelineId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
}
