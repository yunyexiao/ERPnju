package presentation.dataui.commodityui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import blservice.CategoryBLService;
import businesslogic.CategoryBL;
import layout.TableLayout;
import presentation.component.InfoWindow;
import presentation.component.choosewindow.CategoryChooseWin;
import presentation.tools.InputCheck;
import vo.CategoryVO;
import vo.CommodityVO;

@SuppressWarnings("serial")
class InputCommodityPanel extends JPanel{
    
    private JTextField[] textFields;
    private CategoryBLService categoryBl;

    public InputCommodityPanel(String[] data) {
        super();
        this.categoryBl = new CategoryBL();
        double[] rows = new double[25];
        rows[0] = TableLayout.FILL;
        for(int i = 0; i < 12; i++){
            rows[2 * i + 1] = TableLayout.PREFERRED;
            rows[2 * i + 2] = 10.0;
        }
        rows[rows.length - 1] = TableLayout.FILL;
        double[][] size = {{TableLayout.FILL, TableLayout.PREFERRED, 10.0, 0.5, TableLayout.FILL}, rows};
        this.setLayout(new TableLayout(size));
        
        String[] labelTexts = {"��Ʒ���", "��Ʒ����", "��Ʒ�ͺ�", "��Ʒ�������", "��Ʒ����", "��Ʒ����ֵ"
                , "��Ʒ����������", "��Ʒ������������", "��Ʒ����", "��Ʒ�ۼ�"};
        JLabel[] labels = new JLabel[labelTexts.length];
        for(int i = 0; i < labels.length; i++){
            labels[i] = new JLabel(labelTexts[i]);
            this.add(labels[i], "1 " + (2 * i + 1));
        }
        
        textFields = new JTextField[labels.length];
        for(int i = 0; i < textFields.length; i++){
            textFields[i] = new JTextField();
            if(data.length > i && data[i] != null)
                textFields[i].setText(data[i]);
            this.add(textFields[i], "3 " + (2 * i + 1));
        }
        //textFields[6].setText("�����˴�ѡ����Ʒ����");
        if ("".equals(textFields[6].getText())) {
        	textFields[6].setText("�����˴�ѡ����Ʒ����");
        }
        textFields[0].setEditable(false);
        textFields[3].setEditable(false);
        textFields[6].setEditable(false);
        textFields[7].setEditable(false);
        
        if(data[4] != null){
            textFields[4].setEditable(false);
            textFields[8].setEditable(false);
            textFields[9].setEditable(false);
        }
        
        textFields[6].addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		CategoryVO category = new CategoryChooseWin().getCategory();
        		if (category != null) {
            		textFields[6].setText(category.getId());
            		textFields[7].setText(category.getName());
        		}
        	}
        });
   }

    public CommodityVO getCommodityVO(){
    	if (! InputCheck.isLegal(textFields[1].getText())) new InfoWindow("��Ʒ���ƷǷ�");
    	else if (! InputCheck.isLegal(textFields[2].getText())) new InfoWindow("��Ʒ���ͷǷ�");
    	else if (! InputCheck.isAllNumber(textFields[4].getText(), 0)) new InfoWindow("��������ȷ����Ʒ����");
    	else if (! InputCheck.isAllNumber(textFields[5].getText(), 0)) new InfoWindow("��������ȷ����Ʒ����ֵ");
    	else if(categoryBl.hasContent(textFields[6].getText())) new InfoWindow("��ѡ��ֻ������Ʒ�ķ���");
    	else if (! InputCheck.isLegal(textFields[7].getText())) new InfoWindow("��ѡ����Ʒ����");
    	else if (! InputCheck.isDouble(textFields[8].getText())) new InfoWindow("��������ȷ����Ʒ�����۸�");
    	else if (! InputCheck.isDouble(textFields[9].getText())) new InfoWindow("��������ȷ����Ʒ���ۼ۸�");
    	else {
    		String id = textFields[0].getText(),
	               name = textFields[1].getText(),
	               type = textFields[2].getText(),
	               store = textFields[3].getText(),
	               categoryId = textFields[6].getText();
	        long amount = Long.parseLong(textFields[4].getText()),
	             alarm = Long.parseLong(textFields[5].getText());
	        double inPrice = Double.parseDouble(textFields[8].getText()),
	               salePrice = Double.parseDouble(textFields[9].getText());
	        return new CommodityVO(id, name, type, store, categoryId, amount, alarm, inPrice, salePrice, inPrice, salePrice);
    	}
        return null;
    }

}
