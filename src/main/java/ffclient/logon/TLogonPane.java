package ffclient.logon;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ffclient.db.types.TUser;
import ffclient.pane.TMainPane;

public class TLogonPane {

	private Text fUserText;
	private Text fPasswdText;
	private Button fBtnrememberMe;
	private Button fBtnLogon;
	private Color fRED = new Color(Display.getCurrent(), 255, 0, 0);
	private HashMap<Integer, TUser> fAllUsers;
	private String fUser;
	private String fPasswd;
	private Shell fShell;
	private Shell fMainShell;

	public TLogonPane(Display aDisplay, Image aLogo, Shell aMainShell,HashMap<Integer, TUser> aAllUsers) {

		fAllUsers = aAllUsers;

		fMainShell = aMainShell;
		fShell = new Shell(aDisplay, SWT.BORDER);

		if(checkUserSettingStore(RWT.getSettingStore().getAttribute("login"))) {
			return;
		}

		int hDisplayWidth = aDisplay.getBounds().width;
		int hWidth = 0;
		int hHeight = 0;

		if (hDisplayWidth > 500) {
			hWidth = 500;
			hHeight = 350;
			fShell.setBounds((hDisplayWidth - hWidth) / 2, (aDisplay.getBounds().height - hHeight) / 2, hWidth,
					hHeight);
		} else {
			hHeight = 350;
			hWidth = hDisplayWidth;
			fShell.setBounds(0, (aDisplay.getBounds().height - hHeight) / 2, hWidth, hHeight);
		}

		fShell.setLayout(new GridLayout(2, false));

		createControls(fShell, aLogo);
		setListener();

		fShell.open();
	}

	private boolean checkUserSettingStore(Object attribute) {
		if (attribute != null) {

			int hNr = Integer.parseInt(attribute.toString());

			fAllUsers.get(hNr);
			fShell.dispose();
			new TMainPane(fMainShell, SWT.BORDER,fAllUsers,hNr);
			fMainShell.open();
			return true;
		}
		return false;

	}

	private void createControls(Shell parent, Image alogo) {

		Label hLogoLabel = new Label(parent, SWT.NONE);
		GridData hLogoLayoutData = new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1);
		hLogoLayoutData.widthHint = 90;
		hLogoLayoutData.heightHint = 100;
		hLogoLabel.setLayoutData(hLogoLayoutData);
		hLogoLabel.setBackgroundImage(alogo);

		Label hUserLabel = new Label(parent, SWT.NONE);
		hUserLabel.setText("Benutzername");
		hUserLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		fUserText = new Text(parent, SWT.BORDER);
		fUserText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		fUserText.setFocus();

		Label hPasswdLabel = new Label(parent, SWT.NONE);
		hPasswdLabel.setText("Passwort");
		hPasswdLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		fPasswdText = new Text(parent, SWT.BORDER | SWT.PASSWORD);
		fPasswdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		fBtnrememberMe = new Button(parent, SWT.CHECK);
		fBtnrememberMe.setText("Anmeldung merken");
		fBtnrememberMe.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));

		fBtnLogon = new Button(parent, SWT.PUSH);
		fBtnLogon.setText("Anmelden");
		fBtnLogon.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		fBtnLogon.setData(RWT.CUSTOM_VARIANT, "TLogonPaneFbtnLogon");

		parent.setDefaultButton(fBtnLogon);

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		Label hErrorLabel = new Label(parent, SWT.NONE);
		hErrorLabel.setText("Passwort oder Benutzername falsch !");
		hErrorLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		hErrorLabel.setForeground(fRED);
		hErrorLabel.setVisible(false);

		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
	}

	@SuppressWarnings("serial")
	private void setListener() {
		fBtnLogon.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int hId = logon(fUser, fPasswd);
				if (hId != -1) {
					if (fBtnrememberMe.getSelection()) {
						try {
							RWT.getSettingStore().setAttribute("login", "" + hId);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					fShell.dispose();
					new TMainPane(fMainShell, SWT.BORDER, fAllUsers,hId);
					fMainShell.open();
				} else {
					
				}
			}

		});

		fUserText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent event) {
				fUser = fUserText.getText();
			}
		});

		fPasswdText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent event) {
				fPasswd = fPasswdText.getText();
			}
		});
	}

	@SuppressWarnings("rawtypes")

	private int logon(String aName, String aPasswd) {
		Iterator it = fAllUsers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TUser tUser = (TUser) pair.getValue();

			int hUsrNr = tUser.checkCredentials(aName, aPasswd.hashCode());
			if (hUsrNr != -1) {
				return hUsrNr;
			}
		}
		return -1;

	}

	public Shell getShell() {
		return fShell;
	}

}
