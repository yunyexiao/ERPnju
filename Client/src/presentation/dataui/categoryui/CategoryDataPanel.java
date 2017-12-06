package presentation.dataui.categoryui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import blservice.CategoryBLService;
import blservice.infoservice.GetCommodityInterface;
import businesslogic.CommodityBL;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.InfoWindow;
import presentation.component.TopButtonPanel;
import vo.CategoryVO;

public class CategoryDataPanel implements PanelInterface{
    
    private JPanel panel;
    private JTree tree;

    public CategoryDataPanel(CategoryBLService categoryBl, ActionListener closeListener) {
    	GetCommodityInterface commodityBL = new CommodityBL();
    	
        tree = new JTree(categoryBl.getModel());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);//���õ�ѡ
        double[][] size = {{TableLayout.FILL}, {TableLayout.PREFERRED, TableLayout.FILL}};
        panel = new JPanel(new TableLayout(size));
        
        TopButtonPanel buttonPanel = new TopButtonPanel();
        buttonPanel.addButton("����", new ImageIcon("resource/AddData.png"), new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            	if (tree.getSelectionCount() == 1) {
            		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            		CategoryVO category = (CategoryVO)node.getUserObject();
            		if (categoryBl.hasContent(category.getId()) || commodityBL.hasCommodity(category.getId())) {
            			new InfoWindow("��ѡ��һ���սڵ���Ϊ���ڵ�");
            		} else {
                        new AddCategoryWindow(categoryBl, category);
                        tree.setModel(categoryBl.getModel());
            		}
            	} else {
            		new InfoWindow("��ѡ��һ���ڵ���Ϊ���ڵ�");
            	}
            }
        });
		buttonPanel.addButton("�޸�", new ImageIcon("resource/ChangeData.png"), new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){
		    	if (tree.getSelectionCount() == 1) {
			        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
			        if (node.isRoot()) new InfoWindow("�����޸ĸ��ڵ�");
			        else {
				        new UpdateCategoryWindow(categoryBl, (CategoryVO)node.getUserObject());
				        tree.setModel(categoryBl.getModel());
			        }
		    	} else {
		    		new InfoWindow("��ѡ��һ����Ҫ�޸ĵĽڵ�");
		    	}
		    }
		});
		buttonPanel.addButton("ɾ��", new ImageIcon("resource/DeleteData.png"), new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){
		        if(tree.isSelectionEmpty()) {new InfoWindow("��ѡ����Ҫɾ���Ľڵ�"); return;}
				int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��������Ϣ��", "��ʾ", JOptionPane.YES_NO_OPTION);
				if(response == 0){
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                    if (node.isRoot()) new InfoWindow("����ɾ�����ڵ�");
                    else {
                    	CategoryVO category = (CategoryVO)node.getUserObject();
                    	if (categoryBl.hasContent(category.getId()) || commodityBL.hasCommodity(category.getId())) {
                			new InfoWindow("����ɾ���ǿսڵ�");
                		} else {
                			if(categoryBl.delete(category.getId())){
        				        JOptionPane.showMessageDialog(null, "��Ϣ�ѳɹ�ɾ��", "ϵͳ", JOptionPane.INFORMATION_MESSAGE); 
        				        tree.setModel(categoryBl.getModel());
        				    }
                		}
                    }
				}
		    }
		});
		buttonPanel.addButton("�ر�", new ImageIcon("resource/Close.png"), closeListener);
	
		panel.add(buttonPanel.getPanel(), "0 0");
		panel.add(tree, "0 1");
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

}
