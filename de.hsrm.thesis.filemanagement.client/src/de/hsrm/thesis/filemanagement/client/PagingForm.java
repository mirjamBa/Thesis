package de.hsrm.thesis.filemanagement.client;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;

import de.hsrm.thesis.filemanagement.client.PagingForm.MainBox.FormClosingButton;
import de.hsrm.thesis.filemanagement.client.PagingForm.MainBox.PagingBox;
import de.hsrm.thesis.filemanagement.client.PagingForm.MainBox.PagingBox.HierarchyField;
import de.hsrm.thesis.filemanagement.client.PagingForm.MainBox.PagingBox.InformationField;
import de.hsrm.thesis.filemanagement.client.PagingForm.MainBox.PagingBox.NextButton;
import de.hsrm.thesis.filemanagement.client.PagingForm.MainBox.PagingBox.PageField;
import de.hsrm.thesis.filemanagement.client.PagingForm.MainBox.PagingBox.PreviousButton;
import de.hsrm.thesis.filemanagement.client.ui.desktop.outlines.pages.ImageTablePage;
import de.hsrm.thesis.filemanagement.shared.PagingFormData;

@FormData(value = PagingFormData.class, sdkCommand = SdkCommand.CREATE)
public class PagingForm extends AbstractForm {
	private ImageTablePage page;

	public PagingForm(ImageTablePage page) throws ProcessingException {
		super();
		this.page = page;
	}

	@Override
	protected int getConfiguredDisplayHint() {
		return DISPLAY_HINT_VIEW;
	}

	@Override
	protected String getConfiguredDisplayViewId() {
		return VIEW_ID_NE;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Paging");
	}

	public void startNew() throws ProcessingException {
		startInternal(new NewHandler());
	}

	public FormClosingButton getFormClosingButton() {
		return getFieldByClass(FormClosingButton.class);
	}

	public HierarchyField getHierarchyField() {
		return getFieldByClass(HierarchyField.class);
	}

	public InformationField getInformationField() {
		return getFieldByClass(InformationField.class);
	}

	public MainBox getMainBox() {
		return getFieldByClass(MainBox.class);
	}

	public NextButton getNextButton() {
		return getFieldByClass(NextButton.class);
	}

	public PageField getPageField() {
		return getFieldByClass(PageField.class);
	}

	public PagingBox getPagingBox() {
		return getFieldByClass(PagingBox.class);
	}

	public PreviousButton getPreviousButton() {
		return getFieldByClass(PreviousButton.class);
	}

	@Order(10.0)
	public class MainBox extends AbstractGroupBox {

		@Order(10.0)
		public class PagingBox extends AbstractGroupBox {

			@Override
			protected int getConfiguredGridColumnCount() {
				return 3;
			}

			@Override
			protected String getConfiguredLabel() {
				return TEXTS.get("Paging");
			}

			@Order(10.0)
			public class HierarchyField extends AbstractLabelField {

				@Override
				protected int getConfiguredGridW() {
					return 3;
				}

				@Override
				protected boolean getConfiguredLabelVisible() {
					return false;
				}

			}

			@Order(20.0)
			public class InformationField
					extends
						AbstractTableField<InformationField.Table> {

				@Override
				protected int getConfiguredGridH() {
					return 8;
				}

				@Override
				protected int getConfiguredGridW() {
					return 3;
				}

				@Override
				protected boolean getConfiguredLabelVisible() {
					return false;
				}

				public class Table extends AbstractExtensibleTable {

					public ValueColumn getValueColumn() {
						return getColumnSet().getColumnByClass(
								ValueColumn.class);
					}

					public InformationColumn getInformationColumn() {
						return getColumnSet().getColumnByClass(
								InformationColumn.class);
					}

					@Override
					protected boolean getConfiguredHeaderVisible() {
						return false;
					}

					@Override
					protected boolean getConfiguredAutoResizeColumns() {
						return true;
					}

					@Order(10.0)
					public class InformationColumn extends AbstractStringColumn {

					}

					@Order(20.0)
					public class ValueColumn extends AbstractStringColumn {

					}

				}
			}

			@Order(30.0)
			public class PreviousButton extends AbstractButton {

				@Override
				protected int getConfiguredDisplayStyle() {
					return DISPLAY_STYLE_LINK;
				}

				@Override
				protected String getConfiguredFont() {
					return "BOLD-14";
				}

				@Override
				protected int getConfiguredHorizontalAlignment() {
					return 1;
				}

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Previous");
				}

				@Override
				protected String getConfiguredLabelFont() {
					return "14";
				}

				@Override
				protected boolean getConfiguredProcessButton() {
					return false;
				}

				@Override
				protected int getConfiguredWidthInPixel() {
					return 50;
				}

				@Override
				protected void execClickAction() throws ProcessingException {
					int prevPage = Integer.parseInt(getPageField().getValue()) - 1;
					page.setCurrentPage(prevPage);
				}
			}

			@Order(40.0)
			public class PageField extends AbstractLabelField {

				@Override
				protected String getConfiguredFont() {
					return "BOLD";
				}

				@Override
				protected int getConfiguredHorizontalAlignment() {
					return 0;
				}

				@Override
				protected String getConfiguredLabelFont() {
					return "14";
				}

				@Override
				protected boolean getConfiguredLabelVisible() {
					return false;
				}

				@Override
				protected int getConfiguredWidthInPixel() {
					return 50;
				}

			}

			@Order(50.0)
			public class NextButton extends AbstractButton {

				@Override
				protected int getConfiguredDisplayStyle() {
					return DISPLAY_STYLE_LINK;
				}

				@Override
				protected String getConfiguredFont() {
					return "BOLD-14";
				}

				@Override
				protected String getConfiguredLabel() {
					return TEXTS.get("Next");
				}

				@Override
				protected String getConfiguredLabelFont() {
					return "14";
				}

				@Override
				protected boolean getConfiguredProcessButton() {
					return false;
				}

				@Override
				protected int getConfiguredWidthInPixel() {
					return 50;
				}

				@Override
				protected void execClickAction() throws ProcessingException {
					int nextPage = Integer.parseInt(getPageField().getValue()) + 1;
					page.setCurrentPage(nextPage);
				}
			}
		}

		@Order(20.0)
		public class FormClosingButton extends AbstractCloseButton {

		}

	}

	public class NewHandler extends AbstractFormHandler {
	}
}
