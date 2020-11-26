package com.lghai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HelloWorldSwing {
    /**{
     * 创建并显示GUI。出于线程安全的考虑，
     * 这个方法在事件调用线程中调用。
     */
    private static final JPanel panel = new JPanel(); 
    private static final JTextArea m3u8Text = new JTextArea();
    private static final JTextArea textConsole = new JTextArea();
    private static Thread downloadT;
    private static void createAndShowGUI() {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建及设置窗口
        JFrame frame = new JFrame("m3u8 download");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 显示窗口
        frame.pack();
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void placeComponents(final JPanel panel) {
        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);
        int xHeight = 30;
        // 创建登录按钮
        JButton downloadButton = new JButton("下载");
        downloadButton.setBounds(10, 20+xHeight*0, 80, 25);
        downloadButton.addActionListener(new ActionListener() {//给按钮添加事件接收器
            @Override
            public void actionPerformed(ActionEvent e) {//接受到事件后,进行下面的处理
                final JButton  source = (JButton) e.getSource();
                source.setEnabled(false);
                final JTextField pathField = (JTextField) panel.getComponent(3);
                final String dir = pathField.getText();
                final JTextField refererField = (JTextField) panel.getComponent(5);
                final String Referer = refererField.getText();
                final String areaText = m3u8Text.getText();
                final String path = m3u8Text.getText();
                downloadT = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String[] split = areaText.split("\\n");
                            int i = 0;
                            for (String str : split) {
                                if (downloadT.isInterrupted())
                                    break;
                                if ("".equals(str.trim())){
                                    continue;
                                }
                                if (str.trim().startsWith("#"))
                                    continue;
                                i++;
                                String fileName = String.format("%04d", i) + ".ts";

                                HttpURLConnection connection = (HttpURLConnection) new URL(str.trim()).openConnection();
                                String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";

                                connection.setRequestProperty("User-agent", userAgent);
                                connection.setRequestProperty("Referer", Referer);
                                connection.setRequestMethod("GET");
                                int code = connection.getResponseCode();
                                if (code == 200 || code == 206) {
                                    InputStream is = connection.getInputStream();
                                    textConsole.append("Downloading...  "+str+"\r\n");
                                    System.out.println("Downloading...  "+str);
                                    DataInputStream dataInputStream = new DataInputStream(is);

                                    int length = 0;
                                    byte[] buf = new byte[1024 * 1024 * 8];


                                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(new File(dir) , fileName)));
                                    while ((length = dataInputStream.read(buf)) != -1) {
                                        if (downloadT.isInterrupted())
                                            break;
                                        dataOutputStream.write(buf, 0, length);
                                    }
                                    dataOutputStream.close();
                                    dataInputStream.close();

                                    is.close();
                                }else{
                                    textConsole.append("error code :"+connection.getResponseCode()+" \r\nerror msg :"+connection.getResponseMessage()+"\r\n");
                                    break;
                                }
                                connection.disconnect();
                            }
                            textConsole.append("Download completed\r\n");
                            source.setEnabled(true);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                downloadT.start();
            }
        });
        panel.add(downloadButton);
        // 取消按钮
        JButton cancelButton = new JButton("取消");
        cancelButton.setBounds(100, 20+xHeight*0, 80, 25);
        cancelButton.addActionListener(new ActionListener() {//给按钮添加事件接收器
            @Override
            public void actionPerformed(ActionEvent e) {//接受到事件后,进行下面的处理
                JButton source = (JButton) e.getSource();
                final JButton btn0 = (JButton) panel.getComponent(0);
                btn0.setEnabled(true);
                textConsole.append("operation : cancel\r\n");// 控制台打印输出
                downloadT.interrupt();
            }
        });
        panel.add(cancelButton);
        // 创建 JLabel
        JLabel pathLabel = new JLabel("保存文件路劲:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        pathLabel.setBounds(10,20+xHeight*1,100,25);
        panel.add(pathLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField pathText = new JTextField(20);
        pathText.setBounds(100,20+xHeight*1,650,25);
        panel.add(pathText);

        // 输入的文本域
        JLabel refererLabel = new JLabel("防盗链referer:");
        refererLabel.setBounds(10,20+xHeight*2,100,25);
        panel.add(refererLabel);

        JTextField passwordText = new JTextField(20);
        passwordText.setBounds(100,20+xHeight*2,650,25);
        panel.add(passwordText);

        // 输入的文本域
        JLabel m3u8Label = new JLabel("m3u8文本:");
        m3u8Label.setBounds(10,20+xHeight*3,100,25);
        panel.add(m3u8Label);



        JScrollPane scroll = new JScrollPane(m3u8Text);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(100,20+xHeight*3,650,300);
        panel.add(scroll);
        textConsole.setEditable(false);
        textConsole.setLineWrap(true); //自动换行
        textConsole.setWrapStyleWord(true);//断行不断字
        textConsole.setCaretPosition(textConsole.getText().length());
        JScrollPane scrollConsole = new JScrollPane(textConsole);
        scrollConsole.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollConsole.setBounds(100,20+xHeight*3+310,650,100);
        panel.add(scrollConsole);
    }
    
    public static void main(String[] args) {
        // 显示应用 GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}