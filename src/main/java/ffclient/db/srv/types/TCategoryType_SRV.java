package ffclient.db.srv.types;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ffclient.db.types.TCategory;

public class TCategoryType_SRV extends TBaseType_SRV {

	
	HashMap<Integer,TCategory> fAllCategories = new HashMap<Integer,TCategory>();
	
	public TCategoryType_SRV(Connection aConnection) {
		super(aConnection);
	}

	@Override
	public void loadAll() throws SQLException {
		Statement stmt = fConn.createStatement();

		String sql = "SELECT * FROM FF_CATEGORY";
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			fAllCategories.put(rs.getInt("CAT_NR"), new TCategory(rs.getInt("CAT_NR"), rs.getString("CAT_NAME"),
					rs.getInt("CAT_TYPE"), rs.getObject("CAT_BILD"),rs.getString("CAT_TXT")));
		}
		rs.close();
	}

	@Override
	public Object load(Object aDbType) throws SQLException {
		return null;
	}

	@Override
	public boolean saveAll() {
		return false;
	}

	@Override
	public boolean save(Object aDbType) {
		return false;
	}
	
	public HashMap<Integer,TCategory> getAllCategories(){
		return fAllCategories;
	}

	@SuppressWarnings("rawtypes")
	public TCategory getSelectedCategory() {
		
		Iterator it = fAllCategories.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        if(((TCategory)pair.getValue()).isSelected()) {
	        	return (TCategory)pair.getValue();
	        }
	        
	    }
		return null;
	}

}
