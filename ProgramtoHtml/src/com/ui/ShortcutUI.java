/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Li changwang
 */
public class ShortcutUI extends JDialog implements ActionListener{

    private JTable jt;
    private JLabel jl;
    private JButton jb;
    private JPanel jp;
    public ShortcutUI(Frame owner, String title) {
        super(owner, title);
        init();
        this.add(jl, BorderLayout.NORTH);
        this.add(new JScrollPane(jt));
        this.add(jp, BorderLayout.SOUTH);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
    }

    private void init() {
        jb=new JButton("关闭");
        jb.addActionListener(this);
        jp=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp.add(jb);
        jl = new JLabel("快捷键列表",JLabel.CENTER);
        jl.setFont(new Font("楷体", Font.BOLD, 25));
        String[] columnNames = {"操作", "快捷键"};
        Object[][] data = {
            {"关于", "Ctrl+A"}, {"复制", "Ctrl+C"}, {"目录设置", "Ctrl+D"},
            {"退出", "Ctrl+E"}, {"字体和颜色设置", "Ctrl+F"}, {"转换所有文件", "Ctrl+F6"},
            {"转换当前文件", "Shift+F6"}, {"以不打开文件的方式转换", "Ctrl+Shift+F6"},
            {"查看快捷键列表", "Ctrl+K"}, {"新建文件", "Ctrl+N"}, {"打开文件", "Ctrl+O"},
            {"保存文件", "Ctrl+S"},{"文件另存为", "Ctrl+Shift+S"}, {"粘贴", "Ctrl+V"}, {"剪切", "Ctrl+X"},
            {"重做", "Ctrl+Y"}, {"撤销", "Ctrl+Z"}
        };
        jt = new JTable(data, columnNames);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb){
            ProgramToHtml.getMf().setEnabled(true);
            ProgramToHtml.getMf().setFocusable(true);
            this.dispose();
        }
    }
}
