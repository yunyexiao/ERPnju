package presentation.dataui.categoryui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import layout.TableLayout;
import vo.CategoryVO;

@SuppressWarnings("serial")
class InputCategoryPanel extends JPanel {
    
    private JTextField idTextField, nameTextField, fatherIdTextField, fatherNameTextField;

    public InputCategoryPanel(String[] data) {
        super();
        double[][] size = {{TableLayout.FILL, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, TableLayout.FILL}
            , {TableLayout.FILL, TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, 10.0
                , TableLayout.PREFERRED, 10.0, TableLayout.PREFERRED, TableLayout.FILL}};
        this.setLayout(new TableLayout(size));
        
        JLabel idLabel = new JLabel("������"),
               nameLabel = new JLabel("��������"),
               fatherIdLabel = new JLabel("��һ��������"),
               fatherNameLabel = new JLabel("��һ����������");
        idTextField = new JTextField(data[0]);
        nameTextField = new JTextField(data[1]);
        fatherIdTextField = new JTextField(data[2]);
        fatherNameTextField = new JTextField(data[3]);
        
        this.add(idLabel, "1 1");
        this.add(nameLabel, "1 3");
        this.add(fatherIdLabel, "1 5");
        this.add(fatherNameLabel, "1 7");
        this.add(idTextField, "3 1");
        this.add(nameTextField, "3 3");
        this.add(fatherIdTextField, "3 5");
        this.add(fatherNameTextField, "3 7");
    }
    
    public CategoryVO getCategoryVO(){
        return new CategoryVO(fatherIdTextField.getText(), fatherNameTextField.getText()
            , idTextField.getText(), nameTextField.getText());
    }

}
