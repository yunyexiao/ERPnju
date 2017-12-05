package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.CommodityDataService;
import po.CommodityPO;


public class CommodityDs_stub implements CommodityDataService {

    private static ArrayList<CommodityPO> result = new ArrayList<>();
    
    public CommodityDs_stub() {
    	if (result.size() == 0) {
        	result.add(new CommodityPO("000001", "逢考必过灯", "TBD", "A", "000001"
                    , 2000L, 200L, 120.0, 340.0, 120.0, 320.0, true));
            result.add(new CommodityPO("000002", "电磁脉冲灯", "TDD", "A", "000003"
                , 1000L, 100L, 200.0, 350.0, 200.0, 380.0, true));
            result.add(new CommodityPO("000003", "核聚能神灯", "TDD", "A", "000003"
                , 500L, 70L, 100.0, 350.0, 150.0, 160.0, true));
            result.add(new CommodityPO("000004", "超空间照明灯", "TBD", "A", "000002"
                    , 500L, 70L, 100.0, 350.0, 150.0, 160.0, true));
    	}
    }

    @Override
    public String getNewId() throws RemoteException {
        return String.format("%06d", result.size()+1);
    }

    @Override
    public CommodityPO findById(String id) throws RemoteException {
        System.out.println("commodity found in database: " + id);
        return new CommodityPO("002-20171127-00001", "交通信号灯", "TBD", "A", "000001"
            , 2000L, 200L, 120.0, 340.0, 120.0, 320.0, true);
    }

    @Override
    public boolean add(CommodityPO commodity) throws RemoteException {
        System.out.println("commodity added in database: " + commodity.getId());
        result.add(commodity);
        return true;
    }

    @Override
    public boolean delete(String id) throws RemoteException {
        System.out.println("commodity deleted in database: " + id);
        return true;
    }

    @Override
    public boolean update(CommodityPO commodity) throws RemoteException {
        System.out.println("commodity updated in database: " + commodity.getId());
        return true;
    }

    @Override
    public ArrayList<CommodityPO> getAllCommodity() throws RemoteException {
        System.out.println("all commodities in database returned");
        return result;
    }

	@Override
	public ArrayList<CommodityPO> getCommoditysBy(String field, String content, boolean isfuzzy) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
