package de.hsrm.perfunctio.core.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.Replace;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.shared.TEXTS;

/**
 * Displays the folder content of the assigned folder (files and other child
 * folders).
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class FolderTablePage extends FilesAndFoldersTablePage {

	public FolderTablePage(Long folderId) {
		super(folderId);
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("FolderTablePage");
	}

	@Order(10.0)
	public class Table extends FilesAndFoldersTable {

		@Order(10.0)
		@Replace
		public class NewFileInFolderMenu extends NewFileMenu {

			@Override
			protected boolean getConfiguredEmptySpaceAction() {
				return false;
			}
		}

		@Order(20.0)
		@Replace
		public class NewFolderInFolderMenu extends NewFolderMenu {

			@Override
			protected boolean getConfiguredEmptySpaceAction() {
				return false;
			}

		}

		@Order(25.0)
		public class SeperatorMenu extends AbstractExtensibleMenu {

			@Override
			protected boolean getConfiguredSeparator() {
				return true;
			}

		}
	}
}
