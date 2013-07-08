package de.hsrm.thesis.filemanagement.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractSearchForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractResetButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractSearchButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.extension.client.ui.form.fields.smartfield.AbstractExtensibleSmartField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.services.IFileSearchBookmarkService;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.BookmarkButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.LoadBookmarkButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.ResetButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.SearchButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.DetailedBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.DetailedBox.FileSearchMetadataTableField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.DetailedBox.FileTypeField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.DetailedBox.TypistField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.GeneralSearchBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.GeneralSearchBox.FolderSearchField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.GeneralSearchBox.GeneralSearchField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.TagBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileSearchForm.MainBox.TabBox.TagBox.TagField;
import de.hsrm.thesis.filemanagement.shared.services.IUserDefinedAttributesService;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.formdata.BookmarkNameFormData;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FileSearchFormData;
import de.hsrm.thesis.filemanagement.shared.services.lookup.TagLookupCall;
import de.hsrm.thesis.filemanagement.shared.services.lookup.UserLookupCall;

@FormData(value = FileSearchFormData.class, sdkCommand = SdkCommand.USE)
public class FileSearchForm extends AbstractSearchForm {
	private SearchBookmarkForm bookmarkForm;

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("FileSearchForm");
	}

	public FileSearchForm() throws ProcessingException {
		super();
		bookmarkForm = new SearchBookmarkForm(this);
	}

	@Override
	protected void execDataChanged(Object... dataTypes)
			throws ProcessingException {
		getFileSearchMetadataTableField().reloadTableData();
		getTagField().resetValue();
	}

	@Override
	protected void execResetSearchFilter(SearchFilter searchFilter)
			throws ProcessingException {
		super.execResetSearchFilter(searchFilter);
		FileSearchFormData formData = new FileSearchFormData();
		exportFormData(formData);
		searchFilter.setFormData(formData);
	}

	@Override
	public void startSearch() throws ProcessingException {
		startInternal(new SearchHandler());
	}

	public TagField getTagField() {
		return getFieldByClass(TagField.class);
	}

	public BookmarkButton getBookmarkButton() {
		return getFieldByClass(BookmarkButton.class);
	}

	public DetailedBox getDetailedBox() {
		return getFieldByClass(DetailedBox.class);
	}

	public FileSearchMetadataTableField getFileSearchMetadataTableField() {
		return getFieldByClass(FileSearchMetadataTableField.class);
	}

	public FileTypeField getFileTypeField() {
		return getFieldByClass(FileTypeField.class);
	}

	public FolderSearchField getFolderSearchField() {
		return getFieldByClass(FolderSearchField.class);
	}

	public GeneralSearchBox getGeneralSearchBox() {
		return getFieldByClass(GeneralSearchBox.class);
	}

	public GeneralSearchField getGeneralSearchField() {
		return getFieldByClass(GeneralSearchField.class);
	}

	public LoadBookmarkButton getLoadBookmarkButton() {
		return getFieldByClass(LoadBookmarkButton.class);
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public ResetButton getResetButton() {
		return getFieldByClass(ResetButton.class);
	}

	public SearchButton getSearchButton() {
		return getFieldByClass(SearchButton.class);
	}

	public TabBox getTabBox() {
		return getFieldByClass(TabBox.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class TabBox extends AbstractTabBox {

			@Order(10.0)
			public class GeneralSearchBox extends AbstractGroupBox {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("GeneralSearch");
				}

				@Order(10.0)
				public class GeneralSearchField extends AbstractStringField {

					@Override
					protected String getConfiguredTooltipText() {
						return TEXTS.get("GeneralSearchFieldToolTip");
					}

				}

				@Order(20.0)
				public class FolderSearchField extends AbstractCheckBox {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("FolderSearch");
					}

					@Override
					protected void execInitField() throws ProcessingException {
						setValue(true);
					}
				}
			}

			@Order(30.0)
			public class DetailedBox extends AbstractGroupBox {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Detailed");
				}

				@Order(10.0)
				public class FileTypeField
						extends
							AbstractExtensibleSmartField<Long> {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("FileType");
					}

					@Override
					protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
						return FileTypeCodeType.class;
					}
				}

				@Order(20.0)
				public class TypistField
						extends
							AbstractExtensibleSmartField<Long> {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Typist");
					}

					@Override
					protected Class<? extends LookupCall> getConfiguredLookupCall() {
						return UserLookupCall.class;
					}
				}

				@Order(30.0)
				public class FileSearchMetadataTableField
						extends
							MetadataTableField {

					@Override
					protected int getConfiguredGridH() {
						return 4;
					}

					@Override
					protected int getConfiguredGridW() {
						return 2;
					}

				}

			}

			@Order(40.0)
			public class TagBox extends AbstractGroupBox {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Tag");
				}

				@Order(10.0)
				public class TagField extends AbstractListBox<Long> {

					@Override
					protected int getConfiguredGridH() {
						return 5;
					}

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Tag");
					}

					@Override
					protected Class<? extends LookupCall> getConfiguredLookupCall() {
						return TagLookupCall.class;
					}
				}
			}
		}

		@Order(20.0)
		public class ResetButton extends AbstractResetButton {
			@Override
			protected void execClickAction() throws ProcessingException {
				getFileSearchMetadataTableField().getTable().deleteAllRows();
				loadMetaAttributeTable();
			}

		}

		@Order(30.0)
		public class SearchButton extends AbstractSearchButton {
		}

		@Order(40.0)
		public class LoadBookmarkButton extends AbstractButton {

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("ShowBookmarks");
			}

			@Override
			protected void execClickAction() throws ProcessingException {
				if (!bookmarkForm.isFormOpen()) {
					bookmarkForm.startNew();
					setLabel(TEXTS.get("CloseBookmark"));
				} else {
					setLabel(getConfiguredLabel());
					bookmarkForm.doClose();
				}
			}
		}

		@Order(50.0)
		public class BookmarkButton extends AbstractButton {

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("BookmarkSearch");
			}

			@Override
			protected void execClickAction() throws ProcessingException {
				String s = FileSearchForm.this.getXML("UTF-8");
				BookmarkNameForm form = new BookmarkNameForm();
				form.startNew();
				form.waitFor();
				if (form.isFormStored()) {
					BookmarkNameFormData data = new BookmarkNameFormData();
					form.exportFormData(data);
					SERVICES.getService(IFileSearchBookmarkService.class)
							.exportFileSearch(data.getName().getValue(), s);
					bookmarkForm.getBookmarkField().reloadTableData();
				}

			}
		}
	}

	public class SearchHandler extends AbstractFormHandler {

		@Override
		public void execLoad() throws ProcessingException {
			getFileSearchMetadataTableField().getTable().deleteAllRows();
			loadMetaAttributeTable();

		}
	}

	public TagBox getTagBox() {
		return getFieldByClass(TagBox.class);
	}

	public TypistField getTypistField() {
		return getFieldByClass(TypistField.class);
	}

	private void loadMetaAttributeTable() throws ProcessingException {
		MetadataTableField.Table table = getFileSearchMetadataTableField()
				.getTable();
		Object[][] attr = SERVICES.getService(
				IUserDefinedAttributesService.class)
				.getDetailedMetadataForAllFileTypes();
		for (int i = 0; i < attr.length; i++) {
			ITableRow row = table.createRow();
			table.getAttributIDColumn().setValue(row, (Long) attr[i][0]);
			table.getAttributeColumn().setValue(row, (String) attr[i][1]);
			table.getDatatypeColumn().setValue(row, (Long) attr[i][2]);
			table.addRow(row);
		}
	}
}
