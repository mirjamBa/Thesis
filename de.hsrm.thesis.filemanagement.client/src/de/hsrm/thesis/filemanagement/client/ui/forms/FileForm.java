package de.hsrm.thesis.filemanagement.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.CancelButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.AuthorityBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.AuthorityBox.RolesField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.DetailedBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.DetailedBox.FileFormMetadataTableField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.CreationDateField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.ContributorField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.CoverageField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.CreatorField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.DateField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.DescriptionField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.FormatField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.IdentifierField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.LanguageField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.PublisherField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.RelationField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.RightsField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.SourceField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.SubjectField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.TitleField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.DCMIBox.TypeField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.FileExtensionField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.FilesizeField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.MetadataBox.TypistField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.TagBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.TagBox.AvailableTagsBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.FileBox.TagBox.NewTagField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.OkButton;
import de.hsrm.thesis.filemanagement.shared.formdata.FileFormData;
import de.hsrm.thesis.filemanagement.shared.nonFormdataBeans.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.IMetadataService;
import de.hsrm.thesis.filemanagement.shared.services.IUserDefinedAttributesService;
import de.hsrm.thesis.filemanagement.shared.services.IUserProcessService;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.lookup.RoleLookupCall;
import de.hsrm.thesis.filemanagement.shared.services.lookup.TagLookupCall;
import de.hsrm.thesis.filemanagement.shared.services.lookup.UserLookupCall;

@FormData(value = FileFormData.class, sdkCommand = SdkCommand.USE)
public class FileForm extends AbstractForm {

	private Long m_fileNr;
	private ServerFileData m_fileData;
	private long m_filetypeNr;

	public FileForm() throws ProcessingException {
	}

	public FileForm(Long fileNr) throws ProcessingException {
		super();
		m_fileNr = fileNr;
	}

	public FileForm(ServerFileData fileData, long filetypeNr)
			throws ProcessingException {
		super();
		m_filetypeNr = filetypeNr;
		m_fileData = fileData;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("File");
	}

	public void startUpdateAuthorities() throws ProcessingException {
		startInternal(new RoleHandler());
	}

	public FileFormMetadataTableField getFileFormMetadataTableField() {
		return getFieldByClass(FileFormMetadataTableField.class);
	}

	public TitleField getTitleField() {
		return getFieldByClass(TitleField.class);
	}

	public TypeField getTypeField() {
		return getFieldByClass(TypeField.class);
	}

	public TypistField getTypistField() {
		return getFieldByClass(TypistField.class);
	}

	public CreatorField getAuthorField() {
		return getFieldByClass(CreatorField.class);
	}

	public AuthorityBox getAuthorityBox() {
		return getFieldByClass(AuthorityBox.class);
	}

	public AvailableTagsBox getAvailableTagsBox() {
		return getFieldByClass(AvailableTagsBox.class);
	}

	public RolesField getAvailableUserField() {
		return getFieldByClass(RolesField.class);
	}

	public CancelButton getCancelButton() {
		return getFieldByClass(CancelButton.class);
	}

	@FormData
	public Long getFileNr() {
		return m_fileNr;
	}

	@FormData
	public void setFileNr(Long fileNr) {
		this.m_fileNr = fileNr;
	}

	public void startModify() throws ProcessingException {
		startInternal(new ModifyHandler());
	}

	public void startNew() throws ProcessingException {
		startInternal(new NewHandler());
	}

	public void startRoleModify() throws ProcessingException {
		startInternal(new RoleHandler());
	}

	public ContributorField getContributorField() {
		return getFieldByClass(ContributorField.class);
	}

	public CoverageField getCoverageField() {
		return getFieldByClass(CoverageField.class);
	}

	public CreationDateField getCreationDateField() {
		return getFieldByClass(CreationDateField.class);
	}

	public CreatorField getCreatorField() {
		return getFieldByClass(CreatorField.class);
	}

	public DCMIBox getDCMIBox() {
		return getFieldByClass(DCMIBox.class);
	}

	public DateField getDateMetadataField() {
		return getFieldByClass(DateField.class);
	}

	public DescriptionField getDescriptionField() {
		return getFieldByClass(DescriptionField.class);
	}

	public DetailedBox getDetailedBox() {
		return getFieldByClass(DetailedBox.class);
	}

	public FileBox getFile0Box() {
		return getFieldByClass(FileBox.class);
	}

	public TypeField getFileTypeField() {
		return getFieldByClass(TypeField.class);
	}

	public FileExtensionField getFileExtensionField() {
		return getFieldByClass(FileExtensionField.class);
	}

	public FilesizeField getFilesizeField() {
		return getFieldByClass(FilesizeField.class);
	}

	public FormatField getFormatField() {
		return getFieldByClass(FormatField.class);
	}

