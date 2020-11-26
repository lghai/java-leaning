package com.token;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestIK分词 {
    public static void main(String[] args) throws IOException {

        Configuration cfg =DefaultConfig.getInstance();
        cfg.setUseSmart(true);
        Dictionary.initial(cfg);
        Dictionary dg = Dictionary.getSingleton();
        List<String> list=new ArrayList<String>();

        dg.addWords(list);
        dg.disableWords(list);
        //创建一个标准分析器对象
        Analyzer analyzer=new IKAnalyzer();
        //获取tokenStream对象
        //参数1域名 2要分析的文本内容
        StringBuilder sb = new StringBuilder(11 * 1024 * 1024);
        FileReader fr = new FileReader("Z:\\111.txt");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("Z:\\111.txt")));
        BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 1 * 1024);// 10M缓存
        while (in.ready()) {
            sb.append(in.readLine());
        }
        in.close();
        LuceneIndexAndSearchDemo.print();
        TokenStream tokenStream=analyzer.tokenStream("",sb.toString());
        //添加引用,用于获取每个关键词
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //添加一个偏移量的引用，记录了关键词的开始位置以及结束位置
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        //将指针调整到列表的头部
        tokenStream.reset();
        //遍历关键词列表,incrementToken判断是否结束
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        while (tokenStream.incrementToken()) {

            if(map.containsKey(charTermAttribute.toString())){
                map.put(charTermAttribute.toString(),map.get(charTermAttribute.toString())+1);
            }else{
                map.put(charTermAttribute.toString(),1);
            }
        }
        tokenStream.close();
        LuceneIndexAndSearchDemo.print();
        System.out.println(map);
    }
}
