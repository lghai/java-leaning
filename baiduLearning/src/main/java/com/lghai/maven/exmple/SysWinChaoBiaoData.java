package com.lghai.maven.exmple;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SysWinChaoBiaoData {

	public static String getSysClipBoardText() throws Exception {
		String ret = "";
		Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipTf = sysClip.getContents(null);
		if (clipTf != null) {
			if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				ret = (String) clipTf.getTransferData(DataFlavor.stringFlavor);
			}
			DataFlavor[] dataList = clipTf.getTransferDataFlavors();
			int wholeLength = 0;
			for (int i = 0; i < dataList.length; i++) {
				DataFlavor data = dataList[i];

				if (data.getSubType().equals(
						"<a href='https://www.baidu.com/s?wd=rtf&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1Y4uH0kryckPWbLuhNbPvfL0ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3EPHTdnHT3nH64' target='_blank' class='baidu-highlight'>rtf</a>")) {
					// if(data.getSubType().equals("x-java-text-encoding")){
					Reader reader = data.getReaderForText(clipTf);
					OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
							"d:\\test.<a href='https://www.baidu.com/s?wd=rtf&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1Y4uH0kryckPWbLuhNbPvfL0ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3EPHTdnHT3nH64' target='_blank' class='baidu-highlight'>rtf</a>"));
					char[] c = new char[1024];
					int leng = -1;
					while ((leng = reader.read(c)) != -1) {
						osw.write(c, wholeLength, leng);
						// wholeLength += leng;
					}
					osw.flush();
					osw.close();
					// 注意，只有复制的内容是<a
					// href="https://www.baidu.com/s?wd=RTF&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1Y4uH0kryckPWbLuhNbPvfL0ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3EPHTdnHT3nH64"
					// target="_blank"
					// class="baidu-highlight">RTF</a>的才保存，如果你复制的只有文本则不保存；
					break;
				} else {
					// 在此可以判断剪贴板的其它类型，如果不是复制的rtf只在弹出提示框显示；
					System.out.println("剪贴板内容类型：" + data.getSubType());
					JOptionPane.showMessageDialog(null, ret);
					break;
				}
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JButton button = new JButton("把剪贴板的内容通过Java读取，然后保存到d:\\test.rtf");
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				String str;
				try {// 取剪贴板内容；
					str = getSysClipBoardText();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		});
		frame.add(button, BorderLayout.CENTER);
		frame.setBounds(0, 0, 320, 240);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}