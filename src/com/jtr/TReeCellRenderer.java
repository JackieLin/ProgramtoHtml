package com.jtr;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class TReeCellRenderer extends DefaultTreeCellRenderer {

	
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

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		// MyDefaultMutableTreeNode node = (MyDefaultMutableTreeNode)value;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		FileNode fnode = getFileNode(node);
		String str = node.toString();
		// 得到系统文件
		try {
			FileSystemView fv = new FileSystemView() {

				public File createNewFolder(File containingDir)
						throws IOException {
					throw new UnsupportedOperationException(
							"Not supported yet.");
				}
			};

			// 将当前图标设置为系统图标
			// System.out.println(fnode.getFile().getAbsolutePath());
			if (str.equals("计算机")) {
				this.setIcon(new ImageIcon("./images/computer.jpg"));
			} 
		/*	else if( str.equals("A:\\") ||str.equals("D:\\")||
					 str.equals("E:\\")||str.equals("F:\\")||
					 str.equals("G:\\")||str.equals("I:\\")||str.equals("H:\\")){
				this.setIcon(new ImageIcon("images/disk.jpg"));
			}*/
			
				else {
				try {
					this.setIcon(fv.getSystemIcon(new File(fnode.getFile()
							.getAbsolutePath())));
				} catch (Exception e) {
					// TODO: handle exception
				
				}
			}
		} catch (Exception e) {

		}
		return this;
	}

}