	public IdentifierField getIdentifikatorField() {
		return getFieldByClass(IdentifierField.class);
	}

	public LanguageField getLanguageField() {
		return getFieldByClass(LanguageField.class);
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public MetadataBox getMetadataBox() {
		return getFieldByClass(MetadataBox.class);
	}

	public NewTagField getNewTagField() {
		return getFieldByClass(NewTagField.class);
	}

	public OkButton getOkButton() {
		return getFieldByClass(OkButton.class);
	}

	public PublisherField getPublisherField() {
		return getFieldByClass(PublisherField.class);
	}

	public RelationField getRelationField() {
		return getFieldByClass(RelationField.class);
	}

	public RightsField getRightsField() {
		return getFieldByClass(RightsField.class);
	}

	public SourceField getSource0Field() {
		return getFieldByClass(SourceField.class);
	}

	public SubjectField getSubjectField() {
		return getFieldByClass(SubjectField.class);
	}

	public TagBox getTagBox() {
		return getFieldByClass(TagBox.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class FileBox extends AbstractTabBox {

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("File");
			}

			public DetailedBox getDetailedBox() {
				return getFieldByClass(DetailedBox.class);
			}

			@Order(10.0)
			public class MetadataBox extends AbstractGroupBox {

				public DCMIBox getDCMIBox() {
					return getFieldByClass(DCMIBox.class);
				}

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("StandardMetadataDCMI");
				}

				@Order(10.0)
				public class CreationDateField extends AbstractDateField {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("EntryDate");
					}

					@Override
					protected boolean getConfiguredEnabled() {
						return false;
					}
				}

				@Order(20.0)
				public class TypistField extends AbstractSmartField<Long> {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Typist");
					}

					@Override
					protected Class<? extends LookupCall> getConfiguredLookupCall() {
						return UserLookupCall.class;
					}

					@Override
					protected boolean getConfiguredEnabled() {
						return false;
					}

				}

				@Order(30.0)
				public class FilesizeField extends AbstractStringField {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Filesize");
					}

					@Override
					protected boolean getConfiguredEnabled() {
						return false;
					}
				}

				@Order(40.0)
				public class FileExtensionField extends AbstractStringField {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("FileExtension");
					}

