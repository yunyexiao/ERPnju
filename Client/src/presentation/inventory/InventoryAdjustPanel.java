package presentation.inventory;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import layout.TableLayout;

/**
 * ��������������
 * �û������ڸ�ҳ����������汨�絥�Ϳ�汨��<br>
 * @author 192 �Ҷ�� 
 */
public class InventoryAdjustPanel extends JPanel {

    /**
     * auto-generated UID
     */
    private static final long serialVersionUID = -8970304090218398816L;
    
    private static InventoryAdjustPanel instance;
    
    /* From the main panel, in order to send prompt info to users. */
    private JLabel prompt;
    
    /* Components in the north. */
    private JButton newButton, saveButton, closeButton;
    private JRadioButton overflowRadioButton, underflowRadioButton;
    private JToolBar newToolBar;
    private JPanel toolPanel;
    private JLabel label;

    /* Components in the center. */
    private JLabel idLabel, nameLabel, systemAmountLabel, actualAmountLabel, differenceLabel;
    private JTextField idTextField, nameTextField, systemAmountTextField, actualAmountTextField, differenceTextField;
    private JButton selectButton, submitButton;
    private JPanel centerPanel;

  
    // TODO It needs a param of a bussiness service and a prompt.
    private InventoryAdjustPanel(){
        super(new BorderLayout());
        initNorth();
        initCenter();
    }
    
    // TODO It should be modified like the constructor.
    public static InventoryAdjustPanel getInstance(){
        instance = new InventoryAdjustPanel();
        return instance;
    }
    
    private void initNorth(){
        newButton = UiHelper.initButton("�½�����", false);
        saveButton = UiHelper.initButton("���浥��", false);
        closeButton = UiHelper.initButton("�ر�", false);
        overflowRadioButton = new JRadioButton("���絥", true);
        underflowRadioButton = new JRadioButton("����", false);
        overflowRadioButton.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                label.setText("���絥");
            }
            
        });
        underflowRadioButton.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                label.setText("����");
            }
            
        });
        ButtonGroup group = new ButtonGroup();
        group.add(overflowRadioButton); group.add(underflowRadioButton);
        newToolBar = UiHelper.initToolBar(new JComponent[]{newButton, overflowRadioButton, underflowRadioButton});
        label = new JLabel("���絥");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        double border = 10;
        double[][] size = {{border, newToolBar.getPreferredSize().getWidth()
            , saveButton.getPreferredSize().getWidth(), TableLayout.FILL
            , closeButton.getPreferredSize().getWidth(), border}
            , {border, TableLayout.FILL, TableLayout.FILL, border}};
        toolPanel = new JPanel(new TableLayout(size));
        toolPanel.add(newToolBar, "1 1");
        toolPanel.add(saveButton, "2 1");
        toolPanel.add(closeButton, "4 1");
        toolPanel.add(label, "1 2 4 2");
        
        this.add(toolPanel, BorderLayout.NORTH);
    }
    
    private void initCenter(){
        idLabel = new JLabel("��Ʒ���");
        nameLabel = new JLabel("��Ʒ����");
        systemAmountLabel = new JLabel("ϵͳ�������");
        actualAmountLabel = new JLabel("ʵ�ʿ������");
        differenceLabel = new JLabel("��ֵ");
        int cols = 10;
        idTextField = new JTextField(cols);
        nameTextField = new JTextField(cols);
        systemAmountTextField = new JTextField(cols);
        actualAmountTextField = new JTextField(cols);
        differenceTextField = new JTextField(cols);
        differenceTextField.setEditable(false);
        selectButton = UiHelper.initButton("ѡ��", false);
        submitButton = UiHelper.initButton("�ύ", true);
        
        double border = 50.0D, interval = 20.0D, lengthOfTextField = idTextField.getPreferredSize().getWidth()
                        , heightPerLine = 30.0D;
        double[][] size = {{border, TableLayout.FILL, systemAmountLabel.getPreferredSize().getWidth()
            , interval, lengthOfTextField, interval
            , selectButton.getPreferredSize().getWidth(), TableLayout.FILL, border}
            , {border, heightPerLine, interval, heightPerLine, interval, heightPerLine, interval
                , heightPerLine, interval, heightPerLine, interval, heightPerLine, TableLayout.FILL, border}};
        centerPanel = new JPanel(new TableLayout(size));
        centerPanel.add(idLabel, "2 1");
        centerPanel.add(nameLabel, "2 3");
        centerPanel.add(systemAmountLabel, "2 5");
        centerPanel.add(actualAmountLabel, "2 7");
        centerPanel.add(differenceLabel, "2 9");
        centerPanel.add(idTextField, "4 1");
        centerPanel.add(nameTextField, "4 3");
        centerPanel.add(systemAmountTextField, "4 5");
        centerPanel.add(actualAmountTextField, "4 7");
        centerPanel.add(differenceTextField, "4 9");
        centerPanel.add(selectButton, "6 1");
        centerPanel.add(submitButton, "6 11");
        
        this.add(centerPanel, BorderLayout.CENTER);
    }


}
