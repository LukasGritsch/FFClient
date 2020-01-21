package ffclient.db.types;

public abstract class TMainObject {
	
	protected boolean fInserted;
	protected boolean fModified;
	protected boolean fDeleted;
	protected boolean fIsSelected;
	
	
	
	public abstract boolean isInserted();
	
	public abstract boolean isModified();
	
	public abstract boolean isDeleted();
	
	public abstract boolean isSelected();

}
