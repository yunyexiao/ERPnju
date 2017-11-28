package businesslogic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import blservice.CommodityBLService;
import dataservice.CommodityDataService;
import ds_stub.CommodityDs_stub;
import po.CommodityPO;
import presentation.component.MyTableModel;
import vo.CommodityVO;


public class CommodityBL implements CommodityBLService {
    
    private CommodityDataService commodityDs;
    private static final String[] columnNames = {"��Ʒ���", "����", "�ͺ�", "���", "����", "����ֵ"
            , "����������", "������������", "����", "�ۼ�", "�������", "����ۼ�"};

    public CommodityBL() {
        //commodityDs = RemoteHelper.getInstance().getCommodityDataService();
        commodityDs = new CommodityDs_stub();
    }

    @Override
    public String getNewId() {
        try {
            return commodityDs.getNewId();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        try{
            return commodityDs.delete(id);
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public MyTableModel search(String type, String key) {
        try{
            // TODO find by id only...
            CommodityPO c = commodityDs.findById(key);
            String[][] data = {{c.getId(), c.getName(), c.getType(), c.getStore(), c.getAmount() + ""
                , c.getAlarmNum() + "", c.getCategoryId(), c.getCategoryName(), c.getInPrice() + ""
                , c.getSalePrice() + "", c.getRecentInPrice() + "", c.getRecentSalePrice() + ""}};
            MyTableModel model = new MyTableModel(data, columnNames);
            return model;
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MyTableModel update() {
        try{
            ArrayList<CommodityPO> list = commodityDs.getAllCommodity();
            String[][] data = new String[list.size()][columnNames.length];
            for(int i = 0; i < data.length; i++){
                data[i][0] = list.get(i).getId();
                data[i][1] = list.get(i).getName();
                data[i][2] = list.get(i).getType();
                data[i][3] = list.get(i).getStore();
                data[i][4] = list.get(i).getAmount() + "";
                data[i][5] = list.get(i).getAlarmNum() + "";
                data[i][6] = list.get(i).getCategoryId();
                data[i][7] = list.get(i).getCategoryName();
                data[i][8] = list.get(i).getInPrice() + "";
                data[i][9] = list.get(i).getSalePrice() + "";
                data[i][10] = list.get(i).getRecentInPrice() + "";
                data[i][11] = list.get(i).getRecentSalePrice() + "";
            }
            return new MyTableModel(data, columnNames);
        }catch(RemoteException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean add(CommodityVO commodity) {
        try{
            return commodityDs.add(commodity.toPO());
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean change(CommodityVO commodity) {
        try{
            return commodityDs.update(commodity.toPO());
        }catch(RemoteException e){
            e.printStackTrace();
            return false;
        }
    }

}