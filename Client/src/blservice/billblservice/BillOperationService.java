package blservice.billblservice;
/**
 * 财务人员使用的接口
 * @author 钱美缘
 *
 */
public interface BillOperationService {

	/**
	 * 红冲单据
	 * @param billId 单据编号
	 * @return
	 */
	public boolean offsetBill(String billId);
	/**
	 * 复制一张单据
	 * @param billId 单据编号
	 * @return
	 */
	public boolean copyBill(String billId);
}
