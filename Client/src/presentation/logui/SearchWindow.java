package presentation.logui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import layout.TableLayout;
import presentation.component.DateChooser;

/**
 * 选择具体搜索条件的对话框窗体
 * @author 钱美缘
 *
 */
public class SearchWindow {

	private JDialog frame = new JDialog();
	private String[] input = new String[3];
	
	public SearchWindow() {
		frame.setModal(true);
		
		//设置窗体大小及位置
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width/5, screenSize.height/4);
		frame.setLocation((screenSize.width-frame.getWidth())/2, (screenSize.height-frame.getHeight())/2);
		frame.setResizable(false);
		frame.setTitle("选择查询条件");
		
		//设置界面布局
		frame.setLayout(new BorderLayout());
		double[][] size = {{TableLayout.FILL, TableLayout.PREFERRED, 20.0, TableLayout.PREFERRED, TableLayout.FILL}
        , {TableLayout.FILL, TableLayout.PREFERRED, 20.0, TableLayout.PREFERRED, 20.0, TableLayout.PREFERRED, TableLayout.FILL}};
		JPanel centerPanel = new JPanel(new TableLayout(size));
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		centerPanel.add(newLabel("起始日期"), "1 1");
		DateChooser dateChooserA = DateChooser.getInstance("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		JLabel startTimeLabel = newLabel(sdf.format(dt));
		dateChooserA.register(startTimeLabel);
		centerPanel.add(startTimeLabel, "3 1");
		centerPanel.add(newLabel("结束日期"), "1 3");
		DateChooser dateChooserB = DateChooser.getInstance("yyyy-MM-dd");
		JLabel endTimeLabel = newLabel(sdf.format(dt));
		dateChooserB.register(endTimeLabel);
		centerPanel.add(endTimeLabel, "3 3");
		centerPanel.add(newLabel("操作类型"), "1 5");
		JComboBox<String> chooseBox = new JComboBox<String>(new String[]{"全部", "增加商品分类", "增加商品", "增加客户", "增加账户", "增加用户"});
		centerPanel.add(chooseBox, "3 5");
		
		JButton yesButton = new JButton("确定");
		JButton quitButton = new JButton("取消");
		southPanel.add(yesButton);
		southPanel.add(quitButton);
		
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}			
		});

		yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input[0] = startTimeLabel.getText();
                input[1] = endTimeLabel.getText();
                input[2] = (String) chooseBox.getSelectedItem();
                frame.dispose();
            }
        });

		class WindowCloseListener extends WindowAdapter {
			@Override
		    public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		}
		frame.addWindowListener(new WindowCloseListener());
		frame.setVisible(true);
	}
	/**
	 * 生成统一格式的JLabel
	 * @param JLabel的文字
	 * @return JLabel
	 */
	private JLabel newLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("宋体",Font.PLAIN,15));
		return label;
	}
	/**
	 * 获得搜索条件
	 * @return
	 */
	public String[] getInput() {
		return input;
	}
}
