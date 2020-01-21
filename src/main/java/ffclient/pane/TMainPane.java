package ffclient.pane;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.scripting.ClientListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ffclient.DemoApplication;
import ffclient.db.base.TDBConnection;
import ffclient.db.srv.types.TCategoryType_SRV;
import ffclient.db.srv.types.TEntryType_SRV;
import ffclient.db.types.TUser;

public class TMainPane {

	private HashMap<Integer, TUser> fAllUser;
	private TUser fCurrentUser;
	private InputStream fExitPng = DemoApplication.class.getClassLoader().getResourceAsStream("icons/exit.png");
	private Button fSigneOut;
	private Shell fMainShell;
	private Composite fNullComp;
	private StackLayout fContentLayout = new StackLayout();
	private TCategoryType_SRV fCategoryAPI;
	private TEntryType_SRV fEntryAPI;
	private TDBConnection fConn;

	public TMainPane(Shell parent, int style, HashMap<Integer, TUser> aAllUsers, int aCurrentUser) {

		fAllUser = aAllUsers;
		fCurrentUser = fAllUser.get(aCurrentUser);

		try {
			getInitialData();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		parent.setBounds(0, 0, Display.getCurrent().getBounds().width, Display.getCurrent().getBounds().height);

		GridLayout hMainLayout = new GridLayout(1, false);

		parent.setLayout(hMainLayout);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		parent.setData(RWT.CUSTOM_VARIANT, "mainShell");

		fMainShell = parent;

		createContent(parent);
		setListeners();
	}

	@SuppressWarnings("static-access")
	private void getInitialData() throws SQLException {
		
		
		fConn = new TDBConnection();
		
		fCategoryAPI = new TCategoryType_SRV(fConn.fConnection);
		fCategoryAPI.loadAll();
		
		fEntryAPI = new TEntryType_SRV(fConn.fConnection);
		
	}

	private void createContent(Shell parent) {

		Composite hToolbarComposite = new Composite(parent, SWT.BORDER);
		hToolbarComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		GridLayout gl_hToolbarComposite = new GridLayout(6, false);
		gl_hToolbarComposite.marginWidth = 20;
		gl_hToolbarComposite.marginHeight = 20;
		hToolbarComposite.setLayout(gl_hToolbarComposite);
		hToolbarComposite.setData(RWT.CUSTOM_VARIANT, "toolBar");

		Label hFillerLabel = new Label(hToolbarComposite, SWT.NONE);
		hFillerLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		Label hUserLabel = new Label(hToolbarComposite, SWT.NONE);
		hUserLabel.setText(fCurrentUser.getUsrName().toUpperCase());
		hUserLabel.setFont(new Font(Display.getCurrent(), new FontData("Arial", 20, SWT.BOLD)));

		fSigneOut = new Button(hToolbarComposite, SWT.PUSH);
		fSigneOut.setText("Abmelden");
		fSigneOut.setImage(new Image(Display.getCurrent(), fExitPng));
		fSigneOut.setData(RWT.CUSTOM_VARIANT, "hSigneOut");

		Composite hContent = new Composite(parent, SWT.NONE);
		GridLayout gl_hContent = new GridLayout(1, false);
		gl_hContent.marginWidth = 0;
		gl_hContent.marginHeight = 0;
		hContent.setLayout(gl_hContent);
		hContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));
		hContent.setData(RWT.CUSTOM_VARIANT, "hContent");

		SashForm hContentSashForm = new SashForm(hContent, SWT.NONE);
		hContentSashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));
		hContentSashForm.setData(RWT.CUSTOM_VARIANT, "hContent");
		hContentSashForm.setSashWidth(10);

		Composite hTreeComposite = new Composite(hContentSashForm, SWT.BORDER);
		hTreeComposite.setLayout(new GridLayout(1, false));
		hTreeComposite.setData(RWT.CUSTOM_VARIANT, "hContent");

		TTreePane hTreePane = new TTreePane(hTreeComposite, SWT.NONE);

		Composite hContentComposite = new Composite(hContentSashForm, SWT.BORDER);
		hContentComposite.setLayout(fContentLayout);
		hContentComposite.setData(RWT.CUSTOM_VARIANT, "hContent");

		ArrayList<Control> hControlList = new ArrayList<Control>();

		fNullComp = new Composite(hContentComposite, SWT.NONE);

		// TODO: Implement the different category types
		TEntryPane hEntry = new TEntryPane(hContentComposite, SWT.NONE);
		hControlList.add(hEntry);
		
		TCategoryPane  hCategory = new TCategoryPane(hContentComposite, SWT.NONE);
		hControlList.add(hCategory);

		fContentLayout.topControl = fNullComp;

		hTreePane.bind(hContentComposite, fContentLayout, hControlList,fCategoryAPI,fEntryAPI);

		hContentSashForm.setWeights(new int[] { 61, 344 });

		setListeners();
		
		Button hSaveButton = new Button(hContent,SWT.PUSH);
		hSaveButton.setLayoutData(new GridData(SWT.RIGHT,SWT.NONE,true,false));
		hSaveButton.setText("Speichern");
	}

	@SuppressWarnings("serial")
	private void setListeners() {

		fSigneOut.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					RWT.getSettingStore().setAttribute("login", null);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (!fMainShell.isDisposed()) {
					fSigneOut.addListener(SWT.Selection, new ClientListener("window.location.reload(false);"));
					fMainShell.close();
				}

			}
		});
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		fConn.close();
	}
}
