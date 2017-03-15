package com.moiski.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.moiski.utilits.ConfigurationManager;

@Controller
public class PathController {
	
	private static final String ATTRIBUTE_PATH = "path";
	private static final String PATH_FRAGMENT_ADD_PIPELINE = "path.fragment.add.pipeline";
	
	@RequestMapping(value ="/action/getpipelineform", method = RequestMethod.GET)
	public ModelAndView createNewTopic(Model model){
		ModelAndView modelAndView = new ModelAndView();
		model.addAttribute(ATTRIBUTE_PATH, ConfigurationManager.getProperty(PATH_FRAGMENT_ADD_PIPELINE));
		modelAndView.setViewName("main");
		return modelAndView;
	}

}
