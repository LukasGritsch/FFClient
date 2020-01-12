package ffclient.db.srv.types;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ffclient.db.types.TUser;

public class TUserType_SRV extends TBaseType_SRV {

	private HashMap<Integer, TUser> fAllUsers = new HashMap<Integer, TUser>();

	public TUserType_SRV(Connection aConnection) {
		super(aConnection);
	}

	@Override
	public void load() throws SQLException {
		Statement stmt = fConn.createStatement();

		String sql = "SELECT * FROM FF_USER ";
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			fAllUsers.put(rs.getInt("USER_NR"), new TUser(rs.getInt("USER_NR"), rs.getString("USER_NAME"),
					rs.getInt("USER_PASSWD"), rs.getInt("USER_PERM")));
		}
		rs.close();

	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean save() {

		boolean hRet = true;

		Iterator it = fAllUsers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TUser tUser = (TUser) pair.getValue();
			if (!tUser.isChanged()) {
				continue;
			}
			PreparedStatement preparedStatement;
			try {
				preparedStatement = fConn
						.prepareStatement("UPDATE FF_USER SET USER_NAME=?,USER_PASSWD=?,USER_PERM=? WHERE USER_NR=?");
				preparedStatement.setString(1, tUser.getUsrName());
				preparedStatement.setInt(2, tUser.getUsrPasswd());
				preparedStatement.setInt(3, tUser.getUsrPerm());
				preparedStatement.setInt(4, tUser.getUsrNr());
				preparedStatement.executeUpdate();
				hRet = hRet && true;
			} catch (SQLException e) {
				return false;
			}
		}

		return hRet;
	}

	public HashMap<Integer, TUser> getAllUsers() {
		return fAllUsers;
	}
}
