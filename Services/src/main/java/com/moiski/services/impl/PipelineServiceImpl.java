package com.moiski.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moiski.dao.IActionDao;
import com.moiski.dao.IPipelineDao;
import com.moiski.dao.ITaskDao;
import com.moiski.dao.entities.Action;
import com.moiski.dao.entities.Pipeline;
import com.moiski.dao.entities.Task;
import com.moiski.dao.enums.StatusType;
import com.moiski.dao.exceptions.DaoExeptions;
import com.moiski.services.IPipelineService;
import com.moiski.services.exceptions.ErrorExecutePipeline;
import com.moiski.services.exceptions.ErrorExecutePipelineStatusFailed;
import com.moiski.services.exceptions.ErrorSavingPipelineServiceExeption;

@Service
@Transactional
public class PipelineServiceImpl implements IPipelineService {

	private static Thread executionPipelineThread;
	private static Thread taskIntegrationThread;
	private static Thread taskDogsThread;
	private static Thread taskPublishThread;

	private static Logger logger = Logger.getLogger(PipelineServiceImpl.class);

	@Autowired
	IPipelineDao pipelineDao;

	@Autowired
	ITaskDao taskDao;

	@Autowired
	IActionDao actionDao;

	@Override
	public void add(Pipeline pipelineContext) throws ErrorSavingPipelineServiceExeption {
		logger.info("Starting method add() : " + getClass().getName());
		try {
			pipelineDao.saveOrUpdate(pipelineContext);
			Pipeline pipeline = pipelineDao.getPipeline(pipelineContext.getName(), pipelineContext.getDescription());
			for (Task task : pipelineContext.getTasks()) {
				task.setPipeline(pipeline);
				taskDao.saveOrUpdate(task);
				Task taskDB = taskDao.getByNameAndPipiline(task.getName(), pipeline.getPipelineId());
				Action action = task.getAction();
				action.setTask(taskDB);
				actionDao.saveOrUpdate(action);
			}
		} catch (DaoExeptions e) {
			logger.error("Error saving user to database: " + PipelineServiceImpl.class, e);
			throw new ErrorSavingPipelineServiceExeption();
		}
	}

	public List<Pipeline> getAllPipeline() {
		logger.info("Starting method getAllPipeline(): " + getClass().getName());
		List<Pipeline> pipelines = new ArrayList<>();
		try {
			pipelines = pipelineDao.getAllPipelines();
		} catch (HibernateException e) {
			logger.error("Error geting all pipeline: " + PipelineServiceImpl.class, e);
		}
		logger.info("Ending method getAllPipeline(): " + getClass().getName());
		return pipelines;
	}

	public void exucutePipeline(Long pipelineId) throws ErrorExecutePipeline {
		logger.info("Starting method exucutePipeline(): " + getClass().getName());
		Pipeline pipeline = null;
		try {
			pipeline = pipelineDao.get(pipelineId);
		} catch (DaoExeptions e) {
			logger.error("Error geting pipeline by pipelineId: " + PipelineServiceImpl.class, e);
		}
		initFieldPipelineBeforeStart(pipeline);
		try {
			for (Task task : pipeline.getTasks()) {
				task.setStatusType(StatusType.PENDING);
				if (task.getName().equals("Build")) {
					pipeline.setStatusType(StatusType.IN_PROGRESS);
					pipeline.setStartTime(new Date());
					executionPipelineThread = createThread(task, pipeline);
				} else if (task.getName().equals("Test")) {
					executionPipelineThread = managerThread(executionPipelineThread, task, pipeline);
				} else if (task.getName().equals("Integration Test")) {
					taskIntegrationThread = managerThread(executionPipelineThread, task, pipeline);
				} else if (task.getName().equals("Docs")) {
					taskDogsThread = managerThread(executionPipelineThread, task, pipeline);
				} else if (task.getName().equals("Publish")) {
					taskPublishThread = managerThread(taskIntegrationThread, task, pipeline);
				} else if (task.getName().equals("Sync")) {
					try {
						taskDogsThread.join();
						executionPipelineThread = managerThread(taskPublishThread, task, pipeline);
					} catch (InterruptedException e) {
						logger.error("The thread will never be interrupt" + task.getName(), e);
					}
				}
			}
		} catch (ErrorExecutePipelineStatusFailed e) {
			logger.error("Error pipeline status FAILED: " + getClass().getName());
			throw new ErrorExecutePipeline();
		} finally {
			try {
				pipelineDao.saveOrUpdate(pipeline);
			} catch (DaoExeptions e) {
				logger.error("Error saving pipeline to DB: " + PipelineServiceImpl.class, e);
			}
		}
		try {
			pipeline.setStatusType(StatusType.COMPLETED);
			pipeline.setEndTime(new Date());
			pipelineDao.saveOrUpdate(pipeline);
		} catch (DaoExeptions e) {
			logger.error("Error saving pipeline to DB: " + PipelineServiceImpl.class, e);
		}

	}

