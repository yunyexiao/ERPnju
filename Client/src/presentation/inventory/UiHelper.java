package presentation.inventory;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import layout.TableLayout;

/**
 * �������������Ĺ����ࡣ<br>
 * Start time: 2017/10/21<br>
 * Last edit time: 2017/10/21
 * @author 192 �Ҷ��
 */
public class UiHelper {
    
    /* Don't let anyone instantiate this class. */
    private UiHelper(){}
    
    /**
     * ��һ����ť���ݸ����������г�ʼ����<br>
     * �ı����뷽ʽΪˮƽ���У���ֱ���ס�
     * @return ��ʼ�����JButton
     */
    public static JButton initButton(String text, boolean focusable){
        JButton btn = new JButton(text);
        btn.setFocusable(focusable);
        return btn;
    }

    /**
     * ��һ�����������ݸ���������г�ʼ����<br>
     * �趨Ϊrollover = true, floatable = false 
     * @return ��ʼ�����JToolBar
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
     * ��һ������״������ʼ����<br>
     * �иߵ�Ԥ�裺���¶�Ԥ��10pt�ĸ߶ȡ��п��Ԥ�裺�ޡ�
     * @param border �������������Ԥ���Ŀ��
     * @param interval ���֮��Ŀ�϶���
     * @param left ����������
     * @param right ���ҿ�������
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
