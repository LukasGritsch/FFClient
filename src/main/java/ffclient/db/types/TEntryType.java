package ffclient.db.types;

import java.util.Date;

public class TEntryType extends TMainObject {

	private int fEntryId;
	private TCategory fParent;
	private String fName;
	private Date fDate;
	private String fTxt;
	private Object fBild1;
	private Object fBild2;
	private Object fBild3;
	
	public TEntryType(int aId,TCategory aParent, String aName, Date aDate,String aTxt, Object aBild1,Object aBild2,Object aBild3) {
	
		fEntryId = aId;
		fParent = aParent;
		fName = aName;
		fDate = aDate;
		fTxt = aTxt;
		fBild1 = aBild1;
		fBild2 = aBild2;
		fBild3 = aBild3;
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

	public int getfEntryId() {
		return fEntryId;
	}

	public TCategory getParent() {
		return fParent;
	}

	public String getfName() {
		return fName;
	}

	public Date getfDate() {
		return fDate;
	}

	public String getfTxt() {
		return fTxt;
	}

	public Object getfBild1() {
		return fBild1;
	}

	public Object getfBild2() {
		return fBild2;
	}

	public Object getfBild3() {
		return fBild3;
	}
	
	

}
