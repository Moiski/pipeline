package com.moiski.dao;

import java.util.List;

import com.moiski.dao.entities.Pipeline;

public interface IPipelineDao extends IDao<Pipeline> {
	
	Pipeline getPipeline (String name, String description);
	
	List<Pipeline> getAllPipelines();

}
