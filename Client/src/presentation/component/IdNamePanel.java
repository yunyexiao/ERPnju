package presentation.component;

import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import layout.TableLayout;

/**
 * @author ã¢Ò¶Ïö
 */
public class IdNamePanel extends JPanel {

    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = -4890419785265613083L;
    
    private JLabel label;
    private JTextField idField, nameField;

    public IdNamePanel(String text, int idSize, int nameSize) {
        super();
        double[][] size = {
                {-2.0, 10.0, -2.0, 5.0, -2.0},
                {-2.0}
        };
        super.setLayout(new TableLayout(size));
        super.add(label = new JLabel(text), "0 0");
        super.add(idField = new JTextField(idSize), "2 0");
        idField.setEditable(false);
        super.add(new JLabel("--"), "3 0");
        super.add(nameField = new JTextField(nameSize), "4 0");
        nameField.setEditable(false);
    }
    
    public void setLabelText(String text){
        this.label.setText(text);
    }
    
    public void setId(String id){
        this.idField.setText(id);
    }
    
    public void setItsName(String name){
        this.nameField.setText(name);
    }

    public JLabel getLabel(){
        return this.label;
    }
    
    public JTextField getIdField(){
        return this.idField;
    }
    
    public JTextField getNameField(){
        return this.nameField;
    }
    
    public String getId(){
        return this.getIdField().getText();
    }
    
    public String getItsName(){
        return this.getNameField().getText();
    }

    public void addMouseListener(MouseListener l){
        idField.addMouseListener(l);
        nameField.addMouseListener(l);
    }

}
