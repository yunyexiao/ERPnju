package businesslogic.inter;

import vo.MyTableModel;

/**
 * ����ϵͳ�Զ�������Ʒ���͵�
 * 
 * @author �Ҷ��
 */
public interface GiftBillCreation {
    
    boolean createAndCommit(MyTableModel gifts, String salesBillId, String operatorId, String customerId);
}
