package ffclient.db.srv.types;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ffclient.db.types.TCategory;
import ffclient.db.types.TEntryType;

public class TEntryType_SRV extends TBaseType_SRV {

	HashMap<Integer, TEntryType> fAllEntris = new HashMap<Integer, TEntryType>();

	public TEntryType_SRV(Connection aConnection) {
		super(aConnection);
	}

	@Override
	public void loadAll() throws SQLException {
		
		HashMap<Integer, TCategory> hAllCategories = new HashMap<Integer, TCategory> ();
		
		Statement stmt = fConn.createStatement();

		String sql = "SELECT * FROM FF_CATEGORY";
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			
			hAllCategories.put(rs.getInt("CAT_NR"), new TCategory(rs.getInt("CAT_NR"), rs.getString("CAT_NAME"),
					rs.getInt("CAT_TYPE"), rs.getObject("CAT_BILD"),rs.getString("CAT_TXT")));
		}
		rs.close();
		
		sql = "SELECT * FROM FF_ENTRY";
		rs = stmt.executeQuery(sql);
		
		Calendar hCal = Calendar.getInstance();
		
		while (rs.next()) {
			
			int hDate = rs.getInt("ENT_DATUM");
			
			hCal.setTimeInMillis(hDate);
			
			fAllEntris.put(rs.getInt("ENT_NR"),
					new TEntryType(rs.getInt("ENT_NR"), hAllCategories.get(rs.getInt("ENT_PARENT")),
							rs.getString("ENT_NAME"), hCal.getTime(), rs.getString("ENT_TXT"), null, null,
							null));
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

	@SuppressWarnings("rawtypes")
	public ArrayList<TEntryType> getChildrenfromCoategory(TCategory aParentCategorty) throws SQLException {

		ArrayList<TEntryType> hRetList = new ArrayList<TEntryType>();

		if (fAllEntris.size() < 1) {
			loadAll();
		}

		Iterator it = fAllEntris.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			if (pair.getValue() instanceof TEntryType) {

				TEntryType tEntryType = (TEntryType) pair.getValue();
				
				if (tEntryType.getParent().equals(aParentCategorty)) {
					hRetList.add(tEntryType);
				}
			}
		}

		return hRetList;
	}
}
