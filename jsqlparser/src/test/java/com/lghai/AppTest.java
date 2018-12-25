package com.lghai;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.SelectUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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
        Select select = (Select) CCJSqlParserUtil.parse("select name from user where id = 1000");
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
        Select select = (Select) CCJSqlParserUtil.parse("select name from user where id = 1000");
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
     * 测试null条件
     */
    @Test
    public void testParseJoin() throws Exception {

        String sql = "select *from A as a left join B on a.bid = B.id left join C on A.cid = C.id left join D on B.did = D.id";

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

            Select select1 = SelectUtils.buildSelectFromTable(new Table("TABLE1"));
            System.out.println(select1.toString());//SELECT * FROM TABLE1


            Select select2 = SelectUtils.buildSelectFromTableAndExpressions(new Table("TABLE1"), new Column("A"), new Column("B"));
            System.out.println(select2.toString());//SELECT A, B FROM TABLE1

            Select select3 = SelectUtils.buildSelectFromTableAndExpressions(new Table("TABLE1"), "a+b", "name");
            System.out.println(select3.toString());//SELECT a + b, name FROM TABLE1

        } catch (JSQLParserException e) {

            e.printStackTrace();

        }




    }

}
