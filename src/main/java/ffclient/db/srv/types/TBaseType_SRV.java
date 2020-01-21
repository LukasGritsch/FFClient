package ffclient.db.srv.types;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class TBaseType_SRV {
	
	protected Connection fConn = null;
	
	public TBaseType_SRV(Connection aConnection) {
		fConn = aConnection;
	}
	
	public abstract void loadAll()throws SQLException ;
	
	public abstract Object load(Object aDbType)throws SQLException ;
	
	public abstract boolean saveAll();
	
	public abstract boolean save(Object aDbType);
	
	

}
