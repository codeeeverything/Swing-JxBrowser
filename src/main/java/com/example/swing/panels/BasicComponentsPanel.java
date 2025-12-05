package com.example.swing.panels;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.function.Consumer;

/**
 * 基础组件面板
 * 展示按钮、文本框、复选框、单选按钮、滑块等基础组件
 */
public class BasicComponentsPanel extends JPanel {
    
    private final Consumer<String> statusUpdater;
    
    public BasicComponentsPanel(Consumer<String> statusUpdater) {
        this.statusUpdater = statusUpdater;
        setLayout(new GridLayout(2, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(createButtonPanel());
        add(createTextPanel());
        add(createSelectionPanel());
        add(createSliderPanel());
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(new TitledBorder("按钮组件"));
        
        JButton normalBtn = new JButton("普通按钮");
        normalBtn.addActionListener(e -> statusUpdater.accept("点击了普通按钮"));
        
        JButton iconBtn = new JButton("图标按钮 ⭐");
        iconBtn.addActionListener(e -> statusUpdater.accept("点击了图标按钮"));
        
        JToggleButton toggleBtn = new JToggleButton("切换按钮");
        toggleBtn.addActionListener(e -> 
            statusUpdater.accept("切换按钮状态: " + (toggleBtn.isSelected() ? "开" : "关")));
        
        JButton disabledBtn = new JButton("禁用按钮");
        disabledBtn.setEnabled(false);
        
        panel.add(normalBtn);
        panel.add(iconBtn);
        panel.add(toggleBtn);
        panel.add(disabledBtn);
        
        return panel;
    }
    
    private JPanel createTextPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("文本组件"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // 标签
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("用户名:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField textField = new JTextField(15);
        textField.setToolTipText("请输入用户名");
        panel.add(textField, gbc);
        
        // 密码框
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("密码:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);
        
        // 文本区域
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("备注:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        JTextArea textArea = new JTextArea(3, 15);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, gbc);
        
        // 格式化文本框
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("邮箱:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField emailField = new JTextField(15);
        emailField.setText("example@email.com");
        panel.add(emailField, gbc);
        
        return panel;
    }
    
    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("选择组件"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        
        // 复选框
        gbc.gridy = 0;
        JCheckBox check1 = new JCheckBox("选项 A", true);
        check1.addActionListener(e -> 
            statusUpdater.accept("选项 A: " + (check1.isSelected() ? "选中" : "未选中")));
        panel.add(check1, gbc);
        
        gbc.gridy = 1;
        JCheckBox check2 = new JCheckBox("选项 B");
        check2.addActionListener(e -> 
            statusUpdater.accept("选项 B: " + (check2.isSelected() ? "选中" : "未选中")));
        panel.add(check2, gbc);
        
        // 单选按钮
        gbc.gridy = 2;
        panel.add(new JLabel("选择一项:"), gbc);
        
        ButtonGroup radioGroup = new ButtonGroup();
        JRadioButton radio1 = new JRadioButton("红色", true);
        JRadioButton radio2 = new JRadioButton("绿色");
        JRadioButton radio3 = new JRadioButton("蓝色");
        
        radio1.addActionListener(e -> statusUpdater.accept("选择了: 红色"));
        radio2.addActionListener(e -> statusUpdater.accept("选择了: 绿色"));
        radio3.addActionListener(e -> statusUpdater.accept("选择了: 蓝色"));
        
        radioGroup.add(radio1);
        radioGroup.add(radio2);
        radioGroup.add(radio3);
        
        gbc.gridy = 3;
        panel.add(radio1, gbc);
        gbc.gridy = 4;
        panel.add(radio2, gbc);
        gbc.gridy = 5;
        panel.add(radio3, gbc);
        
        // 下拉框
        gbc.gridy = 6;
        panel.add(new JLabel("下拉选择:"), gbc);
        
        gbc.gridy = 7;
        String[] items = {"Java", "Python", "JavaScript", "C++", "Go"};
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.addActionListener(e -> 
            statusUpdater.accept("选择了编程语言: " + comboBox.getSelectedItem()));
        panel.add(comboBox, gbc);
        
        return panel;
    }
    
    private JPanel createSliderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("滑块和进度条"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        // 滑块
        gbc.gridy = 0;
        panel.add(new JLabel("音量:"), gbc);
        
        gbc.gridy = 1;
        JSlider slider = new JSlider(0, 100, 50);
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(e -> 
            statusUpdater.accept("音量: " + slider.getValue() + "%"));
        panel.add(slider, gbc);
        
        // 进度条
        gbc.gridy = 2;
        panel.add(new JLabel("下载进度:"), gbc);
        
        gbc.gridy = 3;
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(65);
        progressBar.setStringPainted(true);
        panel.add(progressBar, gbc);
        
        // 不确定进度条
        gbc.gridy = 4;
        panel.add(new JLabel("加载中:"), gbc);
        
        gbc.gridy = 5;
        JProgressBar indeterminateBar = new JProgressBar();
        indeterminateBar.setIndeterminate(true);
        panel.add(indeterminateBar, gbc);
        
        // 微调器
        gbc.gridy = 6;
        panel.add(new JLabel("数量:"), gbc);
        
        gbc.gridy = 7;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(5, 0, 100, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(e -> 
            statusUpdater.accept("数量: " + spinner.getValue()));
        panel.add(spinner, gbc);
        
        return panel;
    }
}

