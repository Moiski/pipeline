package com.moiski.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.moiski.dao.IPipelineDao;
import com.moiski.dao.entities.Pipeline;

@Repository
public class PipelineDaoImpl extends DaoImpl<Pipeline> implements IPipelineDao {
	
	private Logger logger = Logger.getLogger(PipelineDaoImpl.class);
	
	public Pipeline getPipeline(String name, String description){
		logger.info("Get pipeline by name and description" + getClass().getName());
		String hql = "SELECT p FROM Pipeline p WHERE p.name=:name AND p.description=:description";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("description", description);
		Pipeline pipeline = (Pipeline) query.uniqueResult();
		return pipeline;
	}

	@SuppressWarnings("unchecked")
	public List<Pipeline> getAllPipelines() {
		logger.info("Get all pipeline:  " + getClass().getName());
		String hql = "FROM Pipeline p";
		Query query = getSession().createQuery(hql);
		List<Pipeline> pipelines = query.list();
		return pipelines;
	}

}
