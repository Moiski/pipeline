package com.moiski.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.moiski.dao.ITaskDao;
import com.moiski.dao.entities.Task;

@Repository
public class TaskDaoImpl extends DaoImpl<Task> implements ITaskDao {
	
	private Logger logger = Logger.getLogger(TaskDaoImpl.class);
	
	public Task getByNameAndPipiline(String name, Long pipelineId){
		logger.info("Get task by name and pipelineId :" + getClass().getName());
		String hql = "SELECT t FROM Task t WHERE t.name=:name AND t.pipeline.pipelineId=:pipelineId";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("pipelineId", pipelineId);
		Task pipeline = (Task) query.uniqueResult();
		return pipeline;
	}

}
