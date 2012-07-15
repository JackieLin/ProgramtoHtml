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
        jb = new JButton("�ر�");
        jb.addActionListener(this);
        String about = "���ܣ����Ӧ�ó�����Խ�Դ�����ļ�ת��ΪHTML����ҳ�ļ�\n"
                +"ת���õ��õ���HTML�ļ���������������鿴\n"
                +"�����Դ���������JavaԴ������չ��.java����CԴ������չ��.h��.c��\n"
                +"\n��Ʒ�汾�� Դ����ת������1.0 (Build 201109)\n"
                + "�������ԣ�java\n"
                + "ϵͳ��������windows��JDK������\n";
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
