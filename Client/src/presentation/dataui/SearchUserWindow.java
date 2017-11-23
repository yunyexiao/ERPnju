package presentation.dataui;

import java.awt.BorderLayout;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import blservice.UserBLService;
import layout.TableLayout;
import presentation.component.MyTableModel;

public class SearchUserWindow extends FatherWindow {
    
    private UserBLService userBl;
    private MyTableModel model;
    private JTextField keyTextField;
    private ButtonGroup typeButtonGroup;

    public SearchUserWindow(UserBLService userBl) {
        super();
        this.userBl = userBl;
        
        JLabel keyLabel = new JLabel("¹Ø¼ü´Ê");
        JLabel typeLabel = new JLabel("ËÑË÷ÀàÐÍ");
        keyTextField = new JTextField();
        JRadioButton idRadioButton = new JRadioButton("°´±àºÅËÑË÷");
        JRadioButton nameRadioButton = new JRadioButton("°´Ãû³ÆËÑË÷");
        typeButtonGroup = new ButtonGroup();
        typeButtonGroup.add(idRadioButton);
        typeButtonGroup.add(nameRadioButton);
        JPanel typePanel = new JPanel();
        typePanel.add(idRadioButton);
        typePanel.add(nameRadioButton);
        
        double[][] size = {{TableLayout.FILL, TableLayout.PREFERRED, 20.0, TableLayout.PREFERRED, TableLayout.FILL}
            , {TableLayout.FILL, TableLayout.PREFERRED, 20.0, TableLayout.PREFERRED, TableLayout.FILL}};
        JPanel centerPanel = new JPanel(new TableLayout(size));
        centerPanel.add(keyLabel, "1 1");
        centerPanel.add(typeLabel, "1 3");
        centerPanel.add(keyTextField, "3 1");
        centerPanel.add(typePanel, "3 3");
        
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public MyTableModel getModel() {
    	return model;
    }
    
    @Override
    boolean taskFinished() {
        String key = keyTextField.getText()
            , type = getSelectedText(typeButtonGroup);
        model = userBl.search(type, key);
        return true;
    }

    @Override
    String getSuccessMsg() {
        return "ËÑË÷Íê±Ï";
    }
    
	private String getSelectedText(ButtonGroup bg){
	    Enumeration<AbstractButton> eb = bg.getElements();
	    while(eb.hasMoreElements()){
	        AbstractButton b = eb.nextElement();
	        if(b.isSelected())
	            return b.getText();
	    }
	    return null;
	}

}
