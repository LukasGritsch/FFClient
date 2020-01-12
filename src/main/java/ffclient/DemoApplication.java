package ffclient;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ffclient.db.base.TDBConnection;
import ffclient.db.srv.types.TUserType_SRV;
import ffclient.db.types.TUser;
import ffclient.logon.TLogonPane;

public class DemoApplication implements EntryPoint {

	private InputStream hStr = DemoApplication.class.getClassLoader().getResourceAsStream("icons/logo.png");
	private Display fDisplay = null;
	private Image fLogo = null;
	private HashMap<Integer, TUser> fAllUsers;
	
	public int createUI() {

		fDisplay = new Display();
		
 		new TDBConnection();
 		
 		getInitialData();
 		
 		fLogo = new Image(fDisplay, hStr);

 		Shell hMainShell = new Shell(fDisplay, SWT.BORDER);
 		
 		new TLogonPane(fDisplay, fLogo,hMainShell,fAllUsers);
		
		return 0;
	}
	
	private void getInitialData() {
		TUserType_SRV hUserApi = new TUserType_SRV(TDBConnection.fConnection);
		try {
			hUserApi.load();
			fAllUsers = hUserApi.getAllUsers();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
