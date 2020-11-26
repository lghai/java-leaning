package com.lghai.word;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.common.base.CharMatcher;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.w3c.dom.Document;
public class PoiWordToHtml {
 public static void main(String[] args) throws Throwable {
  /*final String path = "A:\\poi-test\\wordToHtml\\";
  final String file = "云OS.doc";
  InputStream input = new FileInputStream(path + file);
  HWPFDocument wordDocument = new HWPFDocument(input);
  WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
    DocumentBuilderFactory.newInstance().newDocumentBuilder()
      .newDocument());
  wordToHtmlConverter.setPicturesManager(new PicturesManager() {
   public String savePicture(byte[] content, PictureType pictureType,
     String suggestedName, float widthInches, float heightInches) {
    return suggestedName;
   }
  });
  wordToHtmlConverter.processDocument(wordDocument);
  *//*List pics = wordDocument.getPicturesTable().getAllPictures();
  if (pics != null) {
   for (int i = 0; i < pics.size(); i++) {
    Picture pic = (Picture) pics.get(i);
    try {
     pic.writeImageContent(new FileOutputStream("A:\\poi-test\\"
       + pic.suggestFullFileName()));
    } catch (FileNotFoundException e) {
     e.printStackTrace();
    }
   }
  }*//*
  Document htmlDocument = wordToHtmlConverter.getDocument();
  ByteArrayOutputStream outStream = new ByteArrayOutputStream();
  DOMSource domSource = new DOMSource(htmlDocument);
  StreamResult streamResult = new StreamResult(outStream);
  TransformerFactory tf = TransformerFactory.newInstance();
  Transformer serializer = tf.newTransformer();
  serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
  serializer.setOutputProperty(OutputKeys.INDENT, "yes");
  serializer.setOutputProperty(OutputKeys.METHOD, "html");
  serializer.transform(domSource, streamResult);
  outStream.close();
  String content = new String(outStream.toByteArray());
  FileUtils.writeStringToFile(new File(path, "人员选择系分.html"), content, "utf-8");*/
     System.out.println(readWordFile("C:\\Users\\huawei\\OneDrive\\My\\文档\\云OS.docx"));
 }
 public static <T> List<String> readWordFile(String path) {
  List<String> contextList = new ArrayList<>();
  InputStream stream = null;
  try {
   stream = new FileInputStream(new File(path));
   if (path.endsWith(".doc")) {
    HWPFDocument document = new HWPFDocument(stream);
    WordExtractor extractor = new WordExtractor(document);
    String[] contextArray = extractor.getParagraphText();
    Arrays.asList(contextArray).forEach(context -> contextList.add(CharMatcher.whitespace().removeFrom(context)));
    extractor.close();
    document.close();
   } else if (path.endsWith(".docx")) {
    XWPFDocument document = new XWPFDocument(stream).getXWPFDocument();
    List<XWPFParagraph> paragraphList = document.getParagraphs();
    paragraphList.forEach(paragraph -> contextList.add(CharMatcher.whitespace().removeFrom(paragraph.getParagraphText())));
    document.close();
   } else {
    System.out.println("此文件{}不是word文件"+path);
   }
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   if (null != stream) try {
    stream.close();
   } catch (IOException e) {
    e.printStackTrace();
       System.out.println("读取word文件失败");
   }
  }
  return contextList;
 }
}
