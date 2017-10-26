package po;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryPO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2889859929392865267L;
	private ArrayList<String> contents;
	private String name, id;
	
	/**
	 * 上级分类的id
	 */
	private String upper;
	
	public CategoryPO(String id, String name, ArrayList<String> contents, String upper){
		this.id = id; 
		this.name = name; 
		this.upper = upper;
		this.contents = contents;
	}
	
	public CategoryPO(String id, String name){
		this(id, name, null, null);
	}

	public String getName() { 
		return name; 
	}
	public CategoryPO setName(String name) {
		this.name = name;
		return this;
	}

	public String getId() { 
		return id; 
	}
	public CategoryPO setId(String id) { 
		this.id = id;
		return this; 
	}

	public String getUpper() {
		return upper; 
	}
	public CategoryPO setUpper(String upper) { 
		this.upper = upper; 
		return this; 
	}

	public CategoryPO setContent(ArrayList<String> contents){ 
		this.contents = contents; 
		return this; 
	}
	public ArrayList<String> getContents(){
		return contents;
	}

}
