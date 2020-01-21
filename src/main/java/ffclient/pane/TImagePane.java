package ffclient.pane;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.rap.fileupload.DiskFileUploadReceiver;
import org.eclipse.rap.fileupload.FileUploadEvent;
import org.eclipse.rap.fileupload.FileUploadHandler;
import org.eclipse.rap.fileupload.FileUploadListener;
import org.eclipse.rap.rwt.widgets.FileUpload;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import ffclient.db.srv.types.TCategoryType_SRV;
import ffclient.db.types.TCategory;

@SuppressWarnings("serial")
public class TImagePane extends Composite {

	private FileUpload fFileUplad2;
	private FileUpload fFileUplad3;
	private Label fLblImage2;
	private Label fLblImage1;
	private Composite fComp1;
	private FileUpload fFileUplad1;
	private Button fRefreshImages;
	private Image fImage1;
	private TCategory fCategory;


	public TImagePane(Composite parent, int style,boolean isSingleImae) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		if (isSingleImae) {
			createContentForSingelImage();
		} else {
			createContent();
		}
		
	}

	private void createContentForSingelImage() {
		fComp1 = new Composite(this, SWT.NONE);
		fComp1.setLayout(new GridLayout(2, false));
		fComp1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label hLblTitle1 = new Label(fComp1, SWT.NONE);
		hLblTitle1.setText("Bild");
		hLblTitle1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		fLblImage1 = new Label(fComp1, SWT.BORDER);
		GridData hLbl1ImageLayoutData = new GridData(SWT.LEFT, SWT.CENTER, true, true,2,1);
		hLbl1ImageLayoutData.widthHint = 400;
		hLbl1ImageLayoutData.heightHint = 300;
		fLblImage1.setLayoutData(hLbl1ImageLayoutData);

		fFileUplad1 = new FileUpload(fComp1, SWT.NONE);
		fFileUplad1.setLayoutData(new GridData(SWT.LEFT, SWT.NONE, false, false));
		fFileUplad1.setText("Bild hochladen");
		
		fRefreshImages = new Button(fComp1,SWT.PUSH);
		fRefreshImages.setText("Refresh");
		
		setListener();
	}

	private void createContent() {
		
		fComp1 = new Composite(this, SWT.NONE);
		fComp1.setLayout(new GridLayout(2, false));
		fComp1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label hLblTitle1 = new Label(fComp1, SWT.NONE);
		hLblTitle1.setText("Bild 1");
		
		fLblImage1 = new Label(fComp1, SWT.NONE);
		GridData hLbl1ImageLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
//		hLbl1ImageLayoutData.widthHint = 200;
//		hLbl1ImageLayoutData.heightHint = 200;
		fLblImage1.setLayoutData(hLbl1ImageLayoutData);
		fLblImage1.setText("asdasdasd");
		
		fFileUplad1 = new FileUpload(fComp1, SWT.NONE);
		fFileUplad1.setLayoutData(new GridData(SWT.TOP,SWT.NONE,false,false));
		fFileUplad1.setText( "Bild hochladen" );
		
		Composite hComp2 = new Composite(this, SWT.NONE);
		hComp2.setLayout(new GridLayout(2, false));
		hComp2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label hLblTitle2 = new Label(hComp2, SWT.NONE);
		hLblTitle2.setText("Bild 2");
		
		fLblImage2 = new Label(hComp2, SWT.NONE);
		GridData hLbl2ImageLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		hLbl2ImageLayoutData.heightHint = 200;
		fLblImage2.setLayoutData(hLbl2ImageLayoutData);
		
		fFileUplad2 = new FileUpload( hComp2, SWT.NONE );
		fFileUplad2.setLayoutData(new GridData(SWT.TOP,SWT.NONE,false,false));
		fFileUplad2.setText( "Bild hochladen" );
		
		Composite hComp3 = new Composite(this, SWT.NONE);
		hComp3.setLayout(new GridLayout(2, false));
		hComp3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label hLblTitle3 = new Label(hComp3, SWT.NONE);
		hLblTitle3.setText("Bild 3");
		
		Label hLblImage3 = new Label(hComp3, SWT.NONE);
		GridData hLbl3ImageLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		hLbl3ImageLayoutData.heightHint = 200;
		hLblImage3.setLayoutData(hLbl3ImageLayoutData);
		
		fFileUplad3 = new FileUpload( hComp3, SWT.NONE );
		fFileUplad3.setLayoutData(new GridData(SWT.TOP,SWT.NONE,false,false));
		fFileUplad3.setText( "Bild hochladen" );
		
		fRefreshImages = new Button(this,SWT.PUSH);
		fRefreshImages.setText("Refresh");
		
		setListeners();
		
	}
	
	private void setListeners() {
		
	}

	private void setListener() {
		
		fRefreshImages.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ImageData hImageData = fImage1.getImageData();
				hImageData = hImageData.scaledTo(400, 300);
				Image hImage = new Image(Display.getDefault(),hImageData);
				fLblImage1.setImage(hImage);
				fLblImage1.update();
				fComp1.update();
			}
		});

		fFileUplad1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fImage1 = null;
				final DiskFileUploadReceiver receiver = new DiskFileUploadReceiver();
				FileUploadHandler uploadHandler = new FileUploadHandler(receiver);
				uploadHandler.addUploadListener(new FileUploadListener() {

					public void uploadProgress(FileUploadEvent event) {
					}

					public void uploadFailed(FileUploadEvent event) {
					}

					public void uploadFinished(FileUploadEvent event) {
						
						File hFile = receiver.getTargetFiles()[0].getAbsoluteFile();
						InputStream targetStream;
						try {
							targetStream = new FileInputStream(hFile);
							Image hImage = new Image(Display.getDefault(), targetStream);
							ImageData hImageData = hImage.getImageData();
							hImageData = hImageData.scaledTo(1600, 1200);
							fImage1 = new Image(Display.getDefault(),hImageData);
							
							ByteArrayOutputStream out = new ByteArrayOutputStream();
							DataOutputStream witeout = new DataOutputStream(out);
							
							ImageLoader imageLoader = new ImageLoader();
							imageLoader.data = new ImageData[] { hImageData };
							int format = SWT.IMAGE_PNG;
							
							imageLoader.save(witeout, format);
							
							fCategory.setImage(out.toByteArray());
							
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				});
				fFileUplad1.submit(uploadHandler.getUploadUrl());
			}
		});
	}

	public void bindSingleImage(TCategoryType_SRV aCategoryApi) {
		fCategory = aCategoryApi.getSelectedCategory();
		if(fCategory != null) {
			Object hImageData = fCategory.getImage();
			if(hImageData instanceof byte[]) {
				byte[] out = (byte[]) hImageData; 
				InputStream hIn = new ByteArrayInputStream(out);
				ImageData hData = new ImageData(hIn);	
				Image hImage = new Image(Display.getDefault(),hData);
				fLblImage1.setImage(hImage);
				fLblImage1.update();
				fComp1.update();
				return;
			}
		}
		fLblImage1.setImage(null);
		fLblImage1.update();
		fComp1.update();
	}
}
