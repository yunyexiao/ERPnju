package presentation.dataui.categoryui;

import java.awt.BorderLayout;

import blservice.CategoryBLService;
import presentation.dataui.FatherWindow;
import vo.CategoryVO;

public class UpdateCategoryWindow extends FatherWindow{
    
    private CategoryBLService categoryBl;
    private InputCategoryPanel centerPanel;

    public UpdateCategoryWindow(CategoryBLService categoryBl, CategoryVO category) {
        super();
        this.categoryBl = categoryBl;
        centerPanel = new InputCategoryPanel(new String[]{category.getId(), category.getName()
                , category.getFatherId(), category.getFatherName()});
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setTitle("�޸���Ʒ��������");
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    @Override
    protected boolean taskFinished() {
        CategoryVO category = centerPanel.getCategoryVO();
        return categoryBl.change(category);
    }

    @Override
    protected String getSuccessMsg() {
        return "��Ʒ������Ϣ�Ѹ���";
    }

}
