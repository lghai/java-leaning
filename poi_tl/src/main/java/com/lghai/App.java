package com.lghai;

import com.deepoove.poi.XWPFTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        XWPFTemplate.compile("C:\\DATA\\GIT\\java-leaning\\poi_tl\\src\\main\\java\\com\\lghai\\template.docx").render(new HashMap<String, Object>(){{
            put("title", "Poi-tl 模板引擎");
        }}).write(new FileOutputStream(new File("out_template.docx")));
    }
}
