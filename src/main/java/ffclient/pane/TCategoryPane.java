package ffclient.pane;

import java.util.ArrayList;

import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileUploadEvent;
import org.eclipse.rap.fileupload.FileUploadHandler;
import org.eclipse.rap.fileupload.FileUploadListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ffclient.db.srv.types.TCategoryType_SRV;
import ffclient.db.srv.types.TEntryType_SRV;
import ffclient.db.types.TCategory;
import ffclient.db.types.TEntryType;

@SuppressWarnings("serial")
public class TCategoryPane extends Composite{

	private Text fTitle;
	private Text fText;
	private TImagePane fImagePaneFirst;

	public TCategoryPane(Composite parent, int style) {
		super(parent, style);
		
		this.setLayout(new GridLayout(1,false));
		this.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		
		createContent();
	}
	
	public void createContent() {
				
		Composite hContent = new Composite(this,SWT.NONE);
		hContent.setLayout(new GridLayout(1,false));
		hContent.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));

		fImagePaneFirst = new TImagePane(hContent, SWT.NONE,true);
		fImagePaneFirst.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		Label hTitelLabelFirst = new Label(hContent, SWT.NONE);
		hTitelLabelFirst.setText("Titel");

		fTitle = new Text(hContent, SWT.BORDER);
		fTitle.setText("Eintragstitel");
		fTitle.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		Label hTextLabelFirst = new Label(hContent, SWT.NONE);
		hTextLabelFirst.setText("Text:");

		fText = new Text(hContent, SWT.BORDER | SWT.WRAP);
		GridData hGridDataFirstText = new GridData(SWT.FILL, SWT.NONE, true, false);
		hGridDataFirstText.heightHint = 100;
		fText.setLayoutData(hGridDataFirstText);

	}

	public void bind(TCategoryType_SRV aCategoryApi) {
		TCategory hCategory = aCategoryApi.getSelectedCategory();
		if(hCategory != null) {
			fTitle.setText(hCategory.getName());
			fText.setText(hCategory.getText());
			
			fImagePaneFirst.bindSingleImage(aCategoryApi);
			
			setListener(hCategory);
		}
	}

	private void setListener(final TCategory aCategory) {
		fTitle.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent event) {
				aCategory.setName(fTitle.getText());
				aCategory.setIsModified(true);
			}
		});
	}
}
