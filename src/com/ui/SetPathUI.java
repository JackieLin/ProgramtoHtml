/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Li changwang
 */
public class SetPathUI extends JDialog implements ActionListener,
        ItemListener {

    private JPanel jp, jp1, jp2, jp3;
    private JButton jb1, jb2, jb;
    private JRadioButton jrb1, jrb2;
    private ButtonGroup bg;
    private JLabel jl;
    private JTextField jtf;
    private boolean r;

    public SetPathUI(Frame owner, String title) {
        super(owner, title);
        init();
        this.add(jp);
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
    }

    private void init() {
        jl = new JLabel("目录：");
        jtf = new JTextField(50);
        jtf.setEditable(false);
        jb = new JButton("浏览");
        jb.addActionListener(this);
        jb1 = new JButton("确定");
        jb1.addActionListener(this);
        jb2 = new JButton("取消");
        jb2.addActionListener(this);
        jrb1 = new JRadioButton("带行号");
        jrb1.addItemListener(this);
        jrb1.setSelected(true);
        jrb2 = new JRadioButton("不带行号");
        jrb2.addItemListener(this);
        bg = new ButtonGroup();
        bg.add(jrb1);
        bg.add(jrb2);
        jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jp1.setBorder(new TitledBorder("文件转换后存放的目录"));
        jp1.add(jl);
        jp1.add(jtf);
        jp1.add(jb);
        jp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jp2.setBorder(new TitledBorder("转换后的文件是否加上行号"));
        jp2.add(jrb1);
        jp2.add(jrb2);
        jp3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jp3.add(jb1);
        jp3.add(jb2);
        jp = new JPanel(new GridLayout(3, 1));
        jp.add(jp1);
        jp.add(jp2);
        jp.add(jp3);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
            ProgramToHtml.getMf().row = r;
            if (!jtf.getText().equals("")) {
                ProgramToHtml.getMf().fullPath = jtf.getText();
            }
            ProgramToHtml.getMf().setEnabled(true);
            ProgramToHtml.getMf().setFocusable(true);
            this.dispose();
        } else if (e.getSource() == jb2) {
            ProgramToHtml.getMf().setEnabled(true);
            ProgramToHtml.getMf().setFocusable(true);
            this.dispose();
        } else if (e.getSource() == jb) {
            JFileChooser jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File("."));
            jfc.setDialogTitle("选择目录");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = jfc.getSelectedFile();
                String d = f.getPath() + "\\";
                jtf.setText(d);
            }
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == jrb1) {
            r = true;
        } else if (e.getSource() == jrb2) {
            r = false;
        }
    }
}
