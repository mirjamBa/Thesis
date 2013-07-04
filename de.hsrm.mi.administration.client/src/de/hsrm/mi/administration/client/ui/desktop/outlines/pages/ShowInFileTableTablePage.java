package de.hsrm.mi.administration.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.Replace;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.security.BasicHierarchyPermission;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.administration.client.ui.forms.MetadataForm;
import de.hsrm.mi.administration.shared.security.ViewShowInFileTablePagePermission;
import de.hsrm.thesis.filemanagement.shared.services.IAttributeService;

public class ShowInFileTableTablePage extends MetadataTablePage {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ShowInFileTable");
	}

	@Override
	protected void execInitPage() throws ProcessingException {
		setVisibleGranted(ACCESS.getLevel(new ViewShowInFileTablePagePermission()) > BasicHierarchyPermission.LEVEL_NONE);
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		return SERVICES.getService(IAttributeService.class).getAllAttributes();

	}

	@Order(10.0)
	public class DisplayTable extends Table {

		@Order(20.0)
		@Replace()
		public class DisplayModifyMenu extends ModifyMenu {
			@Override
			protected void execAction() throws ProcessingException {
				MetadataForm form = new MetadataForm();

				form.setAttributeId(getAttributIDColumn().getSelectedValue());
				form.getDescriptionField().setValue(
						getNameColumn().getSelectedValue());
				// check for standard meta attribute (no description editing
				// allowed)
				if (getFileTypeColumn().getSelectedValue() == null) {
					form.getDescriptionField().setEnabled(false);
				}
				form.getDatatypeField().setValue(
						getDatatypeColumn().getSelectedValue());
				form.getDatatypeField().setEnabled(false);
				form.getFileTypeField().setValue(
						getFileTypeColumn().getSelectedValue());
				form.getFileTypeField().setEnabled(false);
				form.getShowInFileTableField().setValue(
						getShowInFileTableColumn().getSelectedValue());
				form.startModify();
				form.waitFor();
				if (form.isFormStored()) {
					reloadPage();
				}
			}

		}
	}

}