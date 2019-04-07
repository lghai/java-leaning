package com.lghai.word;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.math.BigInteger;

public class Word4one {

    public static void main(String[] args) {
        //word4One();
        //word4two();
        word4three();
    }

    private static void word4One() {
        // 创建一个文档
        XWPFDocument document = new XWPFDocument();

        try {
            FileOutputStream outputStream = new FileOutputStream("poi/one.docx");
            document.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void word4two(){
        XWPFDocument document = new XWPFDocument();
        //创建一个段落
        XWPFParagraph paragraph = document.createParagraph();

        XWPFRun run = paragraph.createRun();

        run.setText("lghai \n");
        run.addBreak(); //换行
        run.setText("hello ");

        XWPFParagraph paragraph2 = document.createParagraph();

        XWPFRun run2 = paragraph2.createRun();
        run2.setText("sdfa");

        try {
            FileOutputStream outputStream = new FileOutputStream("./poi/two.docx");
            document.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void word4three(){
        XWPFDocument document = new XWPFDocument();
        //创建一个段落
        XWPFParagraph paragraph = document.createParagraph();

        XWPFRun run = paragraph.createRun();

        paragraph.setNumID(BigInteger.ONE); //设置段落序号

        run.setText("One of the first thoughts that came to my mind when I learned about Paragraphs and Runs is, why do I need those two things!? What's the freaking difference between a paragraph and a run?\n" +
                "So in this tutorial we'll be looking at the Paragraph, which is used for text alignment, spacing and some more things that has to do with spacing and borders.\n" +
                "The next tutorial will be all about not ruining runs ;-)");

        run.setTextPosition(35);//设置行间距

        run.setBold(true);//加粗
        run.setColor("000000");//设置颜色--十六进制
        run.setFontFamily("宋体");//字体
        run.setFontSize(24);//字体大小

        paragraph.setAlignment(ParagraphAlignment.CENTER); //居中对齐
        paragraph.setIndentationHanging(-100);//首行缩进，word中1厘米约等于567

        try {
            FileOutputStream outputStream = new FileOutputStream("./poi/three.docx");
            document.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
