package com.x.commons.ui.dialog;

import com.ax.commons.local.LocalManager;
import com.ax.commons.timming.TimerTask;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @Desc
 * @Date 2020-03-21 09:26
 * @Author AD
 */
public class LoadingBox {
    private JFrame box = new JFrame();
    private JTextArea text;
    private Object msgLock = new Object();
    
    public LoadingBox(String title, String imagePath, final String var3, String var4) {
        this.box.setUndecorated(true);
        this.box.setResizable(false);
        this.box.setDefaultCloseOperation(2);
        this.box.setTitle(title);
        this.box.setIconImage(Toolkit.getDefaultToolkit().getImage(imagePath));
        this.box.setSize(500, 350);
        Container container = this.box.getContentPane();
        container.setLayout((LayoutManager)null);
        JLabel label = new JLabel(var4, 0);
        label.setForeground(Color.WHITE);
        label.setFont(LocalManager.getDefaultFont(12));
        label.setBounds(20, this.box.getHeight() / 2 + 10, this.box.getWidth() - 40, 30);
        container.add(label);
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        container.add(textPanel);
        this.text = new JTextArea();
        this.text.setForeground(Color.WHITE);
        this.text.setFont(LocalManager.getDefaultFont(12));
        this.text.setEditable(false);
        this.text.setWrapStyleWord(true);
        this.text.setBorder(null);
        this.text.setOpaque(false);
        this.text.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent event) {
                if (LoadingBox.this.text != null && LoadingBox.this.box != null) {
                    if (LoadingBox.this.text.getWidth() > LoadingBox.this.box.getWidth() - 40) {
                        LoadingBox.this.text.setLineWrap(true);
                        LoadingBox.this.text.setSize(LoadingBox.this.box.getWidth() - 40, 60);
                    }
                    
                }
            }
        });
        textPanel.add(this.text, "Center");
        JPanel var8 = new JPanel() {
            private static final long serialVersionUID = -4345131326110974587L;
            
            protected void paintComponent(Graphics graphics) {
                ImageIcon var2 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(var3));
                Image var3x = var2.getImage();
                int var4 = var2.getIconWidth();
                int var5 = var2.getIconHeight();
                graphics.drawImage(var3x, 0, 0, var4, var5, var2.getImageObserver());
                LoadingBox.this.box.setSize(var4, var5);
            }
        };
        var8.setBorder(new LineBorder(new Color(0, 0, 0)));
        var8.setBounds(0, 0, this.box.getWidth(), this.box.getHeight());
        container.add(var8);
        textPanel.setBounds(20, this.box.getHeight() - 70, this.box.getWidth() - 40, 60);
        this.box.setLocationRelativeTo(null);
        this.box.setVisible(true);
    }
    
    public void setMessageColor(Color var1) {
        this.text.setForeground(var1);
    }
    
    public void appendMessage(String var1) {
        synchronized(this.msgLock) {
            this.text.setLineWrap(false);
            this.text.append(var1);
        }
    }
    
    public void setMessage(String var1) {
        this.setMessage(var1, 500L);
    }
    
    public void setMessage(String var1, long var2) {
        synchronized(this.msgLock) {
            this.text.setLineWrap(false);
            this.text.setText(var1);
            if (var2 > 0L) {
                try {
                    Thread.sleep(var2);
                } catch (InterruptedException var7) {
                    var7.printStackTrace();
                }
            }
            
        }
    }
    
    public void show(String var1) {
        synchronized(this.msgLock) {
            this.text.setText(var1);
            this.box.setVisible(true);
            
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    public void hide() {
        this.box.setVisible(false);
    }
    
    public void hide(int time) {
        TimerTask.timer().add(new Runnable() {
            public void run() {
                LoadingBox.this.box.setVisible(false);
            }
        }, (long)time);
    }
}
