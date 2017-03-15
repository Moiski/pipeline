package com.moiski.dao;

import com.moiski.dao.entities.Task;

public interface ITaskDao extends IDao<Task> {
	
	Task getByNameAndPipiline(String name, Long pipelineId);

}
