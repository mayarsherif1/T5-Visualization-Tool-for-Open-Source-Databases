package Backend.RelationalAlgebra;

import Backend.Database.Database;

import java.util.List;

public class SQLToAlgebraConverter {
    private Database database;

    public SQLToAlgebraConverter(Database database) {
        this.database = database;
    }

    private RelationalAlgebraNode parseSQLToAlgebra(String sql) {
        sql = sql.toLowerCase();
        if (sql.contains("select") && sql.contains("from")) {
            if (sql.contains("join")) {
                return parseJoin(sql);
            } else if (sql.contains("group by")) {
                return parseGroupBy(sql);
            } else {
                return parseSelectFrom(sql);
            }
        }
        return new TableNode("Unknown", database);
    }

    private RelationalAlgebraNode parseSelectFrom(String sql) {
        String[] parts = sql.split("from");
        String[] projections = parts[0].split("select")[1].trim().split(",");
        String tableName = parts[1].trim().split(" ")[0];
        String whereClause = parts[1].contains("where") ? parts[1].split("where")[1].trim() : "true";

        RelationalAlgebraNode tableNode = new TableNode(tableName, database);
        SelectionNode selectionNode = new SelectionNode(whereClause, tableNode);
        return new ProjectionNode(List.of(projections), selectionNode);
    }

    private RelationalAlgebraNode parseJoin(String sql) {
        String[] parts = sql.split("join");
        RelationalAlgebraNode leftTable = parseSelectFrom(parts[0]);
        RelationalAlgebraNode rightTable = parseSelectFrom(parts[1].split("on")[0]);

        String joinCondition = parts[1].split("on")[1].trim();
        return new JoinNode(leftTable, rightTable, joinCondition);
    }

    private RelationalAlgebraNode parseGroupBy(String sql) {
        String[] parts = sql.split("group by");
        RelationalAlgebraNode baseNode = parseSelectFrom(parts[0]);
        String[] groupColumns = parts[1].trim().split(",");
        return new GroupByNode(baseNode, List.of(groupColumns), "Sample Aggregate Function");
    }

}
