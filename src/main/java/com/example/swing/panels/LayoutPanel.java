package com.example.swing.panels;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * 布局示例面板
 * 展示各种布局管理器的用法
 */
public class LayoutPanel extends JPanel {
    
    private final Consumer<String> statusUpdater;
    
    public LayoutPanel(Consumer<String> statusUpdater) {
        this.statusUpdater = statusUpdater;
        setLayout(new GridLayout(2, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(createBorderLayoutDemo());
        add(createGridLayoutDemo());
        add(createFlowLayoutDemo());
        add(createBoxLayoutDemo());
    }
    
    private JPanel createBorderLayoutDemo() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createTitledBorder("BorderLayout"));
        
        JPanel demo = new JPanel(new BorderLayout(5, 5));
        demo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JButton north = createDemoButton("NORTH", Color.decode("#FF6B6B"));
        JButton south = createDemoButton("SOUTH", Color.decode("#4ECDC4"));
        JButton east = createDemoButton("EAST", Color.decode("#45B7D1"));
        JButton west = createDemoButton("WEST", Color.decode("#96CEB4"));
        JButton center = createDemoButton("CENTER", Color.decode("#FFEAA7"));
        
        demo.add(north, BorderLayout.NORTH);
        demo.add(south, BorderLayout.SOUTH);
        demo.add(east, BorderLayout.EAST);
        demo.add(west, BorderLayout.WEST);
        demo.add(center, BorderLayout.CENTER);
        
        container.add(demo, BorderLayout.CENTER);
        return container;
    }
    
    private JPanel createGridLayoutDemo() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createTitledBorder("GridLayout"));
        
        JPanel demo = new JPanel(new GridLayout(3, 3, 5, 5));
        demo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        Color[] colors = {
            Color.decode("#FF6B6B"), Color.decode("#4ECDC4"), Color.decode("#45B7D1"),
            Color.decode("#96CEB4"), Color.decode("#FFEAA7"), Color.decode("#DDA0DD"),
            Color.decode("#98D8C8"), Color.decode("#F7DC6F"), Color.decode("#BB8FCE")
        };
        
        for (int i = 1; i <= 9; i++) {
            JButton btn = createDemoButton(String.valueOf(i), colors[i - 1]);
            demo.add(btn);
        }
        
        container.add(demo, BorderLayout.CENTER);
        return container;
    }
    
    private JPanel createFlowLayoutDemo() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createTitledBorder("FlowLayout"));
        
        JPanel demo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        String[] labels = {"按钮1", "较长按钮2", "按钮3", "非常长的按钮4", "按钮5"};
        Color[] colors = {
            Color.decode("#FF6B6B"), Color.decode("#4ECDC4"), Color.decode("#45B7D1"),
            Color.decode("#96CEB4"), Color.decode("#FFEAA7")
        };
        
        for (int i = 0; i < labels.length; i++) {
            JButton btn = createDemoButton(labels[i], colors[i]);
            demo.add(btn);
        }
        
        container.add(demo, BorderLayout.CENTER);
        
        // 添加对齐方式选择
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JComboBox<String> alignCombo = new JComboBox<>(new String[]{"左对齐", "居中", "右对齐"});
        alignCombo.addActionListener(e -> {
            FlowLayout layout = (FlowLayout) demo.getLayout();
            int align = switch (alignCombo.getSelectedIndex()) {
                case 0 -> FlowLayout.LEFT;
                case 2 -> FlowLayout.RIGHT;
                default -> FlowLayout.CENTER;
            };
            layout.setAlignment(align);
            demo.revalidate();
            statusUpdater.accept("FlowLayout 对齐方式: " + alignCombo.getSelectedItem());
        });
        controlPanel.add(new JLabel("对齐:"));
        controlPanel.add(alignCombo);
        container.add(controlPanel, BorderLayout.SOUTH);
        
        return container;
    }
    
    private JPanel createBoxLayoutDemo() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createTitledBorder("BoxLayout"));
        
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // 垂直布局
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.setBorder(BorderFactory.createTitledBorder("垂直"));
        
        Color[] vColors = {Color.decode("#FF6B6B"), Color.decode("#4ECDC4"), Color.decode("#45B7D1")};
        for (int i = 0; i < 3; i++) {
            JButton btn = createDemoButton("V" + (i + 1), vColors[i]);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            verticalPanel.add(btn);
            if (i < 2) verticalPanel.add(Box.createVerticalStrut(5));
        }
        
        // 水平布局
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setBorder(BorderFactory.createTitledBorder("水平"));
        
        Color[] hColors = {Color.decode("#96CEB4"), Color.decode("#FFEAA7"), Color.decode("#DDA0DD")};
        for (int i = 0; i < 3; i++) {
            JButton btn = createDemoButton("H" + (i + 1), hColors[i]);
            btn.setAlignmentY(Component.CENTER_ALIGNMENT);
            horizontalPanel.add(btn);
            if (i < 2) horizontalPanel.add(Box.createHorizontalStrut(5));
        }
        
        mainPanel.add(verticalPanel);
        mainPanel.add(horizontalPanel);
        
        container.add(mainPanel, BorderLayout.CENTER);
        return container;
    }
    
    private JButton createDemoButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.addActionListener(e -> statusUpdater.accept("点击了布局示例按钮: " + text));
        return button;
    }
}

