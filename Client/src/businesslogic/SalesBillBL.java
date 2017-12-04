package businesslogic;

import java.rmi.RemoteException;

import blservice.billblservice.SaleBillBLService;
import dataservice.SaleBillDataService;
import ds_stub.SalesBillDs_stub;
import vo.billvo.SaleBillVO;


public class SalesBillBL implements SaleBillBLService {
    
    private SaleBillDataService saleBillDs;
    
    public SalesBillBL(){
        saleBillDs = new SalesBillDs_stub();
    }

    @Override
    public String getNewId() {
        try{
            return saleBillDs.getNewId();
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteBill(String id) {
        try{
            return saleBillDs.deleteBill(id);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveBill(SaleBillVO bill) {
        try{
            return saleBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBill(SaleBillVO bill) {
        try{
            return saleBillDs.saveBill(toPO(bill));
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SaleBillVO getBill(String id) {
        try{
            return toVO(saleBillDs.getBillById(id));
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }
    
    private SalesBillPO toPO(SaleBillVO bill){
        //TODO
        return null;
    }
    
    private SaleBillVO toVO(SalesBillPO bill){
        //TODO
        return null;
    }

}
