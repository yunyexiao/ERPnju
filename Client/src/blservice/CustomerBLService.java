package blservice;

import vo.CustomerVO;

public interface CustomerBLService extends DataBLService {

	public boolean add(CustomerVO customer);
	
}
