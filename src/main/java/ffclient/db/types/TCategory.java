package ffclient.db.types;

public class TCategory extends TMainObject{
	
	private Integer fId;
	private String fName;
	private Integer fType;
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

	
	@Override
	public boolean isInserted() {
		return fInserted;
	}

	@Override
	public boolean isModified() {
		return fModified;
	}

	@Override
	public boolean isDeleted() {
		return fDeleted;
	}

	@Override
	public boolean isSelected() {
		return fIsSelected;
	}
	
	public void setIsSelected(boolean isSelected) {
		fIsSelected = isSelected;
	}
	
	@Override
	public int hashCode() {
		if (fImage != null) {
			return fId.hashCode() + fName.hashCode() + fType.hashCode() + fImage.hashCode() + fText.hashCode();
		} else {
			return fId.hashCode() + fName.hashCode() + fType.hashCode() + fText.hashCode();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TCategory) {
			TCategory hCat = (TCategory)obj;
			
			boolean hIsEqual = hCat.hashCode() == this.hashCode();
			return hIsEqual;
		}
		return super.equals(obj);
	}

	public void setName(String text) {
		fText = text;	
	}

	public void setIsModified(boolean b) {
		fModified = b;		
	}

	public void setImage(byte[] byteArray) {
		fImage = byteArray;
	}

}
