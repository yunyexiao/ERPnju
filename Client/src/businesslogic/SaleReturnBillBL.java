package businesslogic;

import java.rmi.RemoteException;

import blservice.billblservice.SaleReturnBillBLService;
import dataservice.SaleReturnBillDataService;
import vo.billvo.SaleReturnBillVO;


public class SaleReturnBillBL implements SaleReturnBillBLService {
    
    private SaleReturnBillDataService saleReturnBillDs;

    @Override
    public String getNewId() {
        try{
            return saleReturnBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            return saleReturnBillDs.deleteBill(id);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(SaleReturnBillVO bill) {
        try{
            return saleReturnBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBill(SaleReturnBillVO bill) {
        try{
            return saleReturnBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SaleReturnBillVO getBill(String id) {
        try{
            return toVO(saleReturnBillDs.getBillById(id));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    private SaleReturnBillPO toPO(SaleReturnBillVO bill){
        // TODO
        return null;
    }
    
    private SaleReturnBillVO toVO(SaleReturnBillPO bill){
        // TODO
        return null;
    }

}
