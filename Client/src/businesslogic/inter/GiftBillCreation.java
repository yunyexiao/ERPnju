package businesslogic.inter;

import presentation.component.MyTableModel;

/**
 * ����ϵͳ�Զ�������Ʒ���͵�
 * 
 * @author �Ҷ��
 */
public interface GiftBillCreation {
    
    boolean createAndCommit(MyTableModel gifts, String salesBillId, String customerId);

}
