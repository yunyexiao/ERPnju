package presentation.inventory;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import layout.TableLayout;

/**
 * 辅助界面制作的工具类。<br>
 * Start time: 2017/10/21<br>
 * Last edit time: 2017/10/21
 * @author 192 恽叶霄
 */
public class UiHelper {
    
    /* Don't let anyone instantiate this class. */
    private UiHelper(){}
    
    /**
     * 将一个按钮根据给定参数进行初始化。<br>
     * 文本对齐方式为水平居中，竖直靠底。
     * @return 初始化后的JButton
     */
    public static JButton initButton(String text, boolean focusable){
        JButton btn = new JButton(text);
        btn.setFocusable(focusable);
        return btn;
    }

    /**
     * 将一个工具栏根据给定组件进行初始化。<br>
     * 设定为rollover = true, floatable = false 
     * @return 初始化后的JToolBar
     */
    public static JToolBar initToolBar(JComponent[] components){
        JToolBar toolBar = new JToolBar();
        toolBar.setRollover(true);
        toolBar.setFloatable(false);
        for (JComponent component: components){
            toolBar.add(component);
        }
        return toolBar;
    }
    
    /**
     * 将一个长条状的面板初始化。<br>
     * 行高的预设：上下都预留10pt的高度。列宽的预设：无。
     * @param border 该面板左右两侧预留的宽度
     * @param interval 组件之间的空隙宽度
     * @param left 向左靠齐的组件
     * @param right 向右靠齐的组件
     */
    public static JPanel initBarlikePanel(double border, double interval, JComponent[] left, JComponent[] right){
        int leftSize = left.length, rightSize = right.length;
        double[] columns = new double[2 * (leftSize + rightSize) + 1];
        columns[0] = border;
        for (int i = 1; i < leftSize * 2; i++){
            columns[i] = i % 2 == 0 ? interval : TableLayout.PREFERRED;
        }
        columns[2 * leftSize] = TableLayout.FILL;
        for (int i = 2 * leftSize + 1; i < columns.length - 1; i++){
            columns[i] = i % 2 == 0 ? interval : TableLayout.PREFERRED;
        }
        columns[columns.length - 1] = border;
        
        JPanel panel = new JPanel(new TableLayout(new double[][]{columns, {10.0D, TableLayout.PREFERRED, 10.0D}}));
        for (int i = 0; i < leftSize; i++){
            panel.add(left[i], (i * 2 + 1) + " 1");
        }
        for (int i = 0; i < rightSize; i++){
            panel.add(right[i], (2 * leftSize + 1 + i * 2) + " 1");
        }
        return panel;
    }

}
