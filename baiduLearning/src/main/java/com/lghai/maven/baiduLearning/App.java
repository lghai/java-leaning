package com.lghai.maven.baiduLearning;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

/**
 * Hello world!
 *
 */
public class App {
	// 设置APPID/AK/SK
	public static final String APP_ID = "10720561";
	public static final String API_KEY = "6u85ff8LqrGWqK9KSHz6ir7Y";
	public static final String SECRET_KEY = "foQk6kQ8zoV6LPo1UEnKK1zxbGfESOmA";

	public static void main(String[] args) throws Exception {
		// 初始化一个AipOcr 文字识别
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// 可选：设置代理服务器地址, http和socket二选一，或者均不设置
		// client.setHttpProxy("proxy_host", proxy_port); // 设置http代理
		// client.setSocketProxy("proxy_host", proxy_port); // 设置socket代理

		// 传入可选参数调用接口 可选
		HashMap<String, String> options = new HashMap<String, String>();
		// options.put("detect_direction", "true");
		// options.put("detect_language", "true");

		// 参数为本地图片二进制数组
		byte[] filebuf = null;

		// 调用接口
		Object objectImg = getImageFromClipboard();
		if (objectImg == null) {
			System.out.println("剪贴板没有图片");
			return;
		}

		if (objectImg instanceof File) {
			File fileImg = (File) objectImg;
			FileInputStream fileInputStream = new FileInputStream(fileImg);
			byte[] buf = new byte[1024 * 1024 * 10];
			int length = fileInputStream.read(buf);

			filebuf = new byte[length];
			System.arraycopy(buf, 0, filebuf, 0, length);
			fileInputStream.close();
		}

		if (objectImg instanceof BufferedImage) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write((RenderedImage) objectImg, "png", outputStream);
			filebuf = outputStream.toByteArray();
		}

		JSONObject res = client.webImage(filebuf, options);
		
		JSONArray array = null;
		try {
			array = res.getJSONArray("words_result");
		} catch (Exception e) {
			System.out.println("无法识别图片,请更换图片");
			return;
		}

		System.out.println("文字识别内容为:\n");
		Iterator<Object> iterator = array.iterator();
		while (iterator.hasNext()) {
			JSONObject object = (JSONObject) iterator.next();
			System.out.println(object.get("words"));
		}
	}

	/**
	 * 从剪切板获得图片。
	 */
	public static Object getImageFromClipboard() throws Exception {
		Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable cc = sysc.getContents(null);
		if (cc == null)
			return null;
		else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
			return (BufferedImage) cc.getTransferData(DataFlavor.imageFlavor);
		else if (cc.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			List<File> files = (List<File>) cc.getTransferData(DataFlavor.javaFileListFlavor);
			return files.get(0);
		}
		return null;
	}
}
