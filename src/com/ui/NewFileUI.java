/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author Li changwang
 */
public class NewFileUI extends JDialog implements ActionListener,
		DocumentListener {

	private JPanel jp, jp1, jp2, jp3, jp4;
	private JLabel jl_name, jl_location, jl_tip;
	private JTextField jtf1, jtf2;
	private JComboBox jcb;
	private JButton jb_ok, jb_cancel, jb_select;
	private String strFile = "";
	private String path = "";

	public NewFileUI(Frame owner, String title) {
		super(owner, title);
		init();
		this.add(jp);
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
	}

	private void init() {
		jl_name = new JLabel("�ļ���:");
		jl_location = new JLabel("·\b\b��:");
		jl_tip = new JLabel();
		jl_tip.setFont(new Font("����", Font.BOLD, 20));
		jl_tip.setForeground(Color.red);

		jtf1 = new JTextField(50);
		jtf1.getDocument().addDocumentListener(this);
		jtf2 = new JTextField(50);
		jtf2.setEditable(false);

		String[] items = { ".java", ".c", ".h", ".H",".js",".cpp" };
		jcb = new JComboBox(items);

		jb_ok = new JButton("ȷ��");
		jb_ok.setEnabled(false);
		jb_ok.addActionListener(this);
		jb_cancel = new JButton("ȡ��");
		jb_cancel.addActionListener(this);
		jb_select = new JButton("���");
		jb_select.addActionListener(this);

		jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp1.setBorder(new TitledBorder("�ļ�����"));
		jp1.add(jl_name);
		jp1.add(jtf1);
		jp1.add(jcb);
		jp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp2.setBorder(new TitledBorder("�ļ�λ��"));
		jp2.add(jl_location);
		jp2.add(jtf2);
		jp2.add(jb_select);
		jp4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp4.add(jb_ok);
		jp4.add(jb_cancel);
		jp3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp3.add(jl_tip);
		jp = new JPanel(new GridLayout(4, 1));
		jp.add(jp1);
		jp.add(jp2);
		jp.add(jp3);
		jp.add(jp4);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb_select) {
			JFileChooser jfc = new JFileChooser();
			String name = jtf1.getText().trim();
			jfc.setCurrentDirectory(new File("."));
			jfc.setDialogTitle("ѡ��Ŀ¼");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				String d = f.getPath() + "\\" + name + jcb.getSelectedItem();
				path = f.getPath();
				jtf2.setText(d);
				if (!name.equals("")) {
					if (new File(d).exists()) {
						jl_tip.setText("*��Ŀ¼���Ѿ�����ͬ���ļ�!");
						jb_ok.setEnabled(false);
					} else {
						jb_ok.setEnabled(true);
						jl_tip.setText("");
						this.strFile = jtf2.getText().trim();
					}
				} else {
					jb_ok.setEnabled(false);
					jl_tip.setText("*�ļ����������淶");
				}
			}
		} else if (e.getSource() == jb_cancel) {
			ProgramToHtml.getMf().setEnabled(true);
			ProgramToHtml.getMf().setFocusable(true);
			this.dispose();
		} else if (e.getSource() == jb_ok) {
			try {
				ProgramToHtml.getMf().setEnabled(true);
				ProgramToHtml.getMf().setFocusable(true);
				File f = new File(this.strFile);
				f.createNewFile();
				File[] f2 = { f };
				ProgramToHtml.getMf().open(f2);
				this.dispose();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void insertUpdate(DocumentEvent e) {
		this.showTip();
	}

	public void removeUpdate(DocumentEvent e) {
		this.showTip();
	}

	public void changedUpdate(DocumentEvent e) {
		this.showTip();
	}

	private void showTip() {
		String name = jtf1.getText().trim();
		String str;
		if (!path.equals("")) {
			str = path + "\\" + name + jcb.getSelectedItem();
		} else {
			str = name + jcb.getSelectedItem();
		}
		jtf2.setText(str);
		File file = new File(str);
		if (file.exists()) {
			jl_tip.setText("*��Ŀ¼���Ѿ�����ͬ���ļ�!");
			jb_ok.setEnabled(false);
		} else {
			if (isDirectory(path) && name.equals("java")) {
				jl_tip.setText("");
				jb_ok.setEnabled(true);
				this.strFile = jtf2.getText().trim();
			} else if (name.matches("[a-zA-Z_$][\\w\\d_$]*")) {
				jl_tip.setText("*�ļ�·����ûѡ��!");
				jb_ok.setEnabled(false);
			} else {
				jl_tip.setText("*�ļ����������淶!");
				jb_ok.setEnabled(false);
			}
		}
	}

	private boolean isDirectory(String str) {
		File f = new File(str);
		if (f.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}
}