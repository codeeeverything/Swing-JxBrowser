package com.example.swing;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

/**
 * Swing 示例应用程序入口
 * 展示各种常用的Swing组件
 */
public class SwingDemoApp {
    
    public static void main(String[] args) {
        // 设置现代化的外观
        try {
            FlatLightLaf.setup();
        } catch (Exception e) {
            System.err.println("无法设置FlatLaf外观，使用默认外观");
        }
        
        // 在事件调度线程中创建和显示GUI
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}

