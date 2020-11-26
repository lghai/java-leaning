/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 */
package com.token;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 使用IKAnalyzer进行Lucene索引和查询的演示
 * 2012-3-2
 * <p>
 * 以下是结合Lucene4.0 API的写法
 */
public class LuceneIndexAndSearchDemo {

    /**
     * 模拟：
     * 创建一个单条记录的索引，并对其进行搜索
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        StringBuilder sb = new StringBuilder(11 * 1024 * 1024);

        sb.setLength(0);
        // Lucene Document的域名
        String fieldName = "text";
        // 检索内容
        /*LineIterator iterator = FileUtils.lineIterator(new File("Z:\\111.txt"), "UTF-8");
        while (iterator.hasNext()) {
            sb.append(iterator.nextLine());
        }
        iterator.close();
        print();*/

        FileReader fr = new FileReader("Z:\\111.txt");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("Z:\\111.txt")));
        BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 1 * 1024);// 10M缓存
        while (in.ready()) {
            sb.append(in.readLine());
        }
        in.close();

        //String text = "IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。";

        print();
        Configuration cfg = DefaultConfig.getInstance();
        cfg.setUseSmart(true);
        Dictionary.initial(cfg);
        Dictionary dg = Dictionary.getSingleton();
        List<String> list = new ArrayList<String>();
        list.add("黑斯廷");
        list.add("达克斯");
        dg.addWords(list);
        // 实例化IKAnalyzer分词器
        Analyzer analyzer = new IKAnalyzer(true);

        Directory directory = new MMapDirectory(new File("z:/111"),null);// 建立内存索引对象
        //Directory directory = new RAMDirectory();
        // 配置IndexWriterConfig
        IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_43, analyzer);
        iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter iwriter = new IndexWriter(directory, iwConfig);
        ;
        IndexReader ireader = null;
        IndexSearcher isearcher = null;
        print();
        try {
            for (int i = 0; i < 10; i++) {
                iwriter.deleteAll();

                // 写入索引
                Document doc = new Document();
                doc.add(new StringField("ID", "10000", Field.Store.YES));
                doc.add(new TextField(fieldName, sb.toString(), Field.Store.NO));
                iwriter.addDocument(doc);
                iwriter.commit();
                // 搜索过程**********************************
                // 实例化搜索器
                ireader = DirectoryReader.open(directory);
                isearcher = new IndexSearcher(ireader);

                String keyword = "黑斯廷 AND 达克斯";
                // 使用QueryParser查询分析器构造Query对象
                QueryParser qp = new QueryParser(Version.LUCENE_43, fieldName, analyzer);
                qp.setDefaultOperator(QueryParser.AND_OPERATOR);
                Query query = qp.parse(keyword);

                System.out.println("Query = " + query);

                // 搜索相似度最高的5条记录
                TopDocs topDocs = isearcher.search(query, 5);
                System.out.println("命中：" + topDocs.totalHits);
                // 输出结果
                ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                for (int j = 0; j < topDocs.totalHits; j++) {
                    Document targetDoc = isearcher.doc(scoreDocs[j].doc);
                    System.out.println("内容：" + targetDoc.getField("ID").stringValue());
                }
                print();
            }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (ireader != null) {
                try {
                    ireader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (directory != null) {
                try {
                    directory.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //iwriter.deleteAll();

        }

    }

    public static void print() {
        NumberFormat format = NumberFormat.getNumberInstance();
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long usedMem = totalMem - freeMem;
        long maxMem = Runtime.getRuntime().maxMemory();
        System.out.println("Max Mem:" + format.format(maxMem) + " byte");
        System.out.println("Total Mem:" + format.format(totalMem) + " byte ");
        System.out.println("Free Mem:" + format.format(freeMem) + " byte<br/>");
        System.out.println("Used Mem:" + format.format(usedMem) + " byte<br/>");
        System.out.println("Usage:" + new BigDecimal(usedMem).divide(new BigDecimal(totalMem), 4, BigDecimal.ROUND_CEILING).multiply(new BigDecimal(100)) + "%<br/>");

    }
}
