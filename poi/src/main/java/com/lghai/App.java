package com.lghai;

import java.util.*;

/**
 *
 *基于 POI 实现一个 Excel 模板引擎
 */
public class App 
{
    private static String[] fName = {"赵","钱","孙","李","周","吴","郑","王","冯","陈"};
    private static Object[] sName = {"1","2","3","4","5","6","7"};
    public static void main( String[] args )
    {
        // 初始化模拟数据
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> cls = new HashMap<>();
        data.put("cls", cls);
        cls.put("headmaster", "李景文");
        cls.put("type", "文科班");
        List<Stu> stus = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Stu stu = new Stu();
            stu.code = String.format("1490001%02d", i + 1);
            stu.name = String.format("%s%s", fName[Math.abs(random.nextInt()) % fName.length], sName[Math.abs(random.nextInt()) % sName.length]);
            stu.gender = String.format("%s", Math.abs(random.nextInt()) % 2 == 0 ? "男" : "女");
            stu.age = Math.abs(random.nextInt()) % 10 + 10;
            stu.phone = String.format("%s%s", "150", Math.abs(random.nextInt()) % 89999999 + 10000000);
            stu.donation = (int) (random.nextDouble() * 10);
            stus.add(stu);
        }
        cls.put("students", stus);

        //FileOutputStream fos = new FileOutputStream(new File("C:\\DATA\\template.xlsx"));
        String templatePath = "./template.xlsx";
        //根据模板 templatePath 和数据 data 生成 excel 文件，写入 fos 流
        //ExcelTemplateEngine.process(data, templatePath, fos);
        ExcelTemplateEngine.process(data, "./template.xls");
    }
}
