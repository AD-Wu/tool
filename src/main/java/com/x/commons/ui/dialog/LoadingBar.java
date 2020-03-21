package com.x.commons.ui.dialog;

import com.ax.commons.timming.TimerTask;

import javax.swing.*;
import java.awt.*;

/**
 * @Desc TODO
 * @Date 2020-03-21 00:47
 * @Author AD
 */
public class LoadingBar {
    private JDialog frame;
    private JLabel label;
    
    public LoadingBar() {
        this(400, 80);
    }
    
    public LoadingBar(int var1, int var2) {
        this.frame = new JDialog();
        this.frame.setDefaultCloseOperation(2);
        Container var3 = this.frame.getContentPane();
        var3.setLayout(new FlowLayout(0));
        JPanel var4 = new JPanel();
        this.label = new JLabel("", 2);
        var4.add(this.label);
        var3.add(var4);
        this.frame.setResizable(false);
        this.frame.setSize(var1 <= 0 ? 400 : var1, var2 <= 0 ? 80 : var2);
        this.frame.setLocationRelativeTo((Component)null);
        this.frame.setAlwaysOnTop(true);
    }
    
    public void setMessage(String var1) {
        this.label.setText(var1);
    }
    
    public void show(String var1, String var2) {
        this.frame.setVisible(true);
        this.frame.setTitle(var1);
        this.label.setText(var2);
    }
    
    public void hide() {
        this.frame.setVisible(false);
    }
    
    public void hide(int var1) {
        TimerTask.timer().add(new Runnable() {
            public void run() {
                LoadingBar.this.frame.setVisible(false);
            }
        }, (long)var1);
    }
    
    public static void main(String[] var0) {
        (new LoadingBar()).show("abc", "aaa");
    }
}
