<<<<<<< HEAD
package presentation.inventory;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class TestFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -2064988251920669383L;
    
    private JPanel panel;

    public TestFrame(){
        super();
        init();
    }
    
    public TestFrame(String title){
        super(title);
        init();
    }
    
    private void init(){
        panel = InventoryViewPanel.getInstance();
        this.setSize(800, 600);
        this.setResizable(true);
        this.setContentPane(panel);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                dispose();
                System.exit(0);
            }
        });
        this.setVisible(true);
    }
    
    public static void main(String[] args){
        new TestFrame("Test Frame");
    }

}
=======
package presentation.inventory;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 给库存管理人员界面测试用的frame
 * @author 恽叶霄
 */
public class TestFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -2064988251920669383L;
    
    private JPanel panel;

    public TestFrame(){
        super();
        init();
    }
    
    public TestFrame(String title){
        super(title);
        init();
    }
    
    private void init(){
        panel = CommodityPanel.getInstance();
        this.setSize(800, 600);
        this.setResizable(true);
        this.setContentPane(panel);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                dispose();
                System.exit(0);
            }
        });
        this.setVisible(true);
    }
    
    public static void main(String[] args){
        new TestFrame("Test Frame");
    }

}
>>>>>>> 192develop
