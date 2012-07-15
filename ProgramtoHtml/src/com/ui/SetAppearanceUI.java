/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.service.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * @author Li changwang
 */
public class SetAppearanceUI extends JDialog implements ActionListener {
	private JPanel jp1, jp1_font, jp1_size, jp2, jp2_type, jp2_color,
			jp2_color1, jp2_color2, jp3;
	private JButton jb1, jb2, jb_color1, jb_color2;
	private JLabel jl_font, jl_size, jl_type;
	private JList jlist1, jlist2, jlist3;
	private Appearance app = new Appearance();

	public SetAppearanceUI(Frame owner, String title) {
		super(owner, title);
		init();
		this.add(jp1, BorderLayout.NORTH);
		this.add(jp2, BorderLayout.CENTER);
		this.add(jp3, BorderLayout.SOUTH);
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
	}

	private void init() {
		jl_font = new JLabel("字体：");
		jl_size = new JLabel("大小：");
		jl_type = new JLabel("类别：");

		String[] font = { "Verdana, Geneva, sans-serif",
				"Georgia, Times New Roman, Times, serif",
				"Courier New, Courier, monospace",
				"Arial, Helvetica, sans-serif",
				"Times New Roman, Times, serif", "MS Serif, New York, serif",
				"Comic Sans MS, cursive", "黑体", "隶书", "宋体", "楷书", "幼圆" };
		String[] size = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "12",
				"14", "16", "18", "xx-small", "x-small", "small", "medium",
				"large", "x-large", "xx-large", "smaller", "larger" };
		String[] type = { "关键字", "字符串", "注解" };
		jlist1 = new JList(font);
		jlist1.setVisibleRowCount(5);
		jlist1.setSelectedIndex(8);
		jlist1.setFixedCellWidth(150);
		jlist1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlist2 = new JList(size);
		jlist2.setVisibleRowCount(5);
		jlist2.setSelectedIndex(3);
		jlist2.setFixedCellWidth(150);
		jlist2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlist3 = new JList(type);
		jlist3.setVisibleRowCount(5);
		jlist3.setSelectedIndex(0);
		jlist3.setFixedCellWidth(150);
		jlist3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		jp1_font = new JPanel(new BorderLayout());
		jp1_font.add(jl_font, BorderLayout.NORTH);
		jp1_font.add(new JScrollPane(jlist1), BorderLayout.SOUTH);
		jp1_size = new JPanel(new BorderLayout());
		jp1_size.add(jl_size, BorderLayout.NORTH);
		jp1_size.add(new JScrollPane(jlist2), BorderLayout.SOUTH);
		jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		jp1.setBorder(new TitledBorder("字体和大小设置"));
		jp1.add(jp1_font);
		jp1.add(jp1_size);

		jp2_type = new JPanel(new BorderLayout());
		jp2_type.add(jl_type, BorderLayout.NORTH);
		jp2_type.add(new JScrollPane(jlist3), BorderLayout.SOUTH);
		jb_color1 = new JButton("设置颜色");
		jb_color1.addActionListener(this);
		jb_color2 = new JButton("重置颜色");
		jb_color2.addActionListener(this);
		jb_color2.setEnabled(false);
		jp2_color1 = new JPanel(new FlowLayout());
		jp2_color1.add(jb_color1);
		jp2_color2 = new JPanel(new FlowLayout());
		jp2_color2.add(jb_color2);
		jp2_color = new JPanel(new GridLayout(2, 1));
		jp2_color.add(jp2_color1);
		jp2_color.add(jp2_color2);
		jp2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		jp2.setBorder(new TitledBorder("颜色设置"));
		jp2.add(jp2_type);
		jp2.add(jp2_color);

		jb1 = new JButton("确定");
		jb1.addActionListener(this);
		jb2 = new JButton("取消");
		jb2.addActionListener(this);
		jp3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp3.add(jb1);
		jp3.add(jb2);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb1) {
			String font = (String) jlist1.getSelectedValue();
			String size = (String) jlist2.getSelectedValue();
			app.setFont(font);
			app.setSize(size);
			ProgramToHtml.getMf().appearance = app;
			ProgramToHtml.getMf().setEnabled(true);
			ProgramToHtml.getMf().setFocusable(true);
			this.dispose();
		} else if (e.getSource() == jb2) {
			ProgramToHtml.getMf().setEnabled(true);
			ProgramToHtml.getMf().setFocusable(true);
			this.dispose();
		} else if (e.getSource() == jb_color1) {
			JColorChooser jcc = new JColorChooser();
			Color c = null;
			if ((c = jcc.showDialog(this, "设置颜色", Color.RED)) == null)
				return;
			int r = c.getRed();
			int g = c.getGreen();
			int b = c.getBlue();
			String strColor = app.changeColor(r, g, b);
			int index = jlist3.getSelectedIndex();
			switch (index) {
			case 0:
				app.setTypeColor(strColor);
				break;
			case 1:
				app.setMarksColor(strColor);
				break;
			case 2:
				app.setAnnotationColor(strColor);
				break;
			}
			jb_color2.setEnabled(true);
		} else if (e.getSource() == jb_color2) {
			app.setTypeColor("#FF0000");
			app.setMarksColor("#0000FF");
			app.setAnnotationColor("#646464");
			jb_color2.setEnabled(false);
		}
	}

}
