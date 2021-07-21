package me.too.generator.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "generator")
@Data
public class GeneratorConfig {
	private String tableSchema;
//	private String templateFolder;
	private String baseFolder;
	private String sqlFolder;
	private String pageFolder;
	private String apiFolder;
	private String mappingFolder;
	private String javaSourceFolder;
	private String mapperPkg;
	private String modelPkg;
	private String servicePkg;
	private String serviceImplPkg;
	private String controllerPkg;
}