	public void initFieldPipelineBeforeStart(Pipeline pipeline) {
		pipeline.setStatusType(null);
		pipeline.setStartTime(null);
		pipeline.setEndTime(null);
		for (Task task : pipeline.getTasks()) {
			task.setStatusType(null);
			task.setStartTime(null);
			task.setEndTime(null);
		}
	}

	private Thread managerThread(Thread executionThread, Task task, Pipeline pipeline)
			throws ErrorExecutePipelineStatusFailed {
		Thread currentThread = null;
		try {
			executionThread.join();
			if (!pipeline.getStatusType().equals(StatusType.FAILED)) {
				currentThread = createThread(task, pipeline);
			} else {
				throw new ErrorExecutePipelineStatusFailed();
			}
		} catch (InterruptedException e) {
			logger.error("The thread will never be interrupt" + task.getName(), e);
		}
		return currentThread;
	}

	private Thread createThread(Task task, Pipeline pipeline) {
		Thread taskThread = new Thread(new TaskTread(task, pipeline));
		taskThread.start();
		return taskThread;
	}

	public class TaskTread implements Runnable {

		private Task task;
		private Pipeline pipeline;

		public TaskTread(Task task, Pipeline pipeline) {
			super();
			this.task = task;
			this.pipeline = pipeline;
		}

		@Override
		public void run() {
			task.setStatusType(StatusType.IN_PROGRESS);
			task.setStartTime(new Date());
			setTaskStatus(task);
			if (task.getStatusType().equals(StatusType.FAILED)) {
				pipeline.setStatusType(StatusType.FAILED);
			} else {
				task.setEndTime(new Date());
			}
		}

	}

	public void setTaskStatus(Task task) {
		switch (task.getAction().getType()) {
		case PRINT:
			logger.info("Before task name : " + task.getName() + " - " + task.getStatusType());
			task.setStatusType(StatusType.COMPLETED);
			logger.info("After task name : " + task.getName() + " - " + task.getStatusType());
			break;
		case RANDOM:
			logger.info("Before task name : " + task.getName() + " - " + task.getStatusType());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Exeption when action - RANDOM, and tread sleep for one second: " + task.getName(), e);
			}
			task.setStatusType(StatusType.getRandomStatus());
			logger.info("After task name : " + task.getName() + " - " + task.getStatusType());
			break;
		case COMPLETED:
			logger.info("Before task name : " + task.getName() + " - " + task.getStatusType());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Exeption when action - COMPLETED, and tread sleep for one second: " + task.getName(), e);
			}
			task.setStatusType(StatusType.COMPLETED);
			logger.info("After task name : " + task.getName() + " - " + task.getStatusType());
			break;
		case DELAYED:
			logger.info("Before task name : " + task.getName() + " - " + task.getStatusType());
			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				logger.error("Exeption when action - DELAYED, and tread sleep for 10 second: " + task.getName(), e);
			}
			task.setStatusType(StatusType.COMPLETED);
			logger.info("After task name : " + task.getName() + " - " + task.getStatusType());
			break;
		default:
			logger.error("In the status is not the correct action type" + task.getName());
			break;
		}
	}

	public void delete(Long pipelineId) {
		logger.info("Starting method delete(): " + getClass().getName());
		Pipeline pipeline = null;
		try {
			pipeline = pipelineDao.get(pipelineId);
			pipelineDao.delete(pipeline);
		} catch (DaoExeptions e) {
			logger.error("Error geting pipeline by pipelineId: " + PipelineServiceImpl.class, e);
		}
	}
	

}
