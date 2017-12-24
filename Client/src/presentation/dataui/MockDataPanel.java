package presentation.dataui;

import java.awt.event.ActionListener;

import blservice.DataBLService;


public class MockDataPanel extends DataPanel {
    
    private DataBLService dataBl;

    public MockDataPanel(DataBLService dataBl, ActionListener closeListener) {
        super(dataBl, closeListener);
        this.dataBl = dataBl;
    }

    @Override
    protected ActionListener getAddListener() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ActionListener getUpdateListener() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected ActionListener getSearchListener() {
        // TODO Auto-generated method stub
        return null;
    }

}
