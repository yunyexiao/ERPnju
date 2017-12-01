package ds_stub;

import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.CategoryDataService;
import po.CategoryPO;


public class CategoryDs_stub implements CategoryDataService {

    public CategoryDs_stub() {}

    @Override
    public String getNewId() throws RemoteException {
        return "001-20171127-12345";
    }

    @Override
    public CategoryPO findById(String id) throws RemoteException {
        System.out.println("category found in database: " + id);
        return new CategoryPO("001-201771127-00001", "some", "001-20171127-00002", "solar", true);
    }

    @Override
    public boolean add(CategoryPO category) throws RemoteException {
        System.out.println("category added in database: " + category.getId());
        return true;
    }

    @Override
    public boolean delete(String id) throws RemoteException {
        System.out.println("category deleted in database: " + id);
        return true;
    }

    @Override
    public boolean update(CategoryPO category) throws RemoteException {
        System.out.println("category updated in database: " + category.getId());
        return true;
    }

    @Override
    public ArrayList<CategoryPO> getAllCategory() throws RemoteException {
        System.out.println("all categories in database returned");
        ArrayList<CategoryPO> result = new ArrayList<>();
        result.add(new CategoryPO("001-20171127-00000", "category-root", "", "", true));
        result.add(new CategoryPO("001-20171127-00001", "some", "001-20171127-00000", "category-root", true));
        result.add(new CategoryPO("001-20171127-00003", "same", "001-20171127-00000", "category-root", true));
        result.add(new CategoryPO("001-20171127-00002", "come", "001-20171127-00001", "some", true));
        result.add(new CategoryPO("001-20171127-00004", "sane", "001-20171127-00003", "same", true));
        return result;
    }

	@Override
	public ArrayList<CategoryPO> getCategorysBy(String field, String content, boolean isfuzzy) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
