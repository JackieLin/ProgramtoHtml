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
        jb=new JButton("�ر�");
        jb.addActionListener(this);
        jp=new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp.add(jb);
        jl = new JLabel("��ݼ��б�",JLabel.CENTER);
        jl.setFont(new Font("����", Font.BOLD, 25));
        String[] columnNames = {"����", "��ݼ�"};
        Object[][] data = {
            {"����", "Ctrl+A"}, {"����", "Ctrl+C"}, {"Ŀ¼����", "Ctrl+D"},
            {"�˳�", "Ctrl+E"}, {"�������ɫ����", "Ctrl+F"}, {"ת�������ļ�", "Ctrl+F6"},
            {"ת����ǰ�ļ�", "Shift+F6"}, {"�Բ����ļ��ķ�ʽת��", "Ctrl+Shift+F6"},
            {"�鿴��ݼ��б�", "Ctrl+K"}, {"�½��ļ�", "Ctrl+N"}, {"���ļ�", "Ctrl+O"},
            {"�����ļ�", "Ctrl+S"},{"�ļ����Ϊ", "Ctrl+Shift+S"}, {"ճ��", "Ctrl+V"}, {"����", "Ctrl+X"},
            {"����", "Ctrl+Y"}, {"����", "Ctrl+Z"}
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
