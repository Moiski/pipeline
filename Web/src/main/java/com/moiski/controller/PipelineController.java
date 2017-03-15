package com.moiski.controller;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.moiski.dao.entities.Pipeline;
import com.moiski.dao.entities.Task;
import com.moiski.services.IPipelineService;
import com.moiski.services.exceptions.ErrorExecutePipeline;
import com.moiski.services.exceptions.ErrorSavingPipelineServiceExeption;
import com.moiski.utilits.ConfigurationManager;
import com.moiski.utilits.YamlSerializator;

@Controller
@RequestMapping("/pipelines")
public class PipelineController {

	private static Logger logger = Logger.getLogger(PipelineController.class);
	private static final String ATTRIBUTE_MESSAGE = "message";
	private static final String ATTRIBUTE_PATH = "path";
	private static final String PATH_FRAGMENT_ALL_PIPELINE = "path.fragment.all.pipeline";

	@Autowired
	IPipelineService pipelineService;

	@Autowired
	private MessageSource message;

	@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView newPipeline(@RequestParam("context") String context, ModelMap model, Locale locale,
			ModelAndView modelAndView) {
		Pipeline pipeline = (Pipeline) YamlSerializator.yamlFileToObject(context, Pipeline.class);
		try {
			pipelineService.add(pipeline);
		} catch (ErrorSavingPipelineServiceExeption e) {
			logger.error("Error saving topic to database: " + getClass().getName());
			model.put(ATTRIBUTE_MESSAGE, getMessage("message.error.add.pipeline", locale));
			modelAndView.setViewName("main");
			return modelAndView;
		}
		model.put(ATTRIBUTE_MESSAGE, getMessage("message.add.pipeline.to.db.ok", locale));
		modelAndView.setViewName("main");
		return modelAndView;
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getPipelines(ModelMap model, Locale locale, ModelAndView modelAndView) {
		List<Pipeline> pipelines = pipelineService.getAllPipeline();
		if (pipelines.isEmpty()) {
			model.put(ATTRIBUTE_MESSAGE, getMessage("message.get.all.pipeline.empty", locale));
			modelAndView.setViewName("main");
			return modelAndView;
		}
		model.addAttribute("pipelinelist", pipelines);
		model.addAttribute("pipeline", new Pipeline());
		model.addAttribute("task", new Task());
		model.addAttribute(ATTRIBUTE_PATH, ConfigurationManager.getProperty(PATH_FRAGMENT_ALL_PIPELINE));
		modelAndView.setViewName("main");
		return modelAndView;
	}
	
	@RequestMapping(value = "/start/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView startPipeline(@PathVariable("id") Long pipelineId, ModelMap model, Locale locale, ModelAndView modelAndView) {
		try {
			pipelineService.exucutePipeline(pipelineId);
		} catch (ErrorExecutePipeline e) {
			logger.error("Error pipeline status FAILED:  " + PipelineController.class, e);
		}
		model.put(ATTRIBUTE_MESSAGE, getMessage("message.start.pipeline.result", locale));
		modelAndView.setViewName("main");
		return modelAndView;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView deletePipeline(@PathVariable("id") Long pipelineId, ModelAndView modelAndView, ModelMap model, Locale locale){
		pipelineService.delete(pipelineId);
		model.put(ATTRIBUTE_MESSAGE, getMessage("message.delete.pipeline.result", locale));
		modelAndView.setViewName("main");
		return modelAndView;	
	}

	private String getMessage(String key, Locale locale) {
		return message.getMessage(key, null, locale);
	}

}
