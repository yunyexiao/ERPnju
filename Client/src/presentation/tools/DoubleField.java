package presentation.tools;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * 处理小数的文本框，里面只能填小数
 * 默认精确到小数点后三位
 */
@SuppressWarnings("serial")
public class DoubleField extends JTextField {
    
    private double defaultValue;
    
    public DoubleField(){
        this(null, 0.0, 0, 0.0);
    }
    
    public DoubleField(int columns){
        this(null, 0.0, columns, 0.0);
    }

    public DoubleField(double value, double defaultValue){
        this(null, value, 0, defaultValue);
    }
    
    public DoubleField(double value, int columns){
        this(null, value, columns, 0.0);
    }
    
    public DoubleField(double value, int columns, double defaultValue){
        this(null, value, columns, defaultValue);
    }
    
    public DoubleField(Document doc, double value, int columns, double defaultValue){
        super(doc, value + "", columns);
        this.defaultValue = defaultValue;
        this.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                if(!valid(c)){
                    e.consume();
                }
            }
        });
    }
    
    public double getValue(){
        return Double.parseDouble(this.getText());
    }

    public void setValue(double value){
        super.setText(String.format("%.1f", value));
    }
    
    public void setDefaultValue(double defaultValue){
        this.defaultValue = defaultValue;
    }
    
    public double getDefaultValue(){
        return this.defaultValue;
    }

    @Override
    public void setText(String text){
        try{
            Double.parseDouble(text);
            super.setText(text);
        }catch(NumberFormatException e){
            return;
        }
    }

    private boolean valid(char c){
        if(c >= '0' && c <= '9') {
            return true;
        }
        if(c == '.' && !super.getText().contains(".")){
            return true;
        }
        if(c >= 32 && c <= 126){
            // seeable chars
            return false;
        }
        return true;
    }

}
