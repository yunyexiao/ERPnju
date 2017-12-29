package presentation.promotionui;

import javax.swing.JFrame;

public class PromotionPanelDriver {
    
    private JFrame frame;
    private PromotionPanel panel;

    public PromotionPanelDriver() {
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLocation(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new PromotionPanel(e->close());
        frame.setContentPane(panel.getPanel());
        frame.setVisible(true);
    }
    
    private void close(){
        if(panel.close())frame.dispose();
    }
    
    public static void main(String[] args){
        new PromotionPanelDriver();
    }

}
