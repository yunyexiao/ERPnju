package presentation.analysisui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class AnalysisDriver {

    public AnalysisDriver() {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLocation(100, 100);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                frame.dispose();
                System.exit(0);
            }
        });

        frame.add(new InventoryDynamicPanel().getPanel());
        frame.setVisible(true);
    }
    
    public static void main(String[] args){
        new AnalysisDriver();
    }

}
