package com.jtr;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.io.File;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.event.*;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.*;
import javax.swing.tree.TreeSelectionModel;

import com.ui.ProgramToHtml;

public class JtreeFile extends JPanel implements TreeSelectionListener,
		TreeExpansionListener, MouseListener, ActionListener, KeyListener {

	JTree jtree1 = new JTree();
	DefaultTreeModel dftm;
	DefaultMutableTreeNode root, Child;
	JScrollPane jsp;
	JPanel jp1;
	DefaultTreeCellRenderer redder;
	JPopupMenu popupMenu = new JPopupMenu();

	JMenuItem openFile, changeFile, exitFile, newName, delFile, newFile;
	Boolean isctrl = false, ischose = true;
	File[] fi2;
	int n = 0;
	ArrayList<File> a = new ArrayList<File>();
	ArrayList<File> b = new ArrayList<File>();

	/*public static void main(String[] args) {
		new JtreeFile();
	}*/

	public JtreeFile() {
		root = new DefaultMutableTreeNode("�����");
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			Child = new DefaultMutableTreeNode(new FileNode(roots[i]));
			root.add(Child);
			Child.add(new DefaultMutableTreeNode(true));
		}

		dftm = new DefaultTreeModel(root);
		jtree1 = new JTree(dftm);

		// �ڵ��ͼ��

		jtree1.setCellRenderer(new TReeCellRenderer());
		// redder = (DefaultTreeCellRenderer) jtree1.getCellRenderer();
		// redder.setLeafIcon(new ImageIcon("images/node.jpg"));
		jtree1.addTreeSelectionListener(this);

		jtree1.addTreeExpansionListener(this);
		jtree1.addMouseListener(this);
		jtree1.addKeyListener(this);
		jtree1.getSelectionModel().setSelectionMode(
				TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

		openFile = new JMenuItem("��"); // ����ѡ��˵������¼�����
		openFile.setIcon(new ImageIcon("images/open.jpg"));
		openFile.addActionListener(this);
		changeFile = new JMenuItem("ת��");
		changeFile.setIcon(new ImageIcon("images/run.jpg"));
		changeFile.addActionListener(this);

		exitFile = new JMenuItem("ȡ��");
		// exitFile.setIcon(new ImageIcon("images/open.jpg"));
		exitFile.addActionListener(this);

		newName = new JMenuItem("������");
		// newName.setIcon(new ImageIcon("images/open.jpg"));
		newName.addActionListener(this);
		delFile = new JMenuItem("ɾ���ļ�");
		delFile.setIcon(new ImageIcon("images/del.jpg"));
		delFile.addActionListener(this);
		newFile = new JMenuItem("�½��ļ�");
		newFile.addActionListener(this);

		// �˵�1
		popupMenu.add(openFile);
		popupMenu.addSeparator();// �ָ���
		popupMenu.add(changeFile);
		popupMenu.addSeparator();// �ָ���
		popupMenu.add(delFile);
		popupMenu.addSeparator();// �ָ���
		popupMenu.add(newName);
		// popupMenu.addSeparator();// �ָ���
		// popupMenu.add(newFile);
		popupMenu.addSeparator();// �ָ���
		popupMenu.add(exitFile);

		this.setLayout(new BorderLayout());
		jtree1.add(popupMenu);
		// jtree1.setEditable(true);
		jsp = new JScrollPane(jtree1); // ����
		// jp1 = new JPanel(new GridLayout(1, 2));
		this.add(jsp);

	}

	DefaultMutableTreeNode getTreeNode(TreePath path) {
		return (DefaultMutableTreeNode) (path.getLastPathComponent());
	}

	FileNode getFileNode(DefaultMutableTreeNode node) {
		if (node == null) {
			return null;
		}
		Object obj = node.getUserObject();
		if (obj instanceof FileNode) {
			return (FileNode) obj;
		} else {
			return null;
		}
	}

	public void valueChanged(TreeSelectionEvent e) {
		// TreePath path =e.getNewLeadSelectionPath();
		// System.out.println(path);
		// TreeNode treenode =(TreeNode)path.getLastPathComponent();
		DefaultMutableTreeNode node = getTreeNode(e.getPath());
		FileNode fnode = getFileNode(node);
		// System.out.println(fnode.getFile().getAbsolutePath());
		try {
			if (!new File(fnode.getFile().getAbsolutePath()).isDirectory()) {
				b.add(new File(fnode.getFile().getAbsolutePath()));
				// System.out.println(b.get(0));
			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
		if (isctrl) {
			if (ischose) {
				if (b.size() >= 2) {
					a.add(b.get(b.size() - 2));
					// for(int i=0;i<b.size();i++){
					// System.out.println(b.get(i));
					// }
					// b.clear();

				}

				ischose = false;
			}
			try {
				if (!new File(fnode.getFile().getAbsolutePath()).isDirectory()) {
					a.add(new File(fnode.getFile().getAbsolutePath()));
					// b.clear();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		// System.out.println(fnode.getFile().getAbsolutePath());
	}

	public void treeExpanded(TreeExpansionEvent e) {
		final DefaultMutableTreeNode node = getTreeNode(e.getPath());
		final FileNode fnode = getFileNode(node);
		// System.out.println(fnode.getFile().getAbsolutePath());
		Thread runner = new Thread() {

			public void run() {
				if (fnode != null && fnode.expand(node)) {
					Runnable runnable = new Runnable() {

						public void run() {
							dftm.reload(node);
						}

					};
					SwingUtilities.invokeLater(runnable);
				}
			}
		};
		runner.start();

	}

	public void treeCollapsed(TreeExpansionEvent event) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		JTree tree = (JTree) e.getSource();
		int selRow = tree.getRowForLocation(e.getX(), e.getY());
		if (selRow != -1) {
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			TreeNode node = (TreeNode) selPath.getLastPathComponent();
			// DefaultMutableTreeNode node = getTreeNode(selPath);
			FileNode fnode = getFileNode((DefaultMutableTreeNode) node);

			// ��Ҷ�ڵ�
			if (node.isLeaf()) {
				// �˴���bug��Ĭ��ÿ���´򿪵Ľڵ㶼��Ҷ�ڵ�
				//
				// System.out.println(123);
				if (e.getClickCount() == 1) {
					// ����
					try {
						if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
							// �һ��¼�
							if (!new File(fnode.getFile().getAbsolutePath())
									.isDirectory()) {
								// ���Ϊ�ļ��оͲ�����
								// �����˵�
								popupMenu.show(tree, e.getX(), e.getY());

							}

						}
					} catch (Exception e2) {
						// TODO: handle exception
					}

				} else if (e.getClickCount() == 2) {
					// ˫��
					// System.out.println(fnode.getFile().getAbsolutePath());
					if (!new File(fnode.getFile().getAbsolutePath())
							.isDirectory()) {
						// ���Ϊ�ļ��оͲ�����

						File[] fi = { new File(fnode.getFile()
								.getAbsolutePath()) };
						ProgramToHtml.getMf().open(fi);
						// System.out.println(fnode.getFile().getAbsolutePath());
					}
				}
			}

		}
	}

	public void actionPerformed(ActionEvent e) {

		// ���ļ�
		if (e.getSource() == openFile) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtree1
					.getLastSelectedPathComponent();
			FileNode fnode = getFileNode((DefaultMutableTreeNode) node);
			if (!a.isEmpty()) {
				// System.out.println(12);
				File[] fi1 = new File[a.size()];
				for (int i = 0; i < a.size(); i++) {
					fi1[i] = a.get(i);
					// System.out.println(fi1[i].getAbsolutePath());
				}
				a.clear();
				b.clear();
				ProgramToHtml.getMf().open(fi1);
			}
			// ��Ҷ�ڵ�
			else if (node.isLeaf()) {

				if (!new File(fnode.getFile().getAbsolutePath()).isDirectory()) {
					// ���Ϊ�ļ��оͲ�����

					File[] fi = { new File(fnode.getFile().getAbsolutePath()) };
					ProgramToHtml.getMf().open(fi);
					// System.out.println(fnode.getFile().getAbsolutePath());
				}
			}

		}
		// ת���ļ�
		if (e.getSource() == changeFile) {

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtree1
					.getLastSelectedPathComponent();

			FileNode fnode = getFileNode((DefaultMutableTreeNode) node);
			if (!a.isEmpty()) {
				int n = 0;
				for (int i = 0; i < a.size(); i++) {
					try {
						if (!ProgramToHtml.getMf().convert(
								a.get(i).getAbsolutePath())) {
							JOptionPane.showMessageDialog(null, "��֧���ļ�" + "��"
									+ a.get(i).getName() + "��" + "�����͵�ת����",
									"ת����ʾ", JOptionPane.ERROR_MESSAGE);
							n = 1;
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}
				if (n == 0) {
					JOptionPane.showMessageDialog(null, "ת���ɹ���", "ת����ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				}
				// else {
				// JOptionPane.showMessageDialog(null, "����ת�����ɹ���", "ת����ʾ",
				// JOptionPane.ERROR_MESSAGE);
				// }
				a.clear();

			} else if (node.isLeaf()) {

				if (!new File(fnode.getFile().getAbsolutePath()).isDirectory()) {
					// ���Ϊ�ļ��оͲ�����

					File[] fi = { new File(fnode.getFile().getAbsolutePath()) };
					try {
						if (ProgramToHtml.getMf().convert(
								fnode.getFile().getAbsolutePath())) {
							JOptionPane.showMessageDialog(null, "ת���ɹ���",
									"ת����ʾ", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "��֧���ļ�" + "��"
									+ fnode.getFile().getName() + "��"
									+ "�����͵�ת����", "ת����ʾ",
									JOptionPane.ERROR_MESSAGE);

						}
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}
			}

		}
		if (e.getSource() == newFile) {

		}
		// ɾ���ļ�
		if (e.getSource() == delFile) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtree1
					.getLastSelectedPathComponent();

			if (!a.isEmpty()) {
				int n = 0;
				for (int i = 0; i < a.size(); i++) {
					try {
						File f1 = new File(a.get(i).getAbsolutePath());// ��ɾ����ԭ�ļ�
						if (!f1.delete()) {
							n = 1;
						}
					} catch (Exception e2) {
						// TODO: handle exception
						// e2.printStackTrace();
					}

				}
				if (n == 0) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ���", "ɾ����ʾ",
							JOptionPane.INFORMATION_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(null, "����ɾ�����ɹ���", "ɾ����ʾ",
							JOptionPane.ERROR_MESSAGE);

				}
				a.clear();
				dftm.reload();
			} else {
				FileNode fnode = getFileNode((DefaultMutableTreeNode) node);
				try {
					File f = new File(fnode.getFile().getAbsolutePath());// ��ɾ����ԭ�ļ�
					dftm.removeNodeFromParent(node);
					if (f.delete()) {
						JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
					}
				} catch (Exception en) {
					// TODO: handle exception
				}
			}
		}

		if (e.getSource() == exitFile) {
			// System.out.println("ȡ��");
		}
		// �������ļ�
		if (e.getSource() == newName) {
			String string;

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtree1
					.getLastSelectedPathComponent();
			FileNode fnode = getFileNode((DefaultMutableTreeNode) node);
			File f = new File(fnode.getFile().getAbsolutePath());// ��������ԭ�ļ�
			try {
				string = JOptionPane.showInputDialog(null, "Ҫע��Ӻ�׺����", "Hello",
						JOptionPane.QUESTION_MESSAGE);
				if (string != null) {

					// node=new DefaultMutableTreeNode(string);
					// ((DefaultTreeModel)(jtree1.getModel())).reload();
					// System.out.println(string);
					try {
						f.renameTo(new File(f.getParent() + "\\" + string));// ��ԭ�ļ���������
						JOptionPane.showMessageDialog(null, "�������ɹ���");

						DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
								string);

						// DefaultMutableTreeNode parNode = (
						// DefaultMutableTreeNode)node.getParent();
						// dftm.removeNodeFromParent(node);
						// dftm.insertNodeInto(newNode, parNode, 0 );
						// TreePath newPath = new TreePath(newNode.getPath());
						jtree1.getModel().valueForPathChanged(
								jtree1.getAnchorSelectionPath(), newNode);
						// TreePath newPath = new TreePath(newNode.getPath());
						// System.out.println(newPath);
						// jtree1.setSelectionPath(newPath);
						dftm.reload();
						// DefaultTreeModel model =
						// (DefaultTreeModel)jtree1.getModel();
						// model.nodeChanged(node);
						//									
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "������ʧ�ܣ�");
				}
			} catch (Exception ex) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "������ʧ�ܣ�");
			}

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			isctrl = true;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			isctrl = false;
			ischose = true;
			b.clear();

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
