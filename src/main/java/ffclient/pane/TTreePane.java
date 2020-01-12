package ffclient.pane;

import java.util.ArrayList;

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

import ffclient.db.types.TCategory;

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

	public void bind(Composite aContentComposite,StackLayout aLayout,ArrayList<Control> aControlList) {
		
		for (int i = 0; i < 4; i++) {

			TCategory hCat = null;

			switch (i) {
			case 1:
				hCat = new TCategory(2, "Einsatz", 0, null, "");
				break;
			case 2:
				hCat = new TCategory(3, "Übungen", 0, null, "");
				break;
			case 3:
				hCat = new TCategory(4, "Sonstiges", 0, null, "");
				break;
			default:
				hCat = new TCategory(1, "Main", 1, null, "");
				break;
			}

			TreeItem treeItem = new TreeItem(fTree, SWT.NONE);
			treeItem.setText(hCat.getName());
			treeItem.setData(hCat);
			
			setListeners(aContentComposite,aLayout,aControlList);
		}
	}

	private void setListeners(final Composite aContentComposite,final StackLayout aLayout,final ArrayList<Control> aControlList) {
		fTree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					System.out.println(event.item + " was checked.");
				} else {
					System.out.println(event.item + " was selected");
				}
				aLayout.topControl = aControlList.get(0);
				aContentComposite.layout();
			}
		});
	}

}
