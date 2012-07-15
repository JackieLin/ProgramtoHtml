package com.jtr;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

class FileNode {

    protected File fileName;

    public FileNode(File file) {
        fileName = file;
    }

    public File getFile() {
        return fileName;
    }

    @Override
    public String toString() {
        return fileName.getName().length() > 0 ? fileName.getName() : fileName.getPath();
    }

    public boolean expand(DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode flag = (DefaultMutableTreeNode) parent.getFirstChild();
        if (flag == null) // No flag
        {
            return false;
        }
        Object obj = flag.getUserObject();
//        if (!(obj instanceof Boolean)) {
//            return false; // Already expanded
//        }
        parent.removeAllChildren(); // Remove Flag

        File[] files = listFiles();
        if (files == null) {
            return true;
        }

        // 将节点的全部子节点装进来
        ArrayList v = new ArrayList();
        for (int k = 0; k < files.length; k++) {
            File f = files[k];
            FileNode newNode = new FileNode(f);
            boolean isAdded = false;
            // 对子文件夹进行排序
            for (int i = 0; i < v.size(); i++) {
                FileNode nd = (FileNode) v.get(i);
                if (newNode.compareTo(nd) < 0) {
                    v.add(i,newNode);
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                v.add(newNode);
            }
        }

        // 添加全部子文件夹
        for (int i = 0; i < v.size(); i++) {
            FileNode nd = (FileNode) v.get(i);
              DefaultMutableTreeNode node = new DefaultMutableTreeNode(nd);
            parent.add(node);
            if (nd.hasSubDirs()) {
                node.add(new DefaultMutableTreeNode(true));
            }
        }

        return true;
    }

    public boolean hasSubDirs() {
        File[] files = listFiles();
        if (files == null) {
            return false;
        }
        for (int k = 0; k < files.length; k++) {
            if (files[k].isDirectory()) {
                return true;
            }
        }
//        return false;
        return true;
    }

    public int compareTo(FileNode toCompare) {
        return fileName.getName().compareToIgnoreCase(toCompare.fileName.getName());
    }

    protected File[] listFiles() {
        if (!fileName.isDirectory()) {
            return null;
        }
        try {
            return fileName.listFiles();
        } catch (Exception e) {
       //     e.printStackTrace();
            return null;
        }
    }
}
