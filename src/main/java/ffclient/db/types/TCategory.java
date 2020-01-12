package ffclient.db.types;

public class TCategory {
	
	private int fId;
	private String fName;
	private int fType;
	private Object fImage;
	private String fText;
	
	public TCategory(int aId,String aName,int aType,Object aImage,String aText) {
		fId = aId;
		fName = aName;
		fType = aType;
		fImage = aImage;
		fText = aText;
	}

	public int getId() {
		return fId;
	}

	public String getName() {
		return fName;
	}

	public int getType() {
		return fType;
	}

	public Object getImage() {
		return fImage;
	}

	public String getText() {
		return fText;
	}
	
	

}
