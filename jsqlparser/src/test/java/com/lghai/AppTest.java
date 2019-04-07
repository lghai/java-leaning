package com.lghai;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.SelectUtils;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {

        assertTrue(true);
    }

    /**
     * 测试查询返回增加一列
     */
    @Test
    public void testAddSelectColumn() throws Exception {
        Select select = (Select) CCJSqlParserUtil.parse("select name from user where id = 1");
        SelectUtils.addExpression(select, new Column("mail"));
        System.out.println(select.toString());
        Assert.assertEquals(select.toString(), "SELECT name, mail FROM user WHERE id = 1");
    }

    /**
     * 测试查询语句增加where条件
     */
    @Test
    public void testAddWhereCondition() throws Exception {
        Select select = (Select) CCJSqlParserUtil.parse("select name from user");
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        if (plainSelect.getWhere() == null) {
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new Column("id"));
            equalsTo.setRightExpression(new LongValue(1000L));
            plainSelect.setWhere(equalsTo);
        }
        Assert.assertEquals(select.toString(), "SELECT name FROM user WHERE id = 1000");
    }

    /**
     * 测试增加where查询条件
     */
    @Test
    public void testAddCondition() throws Exception {
        Select select = (Select) CCJSqlParserUtil.parse("select name from user where id = 1000;\r\n-- shilie");
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        // 原where表达式
        Expression where = plainSelect.getWhere();
        // 新增的查询条件表达式
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new Column("name"));
        equalsTo.setRightExpression(new StringValue("'张三'"));
        // 用and链接条件
        AndExpression and = new AndExpression(where, equalsTo);
        // 设置新的where条件
        plainSelect.setWhere(and);

        Assert.assertEquals(select.toString(), "SELECT name FROM user WHERE id = 1000 AND name = '张三'");
    }

    /**
     * 测试null条件
     */
    @Test
    public void testNullCondition() throws Exception {
        Select select = (Select) CCJSqlParserUtil.parse("select name, array['刚刚'] from user where id = 1000");
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

        // 原where表达式
        Expression where = plainSelect.getWhere();
        // 新增的null判断条件
        IsNullExpression isNullExpression = new IsNullExpression();
        isNullExpression.setLeftExpression(new Column("name"));
        isNullExpression.setNot(true);
        // 用and链接条件
        AndExpression and = new AndExpression(where, isNullExpression);
        // 设置新的where条件
        plainSelect.setWhere(and);

        Assert.assertEquals(select.toString(), "SELECT name FROM user WHERE id = 1000 AND name IS NOT NULL");
    }

    /**
     * 解析join条件
     */
    @Test
    public void testParseJoin() throws Exception {

        String sql = "select distinct *from A as a left join B on a.bid = B.id left join C on A.cid = C.id left join D on B.did = D.id";
        System.out.println(new SQLFormatter().format(sql));
        try {

            Select select = (Select) CCJSqlParserUtil.parse(sql);

            SelectBody selectBody = select.getSelectBody();

            PlainSelect plainSelect = (PlainSelect) selectBody;

            List<Join> joins = plainSelect.getJoins();

            for (Join join : joins) {

                EqualsTo equalsTo = (EqualsTo) join.getOnExpression();
                
                Column leftExpression = (Column) equalsTo.getLeftExpression();
                FromItem Item = join.getRightItem();
                Table item1 = new Table("DAS","LOG");
                System.out.println(item1.toString());
                Column rightExpression = (Column) equalsTo.getRightExpression();


                System.out.println("left table name:" + leftExpression.getTable());

                System.out.println("left table field:" + leftExpression.getColumnName());

                System.out.println("right table name:" + rightExpression.getTable());

                System.out.println("right table field:" + rightExpression.getColumnName());

                System.out.println("---------------------");

            }



        } catch (JSQLParserException e) {

            e.printStackTrace();

        }

    }

    /**
     * test SelectUtils 快速建立select语句
     * @throws Exception
     */
    @Test
    public void testselectutils() throws Exception {
        Table tbl1 = new Table("TABLE1");
        tbl1.setAlias(new Alias("T"));

        Select select1 = SelectUtils.buildSelectFromTable(tbl1);
        System.out.println(select1.toString());//SELECT * FROM TABLE1

        SelectExpressionItem item = new SelectExpressionItem(new Column(tbl1,"A"));
        item.setAlias(new Alias("\"a\""));
        PlainSelect selectBody = (PlainSelect)select1.getSelectBody();
        System.out.println("-------------");
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(item);
        selectBody.setSelectItems(selectItems);
        System.out.println(select1.toString());

        Select select2 = SelectUtils.buildSelectFromTableAndExpressions(new Table("TABLE1"), new Column("A"), new Column("B"));
        System.out.println(select2.toString());//SELECT A, B FROM TABLE1

        Select select3 = SelectUtils.buildSelectFromTableAndExpressions(new Table("TABLE1"), "a+b", "name");
        System.out.println(select3.toString());//SELECT a + b, name FROM TABLE1
    }

    // 获取sql语句中的所有表名
    // 可以获取任意类型sql语句的全部表名，这里使用的select sql
    // **********传入String 得到List<String>,嵌套已测试
    public static List<String> test_select_table(String sql)
            throws JSQLParserException {
        Statement statement = (Statement) CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder
                .getTableList(selectStatement);
        return tableList;
    }

    // 验证sql语法正确性，返回错误信息
    // 传入 String sql
    // ***********返回错误信息such as： “错误单词” “line 1” “column 80”
    public static String judge_type(String sql) {

        try {
            Statement statement = (Statement) CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            String regEx = "Encountered(.*)";
            Pattern pat = Pattern.compile(regEx);
            Matcher mat = pat.matcher(exception);
            while (mat.find()) {
                exception = mat.group(1);
            }
            // System.out.println(exception);
            String line = "";
            String regEx2 = "line (.*),";
            pat = Pattern.compile(regEx2);
            mat = pat.matcher(exception);
            while (mat.find()) {
                line = mat.group(1);
            }
            // System.out.println(line);

            int line_num = Integer.valueOf(line).intValue();
            int indexofcolumn = exception.indexOf("column");
            String errornumber = exception.substring(indexofcolumn + 7,
                    exception.length() - 1);
            int error_num = Integer.valueOf(errornumber).intValue();
            System.out.println(error_num);

            String ERROR_location = "";
            if (error_num != 1) {
                String sql_sub = sql.substring(0, error_num - 2); // 发生错误位置前面的字符串
                // 错误信息单词往往处于错误位置的前一个地方单词
                // 获取错误位置两个前面两个空格之间的单词，并保存
                sql_sub = new StringBuilder(sql_sub).reverse().toString();
                int indexofspace = sql_sub.indexOf(" ");
                String sql_error = sql_sub.substring(0, indexofspace);
                sql_error = new StringBuilder(sql_error).reverse().toString();
                ERROR_location = "\"" + sql_error + "\"" + " at line "
                        + line_num + " at column " + error_num;
            } else {
                int indexofspace = sql.indexOf(" ");
                String sql_error = sql.substring(0, indexofspace);
                ERROR_location = "\"" + sql_error + "\"" + " at line "
                        + line_num + " at column " + error_num;
            }
            return ERROR_location; // 错误信息的返回
        }
        String result = "correct";
        return result; // Jsql可以解析，返回correct
    }

    /**
     * test SelectUtils 快速建立select语句
     * @throws Exception
     */
    @Test
    public void testcheck() throws Exception {
        String sql ="select * from dual a where a='1' ;\n insert into a values('1',)";
        System.out.println(sql);
        try {
            CCJSqlParserUtil.parseStatements(sql);
        } catch (JSQLParserException e) {
            System.out.println(e.getCause());
        }
        System.out.println(judge_type(sql));
    }

}
