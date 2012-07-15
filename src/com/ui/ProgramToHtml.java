/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
*/
package com.ui;

import javax.swing.*;

/**
 *
 * @author Li changwang
 */
public class ProgramToHtml {
    private static MainFrame mf;

    /**
     * @return the mf
     */
    public static MainFrame getMf() {
        return mf;
    }
    public ProgramToHtml(){
        mf=new MainFrame();
        mf.setIconImage(new ImageIcon("./images\\1.jpg").getImage());
        mf.setTitle("源代码自动转换程序");
        mf.setSize(1000,600);
        mf.setLocationRelativeTo(null);
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mf.setVisible(true);
    }
    public static void main(String[] args) {
        new ProgramToHtml();
    }

}
