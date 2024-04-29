//package Backend.RelationalAlgebra;
//
//import org.apache.calcite.rel.RelRoot;
//import org.apache.calcite.rel.RelNode;
//import org.apache.calcite.sql.SqlNode;
//import org.apache.calcite.sql.parser.SqlParser;
//import org.apache.calcite.tools.Frameworks;
//import org.apache.calcite.tools.Planner;
//import org.apache.calcite.config.Lex;
//import org.apache.calcite.sql.parser.SqlParseException;
//import org.apache.calcite.tools.ValidationException;
//import org.apache.calcite.tools.RelConversionException;
//
//public class RelationalAlgebraGenerator {
//    public static void main(String[] args) {
//        try {
//            Planner planner = Frameworks.getPlanner(Frameworks.newConfigBuilder()
//                    .parserConfig(SqlParser.config().withLex(Lex.MYSQL))
//                    .build());
//
//            String[] sqlQueries = {
//                    "SELECT * FROM Employees",
//                    "SELECT name, department FROM Employees WHERE age > 30",
//                    "SELECT department, COUNT(*) FROM Employees GROUP BY department"
//            };
//
//            for (String sql : sqlQueries) {
//                generateTree(planner, sql);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void generateTree(Planner planner, String sql) throws SqlParseException, ValidationException, RelConversionException {
//        SqlNode sqlNode = planner.parse(sql);
//        SqlNode validatedSqlNode = planner.validate(sqlNode);
//        RelRoot relRoot = planner.rel(validatedSqlNode);
//        RelNode relNode = relRoot.project();
//
//        System.out.println("Relational Algebra Tree for SQL Query: " + sql);
//        System.out.println(relNode.explain());
//    }
//}
