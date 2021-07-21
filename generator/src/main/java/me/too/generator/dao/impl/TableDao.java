package me.too.generator.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import me.too.generator.dao.ITableDao;
import me.too.generator.model.Column;
import me.too.generator.model.Constraint;
import me.too.generator.model.ConstraintColumn;
import me.too.generator.model.Table;
import me.too.generator.service.NameConverter;

@Component
public class TableDao implements ITableDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Table> selectTable(String schemaName) {
		String sql = "select `TABLE_NAME`, `TABLE_COMMENT` from `information_schema`.`TABLES` where `TABLE_SCHEMA`=?";
		return jdbcTemplate.query(sql, (ResultSet rs) -> {
		    List<Table> list = new ArrayList<Table>();  
		    while(rs.next()){
		    	Table t = new Table();
		    	t.setTableName(NameConverter.lowerCase(rs.getString("TABLE_NAME")));
		    	t.setTableComment(rs.getString("TABLE_COMMENT"));
		    	list.add(t);
		    }
		    return list;
		}, schemaName);
	}

	@Override
	public Table selectTableByName(String schemaName, String tableName) {
		String sql = "select `TABLE_NAME`, `TABLE_COMMENT` from `information_schema`.`TABLES` where `TABLE_SCHEMA`=? and `TABLE_NAME`=?";
		return jdbcTemplate.query(sql, (ResultSet rs) -> {
		    Table t = null;  
		    if (rs.next()){
		    	t = new Table();
		    	t.setTableName(NameConverter.lowerCase(rs.getString("TABLE_NAME")));
		    	t.setTableComment(rs.getString("TABLE_COMMENT"));
		    }
		    return t;
		}, schemaName, tableName);
	}

	@Override
	public List<Column> selectColumn(String schemaName, String tableName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select `ORDINAL_POSITION`,`COLUMN_NAME`,`DATA_TYPE`,`IS_NULLABLE`,`COLUMN_DEFAULT`,");
		sb.append("`CHARACTER_MAXIMUM_LENGTH`,`CHARACTER_OCTET_LENGTH`,");
		sb.append("`NUMERIC_PRECISION`,`NUMERIC_SCALE`,");
		sb.append("`DATETIME_PRECISION`,");
		sb.append("`COLUMN_KEY`,`EXTRA`,");
		sb.append("`COLUMN_COMMENT`");
		sb.append("from `information_schema`.`COLUMNS` where `TABLE_SCHEMA`=? and `TABLE_NAME`=? order by `ORDINAL_POSITION`");
		return jdbcTemplate.query(sb.toString(), (ResultSet rs) -> {
		    List<Column> list = new ArrayList<Column>();  
		    while(rs.next()){
		    	Column t = new Column();
		    	t.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
		    	t.setColumnName(NameConverter.lowerCase(rs.getString("COLUMN_NAME")));
		    	t.setDataType(NameConverter.lowerCase(rs.getString("DATA_TYPE")));
		    	t.setIsNullable(NameConverter.lowerCase(rs.getString("IS_NULLABLE")));
		    	t.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
		    	t.setCharacterMaximumLength(rs.getLong("CHARACTER_MAXIMUM_LENGTH"));
		    	t.setCharacterOctetLength(rs.getLong("CHARACTER_OCTET_LENGTH"));
		    	t.setNumericPrecision(rs.getLong("NUMERIC_PRECISION"));
		    	t.setNumericScale(rs.getLong("NUMERIC_SCALE"));
		    	t.setDatetimePrecision(rs.getInt("DATETIME_PRECISION"));
		    	t.setColumnKey(NameConverter.lowerCase(rs.getString("COLUMN_KEY")));
		    	t.setExtra(NameConverter.lowerCase(rs.getString("EXTRA")));
		    	t.setColumnComment(rs.getString("COLUMN_COMMENT"));
		    	list.add(t);
		    }
		    return list;
		}, schemaName, tableName);
	}

	@Override
	public List<Constraint> selectConstraint(String schemaName, String tableName, String constraintType) {
		String sql = "select `CONSTRAINT_NAME`, `CONSTRAINT_TYPE` from `information_schema`.`TABLE_CONSTRAINTS` where `TABLE_SCHEMA`=? and `TABLE_NAME`=? and `CONSTRAINT_TYPE`=?";
		return jdbcTemplate.query(sql, (ResultSet rs) -> {
		    List<Constraint> list = new ArrayList<Constraint>();  
		    while(rs.next()){
		    	Constraint t = new Constraint();
		    	t.setConstraintName(NameConverter.lowerCase(rs.getString("CONSTRAINT_NAME")));
		    	t.setConstraintType(NameConverter.lowerCase(rs.getString("CONSTRAINT_TYPE")));
		    	list.add(t);
		    }
		    return list;
		}, schemaName, tableName, constraintType);
	}

	@Override
	public List<ConstraintColumn> selectConstraintColumn(String schemaName, String tableName, String constraintName) {

		StringBuffer sb = new StringBuffer();
		sb.append("select `ORDINAL_POSITION`,`COLUMN_NAME`");
		sb.append("from `information_schema`.`KEY_COLUMN_USAGE` where `TABLE_SCHEMA`=? and `TABLE_NAME`=? and `CONSTRAINT_NAME`=? order by `ORDINAL_POSITION`");
		return jdbcTemplate.query(sb.toString(), (ResultSet rs) -> {
		    List<ConstraintColumn> list = new ArrayList<ConstraintColumn>();  
		    while(rs.next()){
		    	ConstraintColumn t = new ConstraintColumn();
		    	t.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
		    	t.setColumnName(NameConverter.lowerCase(rs.getString("COLUMN_NAME")));
		    	list.add(t);
		    }
		    return list;
		}, schemaName, tableName, constraintName);
	}

}
