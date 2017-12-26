package presentation.billui;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.TopButtonPanel;

/**
 * �������췽���ֱ��Ӧ�½����ݺ��޸ĵ��ݣ�����Ҳ������������ͬ�ĳ��󷽷���ʼ��BillPanel(ʹ��getBillPanel�õ�)<br/>
 * ������ȡ�����������������б���Ϊ��ͬ��ҵ���߼�����ļ�����<br/>
 * �����н���Ӧ���ݵ�BL��Ϊ˽�г�Ա�����������Ҫ�������Լ�д׮stub<br/>
 * ���������Ҫ�����ݵ�VO��Ϊ˽�г�Ա�����������Ǵ��б��ĵ���<br/>
 * ����Ĺ��췽����ʹ����Ӧ��BillVO������
 * @author Ǯ��Ե
 *
 */
public class BillPanel implements PanelInterface {

	private JPanel panel;
	private TopButtonPanel buttonPanel;
	private BillPanelInterface billPanel;
	/**
	 * �½�һ�ŵ���ʱ�Ĺ��췽��
	 * @param closeListener MainWindow�Ĺر�Panel������
	 */
	public BillPanel(ActionListener closeListener, BillPanelInterface billPanel) {
		this.billPanel = billPanel;
		buttonPanel = new TopButtonPanel();
		double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
		panel = new JPanel(new TableLayout(size));
		
		buttonPanel.addButton("�½�", new ImageIcon("resource/New.png"), e->billPanel.newAction());
		buttonPanel.addButton("����", new ImageIcon("resource/Save.png"), e->billPanel.saveAction());
		buttonPanel.addButton("�ύ", new ImageIcon("resource/Commit.png"), e->billPanel.commitAction());
		buttonPanel.addButton("�ر�", new ImageIcon("resource/Close.png"), closeListener);
		
		panel.add(buttonPanel.getPanel(), "0 0");
		panel.add((JPanel)billPanel, "0 1");
	}
	/**
	 * ���ò����޸ĵķ���
	 * @param b
	 */
	public void setEditable(boolean b) {
		buttonPanel.setEnable(b);
		billPanel.setEditable(b);
	}
	
	@Override
	public boolean close() {
		return true;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
