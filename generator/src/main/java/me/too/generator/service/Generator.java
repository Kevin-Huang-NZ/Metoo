package me.too.generator.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import me.too.generator.dao.ITableDao;
import me.too.generator.model.Column;
import me.too.generator.model.Constraint;
import me.too.generator.model.ConstraintColumn;
import me.too.generator.model.Table;
import me.too.generator.util.MakeDir;

@Component
public class Generator {
	Logger logger = LoggerFactory.getLogger(Generator.class);
	
	public static final String CONSTRAINT_TYPE_UK = "UNIQUE";

	@Autowired
	private Configuration freeMarkerCfg;

	@Autowired
	private GeneratorConfig generatorConfig;
	
	@Autowired 
	private ITableDao tableDao;
	
	@Autowired 
	private DataTypeMapper dataTypeMapper;

	private Table table;
	private List<Column> columns;
//	private List<Constraint> constraints;
	private Constraint firstConstraints;
	private List<Column> constraintColumns;
	
	// 数据表名称原始格式：aaa_bbb
	private String tableName;
	// 数据表名称大写驼峰格式：AaaBbb
	private String ucTableName;
	// 数据表名称小写驼峰格式：aaaBbb
	private String lcTableName;
	// 数据表名称中划线格式：aaa-bbb
	private String lhTableName;

	// 数据表名称中划线格式：baseFolder + javaSourceFolder
	private String srcMainJava;

	public void generate(String schemaName, String tn) {
		this.tableName = NameConverter.lowerCase(tn);
		this.ucTableName = NameConverter.upperCamel(this.tableName);
		this.lcTableName = NameConverter.lowerCamel(this.tableName);
		this.lhTableName = NameConverter.lowerHyphen(this.tableName);
		this.srcMainJava = generatorConfig.getBaseFolder() + "/" + generatorConfig.getJavaSourceFolder();
		// 检索表信息
		table = tableDao.selectTableByName(schemaName, tn);
		
		// 检索表的字段信息，过滤掉id字段
		List<Column> tempColumns = tableDao.selectColumn(schemaName, tn);
		this.columns = new ArrayList<Column>();
		for (Column column : tempColumns) {
			if (!StringUtils.equals("id", column.getColumnName())) {
				this.columns.add(column);
			}
		}
		
		// 检索表的唯一索引,只取第一个
		List<Constraint> constraints = tableDao.selectConstraint(schemaName, tn, Generator.CONSTRAINT_TYPE_UK);
		if (constraints != null && constraints.size() > 0) {
			firstConstraints = constraints.get(0);
		}
		
		if (firstConstraints != null) {
			// 有唯一索引，检索该索引的字段
			this.constraintColumns = new ArrayList<Column>();
			List<ConstraintColumn> tempConstraintColumn = tableDao.selectConstraintColumn(schemaName, tn, firstConstraints.getConstraintName());
			for (ConstraintColumn constraintColumn : tempConstraintColumn) {
				for (Column column : columns) {
					if (StringUtils.equals(constraintColumn.getColumnName(), column.getColumnName())) {
						constraintColumns.add(column);
						break;
					}
				}
			}
		}
		this.generateModel();
		this.generateMapperXml();
		this.generateMapperJava();
		this.generateService();
		this.generateServiceImpl();
		this.generateController();
		this.generateApi();
		this.generatePage();
		this.generateAuthData();
	}

