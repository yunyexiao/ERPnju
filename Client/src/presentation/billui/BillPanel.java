package presentation.billui;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import layout.TableLayout;
import presentation.PanelInterface;
import presentation.component.TopButtonPanel;
import vo.UserVO;

/**
 * �������췽���ֱ��Ӧ�½����ݺ��޸ĵ��ݣ�����Ҳ������������ͬ�ĳ��󷽷���ʼ��BillPanel(ʹ��getBillPanel�õ�)<br/>
 * ������ȡ�����������������б���Ϊ��ͬ��ҵ���߼�����ļ�����<br/>
 * �����н���Ӧ���ݵ�BL��Ϊ˽�г�Ա�����������Ҫ�������Լ�д׮stub<br/>
 * ���������Ҫ�����ݵ�VO��Ϊ˽�г�Ա�����������Ǵ��б��ĵ���<br/>
 * ����Ĺ��췽����ʹ����Ӧ��BillVO������
 * @author Ǯ��Ե
 *
 */
public abstract class BillPanel implements PanelInterface {

	protected UserVO user;
	private JPanel panel;
	protected JPanel billPanel;
	/**
	 * �½�һ�ŵ���ʱ�Ĺ��췽��
	 * @param closeListener MainWindow�Ĺر�Panel������
	 */
	public BillPanel(UserVO user, ActionListener closeListener) {
		this.user = user;
		initPanel(closeListener);
		initBillPanel();
	}
	
	private void initPanel(ActionListener closeListener) {
		TopButtonPanel buttonPanel = new TopButtonPanel();
		
		double[][] size = {{TableLayout.FILL},{0.1,TableLayout.FILL}};
		panel = new JPanel(new TableLayout(size));
		billPanel = new JPanel();
		
		buttonPanel.addButton("�½�", new ImageIcon("resource/New.png"), getNewActionListener());
		buttonPanel.addButton("����", new ImageIcon("resource/Save.png"), getSaveActionListener());
		buttonPanel.addButton("�ύ", new ImageIcon("resource/Commit.png"), getCommitActionListener());
		buttonPanel.addButton("�ر�", new ImageIcon("resource/Close.png"), closeListener);
		
		panel.add(buttonPanel.getPanel(), "0 0");
		panel.add(billPanel, "0 1");
	}
	/**
	 * ��ʼ��BillPanel����ʾ���֣����˵���id������Ϊ�հף�<br/>
	 * �鳤��ʹ��ѵ��ǧ�����newһ��billPaenl������ֱ��set
	 */
	abstract protected void initBillPanel();
	/**
	 * 
	 * @return ����½���ť��Ҫ�󶨵ļ�����
	 */
	abstract protected ActionListener getNewActionListener();
	/**
	 * 
	 * @return ��ñ��水ť��Ҫ�󶨵ļ�����
	 */
	abstract protected ActionListener getSaveActionListener();
	/**
	 * 
	 * @return ����ύ��ť��Ҫ�󶨵ļ�����
	 */
	abstract protected ActionListener getCommitActionListener();
	/**
	 * 
	 * @return �жϵ�����д�����Ƿ���ȷ
	 */
	abstract protected boolean isCorrectable();
	
	@Override
	public boolean close() {
		return true;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
