package com.moiski.services;

import java.util.List;

import com.moiski.dao.entities.Pipeline;
import com.moiski.services.exceptions.ErrorExecutePipeline;
import com.moiski.services.exceptions.ErrorSavingPipelineServiceExeption;

public interface IPipelineService {
	
	void add(Pipeline pipeline) throws ErrorSavingPipelineServiceExeption;
	
	List<Pipeline> getAllPipeline();
	
	void exucutePipeline(Long pipelineId) throws ErrorExecutePipeline;
	
	void delete(Long pipelineId);

}
