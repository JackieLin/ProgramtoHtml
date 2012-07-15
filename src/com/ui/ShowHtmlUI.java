package com.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.text.html.*;

import java.io.*;

public class ShowHtmlUI extends JDialog implements ActionListener,ItemListener{
	private JEditorPane jep;
	private JScrollPane jsp;
	private JPanel jp, jp1, jp2, jp3;
	private JLabel jl;
	private JComboBox jcb;
	private JButton jb_go, jb_select;
	private String url;
	private ImageIcon runImageIcon = new ImageIcon("./images/run2.jpg");

	public ShowHtmlUI(Frame owner, String title) {
		super(owner, title);
		init();
		this.setLayout(new BorderLayout());
		this.add(jp, BorderLayout.NORTH);
		this.add(jsp, BorderLayout.CENTER);
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
	}

	private void init() {
		// TODO Auto-generated method stub
		jep = new JEditorPane();// "file:/"+url
		Hyperactive h=new Hyperactive();
		jep.addHyperlinkListener(h);
		jep.setEditable(false);
		jsp = new JScrollPane(jep);
		jl = new JLabel("地址");
		jcb = new JComboBox();
		jcb.addItemListener(this);
		jcb.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if ( KeyEvent.VK_ENTER== e.getKeyCode()) {
					showHtml();
				}
			}
		});
		jcb.setEditable(true);
		jb_go = new JButton(runImageIcon);
		jb_go.addActionListener(this);
		jb_go.setContentAreaFilled(false);
		jb_select = new JButton("浏览");
		jb_select.addActionListener(this);
		jp1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp1.add(jl);
		jp2 = new JPanel(new BorderLayout());
		jp2.add(jcb);
		jp3 = new JPanel(new BorderLayout());
		jp3.add(jb_go, BorderLayout.WEST);
		jp3.add(jb_select, BorderLayout.EAST);
		jp = new JPanel(new BorderLayout());
		jp.add(jp1, BorderLayout.WEST);
		jp.add(jp2);
		jp.add(jp3, BorderLayout.EAST);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb_go){
			showHtml();
		}else if(e.getSource()==jb_select){
			JFileChooser jfc = new JFileChooser();
			jfc.setMultiSelectionEnabled(false);
			jfc.setDialogTitle("打开需要现实的HTML文件");
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
					"html文件[.html]", "html"), filter2 = new FileNameExtensionFilter(
					"htm文件[.htm]", "htm");
			jfc.addChoosableFileFilter(filter2);
			jfc.addChoosableFileFilter(filter1);

			if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
				return;
			String strurl;
			strurl=jfc.getSelectedFile().getAbsolutePath();
			if(hasItem(strurl)==-1){
				jcb.addItem(strurl);
				jcb.setSelectedItem(strurl);
			}else{
				jcb.setSelectedItem(strurl);
			}
		}
	}

	private int hasItem(String strurl) {
		// TODO Auto-generated method stub
		int num=jcb.getItemCount();
		for(int i=0;i<num;i++){
			if(strurl.equals((String)jcb.getItemAt(i))){
				return i;
			}
		}
		return -1;
	}
	

	private void showHtml() {
		// TODO Auto-generated method stub
		url=jcb.getEditor().getItem().toString();
		if(hasItem(url)==-1){
			jcb.addItem(url);
			jcb.setSelectedItem(url);
		}else{
			jcb.setSelectedItem(url);
		}
		try {
			jep.setPage(url);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			File f=new File(url);
			if(f.isFile()){
				try {
					jep.setPage("file:/"+url);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					try {
						File fhtml=new File("htmlerror.html");
						jep.setPage("file:/"+fhtml.getAbsolutePath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				try {
					File fhtml=new File("htmlerror.html");
					jep.setPage("file:/"+fhtml.getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
    class Hyperactive implements HyperlinkListener {
		 
        public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                jep = (JEditorPane) e.getSource();
                if (e instanceof HTMLFrameHyperlinkEvent) {
                    HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                    HTMLDocument doc = (HTMLDocument)jep.getDocument();
                    doc.processHTMLFrameHyperlinkEvent(evt);
                } else {
                    try {
                        jep.setPage(e.getURL());
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }

	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange()==ItemEvent.SELECTED){
			showHtml();
		}
	}

}

