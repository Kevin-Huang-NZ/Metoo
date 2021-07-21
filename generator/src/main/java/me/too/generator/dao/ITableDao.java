package me.too.generator.dao;

import java.util.List;

import me.too.generator.model.Column;
import me.too.generator.model.Constraint;
import me.too.generator.model.ConstraintColumn;
import me.too.generator.model.Table;

public interface ITableDao {
	List<Table> selectTable(String schemaName);
	Table selectTableByName(String schemaName, String tableName);
	List<Column> selectColumn(String schemaName, String tableName);
	List<Constraint> selectConstraint(String schemaName, String tableName, String constraintType);
	List<ConstraintColumn> selectConstraintColumn(String schemaName, String tableName, String constraintName);
}