	private void generateModel() {
		OutputStreamWriter out = null;
		try {
			Template template = freeMarkerCfg.getTemplate("model.ftl");

			Map<String, Object> root = new HashMap<String, Object>();

			root.put("modelPkg", generatorConfig.getModelPkg());
			root.put("modelName", this.ucTableName);
			
			List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
			for(Column column : this.columns) {
				Map<String, Object> fieldMap = new HashMap<String, Object>();

				fieldMap.put("ucColumnName", NameConverter.upperCamel(column.getColumnName()));
				fieldMap.put("lcColumnName", NameConverter.lowerCamel(column.getColumnName()));
				fieldMap.put("javaDataType", dataTypeMapper.getJavaDataType(NameConverter.lowerCase(column.getDataType())));
				
				fields.add(fieldMap);
			}
			root.put("fields", fields);
			
			String outputFolder = MakeDir.makeByPackage(generatorConfig.getModelPkg(), this.srcMainJava);
			String fileName = outputFolder + "/" + this.ucTableName + ".java";
			out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			template.process(root, out);

			out.close();
		} catch (Exception e) {
			logger.error("Failed to get template.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void generateMapperXml() {
		OutputStreamWriter out = null;
		try {
			Template template = freeMarkerCfg.getTemplate("mapper-xml.ftl");

			Map<String, Object> root = new HashMap<String, Object>();

			root.put("tableName", this.tableName);
			root.put("mapperPkg", generatorConfig.getMapperPkg());
			root.put("mapperName", NameConverter.getMapperName(this.ucTableName));
			root.put("modelPkg", generatorConfig.getModelPkg());
			root.put("modelName", this.ucTableName);

			List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
			for(Column column : this.columns) {
				Map<String, Object> fieldMap = new HashMap<String, Object>();

				fieldMap.put("columnName", column.getColumnName());
				fieldMap.put("columnNameWithPrefix", NameConverter.getColumnNameWithPrefix(column.getColumnName()));
				fieldMap.put("lcColumnName", NameConverter.lowerCamel(column.getColumnName()));
				fieldMap.put("jdbcType", dataTypeMapper.getJdbcDataType(NameConverter.lowerCase(column.getDataType())));
				
				fields.add(fieldMap);
			}
			root.put("fields", fields);
			
			if (firstConstraints != null) {
				root.put("hasUniqueKey", true);

				List<Map<String, Object>> ukFields = new ArrayList<Map<String, Object>>();
				for(Column column : this.constraintColumns) {
					Map<String, Object> fieldMap = new HashMap<String, Object>();

					fieldMap.put("columnName", column.getColumnName());
					fieldMap.put("columnNameWithPrefix", NameConverter.getColumnNameWithPrefix(column.getColumnName()));
					fieldMap.put("lcColumnName", NameConverter.lowerCamel(column.getColumnName()));
					fieldMap.put("jdbcType", dataTypeMapper.getJdbcDataType(NameConverter.lowerCase(column.getDataType())));
					
					ukFields.add(fieldMap);
				}
				
				root.put("ukFields", ukFields);
			} else {
				root.put("hasUniqueKey", false);
			}
			
			String outputFolder = MakeDir.make(generatorConfig.getMappingFolder(), generatorConfig.getBaseFolder());
			String fileName = outputFolder + "/" + NameConverter.getMapperName(this.ucTableName) + ".xml";
			out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			template.process(root, out);

			out.close();
		} catch (Exception e) {
			logger.error("Failed to get template.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void generateMapperJava() {
		OutputStreamWriter out = null;
		try {
			Template template = freeMarkerCfg.getTemplate("mapper-java.ftl");

			Map<String, Object> root = new HashMap<String, Object>();

			root.put("mapperPkg", generatorConfig.getMapperPkg());
			root.put("mapperName", NameConverter.getMapperName(this.ucTableName));
			root.put("modelPkg", generatorConfig.getModelPkg());
			root.put("modelName", this.ucTableName);
			
			if (firstConstraints != null) {
				root.put("hasUniqueKey", true);

				List<Map<String, Object>> ukFields = new ArrayList<Map<String, Object>>();
				for(Column column : this.constraintColumns) {
					Map<String, Object> fieldMap = new HashMap<String, Object>();

					fieldMap.put("lcColumnName", NameConverter.lowerCamel(column.getColumnName()));
					fieldMap.put("javaDataType", dataTypeMapper.getJavaDataType(NameConverter.lowerCase(column.getDataType())));
					
					ukFields.add(fieldMap);
				}
				
				root.put("ukFields", ukFields);
			} else {
				root.put("hasUniqueKey", false);
			}
			
			String outputFolder = MakeDir.makeByPackage(generatorConfig.getMapperPkg(), this.srcMainJava);
			String fileName = outputFolder + "/" + NameConverter.getMapperName(this.ucTableName) + ".java";
			out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			template.process(root, out);

			out.close();
		} catch (Exception e) {
			logger.error("Failed to get template.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void generateService() {
		OutputStreamWriter out = null;
		try {
			Template template = freeMarkerCfg.getTemplate("service.ftl");

			Map<String, Object> root = new HashMap<String, Object>();

			root.put("servicePkg", generatorConfig.getServicePkg());
			root.put("serviceName", NameConverter.getServiceName(this.ucTableName));
			root.put("modelPkg", generatorConfig.getModelPkg());
			root.put("modelName", this.ucTableName);
			root.put("lcTableName", this.lcTableName);
			
			if (firstConstraints != null) {
				root.put("hasUniqueKey", true);

				List<Map<String, Object>> ukFields = new ArrayList<Map<String, Object>>();
				for(Column column : this.constraintColumns) {
					Map<String, Object> fieldMap = new HashMap<String, Object>();

					fieldMap.put("lcColumnName", NameConverter.lowerCamel(column.getColumnName()));
					fieldMap.put("javaDataType", dataTypeMapper.getJavaDataType(NameConverter.lowerCase(column.getDataType())));
					
					ukFields.add(fieldMap);
				}
				
				root.put("ukFields", ukFields);
			} else {
				root.put("hasUniqueKey", false);
			}
			
			String outputFolder = MakeDir.makeByPackage(generatorConfig.getServicePkg(), this.srcMainJava);
			String fileName = outputFolder + "/" + NameConverter.getServiceName(this.ucTableName) + ".java";
			out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			template.process(root, out);

			out.close();
		} catch (Exception e) {
			logger.error("Failed to get template.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void generateServiceImpl() {
		OutputStreamWriter out = null;
		try {
			Template template = freeMarkerCfg.getTemplate("service-impl.ftl");

			Map<String, Object> root = new HashMap<String, Object>();

			root.put("serviceImplPkg", generatorConfig.getServiceImplPkg());
			root.put("serviceImplName", NameConverter.getServiceImplName(this.ucTableName));
			root.put("servicePkg", generatorConfig.getServicePkg());
			root.put("serviceName", NameConverter.getServiceName(this.ucTableName));
			root.put("mapperPkg", generatorConfig.getMapperPkg());
			root.put("mapperName", NameConverter.getMapperName(this.ucTableName));
			root.put("modelPkg", generatorConfig.getModelPkg());
			root.put("modelName", this.ucTableName);
			root.put("lcTableName", this.lcTableName);

			List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
			for(Column column : this.columns) {
				Map<String, Object> fieldMap = new HashMap<String, Object>();

				fieldMap.put("ucColumnName", NameConverter.upperCamel(column.getColumnName()));
				fieldMap.put("lcColumnName", NameConverter.lowerCamel(column.getColumnName()));
				fieldMap.put("javaDataType", dataTypeMapper.getJavaDataType(NameConverter.lowerCase(column.getDataType())));
				
				fields.add(fieldMap);
			}
			root.put("fields", fields);
			
			if (firstConstraints != null) {
				root.put("hasUniqueKey", true);

				List<Map<String, Object>> ukFields = new ArrayList<Map<String, Object>>();
				for(Column column : this.constraintColumns) {
					Map<String, Object> fieldMap = new HashMap<String, Object>();

					fieldMap.put("ucColumnName", NameConverter.upperCamel(column.getColumnName()));
					fieldMap.put("lcColumnName", NameConverter.lowerCamel(column.getColumnName()));
					fieldMap.put("javaDataType", dataTypeMapper.getJavaDataType(NameConverter.lowerCase(column.getDataType())));
					
					ukFields.add(fieldMap);
				}
				
				root.put("ukFields", ukFields);
			} else {
				root.put("hasUniqueKey", false);
			}
			
			String outputFolder = MakeDir.makeByPackage(generatorConfig.getServiceImplPkg(), this.srcMainJava);
			String fileName = outputFolder + "/" + NameConverter.getServiceImplName(this.ucTableName) + ".java";
			out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			template.process(root, out);

			out.close();
		} catch (Exception e) {
			logger.error("Failed to get template.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void generateController() {
		OutputStreamWriter out = null;
		try {
			Template template = freeMarkerCfg.getTemplate("controller.ftl");

			Map<String, Object> root = new HashMap<String, Object>();

			root.put("controllerPkg", generatorConfig.getControllerPkg());
			root.put("controllerName", NameConverter.getControllerName(this.ucTableName));
			root.put("servicePkg", generatorConfig.getServicePkg());
			root.put("serviceName", NameConverter.getServiceName(this.ucTableName));
			root.put("modelPkg", generatorConfig.getModelPkg());
			root.put("modelName", this.ucTableName);
			root.put("lcTableName", this.lcTableName);
			root.put("lowerTableName", NameConverter.lowerCase(this.lcTableName));

			List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
			for(Column column : this.columns) {
				Map<String, Object> fieldMap = new HashMap<String, Object>();

				fieldMap.put("ucColumnName", NameConverter.upperCamel(column.getColumnName()));
				fieldMap.put("lcColumnName", NameConverter.lowerCamel(column.getColumnName()));
				fieldMap.put("javaDataType", dataTypeMapper.getJavaDataType(NameConverter.lowerCase(column.getDataType())));
				fieldMap.put("isNullable", NameConverter.upperCase(column.getIsNullable()));
				fieldMap.put("characterMaximumLength", column.getCharacterMaximumLength());
				fieldMap.put("columnTitle", this.getColumnTitle(column.getColumnName(), column.getColumnComment()));
				
				fields.add(fieldMap);
			}
			root.put("fields", fields);
			
			String outputFolder = MakeDir.makeByPackage(generatorConfig.getControllerPkg(), this.srcMainJava);
			String fileName = outputFolder + "/" + NameConverter.getControllerName(this.ucTableName) + ".java";
			out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			template.process(root, out);

			out.close();
		} catch (Exception e) {
			logger.error("Failed to get template.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void generatePage() {
		OutputStreamWriter out = null;
		try {
			Template template = freeMarkerCfg.getTemplate("page.ftl");

			Map<String, Object> root = new HashMap<String, Object>();
			root.put("ucTableName", this.ucTableName);
			root.put("lowerTableName", NameConverter.lowerCase(this.lcTableName));
			root.put("lhTableName", this.lhTableName);

			List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();
			for(Column column : this.columns) {
				Map<String, Object> fieldMap = new HashMap<String, Object>();

				fieldMap.put("ucColumnName", NameConverter.upperCamel(column.getColumnName()));
				fieldMap.put("lcColumnName", NameConverter.lowerCamel(column.getColumnName()));
				fieldMap.put("isNullable", NameConverter.upperCase(column.getIsNullable()));
				if (StringUtils.equals(dataTypeMapper.getJavaDataType(NameConverter.lowerCase(column.getDataType())), "String")) {
					fieldMap.put("maxLen", column.getCharacterMaximumLength());
				} else {
					fieldMap.put("maxLen", null);
				}
				fieldMap.put("columnTitle", this.getColumnTitle(column.getColumnName(), column.getColumnComment()));
				
				fields.add(fieldMap);
			}
			root.put("fields", fields);
			
			String outputFolder = MakeDir.make(generatorConfig.getPageFolder(), generatorConfig.getBaseFolder());
			String fileName = outputFolder + "/" + this.lhTableName + ".vue";
			out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			template.process(root, out);

			out.close();
		} catch (Exception e) {
			logger.error("Failed to get template.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void generateApi() {
		OutputStreamWriter out = null;
		try {
			Template template = freeMarkerCfg.getTemplate("api.ftl");

			Map<String, Object> root = new HashMap<String, Object>();
			root.put("ucTableName", this.ucTableName);
			root.put("lowerTableName", NameConverter.lowerCase(this.lcTableName));

			String outputFolder = MakeDir.make(generatorConfig.getApiFolder(), generatorConfig.getBaseFolder());
			String fileName = outputFolder + "/" + this.lhTableName + ".js";
			out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			template.process(root, out);

			out.close();
		} catch (Exception e) {
			logger.error("Failed to get template.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private void generateAuthData() {
		OutputStreamWriter out = null;
		try {
			Template template = freeMarkerCfg.getTemplate("auth-sql.ftl");

			Map<String, Object> root = new HashMap<String, Object>();
			root.put("tableTitle", this.getTableTitle(this.table.getTableName(), this.table.getTableComment()));
			root.put("lowerTableName", NameConverter.lowerCase(this.lcTableName));

			String outputFolder = MakeDir.make(generatorConfig.getSqlFolder(), generatorConfig.getBaseFolder());
			String fileName = outputFolder + "/" + this.lhTableName + ".sql";
			out = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
			template.process(root, out);

			out.close();
		} catch (Exception e) {
			logger.error("Failed to get template.", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private String getTableTitle(String tableName, String tableComment) {
		if (StringUtils.isEmpty(tableComment)) {
			return tableName;
		}
		
		return tableComment;
	}
	
	private String getColumnTitle(String columnName, String columnComment) {
		if (StringUtils.isEmpty(columnComment)) {
			return columnName;
		}
		String[] splited = null;
		if (columnComment.indexOf("：") > 0) {
			splited = columnComment.split("：");
		}
		if (columnComment.indexOf(":") > 0) {
			splited = columnComment.split(":");
		}
		if (splited == null || splited.length == 0) {
			return columnComment;
		} else {
			return splited[0];
		}
	}
}
