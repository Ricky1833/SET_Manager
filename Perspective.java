package org.sdsu.cs532.set;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "org.sdsu.cs532.SET.perspective";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		layout.addStandaloneView(NavigationView.ID,  true, IPageLayout.LEFT, 0.2f, editorArea);
		IFolderLayout folder = layout.createFolder("", IPageLayout.RIGHT, 0.5f, editorArea);
//		folder.addPlaceholder(View.ID + ":*");
//		folder.addView(View.ID);
		
		layout.getViewLayout(NavigationView.ID).setCloseable(false);
	}
}
