package com.example.swing;

import com.example.swing.panels.*;
import javax.swing.*;
import java.awt.*;

/**
 * 主窗口框架
 * 使用选项卡面板展示各种Swing组件示例
 */
public class MainFrame extends JFrame {
    
    private JTabbedPane tabbedPane;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JLabel statusLabel;
    
    public MainFrame() {
        initializeFrame();
        createMenuBar();
        createToolBar();
        createTabbedPane();
        createStatusBar();
        layoutComponents();
    }
    
    private void initializeFrame() {
        setTitle("Swing 组件示例 - JxBrowser 集成练习");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null); // 居中显示
        
        // 设置应用图标
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/app.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // 忽略图标加载失败
        }
    }
    
    private void createMenuBar() {
        menuBar = new JMenuBar();
        
        // 文件菜单
        JMenu fileMenu = new JMenu("文件(F)");
        fileMenu.setMnemonic('F');
        
        JMenuItem newItem = new JMenuItem("新建", 'N');
        newItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        newItem.addActionListener(e -> updateStatus("点击了: 新建"));
        
        JMenuItem openItem = new JMenuItem("打开", 'O');
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        openItem.addActionListener(e -> showFileChooser());
        
        JMenuItem saveItem = new JMenuItem("保存", 'S');
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        saveItem.addActionListener(e -> updateStatus("点击了: 保存"));
        
        JMenuItem exitItem = new JMenuItem("退出", 'X');
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // 编辑菜单
        JMenu editMenu = new JMenu("编辑(E)");
        editMenu.setMnemonic('E');
        
        JMenuItem cutItem = new JMenuItem("剪切");
        cutItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        
        JMenuItem copyItem = new JMenuItem("复制");
        copyItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        
        JMenuItem pasteItem = new JMenuItem("粘贴");
        pasteItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
        
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        
        // 视图菜单
        JMenu viewMenu = new JMenu("视图(V)");
        viewMenu.setMnemonic('V');
        
        JCheckBoxMenuItem showToolbar = new JCheckBoxMenuItem("显示工具栏", true);
        showToolbar.addActionListener(e -> toolBar.setVisible(showToolbar.isSelected()));
        
        JCheckBoxMenuItem showStatus = new JCheckBoxMenuItem("显示状态栏", true);
        showStatus.addActionListener(e -> statusLabel.getParent().setVisible(showStatus.isSelected()));
        
        viewMenu.add(showToolbar);
        viewMenu.add(showStatus);
        
        // 帮助菜单
        JMenu helpMenu = new JMenu("帮助(H)");
        helpMenu.setMnemonic('H');
        
        JMenuItem aboutItem = new JMenuItem("关于");
        aboutItem.addActionListener(e -> showAboutDialog());
        
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void createToolBar() {
        toolBar = new JToolBar("工具栏");
        toolBar.setFloatable(false);
        
        JButton newBtn = createToolButton("新建", "创建新文件");
        JButton openBtn = createToolButton("打开", "打开文件");
        JButton saveBtn = createToolButton("保存", "保存文件");
        
        toolBar.add(newBtn);
        toolBar.add(openBtn);
        toolBar.add(saveBtn);
        toolBar.addSeparator();
        
        JButton cutBtn = createToolButton("剪切", "剪切选中内容");
        JButton copyBtn = createToolButton("复制", "复制选中内容");
        JButton pasteBtn = createToolButton("粘贴", "粘贴内容");
        
        toolBar.add(cutBtn);
        toolBar.add(copyBtn);
        toolBar.add(pasteBtn);
        toolBar.addSeparator();
        
        // 添加一个搜索框
        JTextField searchField = new JTextField(15);
        searchField.setMaximumSize(new Dimension(200, 30));
        searchField.setToolTipText("搜索...");
        toolBar.add(searchField);
        
        JButton searchBtn = createToolButton("搜索", "执行搜索");
        toolBar.add(searchBtn);
    }
    
    private JButton createToolButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setFocusPainted(false);
        button.addActionListener(e -> updateStatus("点击了工具栏按钮: " + text));
        return button;
    }
    
    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        
        // 添加各种组件示例面板
        tabbedPane.addTab("基础组件", new BasicComponentsPanel(this::updateStatus));
        tabbedPane.addTab("表格示例", new TablePanel(this::updateStatus));
        tabbedPane.addTab("树形结构", new TreePanel(this::updateStatus));
        tabbedPane.addTab("列表组件", new ListPanel(this::updateStatus));
        tabbedPane.addTab("对话框", new DialogPanel(this, this::updateStatus));
        tabbedPane.addTab("布局示例", new LayoutPanel(this::updateStatus));
        tabbedPane.addTab("浏览器区域", new BrowserPlaceholderPanel(this::updateStatus));
        
        // 选项卡切换事件
        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            String tabTitle = tabbedPane.getTitleAt(index);
            updateStatus("切换到选项卡: " + tabTitle);
        });
    }
    
    private void createStatusBar() {
        statusLabel = new JLabel("就绪");
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }
    
    public void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    private void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择文件");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            updateStatus("选择的文件: " + fileChooser.getSelectedFile().getName());
        }
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Swing 组件示例程序\n\n" +
            "版本: 1.0\n" +
            "用于练习 JxBrowser 集成\n\n" +
            "包含常用Swing组件示例",
            "关于",
            JOptionPane.INFORMATION_MESSAGE);
    }
}

