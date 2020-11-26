package com.token;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

public class MyIk {
    public static void main(String[] args) throws IOException {
        MyConfiguration myCfg = new MyConfiguration();
        myCfg.setUseSmart(true);
        // 设置为智能分词
        myCfg.setMainDictionary("com/token/ext_begin.dic");

        Dictionary.initial(myCfg);

        Analyzer analyzer = new IKAnalyzer();

        System.out.println("当前使用的分词器：" + analyzer.getClass().getSimpleName());
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader("我是中国人，是中华人民共和国公民，daffx y daffx"));
        tokenStream.reset();
        tokenStream.addAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()) {
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(new String(charTermAttribute.buffer()));


        }
    }
}