package ffclient.db.types;

public class TUser {

	private int fUsrNr;
	private String fUsrName;
	private int fUsrPasswd;
	private int fUsrPerm;
	private boolean fIsChanged = false;

	public TUser(int aUsrNr, String aUsrName, int aUsrPasswd, int aUsrPerm) {
		this.fUsrNr = aUsrNr;
		this.fUsrName = aUsrName;
		this.fUsrPasswd = aUsrPasswd;
		this.fUsrPerm = aUsrPerm;
	}

	public int getUsrNr() {
		return fUsrNr;
	}

	public String getUsrName() {
		return fUsrName;
	}

	public int getUsrPasswd() {
		return fUsrPasswd;
	}

	public int getUsrPerm() {
		return fUsrPerm;
	}

	public boolean isChanged() {
		return fIsChanged;
	}

	public void setIsChanged(boolean fIsChanged) {
		this.fIsChanged = fIsChanged;
	}
	
	public int checkCredentials(String aName,int aPasswd) {
		
		if(this.fUsrName.equals(aName) && this.fUsrPasswd == aPasswd) {
			return this.fUsrNr;
		}else {
			return -1;
		}
		
	}
	
}
