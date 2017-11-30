package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.CommodityDataService;
import po.CommodityPO;


public class CommodityDs_stub implements CommodityDataService {
    
    public CommodityDs_stub() {}

    @Override
    public String getNewId() throws RemoteException {
        return "002-20171127-12345";
    }

    @Override
    public CommodityPO findById(String id) throws RemoteException {
        System.out.println("commodity found in database: " + id);
        return new CommodityPO("002-20171127-00001", "交通信号灯", "TBD", "A", "特殊用途灯具", "001-20171127-00021"
            , 2000L, 200L, 120.0, 340.0, 120.0, 320.0, true);
    }

    @Override
    public boolean add(CommodityPO commodity) throws RemoteException {
        System.out.println("commodity added in database: " + commodity.getId());
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
        ArrayList<CommodityPO> result = new ArrayList<>();
        result.add(new CommodityPO("002-20171127-00001", "交通信号灯", "TBD", "A", "001-20171127-00021", "特殊用途灯具"
            , 2000L, 200L, 120.0, 340.0, 120.0, 320.0, true));
        result.add(new CommodityPO("002-20171127-00002", "de-light", "TDD", "A", "001-20171127-00539", "inspectional"
            , 1000L, 100L, 200.0, 350.0, 200.0, 380.0, true));
        return result;
    }

	@Override
	public ArrayList<CommodityPO> getUsersBy(String field, String content, boolean isfuzzy) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
