package com.example.swing.panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.util.function.Consumer;

/**
 * 列表组件面板
 * 展示JList的各种用法
 */
public class ListPanel extends JPanel {
    
    private final Consumer<String> statusUpdater;
    private JList<String> leftList;
    private JList<String> rightList;
    private DefaultListModel<String> leftModel;
    private DefaultListModel<String> rightModel;
    
    public ListPanel(Consumer<String> statusUpdater) {
        this.statusUpdater = statusUpdater;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(createDualListPanel(), BorderLayout.CENTER);
        add(createSimpleListPanel(), BorderLayout.EAST);
    }
    
    private JPanel createDualListPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("双列表选择器"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // 左侧列表（可选项）
        leftModel = new DefaultListModel<>();
        String[] items = {"Java", "Python", "JavaScript", "C++", "Go", "Rust", "Swift", "Kotlin"};
        for (String item : items) {
            leftModel.addElement(item);
        }
        
        leftList = new JList<>(leftModel);
        leftList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        leftList.addListSelectionListener(this::onLeftListSelection);
        
        JScrollPane leftScroll = new JScrollPane(leftList);
        leftScroll.setPreferredSize(new Dimension(150, 200));
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(leftScroll, gbc);
        
        // 按钮区域
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JButton addButton = new JButton(">");
        addButton.setToolTipText("添加选中项");
        addButton.addActionListener(e -> moveSelectedItems(leftList, leftModel, rightModel));
        panel.add(addButton, gbc);
        
        gbc.gridy = 1;
        JButton addAllButton = new JButton(">>");
        addAllButton.setToolTipText("添加所有项");
        addAllButton.addActionListener(e -> moveAllItems(leftModel, rightModel));
        panel.add(addAllButton, gbc);
        
        gbc.gridy = 2;
        JButton removeButton = new JButton("<");
        removeButton.setToolTipText("移除选中项");
        removeButton.addActionListener(e -> moveSelectedItems(rightList, rightModel, leftModel));
        panel.add(removeButton, gbc);
        
        gbc.gridy = 3;
        JButton removeAllButton = new JButton("<<");
        removeAllButton.setToolTipText("移除所有项");
        removeAllButton.addActionListener(e -> moveAllItems(rightModel, leftModel));
        panel.add(removeAllButton, gbc);
        
        // 右侧列表（已选项）
        rightModel = new DefaultListModel<>();
        rightList = new JList<>(rightModel);
        rightList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        rightList.addListSelectionListener(this::onRightListSelection);
        
        JScrollPane rightScroll = new JScrollPane(rightList);
        rightScroll.setPreferredSize(new Dimension(150, 200));
        
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(rightScroll, gbc);
        
        // 标签
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("可选语言", SwingConstants.CENTER), gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("已选语言", SwingConstants.CENTER), gbc);
        
        return panel;
    }
    
    private JPanel createSimpleListPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("带图标列表"));
        panel.setPreferredSize(new Dimension(200, 0));
        
        // 创建带图标的列表
        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("📁 文档");
        model.addElement("📁 图片");
        model.addElement("📁 音乐");
        model.addElement("📁 视频");
        model.addElement("📄 readme.txt");
        model.addElement("📄 config.json");
        model.addElement("🖼️ logo.png");
        model.addElement("🎵 music.mp3");
        
        JList<String> iconList = new JList<>(model);
        iconList.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        iconList.setFixedCellHeight(30);
        iconList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = iconList.getSelectedValue();
                if (selected != null) {
                    statusUpdater.accept("选中文件: " + selected);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(iconList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // 操作按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton openButton = new JButton("打开");
        openButton.addActionListener(e -> {
            String selected = iconList.getSelectedValue();
            if (selected != null) {
                JOptionPane.showMessageDialog(this, 
                    "打开: " + selected, "操作", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(e -> {
            int index = iconList.getSelectedIndex();
            if (index >= 0) {
                String item = model.remove(index);
                statusUpdater.accept("删除了: " + item);
            }
        });
        
        buttonPanel.add(openButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void onLeftListSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            java.util.List<String> selected = leftList.getSelectedValuesList();
            if (!selected.isEmpty()) {
                statusUpdater.accept("可选列表选中: " + String.join(", ", selected));
            }
        }
    }
    
    private void onRightListSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            java.util.List<String> selected = rightList.getSelectedValuesList();
            if (!selected.isEmpty()) {
                statusUpdater.accept("已选列表选中: " + String.join(", ", selected));
            }
        }
    }
    
    private void moveSelectedItems(JList<String> sourceList, 
                                   DefaultListModel<String> sourceModel,
                                   DefaultListModel<String> targetModel) {
        java.util.List<String> selected = sourceList.getSelectedValuesList();
        for (String item : selected) {
            sourceModel.removeElement(item);
            targetModel.addElement(item);
        }
        if (!selected.isEmpty()) {
            statusUpdater.accept("移动了 " + selected.size() + " 项");
        }
    }
    
    private void moveAllItems(DefaultListModel<String> sourceModel,
                              DefaultListModel<String> targetModel) {
        int count = sourceModel.size();
        while (sourceModel.size() > 0) {
            String item = sourceModel.remove(0);
            targetModel.addElement(item);
        }
        if (count > 0) {
            statusUpdater.accept("移动了所有 " + count + " 项");
        }
    }
}

