package ffclient.pane;

import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileUploadEvent;
import org.eclipse.rap.fileupload.FileUploadHandler;
import org.eclipse.rap.fileupload.FileUploadListener;
import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("serial")
public class TEntryPane extends Composite {

	private FileUpload fFileUpload;
	private DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();

	public TEntryPane(Composite parent, int style) {
		super(parent, style);
		
		this.setLayout(new GridLayout(1,false));
		this.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		
		fFileUpload = new FileUpload( this, SWT.NONE );
		fFileUpload.setText( "Select File" );
		
		setListeners();
	}
	
	private void setListeners() {
		final FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
		uploadHandler.addUploadListener(new FileUploadListener() {
			public void uploadProgress(FileUploadEvent event) {
			}

			public void uploadFailed(FileUploadEvent event) {
			}

			public void uploadFinished(FileUploadEvent event) {
				System.out.println("Stored file: " + receiver.getTargetFiles()[0].getAbsolutePath());
			}
		});

		fFileUpload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fFileUpload.submit(uploadHandler.getUploadUrl());
			}
		});

	}

}
