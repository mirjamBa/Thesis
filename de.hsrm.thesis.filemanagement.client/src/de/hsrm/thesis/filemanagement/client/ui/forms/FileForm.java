package de.hsrm.thesis.filemanagement.client.ui.forms;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.AbstractValueField;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.AbstractDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.administration.shared.services.IMetadataService;
import de.hsrm.mi.administration.shared.services.code.DatatypeCodeType;
import de.hsrm.mi.administration.shared.services.lookup.TagLookupCall;
import de.hsrm.thesis.bachelor.shared.services.lookup.RoleLookupCall;
import de.hsrm.thesis.bachelor.shared.services.lookup.UserLookupCall;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.CancelButton;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.AuthorityBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.AuthorityBox.RolesField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.DetailedBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.DetailedBox.AttributeField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.CreationDateField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.ContributorField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.CoverageField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.CreatorField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.DateMetadataField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.DescriptionField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.FormatField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.IdentifierField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.LanguageField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.PublisherField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.RelationField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.RightsField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.Source0Field;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.Subject0Field;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.TitleField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.DCMIBox.TypeField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.FileExtensionField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.FilesizeField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.MetadataBox.TypistField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.TagBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.TagBox.AvailableTagsBox;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.File0Box.TagBox.NewTagField;
import de.hsrm.thesis.filemanagement.client.ui.forms.FileForm.MainBox.OkButton;
import de.hsrm.thesis.filemanagement.shared.files.ServerFileData;
import de.hsrm.thesis.filemanagement.shared.formdata.FileFormData;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;

@FormData(value = FileFormData.class, sdkCommand = SdkCommand.CREATE)
public class FileForm extends AbstractForm {

	private Long m_fileNr;
	private ServerFileData m_fileData;
	private long m_filetypeNr;

	public FileForm(Long fileNr) throws ProcessingException {
		super();
		m_fileNr = fileNr;
	}

