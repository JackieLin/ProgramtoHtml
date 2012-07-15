/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

import com.jtr.JtreeFile;
import com.service.Appearance;
import com.service.ProgramChangeTOHtml;

/**
 * 
 * @author Li changwang
 */
public class MainFrame extends JFrame implements DocumentListener,
		ActionListener, ChangeListener, UndoableEditListener, MouseListener {

	private JMenuBar jmb;
	private JToolBar jtb1, jtb2, jtb3, jtb4;
	private JTabbedPane jtp;
	private JMenu jm_file, jm_edit, jm_run, jm_option, jm_help;
	private JMenuItem jmt_new, jmt_open, jmt_save, jmt_saveas, jmt_exit;
	private JMenuItem jmt_undo, jmt_redo, jmt_cut, jmt_copy, jmt_paste;
	private JMenuItem jmt_cut2, jmt_runall2, jmt_copy2, jmt_paste2, jmt_run2;
	private JMenuItem jmt_run, jmt_runall, jmt_run_notopen;
	private JMenuItem jmt_path, jmt_font;
	private JMenuItem jmt_shortcut, jmt_about;
	private JPopupMenu jpm;
	private JSplitPane jsp;
	private JPanel jp1, jp2, jp_jtoolbar;
	private JMenuItem jmt_close, jmt_close_all, jmt_close_others;
	private JButton jb_new, jb_open, jb_save, jb_pre, jb_next, jb_run,
			jb_htmlshow;
	private ImageIcon newImageIcon = new ImageIcon("./images/new.jpg");
	private ImageIcon htmlImageIcon = new ImageIcon("./images\\htmlshow.jpg");
	private ImageIcon openImageIcon = new ImageIcon("./images\\open.jpg");
	private ImageIcon saveImageIcon = new ImageIcon("./images\\save.jpg");
	private ImageIcon preImageIcon = new ImageIcon("./images\\pre.jpg");
	private ImageIcon nextImageIcon = new ImageIcon("./images\\next.jpg");
	private ImageIcon runImageIcon = new ImageIcon("./images\\run.jpg");
	private ImageIcon cutImageIcon = new ImageIcon("./images\\cut.jpg");
	private ImageIcon copyImageIcon = new ImageIcon("./images\\copy.jpg");
	private ImageIcon pasteImageIcon = new ImageIcon("./images\\paste.jpg");
	private ArrayList<String> names = new ArrayList<String>();
	private HashMap<String, UndoManager> hashMap = new HashMap<String, UndoManager>();
	private HashMap<String, JTextArea> textMap = new HashMap<String, JTextArea>();
	public String fullPath = new File(".").getAbsolutePath();
	public Appearance appearance = new Appearance();
	public boolean row = true;

	public MainFrame() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			init();
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	private void init() {
		jmt_new = new JMenuItem("新建(N)");
		jmt_new.setMnemonic('N');
		jmt_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		jmt_new.setIcon(newImageIcon);
		jmt_new.addActionListener(this);
		jmt_open = new JMenuItem("打开(O)");
		jmt_open.setMnemonic('O');
		jmt_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		jmt_open.setIcon(openImageIcon);
		jmt_open.addActionListener(this);
		jmt_save = new JMenuItem("保存(S)");
		jmt_save.setEnabled(false);
		jmt_save.setMnemonic('S');
		jmt_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		jmt_save.setIcon(saveImageIcon);
		jmt_save.addActionListener(this);
		jmt_saveas = new JMenuItem("保存为(A)");
		jmt_saveas.setMnemonic('A');
		jmt_saveas.setEnabled(false);
		jmt_saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		jmt_saveas.setIcon(saveImageIcon);
		jmt_saveas.addActionListener(this);
		jmt_exit = new JMenuItem("退出(E)");
		jmt_exit.setMnemonic('E');
		jmt_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.CTRL_MASK));
		jmt_exit.addActionListener(this);

		jmt_undo = new JMenuItem("撤销(U)");
		jmt_undo.setEnabled(false);
		jmt_undo.setMnemonic('U');
		jmt_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
		jmt_undo.setIcon(preImageIcon);
		jmt_undo.addActionListener(this);
		jmt_redo = new JMenuItem("重做(R)");
		jmt_redo.setEnabled(false);
		jmt_redo.setMnemonic('R');
		jmt_redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				ActionEvent.CTRL_MASK));
		jmt_redo.setIcon(nextImageIcon);
		jmt_redo.addActionListener(this);
		jmt_cut = new JMenuItem("剪切(T)");
		jmt_cut.setEnabled(false);
		jmt_cut.setMnemonic('T');
		jmt_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		jmt_cut.setIcon(cutImageIcon);
		jmt_cut.addActionListener(this);
		jmt_copy = new JMenuItem("复制(C)");
		jmt_copy.setMnemonic('C');
		jmt_copy.setEnabled(false);
		jmt_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
		jmt_copy.setIcon(copyImageIcon);
		jmt_copy.addActionListener(this);
		jmt_paste = new JMenuItem("粘贴(P)");
		jmt_paste.setEnabled(false);
		jmt_paste.setMnemonic('P');
		jmt_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));
		jmt_paste.setIcon(pasteImageIcon);
		jmt_paste.addActionListener(this);

		jmt_run = new JMenuItem("转换当前文件(T)");
		jmt_run.setEnabled(false);
		jmt_run.setMnemonic('T');
		jmt_run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6,
				ActionEvent.SHIFT_MASK));
		jmt_run.setIcon(runImageIcon);
		jmt_run.addActionListener(this);
		jmt_runall = new JMenuItem("转换所有文件(R)");
		jmt_runall.setEnabled(false);
		jmt_runall.setMnemonic('R');
		jmt_runall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6,
				ActionEvent.CTRL_MASK));
		jmt_runall.setIcon(runImageIcon);
		jmt_runall.addActionListener(this);
		jmt_run_notopen = new JMenuItem("以不打开文件的方式转换(R)");
		jmt_run_notopen.setMnemonic('F');
		jmt_run_notopen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6,
				ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK));
		jmt_run_notopen.setIcon(runImageIcon);
		jmt_run_notopen.addActionListener(this);

		jmt_path = new JMenuItem("目录设置(D)");
		jmt_path.setMnemonic('D');
		jmt_path.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.CTRL_MASK));
		jmt_path.addActionListener(this);

		jmt_font = new JMenuItem("字体和颜色(F)");
		jmt_font.setMnemonic('F');
		jmt_font.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				ActionEvent.CTRL_MASK));
		jmt_font.addActionListener(this);

		jmt_shortcut = new JMenuItem("快捷键列表(K)");
		jmt_shortcut.setMnemonic('K');
		jmt_shortcut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,
				ActionEvent.CTRL_MASK));
		jmt_shortcut.addActionListener(this);

		jmt_about = new JMenuItem("关于(A)");
		jmt_about.setMnemonic('A');
		jmt_about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		jmt_about.addActionListener(this);

		jm_file = new JMenu("文件(F)");
		jm_file.setMnemonic('F');
		jm_file.add(jmt_new);
		jm_file.add(jmt_open);
		jm_file.add(jmt_save);
		jm_file.add(jmt_saveas);
		jm_file.addSeparator();
		jm_file.add(jmt_exit);

		jm_edit = new JMenu("编辑(E)");
		jm_edit.setMnemonic('E');
		jm_edit.add(jmt_undo);
		jm_edit.add(jmt_redo);
		jm_edit.addSeparator();
		jm_edit.add(jmt_cut);
		jm_edit.add(jmt_copy);
		jm_edit.add(jmt_paste);

		jm_run = new JMenu("转换(T)");
		jm_run.setMnemonic('T');
		jm_run.add(jmt_run);
		jm_run.add(jmt_runall);
		jm_run.add(jmt_run_notopen);

		jm_option = new JMenu("选项(O)");
		jm_option.setMnemonic('O');
		jm_option.add(jmt_path);
		jm_option.add(jmt_font);

		jm_help = new JMenu("帮助(H)");
		jm_help.setMnemonic('H');
		jm_help.add(jmt_shortcut);
		jm_help.add(jmt_about);

		jmb = new JMenuBar();
		jmb.add(jm_file);
		jmb.add(jm_edit);
		jmb.add(jm_run);
		jmb.add(jm_option);
		jmb.add(jm_help);

		jb_new = new JButton(newImageIcon);
		jb_new.setToolTipText("新建文件(Ctrl+N)");
		jb_new.addActionListener(this);
		jb_open = new JButton(openImageIcon);
		jb_open.setToolTipText("打开文件(Ctrl+O)");
		jb_open.addActionListener(this);
		jb_save = new JButton(saveImageIcon);
		jb_save.setToolTipText("保存文件(Ctrl+S)");
		jb_save.addActionListener(this);
		jb_save.setEnabled(false);
		jb_pre = new JButton(preImageIcon);
		jb_pre.setToolTipText("撤销(Ctrl+Z)");
		jb_pre.addActionListener(this);
		jb_pre.setEnabled(false);
		jb_next = new JButton(nextImageIcon);
		jb_next.setToolTipText("重做(Ctrl+Y)");
		jb_next.addActionListener(this);
		jb_next.setEnabled(false);
		jb_run = new JButton(runImageIcon);
		jb_run.setToolTipText("转换当前文件(Shift+F6)");
		jb_run.addActionListener(this);
		jb_run.setEnabled(false);
		jb_htmlshow = new JButton(htmlImageIcon);
		jb_htmlshow.setToolTipText("显示HTML文件");
		jb_htmlshow.addActionListener(this);

		jmt_cut2 = new JMenuItem("剪切(T)");
		jmt_cut2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		jmt_cut2.setIcon(cutImageIcon);
		jmt_cut2.addActionListener(this);
		jmt_copy2 = new JMenuItem("复制(C)");
		jmt_copy2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.CTRL_MASK));
		jmt_copy2.setIcon(copyImageIcon);
		jmt_copy2.addActionListener(this);
		jmt_paste2 = new JMenuItem("粘贴(P)");
		jmt_paste2.setEnabled(false);
		jmt_paste2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				ActionEvent.CTRL_MASK));
		jmt_paste2.setIcon(pasteImageIcon);
		jmt_paste2.addActionListener(this);
		jmt_run2 = new JMenuItem("转换当前文件");
		jmt_run2.setEnabled(false);
		jmt_run2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6,
				ActionEvent.SHIFT_MASK));
		jmt_run2.setIcon(runImageIcon);
		jmt_run2.addActionListener(this);
		jmt_runall2 = new JMenuItem("转换所有文件");
		jmt_runall2.setEnabled(false);
		jmt_runall2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6,
				ActionEvent.CTRL_MASK));
		jmt_runall2.setIcon(runImageIcon);
		jmt_runall2.addActionListener(this);

		jmt_close = new JMenuItem("关闭当前文档");
		jmt_close.addActionListener(this);
		jmt_close_all = new JMenuItem("关闭所有文档");
		jmt_close_all.addActionListener(this);
		jmt_close_others = new JMenuItem("关闭其他文档");
		jmt_close_others.addActionListener(this);
		jpm = new JPopupMenu();
		jpm.add(jmt_close);
		jpm.add(jmt_close_all);
		jpm.add(jmt_close_others);

		jtb1 = new JToolBar();
		jtb1.add(jb_new);
		jtb1.add(jb_open);
		jtb1.add(jb_save);
		jtb2 = new JToolBar();
		jtb2.add(jb_pre);
		jtb2.add(jb_next);
		jtb3 = new JToolBar();
		jtb3.add(jb_run);
		jtb4 = new JToolBar();
		jtb4.add(jb_htmlshow);

		jtp = new JTabbedPane();
		jtp.addChangeListener(this);
		jtp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		jtp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getButton() == 3) {
					jpm.show(me.getComponent(), me.getX(), me.getY());
				}
			}
		});

		jp1 = new JPanel(new BorderLayout());
		jp2 = new JPanel();
		jp_jtoolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp_jtoolbar.add(jtb1);
		jp_jtoolbar.add(jtb2);
		jp_jtoolbar.add(jtb3);
		jp_jtoolbar.add(jtb4);
		jp1.add(new JtreeFile());
		// jp1.add(new Jt());
		jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jp1, jtp);

		jsp.setDividerLocation(150);
		jsp.setOneTouchExpandable(true);
		// this.add(new Jt(),BorderLayout.WEST);
		this.setJMenuBar(jmb);
		this.add(jp_jtoolbar, BorderLayout.NORTH);
		this.add(jsp, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jmt_open || e.getSource() == jb_open) {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File("."));
			jfc.setMultiSelectionEnabled(true);
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
					"Java文件[.java]", "java"), filter2 = new FileNameExtensionFilter(
					"C文件[.c]", "c"), filter3 = new FileNameExtensionFilter(
					"头文件[.h]", "h");
			jfc.addChoosableFileFilter(filter3);
			jfc.addChoosableFileFilter(filter2);
			jfc.addChoosableFileFilter(filter1);

			if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				open(jfc.getSelectedFiles());
			}
		} else if (e.getSource() == jmt_save || e.getSource() == jb_save) {
			jmt_save.setEnabled(false);
			jb_save.setEnabled(false);
			this.saveFile();
		} else if (e.getSource() == jmt_saveas) {
			JFileChooser jfc = new JFileChooser();
			String fullName;
			int index = jtp.getSelectedIndex();
			String name = jtp.getToolTipTextAt(index);
			File f = new File(name);
			String name2 = f.getName();
			String content;
			// jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jfc.setSelectedFile(new File(name2));
			if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				fullName = jfc.getSelectedFile().getAbsolutePath();
				try {
					content = ((JTextArea) textMap.get(name)).getText();
					PrintWriter pw = new PrintWriter(fullName);
					pw.print(content);
					pw.close();
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "保存成功！", "保存提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == jmt_new || e.getSource() == jb_new) {
			this.setEnabled(false);
			final NewFileUI nfui = new NewFileUI(this, "新建文件");
			nfui.setVisible(true);
			nfui.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					setEnabled(true);
					nfui.dispose();
				}
			});
		} else if (e.getSource() == jmt_font) {
			this.setEnabled(false);
			final SetAppearanceUI saui = new SetAppearanceUI(this, "外观设置");
			saui.setVisible(true);
			saui.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					setEnabled(true);
					saui.dispose();
				}
			});
		} else if (e.getSource() == jmt_path) {
			this.setEnabled(false);
			final SetPathUI spui = new SetPathUI(this, "设置目录");
			spui.setVisible(true);
			spui.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					setEnabled(true);
					spui.dispose();
				}
			});
		} else if (e.getSource() == jmt_shortcut) {
			this.setEnabled(false);
			final ShortcutUI sui = new ShortcutUI(this, "快捷键");
			sui.setVisible(true);
			sui.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					setEnabled(true);
					sui.dispose();
				}
			});
		} else if (e.getSource() == jmt_about) {
			this.setEnabled(false);
			final AboutUI aui = new AboutUI(this, "关于");
			aui.setVisible(true);
			aui.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					setEnabled(true);
					aui.dispose();
				}
			});
		} else if (e.getSource() == jmt_close) {
			int index = jtp.getSelectedIndex();
			String name = jtp.getToolTipTextAt(index);
			jtp.remove(index);

			if (names.contains(name)) {
				names.remove(name);
			}
			if (names.isEmpty()) {
				jmt_saveas.setEnabled(false);
				jmt_save.setEnabled(false);
				jb_pre.setEnabled(false);
				jb_next.setEnabled(false);
				jmt_undo.setEnabled(false);
				jmt_redo.setEnabled(false);
				jb_run.setEnabled(false);
				jmt_run.setEnabled(false);
				jmt_run2.setEnabled(false);
				jmt_runall.setEnabled(false);
				jmt_runall2.setEnabled(false);
				// jmt_run_notopen.setEnabled(false);
				jmt_cut.setEnabled(false);
				jmt_copy.setEnabled(false);
			}
			hashMap.remove(name);
			textMap.remove(name);
		} else if (e.getSource() == jmt_close_all) {
			jtp.removeAll();
			names.clear();
			hashMap.clear();
			textMap.clear();
			jmt_save.setEnabled(false);
			jmt_saveas.setEnabled(false);
			jb_pre.setEnabled(false);
			jb_next.setEnabled(false);
			jmt_undo.setEnabled(false);
			jmt_redo.setEnabled(false);
			jb_run.setEnabled(false);
			jmt_run.setEnabled(false);
			jmt_run2.setEnabled(false);
			jmt_runall.setEnabled(false);
			jmt_runall2.setEnabled(false);
			// jmt_run_notopen.setEnabled(false);
			jmt_cut.setEnabled(false);
			jmt_copy.setEnabled(false);
		} else if (e.getSource() == jmt_close_others) {
			int index = jtp.getSelectedIndex();
			String name = jtp.getToolTipTextAt(index);
			File[] f = { new File(name) };
			jtp.removeAll();
			names.clear();
			hashMap.clear();
			textMap.clear();
			this.open(f);
			// int n = jtp.getTabCount();
			// for (int i = 0; i < n; i++) {
			// int index = jtp.getSelectedIndex();
			// if (i != index) {
			// jtp.remove(i);
			// names.remove(jtp.getToolTipTextAt(i));
			// hashMap.remove(jtp.getToolTipTextAt(i));
			// textMap.remove(jtp.getToolTipTextAt(i));
			// }
			// }
		} else if (e.getSource() == jb_pre || e.getSource() == jmt_undo) {
			int index = jtp.getSelectedIndex();
			String name = jtp.getToolTipTextAt(index);
			UndoManager um = hashMap.get(jtp.getToolTipTextAt(jtp
					.getSelectedIndex()));
			um.undo();
			hashMap.put(name, um);
			jb_pre.setEnabled(um.canUndo());
			jb_next.setEnabled(um.canRedo());
			jmt_undo.setEnabled(um.canUndo());
			jmt_redo.setEnabled(um.canRedo());
		} else if (e.getSource() == jb_next || e.getSource() == jmt_redo) {
			int index = jtp.getSelectedIndex();
			String name = jtp.getToolTipTextAt(index);
			UndoManager um = hashMap.get(jtp.getToolTipTextAt(jtp
					.getSelectedIndex()));
			um.redo();
			hashMap.put(name, um);
			jb_pre.setEnabled(um.canUndo());
			jb_next.setEnabled(um.canRedo());
			jmt_undo.setEnabled(um.canUndo());
			jmt_redo.setEnabled(um.canRedo());
		} else if (e.getSource() == jmt_cut || e.getSource() == jmt_cut2) {
			int index = jtp.getSelectedIndex();
			String name = jtp.getToolTipTextAt(index);
			JTextArea jta1 = textMap.get(name);
			jta1.cut();
			jmt_paste.setEnabled(true);
			jmt_paste2.setEnabled(true);
		} else if (e.getSource() == jmt_copy || e.getSource() == jmt_copy2) {
			int index = jtp.getSelectedIndex();
			String name = jtp.getToolTipTextAt(index);
			JTextArea jta1 = textMap.get(name);
			jta1.copy();
			jmt_paste.setEnabled(true);
			jmt_paste2.setEnabled(true);
		} else if (e.getSource() == jmt_paste || e.getSource() == jmt_paste2) {
			int index = jtp.getSelectedIndex();
			String name = jtp.getToolTipTextAt(index);
			JTextArea jta1 = textMap.get(name);
			jta1.paste();
			jmt_paste.setEnabled(false);
			jmt_paste2.setEnabled(false);
		} else if (e.getSource() == jmt_run || e.getSource() == jmt_run2
				|| e.getSource() == jb_run) {
			if (jb_save.isEnabled()) {
				jmt_save.setEnabled(false);
				jb_save.setEnabled(false);
				this.saveFile();
			}
			int index = jtp.getSelectedIndex();
			String fullName = jtp.getToolTipTextAt(index);
			if (convert(fullName)) {
				JOptionPane.showMessageDialog(null, "文件已经转换成功！", "转换提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "文件转换时出错！", "转换提示",
						JOptionPane.ERROR_MESSAGE);
			}

		} else if (e.getSource() == jmt_runall || e.getSource() == jmt_runall2) {
			if (jb_save.isEnabled()) {
				jmt_save.setEnabled(false);
				jb_save.setEnabled(false);
				this.saveFile();
			}
			boolean b = true;
			for (int i = 0; i < names.size(); i++) {
				if (!convert(names.get(i))) {
					b = false;
				}
			}
			if (b) {
				JOptionPane.showMessageDialog(null, "文件已经转换成功！", "转换提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "文件转换时出错！", "转换提示",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == jmt_run_notopen) {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File("."));
			jfc.setMultiSelectionEnabled(true);
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
					"Java文件[.java]", "java"), filter2 = new FileNameExtensionFilter(
					"C文件[.c]", "c"), filter3 = new FileNameExtensionFilter(
					"头文件[.h]", "h");
			jfc.addChoosableFileFilter(filter3);
			jfc.addChoosableFileFilter(filter2);
			jfc.addChoosableFileFilter(filter1);

			if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
				return;
			File[] f = jfc.getSelectedFiles();
			String n;
			boolean b = true;
			for (int i = 0; i < f.length; i++) {
				n = f[i].getAbsolutePath();
				if (!convert(n)) {
					b = false;
				}
			}
			if (b) {
				JOptionPane.showMessageDialog(null, "文件已经转换成功！", "转换提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "文件转换时出错！", "转换提示",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == jb_htmlshow) {

			this.setEnabled(false);
			final ShowHtmlUI shui = new ShowHtmlUI(this, "显示HTML文件");
			shui.setVisible(true);
			shui.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					setEnabled(true);
					shui.dispose();
				}
			});
		} else if (e.getSource() == jmt_exit) {
			System.exit(0);
		}
	}

	public void open(File[] selectedFile) {
		int n = selectedFile.length;
		String[] name = new String[n];
		String[] fullName = new String[n];
		JScrollPane jsp1;
		ProgramChangeTOHtml pctoh;
		StringBuilder text;
		for (int i = 0; i < n; i++) {
			name[i] = selectedFile[i].getName();
			fullName[i] = selectedFile[i].getAbsolutePath();
			if (names.contains(fullName[i])) {
				jtp.setSelectedIndex(this.returnSelectedComponent(fullName[i]));
				continue;
			}
			pctoh = new ProgramChangeTOHtml();
			if (!pctoh.getSelectedFileContent(fullName[i])) {
				JOptionPane.showMessageDialog(null, "无法打开文件", "打开提示",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			names.add(fullName[i]);
			text = new StringBuilder();
			int size = pctoh.getConent().size();
			for (int j = 0; j < size; j++) {
				text.append(pctoh.getConent().get(j));
				if (j < size - 1) {
					text.append("\n");
				}
			}
			JTextArea jta = new JTextArea();
			jta.setFont(new Font("宋体", Font.BOLD, 20));
			jta.setText(text.toString());
			jta.setCaretPosition(0);
			jsp1 = new JScrollPane(jta);
			jtp.add(name[i], jsp1);
			jtp.setSelectedComponent(jsp1);
			jtp.setToolTipTextAt(jtp.getSelectedIndex(), fullName[i]);
			jb_pre.setEnabled(false);
			jb_next.setEnabled(false);
			jmt_undo.setEnabled(false);
			jmt_redo.setEnabled(false);
			UndoManager undoManager = new UndoManager();
			hashMap.put(fullName[i], undoManager);
			textMap.put(fullName[i], jta);
			textMap.get(fullName[i]).getDocument()
					.addUndoableEditListener(this);
			textMap.get(fullName[i]).getDocument().addDocumentListener(this);
			textMap.get(fullName[i]).addMouseListener(this);
		}
		jmt_saveas.setEnabled(true);
		jb_run.setEnabled(true);
		jmt_run.setEnabled(true);
		jmt_run2.setEnabled(true);
		jmt_runall.setEnabled(true);
		jmt_runall2.setEnabled(true);
		jmt_run_notopen.setEnabled(true);
		jmt_cut.setEnabled(true);
		jmt_copy.setEnabled(true);
	}

	private void saveFile() {
		int index = 0;
		String name;
		String content;
		try {
			for (int i = 0; i < textMap.size(); i++) {
				name = names.get(i);
				content = ((JTextArea) textMap.get(name)).getText();
				PrintWriter pw = new PrintWriter(name);
				pw.print(content);
				pw.close();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

	}

	private int returnSelectedComponent(String name) {
		String str;
		for (int i = 0; i < names.size(); i++) {
			str = jtp.getToolTipTextAt(i);
			if (name.equals(str)) {
				return i;
			}
		}
		return -1;
	}

	public void stateChanged(ChangeEvent e) {
		if (!names.isEmpty()) {
			int index = ((JTabbedPane) e.getSource()).getSelectedIndex();
			if (index >= 0) {
				String name = jtp.getToolTipTextAt(index);
				UndoManager um = hashMap.get(name);
				if (um != null) {
					jb_pre.setEnabled(um.canUndo());
					jb_next.setEnabled(um.canRedo());
					jmt_undo.setEnabled(um.canUndo());
					jmt_redo.setEnabled(um.canRedo());
				}
			}
		}
	}

	public void undoableEditHappened(UndoableEditEvent e) {
		int index = jtp.getSelectedIndex();
		String name = jtp.getToolTipTextAt(index);
		UndoManager um = hashMap.get(name);
		um.addEdit(e.getEdit());
		hashMap.put(name, um);
		jb_pre.setEnabled((hashMap.get(name)).canUndo());
		jb_next.setEnabled((hashMap.get(name)).canRedo());
		jmt_undo.setEnabled((hashMap.get(name)).canUndo());
		jmt_redo.setEnabled((hashMap.get(name)).canRedo());
	}

	public void insertUpdate(DocumentEvent e) {
		jmt_save.setEnabled(true);
		jb_save.setEnabled(true);
	}

	public void removeUpdate(DocumentEvent e) {
		jmt_save.setEnabled(true);
		jb_save.setEnabled(true);
	}

	public void changedUpdate(DocumentEvent e) {
		jmt_save.setEnabled(true);
		jb_save.setEnabled(true);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 3) {
			JPopupMenu jpm2 = new JPopupMenu();
			jpm2.add(jmt_cut2);
			jpm2.add(jmt_copy2);
			jpm2.add(jmt_paste2);
			jpm2.add(jmt_run2);
			jpm2.add(jmt_runall2);
			jpm2.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public boolean convert(String fullName) {
		int i = fullName.lastIndexOf(".") + 1;
		File f = new File(fullName);
		String str = f.getName();
		String suffix = fullName.substring(i);
		boolean b = suffix.equals("java") || suffix.equals("c")
				|| suffix.equals("h") || suffix.equals("H") || suffix.equals("js") || suffix.equals("cpp");
		if (!b) {
			return false;
		}
		String fileName = str.substring(0, str.lastIndexOf("."));
		ProgramChangeTOHtml pctoh = new ProgramChangeTOHtml();
		pctoh.getSelectedFileContent(fullName);
		if (row) {
			pctoh.changeProgramtoHtmlWithRow(appearance.getTypeColor(),
					appearance.getMarksColor(),
					appearance.getAnnotationColor(), appearance.getFont(),
					appearance.getSize(), suffix);
		} else {
			pctoh.changeProgramtoHtml(appearance.getTypeColor(), appearance
					.getMarksColor(), appearance.getAnnotationColor(),
					appearance.getFont(), appearance.getSize(), suffix);
		}
		if (fullPath.endsWith(".")) {
			fullPath = fullPath.substring(0, fullPath.length() - 1);
		}
		if (pctoh.save(fullPath + fileName + ".html")) {
			return true;
		} else {
			return false;
		}
	}
}
