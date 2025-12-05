package com.example.swing.panels;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * 对话框面板
 * 展示各种对话框的用法
 */
public class DialogPanel extends JPanel {
    
    private final JFrame parentFrame;
    private final Consumer<String> statusUpdater;
    
    public DialogPanel(JFrame parentFrame, Consumer<String> statusUpdater) {
        this.parentFrame = parentFrame;
        this.statusUpdater = statusUpdater;
        setLayout(new GridLayout(2, 2, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(createMessageDialogPanel());
        add(createInputDialogPanel());
        add(createConfirmDialogPanel());
        add(createCustomDialogPanel());
    }
    
    private JPanel createMessageDialogPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 10));
        panel.setBorder(BorderFactory.createTitledBorder("消息对话框"));
        
        JButton infoBtn = new JButton("信息对话框");
        infoBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(parentFrame,
                "这是一条信息消息。",
                "信息",
                JOptionPane.INFORMATION_MESSAGE);
            statusUpdater.accept("显示了信息对话框");
        });
        
        JButton warnBtn = new JButton("警告对话框");
        warnBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(parentFrame,
                "这是一条警告消息！",
                "警告",
                JOptionPane.WARNING_MESSAGE);
            statusUpdater.accept("显示了警告对话框");
        });
        
        JButton errorBtn = new JButton("错误对话框");
        errorBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(parentFrame,
                "发生了一个错误！",
                "错误",
                JOptionPane.ERROR_MESSAGE);
            statusUpdater.accept("显示了错误对话框");
        });
        
        JButton plainBtn = new JButton("普通对话框");
        plainBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(parentFrame,
                "这是一条普通消息。",
                "消息",
                JOptionPane.PLAIN_MESSAGE);
            statusUpdater.accept("显示了普通对话框");
        });
        
        panel.add(infoBtn);
        panel.add(warnBtn);
        panel.add(errorBtn);
        panel.add(plainBtn);
        
        return panel;
    }
    
    private JPanel createInputDialogPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 10));
        panel.setBorder(BorderFactory.createTitledBorder("输入对话框"));
        
        JButton textInputBtn = new JButton("文本输入");
        textInputBtn.addActionListener(e -> {
            String result = JOptionPane.showInputDialog(parentFrame,
                "请输入您的姓名:",
                "文本输入",
                JOptionPane.QUESTION_MESSAGE);
            if (result != null) {
                statusUpdater.accept("输入的姓名: " + result);
            } else {
                statusUpdater.accept("取消了输入");
            }
        });
        
        JButton comboInputBtn = new JButton("下拉选择输入");
        comboInputBtn.addActionListener(e -> {
            String[] options = {"Java", "Python", "JavaScript", "C++", "Go"};
            String result = (String) JOptionPane.showInputDialog(parentFrame,
                "请选择您喜欢的编程语言:",
                "选择语言",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            if (result != null) {
                statusUpdater.accept("选择的语言: " + result);
            } else {
                statusUpdater.accept("取消了选择");
            }
        });
        
        JButton initialValueBtn = new JButton("带默认值输入");
        initialValueBtn.addActionListener(e -> {
            String result = JOptionPane.showInputDialog(parentFrame,
                "请输入邮箱地址:",
                "user@example.com");
            if (result != null) {
                statusUpdater.accept("输入的邮箱: " + result);
            } else {
                statusUpdater.accept("取消了输入");
            }
        });
        
        panel.add(textInputBtn);
        panel.add(comboInputBtn);
        panel.add(initialValueBtn);
        
        return panel;
    }
    
    private JPanel createConfirmDialogPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 10));
        panel.setBorder(BorderFactory.createTitledBorder("确认对话框"));
        
        JButton yesNoBtn = new JButton("是/否 确认");
        yesNoBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(parentFrame,
                "您确定要执行此操作吗?",
                "确认",
                JOptionPane.YES_NO_OPTION);
            String response = result == JOptionPane.YES_OPTION ? "是" : "否";
            statusUpdater.accept("用户选择: " + response);
        });
        
        JButton yesNoCancelBtn = new JButton("是/否/取消 确认");
        yesNoCancelBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(parentFrame,
                "是否保存更改?",
                "保存确认",
                JOptionPane.YES_NO_CANCEL_OPTION);
            String response = switch (result) {
                case JOptionPane.YES_OPTION -> "是";
                case JOptionPane.NO_OPTION -> "否";
                default -> "取消";
            };
            statusUpdater.accept("用户选择: " + response);
        });
        
        JButton okCancelBtn = new JButton("确定/取消 确认");
        okCancelBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(parentFrame,
                "确定要继续吗?",
                "确认",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
            String response = result == JOptionPane.OK_OPTION ? "确定" : "取消";
            statusUpdater.accept("用户选择: " + response);
        });
        
        panel.add(yesNoBtn);
        panel.add(yesNoCancelBtn);
        panel.add(okCancelBtn);
        
        return panel;
    }
    
    private JPanel createCustomDialogPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 10));
        panel.setBorder(BorderFactory.createTitledBorder("自定义对话框"));
        
        JButton customOptionsBtn = new JButton("自定义按钮选项");
        customOptionsBtn.addActionListener(e -> {
            String[] options = {"立即执行", "稍后执行", "从不执行"};
            int result = JOptionPane.showOptionDialog(parentFrame,
                "请选择执行时间:",
                "自定义选项",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            if (result >= 0) {
                statusUpdater.accept("选择了: " + options[result]);
            } else {
                statusUpdater.accept("关闭了对话框");
            }
        });
        
        JButton fileChooserBtn = new JButton("文件选择器");
        fileChooserBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("选择文件");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int result = fileChooser.showOpenDialog(parentFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                statusUpdater.accept("选择的文件: " + fileChooser.getSelectedFile().getName());
            } else {
                statusUpdater.accept("取消了文件选择");
            }
        });
        
        JButton colorChooserBtn = new JButton("颜色选择器");
        colorChooserBtn.addActionListener(e -> {
            Color color = JColorChooser.showDialog(parentFrame, "选择颜色", Color.BLUE);
            if (color != null) {
                String colorStr = String.format("RGB(%d, %d, %d)", 
                    color.getRed(), color.getGreen(), color.getBlue());
                statusUpdater.accept("选择的颜色: " + colorStr);
            } else {
                statusUpdater.accept("取消了颜色选择");
            }
        });
        
        JButton customDialogBtn = new JButton("完全自定义对话框");
        customDialogBtn.addActionListener(e -> showCustomDialog());
        
        panel.add(customOptionsBtn);
        panel.add(fileChooserBtn);
        panel.add(colorChooserBtn);
        panel.add(customDialogBtn);
        
        return panel;
    }
    
    private void showCustomDialog() {
        JDialog dialog = new JDialog(parentFrame, "用户信息", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(parentFrame);
        
        // 表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("用户名:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("邮箱:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField emailField = new JTextField(15);
        formPanel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("年龄:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(25, 1, 120, 1));
        formPanel.add(ageSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JCheckBox subscribeCheck = new JCheckBox("订阅邮件通知");
        formPanel.add(subscribeCheck, gbc);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> {
            String info = String.format("用户名: %s, 邮箱: %s, 年龄: %s, 订阅: %s",
                usernameField.getText(),
                emailField.getText(),
                ageSpinner.getValue(),
                subscribeCheck.isSelected() ? "是" : "否");
            statusUpdater.accept(info);
            dialog.dispose();
        });
        
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> {
            statusUpdater.accept("取消了自定义对话框");
            dialog.dispose();
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
}

