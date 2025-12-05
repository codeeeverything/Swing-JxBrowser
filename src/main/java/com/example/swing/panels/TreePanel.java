package com.example.swing.panels;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.function.Consumer;

/**
 * 树形结构面板
 * 展示JTree的用法
 */
public class TreePanel extends JPanel {
    
    private final Consumer<String> statusUpdater;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;
    
    public TreePanel(Consumer<String> statusUpdater) {
        this.statusUpdater = statusUpdater;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        createTree();
        createControlPanel();
        createInfoPanel();
    }
    
    private void createTree() {
        // 创建根节点
        rootNode = new DefaultMutableTreeNode("公司组织架构");
        
        // 创建部门节点
        DefaultMutableTreeNode techDept = new DefaultMutableTreeNode("技术部");
        DefaultMutableTreeNode salesDept = new DefaultMutableTreeNode("销售部");
        DefaultMutableTreeNode hrDept = new DefaultMutableTreeNode("人力资源部");
        DefaultMutableTreeNode financeDept = new DefaultMutableTreeNode("财务部");
        
        // 技术部子节点
        DefaultMutableTreeNode devTeam = new DefaultMutableTreeNode("研发组");
        devTeam.add(new DefaultMutableTreeNode("张三 - 高级工程师"));
        devTeam.add(new DefaultMutableTreeNode("李四 - 中级工程师"));
        devTeam.add(new DefaultMutableTreeNode("王五 - 初级工程师"));
        
        DefaultMutableTreeNode testTeam = new DefaultMutableTreeNode("测试组");
        testTeam.add(new DefaultMutableTreeNode("赵六 - 测试主管"));
        testTeam.add(new DefaultMutableTreeNode("钱七 - 测试工程师"));
        
        DefaultMutableTreeNode opsTeam = new DefaultMutableTreeNode("运维组");
        opsTeam.add(new DefaultMutableTreeNode("孙八 - 运维工程师"));
        
        techDept.add(devTeam);
        techDept.add(testTeam);
        techDept.add(opsTeam);
        
        // 销售部子节点
        salesDept.add(new DefaultMutableTreeNode("周九 - 销售经理"));
        salesDept.add(new DefaultMutableTreeNode("吴十 - 销售代表"));
        salesDept.add(new DefaultMutableTreeNode("郑十一 - 销售代表"));
        
        // 人力资源部子节点
        hrDept.add(new DefaultMutableTreeNode("王十二 - HR主管"));
        hrDept.add(new DefaultMutableTreeNode("冯十三 - 招聘专员"));
        
        // 财务部子节点
        financeDept.add(new DefaultMutableTreeNode("陈十四 - 财务主管"));
        financeDept.add(new DefaultMutableTreeNode("褚十五 - 会计"));
        
        // 添加到根节点
        rootNode.add(techDept);
        rootNode.add(salesDept);
        rootNode.add(hrDept);
        rootNode.add(financeDept);
        
        // 创建树模型和树
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        
        // 展开所有节点
        expandAllNodes(tree, 0, tree.getRowCount());
        
        // 选择事件
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
                tree.getLastSelectedPathComponent();
            if (node != null) {
                statusUpdater.accept("选中节点: " + node.getUserObject());
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }
        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
    
    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.setBorder(BorderFactory.createTitledBorder("树操作"));
        
        JButton addButton = new JButton("添加节点");
        addButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) 
                tree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                String nodeName = JOptionPane.showInputDialog(this, 
                    "请输入新节点名称:", "添加节点", JOptionPane.PLAIN_MESSAGE);
                if (nodeName != null && !nodeName.trim().isEmpty()) {
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeName);
                    treeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
                    tree.scrollPathToVisible(new TreePath(newNode.getPath()));
                    statusUpdater.accept("添加了节点: " + nodeName);
                }
            } else {
                statusUpdater.accept("请先选择一个父节点");
            }
        });
        
        JButton deleteButton = new JButton("删除节点");
        deleteButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) 
                tree.getLastSelectedPathComponent();
            if (selectedNode != null && selectedNode != rootNode) {
                String nodeName = selectedNode.getUserObject().toString();
                treeModel.removeNodeFromParent(selectedNode);
                statusUpdater.accept("删除了节点: " + nodeName);
            } else {
                statusUpdater.accept("请选择要删除的节点（不能删除根节点）");
            }
        });
        
        JButton expandButton = new JButton("全部展开");
        expandButton.addActionListener(e -> {
            expandAllNodes(tree, 0, tree.getRowCount());
            statusUpdater.accept("展开了所有节点");
        });
        
        JButton collapseButton = new JButton("全部折叠");
        collapseButton.addActionListener(e -> {
            int row = tree.getRowCount() - 1;
            while (row >= 1) {
                tree.collapseRow(row);
                row--;
            }
            statusUpdater.accept("折叠了所有节点");
        });
        
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        controlPanel.add(expandButton);
        controlPanel.add(collapseButton);
        
        add(controlPanel, BorderLayout.NORTH);
    }
    
    private void createInfoPanel() {
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("节点信息"));
        
        JTextArea infoArea = new JTextArea(5, 20);
        infoArea.setEditable(false);
        infoArea.setText("选择一个节点查看详细信息...");
        
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
                tree.getLastSelectedPathComponent();
            if (node != null) {
                StringBuilder info = new StringBuilder();
                info.append("节点名称: ").append(node.getUserObject()).append("\n");
                info.append("层级深度: ").append(node.getLevel()).append("\n");
                info.append("子节点数: ").append(node.getChildCount()).append("\n");
                info.append("是否叶节点: ").append(node.isLeaf() ? "是" : "否").append("\n");
                info.append("路径: ").append(getNodePath(node));
                infoArea.setText(info.toString());
            }
        });
        
        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
    }
    
    private String getNodePath(DefaultMutableTreeNode node) {
        StringBuilder path = new StringBuilder();
        Object[] nodes = node.getUserObjectPath();
        for (int i = 0; i < nodes.length; i++) {
            path.append(nodes[i]);
            if (i < nodes.length - 1) {
                path.append(" > ");
            }
        }
        return path.toString();
    }
}