					@Override
					protected boolean getConfiguredEnabled() {
						return false;
					}
				}

				@Order(50.0)
				public class DCMIBox extends AbstractGroupBox {

					@Override
					protected String getConfiguredBorderDecoration() {
						return BORDER_DECORATION_LINE;
					}

					@Order(10.0)
					public class TitleField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Title");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("TitleToolTip");
						}

						@Override
						protected boolean getConfiguredMandatory() {
							return true;
						}
					}

					@Order(20.0)
					public class CreatorField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Creator");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("CreatorToolTip");
						}
					}

					@Order(30.0)
					public class TypeField extends AbstractSmartField<Long> {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("FileType");
						}

						@Override
						protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
							return FileTypeCodeType.class;
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("TypeToolTip");
						}

						@Override
						protected boolean getConfiguredEnabled() {
							return false;
						}
					}

					@Order(40.0)
					public class SubjectField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Subject");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("SubjectToolTip");
						}
					}

					@Order(50.0)
					public class DescriptionField extends AbstractStringField {

						@Override
						protected int getConfiguredGridH() {
							return 4;
						}

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Description");
						}

						@Override
						protected boolean getConfiguredMultilineText() {
							return true;
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("DescriptionToolTip");
						}

						@Override
						protected boolean getConfiguredWrapText() {
							return true;
						}
					}

					@Order(60.0)
					public class PublisherField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Publisher");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("PublisherToolTip");
						}
					}

					@Order(70.0)
					public class ContributorField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Contributor");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("ContributorToolTip");
						}
					}

					@Order(80.0)
					public class DateField extends AbstractDateField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("DateMetadata");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("DateMetadataToolTip");
						}
					}

					@Order(90.0)
					public class FormatField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Format");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("FormatToolTip");
						}
					}

					@Order(100.0)
					public class IdentifierField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Identifier");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("IdentifierToolTip");
						}
					}

					@Order(110.0)
					public class SourceField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Source");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("SourceToolTip");
						}
					}

					@Order(120.0)
					public class LanguageField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Language");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("LanguageToolTip");
						}
					}

					@Order(130.0)
					public class RelationField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Relation");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("RelationToolTip");
						}
					}

					@Order(140.0)
					public class CoverageField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Coverage");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("CoverageToolTip");
						}
					}

					@Order(150.0)
					public class RightsField extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Rights");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("RightsToolTip");
						}
					}

				}
			}

			@Order(20.0)
			public class DetailedBox extends AbstractGroupBox {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Detailed");
				}

				public FileFormMetadataTableField getFileFormMetadataTableField() {
					return getFieldByClass(FileFormMetadataTableField.class);
				}

				@Order(10.0)
				public class FileFormMetadataTableField
						extends
							MetadataTableField {
					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Attribute");
					}
				}

			}

			@Order(30.0)
			public class TagBox extends AbstractGroupBox {

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Tag");
				}

				@Order(10.0)
				public class AvailableTagsBox extends AbstractListBox<Long> {

					@Override
					protected int getConfiguredGridH() {
						return 5;
					}

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("AvailableTags");
					}

					@Override
					protected Class<? extends LookupCall> getConfiguredLookupCall() {
						return TagLookupCall.class;
					}

				}

				@Order(60.0)
				public class NewTagField extends AbstractStringField {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("NewTags");
					}

					@Override
					protected String getConfiguredTooltipText() {
						return TEXTS.get("TooltipNewTagField");
					}

				}
			}

			@Order(40.0)
			public class AuthorityBox extends AbstractGroupBox {

				public RolesField getRolesField() {
					return getFieldByClass(RolesField.class);
				}

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Authority");
				}

				@Order(10.0)
				public class RolesField extends AbstractListBox<Long> {

					@Override
					protected int getConfiguredGridH() {
						return 5;
					}

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("UserRoles");
					}

					@Override
					protected Class<? extends LookupCall> getConfiguredLookupCall() {
						return RoleLookupCall.class;
					}

					@Override
					protected void execPrepareLookup(LookupCall call)
							throws ProcessingException {
						((RoleLookupCall) call).setUserId(SERVICES.getService(
								IUserProcessService.class).getCurrentUserId());
					}

					@Override
					public String getTooltipText() {
						return TEXTS.get("TooltipRolesField");
					}
				}
			}
		}

		@Order(20.0)
		public class OkButton extends AbstractOkButton {
		}

		@Order(30.0)
		public class CancelButton extends AbstractCancelButton {
		}
	}

	private void loadMetadataTable(Object[][] attributes, boolean withValues)
			throws ProcessingException {
		MetadataTableField.Table table = getFileFormMetadataTableField()
				.getTable();

		for (int i = 0; i < attributes.length; i++) {
			ITableRow row = table.createRow();
			table.getAttributIDColumn().setValue(row, (Long) attributes[i][0]);
			table.getAttributeColumn().setValue(row, (String) attributes[i][1]);
			if (withValues) {
				table.getValueColumn().setValue(row, (String) attributes[i][2]);
			}
			table.getDatatypeColumn().setValue(row, (Long) attributes[i][3]);
			table.addRow(row);
		}
	}

	public class ModifyHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() throws ProcessingException {
			loadMetadataTable(
					SERVICES.getService(IMetadataService.class)
							.getMetadataAttributeMatrix(getFileNr(),
									getFiletypeNr(), true), true);
		}

		@Override
		public void execStore() throws ProcessingException {
			IFileService service = SERVICES.getService(IFileService.class);
			FileFormData formData = new FileFormData();
			exportFormData(formData);
			formData = service.update(formData);
		}
	}

	public class NewHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() throws ProcessingException {
			loadMetadataTable(
					SERVICES.getService(IUserDefinedAttributesService.class)
							.getAttributes(getFiletypeNr()), false);
		}

		@Override
		public void execStore() throws ProcessingException {
			IFileService service = SERVICES.getService(IFileService.class);
			FileFormData formData = new FileFormData();
			exportFormData(formData);
			formData = service.create(formData, m_fileData);
		}
	}

	public class RoleHandler extends AbstractFormHandler {

		@Override
		protected void execStore() throws ProcessingException {
			IFileService service = SERVICES.getService(IFileService.class);
			FileFormData formData = new FileFormData();
			exportFormData(formData);
			service.updateRoleFilePermission(getFileNr(), formData.getRoles()
					.getValue());
		}
	}

	// public class ViewHandler extends AbstractFormHandler {
	//
	// @Override
	// protected void execLoad() throws ProcessingException {
	// // open form code
	// }
	//
	// @Override
	// protected boolean getConfiguredOpenExclusive() {
	// return true;
	// }
	// }
	//
	// public void startView() throws ProcessingException {
	// startInternalExclusive(new FileForm.ViewHandler());
	// }
	//
	// @Override
	// public Object computeExclusiveKey() throws ProcessingException {
	// return m_fileNr;
	// }

	@FormData
	public ServerFileData getFileData() {
		return m_fileData;
	}

	@FormData
	public void setFileData(ServerFileData fileData) {
		m_fileData = fileData;
	}

	@FormData
	public long getFiletypeNr() {
		return m_filetypeNr;
	}

	@FormData
	public void setFiletypeNr(long filetypeNr) {
		m_filetypeNr = filetypeNr;
	}
}
