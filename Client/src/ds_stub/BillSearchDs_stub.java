package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.BillSearchDataService;
import po.billpo.*;

public class BillSearchDs_stub implements BillSearchDataService {

    @Override
    public ArrayList<PurchaseBillPO> searchPurchaseBills(String fromDate, String toDate, String customerId,
        String operatorId) throws RemoteException {
        ArrayList<PurchaseBillPO> result = new ArrayList<>();
        ArrayList<SalesItemsPO> items1 = new ArrayList<>();
        items1.add(new SalesItemsPO("000001", "", 50, 100, 5000));
        items1.add(new SalesItemsPO("000002", "", 100, 100, 10000));
        
        result.add(new PurchaseBillPO(
            "2017-12-05", "19:23:55", "12345", null
            , BillPO.PASS, null, null, 15000, items1
        ));
        
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 50, 200, 10000));
        
        result.add(new PurchaseBillPO(
            "2017-12-01", "08:30:02", "12000", null
            , BillPO.PASS, null, null, 10000, items2
        ));
        return result;
    }

    @Override
    public ArrayList<PurchaseReturnBillPO> searchPurchaseReturnBills(String fromDate, String toDate, String customerId,
        String operatorId) throws RemoteException {
        ArrayList<PurchaseReturnBillPO> result = new ArrayList<>();
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 10, 60.0, 600.0));
        items2.add(new SalesItemsPO("000004", "", 50, 80.0, 4000.0));
        result.add(new PurchaseReturnBillPO(
            "2017-12-13", "18:33:47", "01921", "0007"
            , BillPO.COMMITED, "000003", "", 4600.0, items2
        ));

        ArrayList<SalesItemsPO> items3 = new ArrayList<>();
        items3.add(new SalesItemsPO("000001", "", 50, 100.0, 5000.0));
        items3.add(new SalesItemsPO("000004", "", 70, 300.0, 21000.0));
        result.add(new PurchaseReturnBillPO(
            "2017-11-23", "14:38:47", "21171", "0007"
            , BillPO.PASS, "000002", "", 26000.0, items3
        ));
        return result;
    }

    @Override
    public ArrayList<SalesBillPO> searchSalesBills(String fromDate, String toDate, String customerId,
        String operatorId) throws RemoteException {
        ArrayList<SalesBillPO> result = new ArrayList<>();
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 50, 200, 10000));
        
        result.add(new SalesBillPO(
            "2017-12-01", "08:30:02", "12000", null, BillPO.PASS
            , null, null, null, null, 10000, 1000, 1000, 8000, items2
        ));
        
        ArrayList<SalesItemsPO> items3 = new ArrayList<>();
        items3.add(new SalesItemsPO("000004", "", 100, 100, 10000));
        items3.add(new SalesItemsPO("000003", "", 200, 200, 40000));
        
        result.add(new SalesBillPO(
            "2017-12-08", "11:24:51", "32100", null, BillPO.COMMITED
            , null, "rarara", null, "12300", 50000, 1000, 1000, 48000, items3
        ));
        return result;
    }

    @Override
    public ArrayList<SalesReturnBillPO> searchSalesReturnBills(String fromDate, String toDate, String customerId,
        String operatorId) throws RemoteException {
        ArrayList<SalesReturnBillPO> result = new ArrayList<>();
        ArrayList<SalesItemsPO> items1 = new ArrayList<>();
        items1.add(new SalesItemsPO("000001", "", 100, 50.0, 5000.0));
        items1.add(new SalesItemsPO("000002", "", 20, 100.0, 2000.0));
        result.add(new SalesReturnBillPO(
            "2017-12-09", "21:03:23", "00123", "0002", BillPO.PASS,
            "000001", "", "", "00012", 7000.0, 6800.0, items1
        ));
        
        ArrayList<SalesItemsPO> items2 = new ArrayList<>();
        items2.add(new SalesItemsPO("000003", "", 30, 80.0, 2400.0));
        items2.add(new SalesItemsPO("000004", "", 40, 60.0, 2400.0));
        result.add(new SalesReturnBillPO(
            "2017-12-11", "12:23:28", "08193", "0007", BillPO.COMMITED,
            "000002", "", "", "00312", 4800.0, 3800.0, items2
        ));
        return result;
    }

    @Override
    public ArrayList<CashCostBillPO> searchCashCostBills(String fromDate, String toDate, String customerId,
        String operatorId) throws RemoteException {
        ArrayList<CashCostBillPO> result = new ArrayList<>();
        // TODO fill the list with some fake bills
        return result;
    }

    @Override
    public ArrayList<ChangeBillPO> searchChangeBills(String fromDate, String toDate, String store,
        String operatorId) throws RemoteException {
        ArrayList<ChangeBillPO> result = new ArrayList<>();
        // TODO fill the list with some fake bills
        return result;
    }

    @Override
    public ArrayList<PaymentBillPO> searchPaymentBills(String fromDate, String toDate, String customerId,
        String operatorId) throws RemoteException {
        ArrayList<PaymentBillPO> result = new ArrayList<>();
        // TODO fill the list with some fake bills
        return result;
    }

    @Override
    public ArrayList<ReceiptBillPO> searchReceiptBills(String fromDate, String toDate, String customerId,
        String operatorId) throws RemoteException {
        ArrayList<ReceiptBillPO> result = new ArrayList<>();
        // TODO fill the list with some fake bills
        return result;
    }

}
