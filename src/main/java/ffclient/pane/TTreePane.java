package ffclient.pane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ffclient.db.srv.types.TCategoryType_SRV;
import ffclient.db.srv.types.TEntryType_SRV;
import ffclient.db.types.TCategory;
import ffclient.db.types.TEntryType;

@SuppressWarnings("serial")
public class TTreePane extends Composite {

	private Tree fTree;

	public TTreePane(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		createContent(parent);
	}

	private void createContent(Composite parent) {

		fTree = new Tree(parent, SWT.NONE);
		fTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
	}

	@SuppressWarnings("rawtypes")
	public void bind(Composite aContentComposite,StackLayout aLayout,ArrayList<Control> aControlList,TCategoryType_SRV aCategoryAPI,TEntryType_SRV aEntryApi) {
		
		Iterator it = aCategoryAPI.getAllCategories().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			if(pair.getValue() instanceof TCategory) {
				TreeItem treeItem = new TreeItem(fTree, SWT.NONE);
				treeItem.setText(((TCategory) pair.getValue()).getName());
				treeItem.setData((TCategory) pair.getValue());
				
				try {
					ArrayList<TEntryType> childrenfromCoategory = aEntryApi.getChildrenfromCoategory((TCategory) pair.getValue());
					if(childrenfromCoategory.size() > 0) {
						for (TEntryType tEntryType : childrenfromCoategory) {
							TreeItem childItem = new TreeItem(treeItem, SWT.NONE);
							childItem.setText(tEntryType.getfName());
							childItem.setData(tEntryType);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		setListeners(aContentComposite,aLayout,aControlList,aCategoryAPI,aEntryApi);
	}

	private void setListeners(final Composite aContentComposite, final StackLayout aLayout,
			final ArrayList<Control> aControlList,final TCategoryType_SRV aCategoryAPI,final TEntryType_SRV aEntryApi) {
		fTree.addListener(SWT.Selection, new Listener() {
			private TCategory fLastSelection;

			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
				} else {

					if (event.item.getData() instanceof TCategory) {
						
						
						TCategory hSelectedCategory = (TCategory) event.item.getData();
						
						if(fLastSelection != null && !hSelectedCategory.equals(fLastSelection)) {
							fLastSelection.setIsSelected(false);
						}
						
						hSelectedCategory.setIsSelected(true);
						fLastSelection = hSelectedCategory;

						for (Control control : aControlList) {
							if (control instanceof TCategoryPane) {
								TCategoryPane hCategoryPane = (TCategoryPane) control;
								hCategoryPane.bind(aCategoryAPI);
								aLayout.topControl = hCategoryPane;
							}
						}

					}
					aContentComposite.layout();
				}

			}
		});
	}

}
