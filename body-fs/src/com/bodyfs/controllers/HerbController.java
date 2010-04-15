package com.bodyfs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bodyfs.dao.IHerbDAO;
import com.bodyfs.model.Diagnosis;
import com.bodyfs.model.Herb;
import com.bodyfs.model.HerbFormula;

@Controller
@RequestMapping("/herb")
public class HerbController {

	@Autowired
	private IHerbDAO herbDAO;

	public void setHerbDAO(IHerbDAO herbDAO) {
		this.herbDAO = herbDAO;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody
	List<Herb> getHerbs() {
		return herbDAO.getHerbs();
	}

	@RequestMapping(value = "/formulas", method = RequestMethod.GET)
	public @ResponseBody
	List<HerbFormula> getFormulas() {
		return herbDAO.getFormulas();
	}

	@RequestMapping(value = "/diagnoses", method = RequestMethod.GET)
	public @ResponseBody
	List<Diagnosis> getDiagnoses() {
		return herbDAO.getDiagnoses();
	}
}
