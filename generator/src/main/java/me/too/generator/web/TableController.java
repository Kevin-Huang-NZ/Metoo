package me.too.generator.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.too.generator.dao.ITableDao;
import me.too.generator.service.Generator;
import me.too.generator.service.GeneratorConfig;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class TableController extends BaseController {
	Logger logger = LoggerFactory.getLogger(TableController.class);
	
	@Autowired 
	private ITableDao tableDao;
	@Autowired 
	private GeneratorConfig generatorConfig;
	@Autowired 
	private Generator generator;


	@GetMapping(value = { "/tables" })
	public CommonReturnType listTable(String schemaName) throws Exception {

		return CommonReturnType.create(tableDao.selectTable(schemaName));
	}

	@PostMapping(value = { "/generate" })
	public CommonReturnType generate(@RequestParam(value = "schemaName") String schemaName, @RequestParam(value = "tables[]") List<String> tables) throws Exception {
		for (String table : tables) {
			generator.generate(schemaName, table);
		}
		return CommonReturnType.create(null);
	}

	@GetMapping(value = { "/properties" })
	public CommonReturnType getProperties() throws Exception {

		return CommonReturnType.create(generatorConfig);
	}
}
