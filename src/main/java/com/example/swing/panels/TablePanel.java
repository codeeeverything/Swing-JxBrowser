package com.example.swing.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.function.Consumer;

/**
 * 表格面板
 * 展示JTable的各种用法
 */
public class TablePanel extends JPanel {
    
    private final Consumer<String> statusUpdater;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public TablePanel(Consumer<String> statusUpdater) {
        this.statusUpdater = statusUpdater;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        createTable();
        createControlPanel();
    }
    
    private void createTable() {
        // 表格列名
        String[] columnNames = {"ID", "姓名", "年龄", "部门", "薪资", "在职"};
        
        // 表格数据
        Object[][] data = {
            {1, "张三", 28, "研发部", 15000.0, true},
            {2, "李四", 32, "市场部", 12000.0, true},
            {3, "王五", 25, "人事部", 10000.0, true},
            {4, "赵六", 35, "财务部", 18000.0, false},
            {5, "钱七", 29, "研发部", 16000.0, true},
            {6, "孙八", 31, "市场部", 13000.0, true},
            {7, "周九", 27, "研发部", 14000.0, true},
            {8, "吴十", 33, "产品部", 17000.0, false},
        };
        
        // 创建表格模型
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> Integer.class;
                    case 2 -> Integer.class;
                    case 4 -> Double.class;
                    case 5 -> Boolean.class;
                    default -> String.class;
                };
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // ID列不可编辑
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);
        
        // 设置列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(60);
        
        // 选择事件
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int modelRow = table.convertRowIndexToModel(row);
                    String name = tableModel.getValueAt(modelRow, 1).toString();
                    statusUpdater.accept("选中员工: " + name);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.setBorder(BorderFactory.createTitledBorder("表格操作"));
        
        // 添加行按钮
        JButton addButton = new JButton("添加行");
        addButton.addActionListener(e -> {
            int newId = tableModel.getRowCount() + 1;
            tableModel.addRow(new Object[]{newId, "新员工", 25, "未分配", 8000.0, true});
            statusUpdater.accept("添加了新行");
        });
        
        // 删除行按钮
        JButton deleteButton = new JButton("删除选中行");
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int modelRow = table.convertRowIndexToModel(row);
                String name = tableModel.getValueAt(modelRow, 1).toString();
                tableModel.removeRow(modelRow);
                statusUpdater.accept("删除了员工: " + name);
            } else {
                statusUpdater.accept("请先选择要删除的行");
            }
        });
        
        // 搜索框
        JLabel searchLabel = new JLabel("搜索:");
        JTextField searchField = new JTextField(15);
        searchField.addActionListener(e -> {
            String text = searchField.getText();
            TableRowSorter<?> sorter = (TableRowSorter<?>) table.getRowSorter();
            if (text.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
            statusUpdater.accept("搜索: " + text);
        });
        
        JButton searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> searchField.postActionEvent());
        
        JButton clearButton = new JButton("清除筛选");
        clearButton.addActionListener(e -> {
            searchField.setText("");
            TableRowSorter<?> sorter = (TableRowSorter<?>) table.getRowSorter();
            sorter.setRowFilter(null);
            statusUpdater.accept("清除了搜索筛选");
        });
        
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(searchLabel);
        controlPanel.add(searchField);
        controlPanel.add(searchButton);
        controlPanel.add(clearButton);
        
        add(controlPanel, BorderLayout.NORTH);
    }
}

