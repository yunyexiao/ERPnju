package presentation.dataui.categoryui;

import java.awt.BorderLayout;

import blservice.CategoryBLService;
import presentation.dataui.FatherWindow;
import vo.CategoryVO;


public class AddCategoryWindow extends FatherWindow {
    
    private CategoryBLService categoryBl;
    private InputCategoryPanel centerPanel;

    public AddCategoryWindow(CategoryBLService categoryBl) {
        super();
        this.categoryBl = categoryBl;
        centerPanel = new InputCategoryPanel(new String[]{categoryBl.getNewId(), "", "", ""});
        frame.add(centerPanel, BorderLayout.CENTER);
        
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    @Override
    protected boolean taskFinished() {
        CategoryVO category = centerPanel.getCategoryVO();
        return category != null && categoryBl.add(category);
    }

    @Override
    protected String getSuccessMsg() {
        return "添加商品分类成功";
    }

}
