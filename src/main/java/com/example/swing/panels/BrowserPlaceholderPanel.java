package com.example.swing.panels;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * 浏览器占位面板
 * 这个面板预留给JxBrowser集成使用
 * 目前显示一个占位符，提示这里将嵌入浏览器组件
 */
public class BrowserPlaceholderPanel extends JPanel {
    
    private final Consumer<String> statusUpdater;
    private JPanel browserContainer;
    private JTextField urlField;
    
    public BrowserPlaceholderPanel(Consumer<String> statusUpdater) {
        this.statusUpdater = statusUpdater;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        createToolbar();
        createBrowserPlaceholder();
        createInfoPanel();
    }
    
    private void createToolbar() {
        JPanel toolbar = new JPanel(new BorderLayout(5, 0));
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // 导航按钮
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        JButton backBtn = new JButton("◀");
        backBtn.setToolTipText("后退");
        backBtn.addActionListener(e -> statusUpdater.accept("点击了后退按钮 (JxBrowser集成后生效)"));
        
        JButton forwardBtn = new JButton("▶");
        forwardBtn.setToolTipText("前进");
        forwardBtn.addActionListener(e -> statusUpdater.accept("点击了前进按钮 (JxBrowser集成后生效)"));
        
        JButton refreshBtn = new JButton("⟳");
        refreshBtn.setToolTipText("刷新");
        refreshBtn.addActionListener(e -> statusUpdater.accept("点击了刷新按钮 (JxBrowser集成后生效)"));
        
        JButton homeBtn = new JButton("🏠");
        homeBtn.setToolTipText("主页");
        homeBtn.addActionListener(e -> statusUpdater.accept("点击了主页按钮 (JxBrowser集成后生效)"));
        
        navPanel.add(backBtn);
        navPanel.add(forwardBtn);
        navPanel.add(refreshBtn);
        navPanel.add(homeBtn);
        
        // URL输入框
        urlField = new JTextField("https://www.example.com");
        urlField.addActionListener(e -> {
            String url = urlField.getText();
            statusUpdater.accept("尝试导航到: " + url + " (JxBrowser集成后生效)");
        });
        
        JButton goBtn = new JButton("前往");
        goBtn.addActionListener(e -> urlField.postActionEvent());
        
        JPanel urlPanel = new JPanel(new BorderLayout(5, 0));
        urlPanel.add(urlField, BorderLayout.CENTER);
        urlPanel.add(goBtn, BorderLayout.EAST);
        
        toolbar.add(navPanel, BorderLayout.WEST);
        toolbar.add(urlPanel, BorderLayout.CENTER);
        
        add(toolbar, BorderLayout.NORTH);
    }
    
    private void createBrowserPlaceholder() {
        browserContainer = new JPanel(new BorderLayout());
        browserContainer.setBackground(Color.WHITE);
        browserContainer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        // 占位符内容
        JPanel placeholder = new JPanel(new GridBagLayout());
        placeholder.setBackground(new Color(248, 249, 250));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // 图标
        JLabel iconLabel = new JLabel("🌐");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        gbc.gridy = 0;
        placeholder.add(iconLabel, gbc);
        
        // 标题
        JLabel titleLabel = new JLabel("JxBrowser 集成区域");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        gbc.gridy = 1;
        placeholder.add(titleLabel, gbc);
        
        // 描述
        JLabel descLabel = new JLabel("<html><center>这里将嵌入 JxBrowser 组件<br>" +
            "用于在 Swing 应用中显示网页内容</center></html>");
        descLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        descLabel.setForeground(new Color(127, 140, 141));
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        placeholder.add(descLabel, gbc);
        
        // 功能列表
        JPanel featuresPanel = new JPanel(new GridLayout(0, 2, 20, 10));
        featuresPanel.setOpaque(false);
        featuresPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        String[][] features = {
            {"✓ 完整的Chromium内核", "✓ JavaScript执行"},
            {"✓ DOM操作", "✓ 网络请求拦截"},
            {"✓ Cookie管理", "✓ 打印支持"},
            {"✓ 文件下载", "✓ 开发者工具"}
        };
        
        for (String[] row : features) {
            for (String feature : row) {
                JLabel featureLabel = new JLabel(feature);
                featureLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
                featureLabel.setForeground(new Color(46, 204, 113));
                featuresPanel.add(featureLabel);
            }
        }
        
        gbc.gridy = 3;
        placeholder.add(featuresPanel, gbc);
        
        // 集成提示
        JLabel tipLabel = new JLabel("<html><center><br>集成步骤:<br>" +
            "1. 在 build.gradle 添加 JxBrowser 依赖<br>" +
            "2. 创建 Engine 和 Browser 实例<br>" +
            "3. 将 BrowserView 添加到此面板</center></html>");
        tipLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        tipLabel.setForeground(new Color(149, 165, 166));
        tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 4;
        placeholder.add(tipLabel, gbc);
        
        browserContainer.add(placeholder, BorderLayout.CENTER);
        add(browserContainer, BorderLayout.CENTER);
    }
    
    private void createInfoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        infoPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        
        JLabel statusIcon = new JLabel("⚪");
        JLabel statusText = new JLabel("浏览器未加载");
        statusText.setForeground(Color.GRAY);
        
        infoPanel.add(statusIcon);
        infoPanel.add(statusText);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(new JLabel("安全:"));
        infoPanel.add(new JLabel("🔒 HTTPS"));
        
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    /**
     * 获取浏览器容器面板
     * JxBrowser集成时，可以将BrowserView添加到这个容器中
     * 
     * @return 浏览器容器面板
     */
    public JPanel getBrowserContainer() {
        return browserContainer;
    }
    
    /**
     * 获取URL输入框
     * JxBrowser集成时，可以监听这个输入框的事件来导航
     * 
     * @return URL输入框
     */
    public JTextField getUrlField() {
        return urlField;
    }
}

