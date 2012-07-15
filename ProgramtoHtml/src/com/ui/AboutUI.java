/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Li changwang
 */
public class AboutUI extends JDialog implements ActionListener {

    private JLabel jl;
    private JButton jb;
    private JTextArea jta;
    private JPanel jp, jp1, jp2;
    private ImageIcon htmlImageIcon = new ImageIcon("./images/html.png");

    public AboutUI(Frame owner, String title) {
        super(owner, title);
        init();
        this.add(jp1, BorderLayout.NORTH);
        this.add(jp2);
        this.add(jp, BorderLayout.SOUTH);
        this.setSize(550, 500);
        this.setLocationRelativeTo(null);
    }

    private void init() {
        jl = new JLabel(htmlImageIcon);
        jb = new JButton("关闭");
        jb.addActionListener(this);
        String about = "功能：这个应用程序可以将源程序文件转换为HTML的网页文件\n"
                +"转换得到得到的HTML文件，可以用浏览器查看\n"
                +"处理的源程序包括：Java源程序（扩展名.java）、C源程序（扩展名.h和.c）\n"
                +"\n产品版本： 源代码转换程序1.0 (Build 201109)\n"
                + "开发语言：java\n"
                + "系统：运行于windows的JDK环境中\n";
        jta = new JTextArea(about);
        jta.setEditable(false);
        jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp1.add(jl);
        jp2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp2.add(new JScrollPane(jta));
        jp.add(jb);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb) {
            ProgramToHtml.getMf().setEnabled(true);
            ProgramToHtml.getMf().setFocusable(true);
            this.dispose();
        }
    }
}