	public FileForm(ServerFileData fileData, long filetypeNr) throws ProcessingException {
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

	public AttributeField getAttributeField() {
		return getFieldByClass(AttributeField.class);
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

	public DateMetadataField getDateMetadataField() {
		return getFieldByClass(DateMetadataField.class);
	}

	public DescriptionField getDescriptionField() {
		return getFieldByClass(DescriptionField.class);
	}

	public DetailedBox getDetailedBox() {
		return getFieldByClass(DetailedBox.class);
	}

	public File0Box getFile0Box() {
		return getFieldByClass(File0Box.class);
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

	public Source0Field getSource0Field() {
		return getFieldByClass(Source0Field.class);
	}

	public Subject0Field getSubject0Field() {
		return getFieldByClass(Subject0Field.class);
	}

	public TagBox getTagBox() {
		return getFieldByClass(TagBox.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class File0Box extends AbstractTabBox {

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
					public class Subject0Field extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Subject0");
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
					public class DateMetadataField extends AbstractDateField {

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
							return TEXTS.get("Identifikator");
						}

						@Override
						protected String getConfiguredTooltipText() {
							return TEXTS.get("IdentifierToolTip");
						}
					}

					@Order(110.0)
					public class Source0Field extends AbstractStringField {

						@Override
						protected String getConfiguredLabel() {
							return TEXTS.get("Source0");
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

				public AttributeField getAttributeField() {
					return getFieldByClass(AttributeField.class);
				}

				@Order(10.0)
				public class AttributeField extends AbstractTableField<AttributeField.Table> {

					@Override
					protected String getConfiguredLabel() {
						return TEXTS.get("Attribute");
					}

					@Order(10.0)
					public class Table extends AbstractExtensibleTable {

						public ValueColumn getValueColumn() {
							return getColumnSet().getColumnByClass(ValueColumn.class);
						}

						@Override
						protected boolean getConfiguredAutoResizeColumns() {
							return true;
						}

						@Override
						protected boolean getConfiguredMultiSelect() {
							return false;
						}

						public DatatypeColumn getDatatypeColumn() {
							return getColumnSet().getColumnByClass(DatatypeColumn.class);
						}

						public AttributIDColumn getAttributIDColumn() {
							return getColumnSet().getColumnByClass(AttributIDColumn.class);
						}

						public AttributeColumn getAttributeColumn() {
							return getColumnSet().getColumnByClass(AttributeColumn.class);
						}

						@Order(10.0)
						public class AttributIDColumn extends AbstractLongColumn {

							@Override
							protected boolean getConfiguredDisplayable() {
								return false;
							}

							@Override
							protected String getConfiguredHeaderText() {
								return TEXTS.get("AttributID");
							}

							@Override
							protected boolean getConfiguredVisible() {
								return false;
							}
						}

						@Order(20.0)
						public class AttributeColumn extends AbstractStringColumn {

							@Override
							protected String getConfiguredHeaderText() {
								return TEXTS.get("Attribute");
							}
						}

						@Order(30.0)
						public class ValueColumn extends AbstractStringColumn {

							@Override
							protected String getConfiguredHeaderText() {
								return TEXTS.get("Value");
							}

							@Override
							protected boolean getConfiguredEditable() {
								return true;
							}

							@Override
							protected IFormField execPrepareEdit(final ITableRow row) throws ProcessingException {
								Long datatype = (Long) row.getCellValue(getTable().getColumnSet().getColumnCount() - 1);
								if (datatype.equals(DatatypeCodeType.DateCode.ID)) {
									return new AbstractDateField() {
									};
								}
								if (datatype.equals(DatatypeCodeType.StringCode.ID)) {
									return new AbstractStringField() {
									};
								}
								if (datatype.equals(DatatypeCodeType.LongCode.ID)) {
									return new AbstractLongField() {
									};
								}
								if (datatype.equals(DatatypeCodeType.DoubleCode.ID)) {
									return new AbstractDoubleField() {
									};
								}
								return new AbstractStringField() {
								};
							}

							@Override
							protected void execCompleteEdit(ITableRow row, IFormField editingField)
									throws ProcessingException {

								@SuppressWarnings("rawtypes")
								Object value = (Object) ((AbstractValueField) editingField).getValue();

								if (value != null) {
									if (editingField instanceof AbstractDateField) {
										Date date = ((AbstractDateField) editingField).getValue();
										SimpleDateFormat formatter = new SimpleDateFormat(TEXTS.get("SimpleDateFormat"));
										getValueColumn().setValue(row, formatter.format(date));
									} else {
										getValueColumn().setValue(row, value.toString());
									}
								}

							}

							@Order(10)
							public class StringEditor extends AbstractStringField {

								@Override
								protected int getConfiguredMaxLength() {
									return 60;
								}
							}
						}

						// must be always the last column of the table
						@Order(40.0)
						public class DatatypeColumn extends AbstractSmartColumn<Long> {

							@Override
							protected String getConfiguredHeaderText() {
								return TEXTS.get("Datatype");
							}

							@Override
							protected Class<? extends ICodeType<Long>> getConfiguredCodeType() {
								return DatatypeCodeType.class;
							}

						}
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

	private void loadMetaAttributeTable() throws ProcessingException {
		AttributeField.Table table = getFile0Box().getDetailedBox().getAttributeField().getTable();
		Object[][] attr = SERVICES.getService(IMetadataService.class).getAttributes(getFiletypeNr());
		for (int i = 0; i < attr.length; i++) {
			ITableRow row = table.createRow();
			table.getAttributIDColumn().setValue(row, (Long) attr[i][0]);
			table.getAttributeColumn().setValue(row, (String) attr[i][1]);
			table.getDatatypeColumn().setValue(row, (Long) attr[i][2]);
			table.addRow(row);
		}
	}

	private void loadMetaAttributeValueTable() throws ProcessingException {
		AttributeField.Table table = getFile0Box().getDetailedBox().getAttributeField().getTable();
		Object[][] attr = SERVICES.getService(IMetadataService.class).getMetadataAttributeMatrix(getFileNr(),
				getFiletypeNr(), true);
		for (int i = 0; i < attr.length; i++) {
			ITableRow row = table.createRow();
			table.getAttributIDColumn().setValue(row, (Long) attr[i][0]);
			table.getAttributeColumn().setValue(row, (String) attr[i][1]);
			table.getValueColumn().setValue(row, (String) attr[i][2]);
			table.getDatatypeColumn().setValue(row, (Long) attr[i][3]);
			table.addRow(row);
		}
	}

	public class ModifyHandler extends AbstractFormHandler {

		@Override
		protected void execLoad() throws ProcessingException {
			loadMetaAttributeValueTable();
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
			loadMetaAttributeTable();
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
			service.updateRoleFilePermission(getFileNr(), formData.getRoles().getValue());
		}
	}

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
