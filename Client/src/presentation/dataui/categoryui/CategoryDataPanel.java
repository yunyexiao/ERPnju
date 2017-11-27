package presentation.dataui.categoryui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import blservice.CategoryBLService;
import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.TopButtonPanel;
import vo.CategoryVO;

public class CategoryDataPanel implements PanelInterface{
    
    private JPanel panel;
    private JTree tree;

    public CategoryDataPanel(CategoryBLService categoryBl, ActionListener closeListener) {
        tree = new JTree(categoryBl.getModel());
        double[][] size = {{TableLayout.FILL}, {TableLayout.PREFERRED, TableLayout.FILL}};
        panel = new JPanel(new TableLayout(size));
        
        TopButtonPanel buttonPanel = new TopButtonPanel();
        buttonPanel.addButton("����", new ImageIcon("resource/AddData.png"), new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                new AddCategoryWindow(categoryBl);
                tree.setModel(categoryBl.getModel());
            }
        });
		buttonPanel.addButton("�޸�", new ImageIcon("resource/ChangeData.png"), new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
		        new UpdateCategoryWindow(categoryBl, (CategoryVO)node.getUserObject());
		        tree.setModel(categoryBl.getModel());
		    }
		});
		buttonPanel.addButton("ɾ��", new ImageIcon("resource/DeleteData.png"), new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){
				int response = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��������Ϣ��", "��ʾ", JOptionPane.YES_NO_OPTION);
				if(response == 0){
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
				    String id = ((CategoryVO)node.getUserObject()).getId();
				    if(categoryBl.delete(id)){
				        JOptionPane.showMessageDialog(null, "��Ϣ�ѳɹ�ɾ��", "ϵͳ", JOptionPane.INFORMATION_MESSAGE); 
				        tree.setModel(categoryBl.getModel());
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
