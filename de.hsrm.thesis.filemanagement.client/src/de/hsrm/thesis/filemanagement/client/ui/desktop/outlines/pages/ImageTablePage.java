package de.hsrm.thesis.filemanagement.client.ui.desktop.outlines.pages;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.client.Activator;
import de.hsrm.thesis.filemanagement.client.PagingForm;
import de.hsrm.thesis.filemanagement.client.PagingForm.MainBox.PagingBox.InformationField;
import de.hsrm.thesis.filemanagement.shared.Icons;
import de.hsrm.thesis.filemanagement.shared.beans.ColumnSpec;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.IFolderService;
import de.hsrm.thesis.filemanagement.shared.services.IImageProcessService;
import de.hsrm.thesis.filemanagement.shared.services.IMetadataService;
import de.hsrm.thesis.filemanagement.shared.services.IStorageService;
import de.hsrm.thesis.filemanagement.shared.services.ITikaService;
import de.hsrm.thesis.filemanagement.shared.services.code.DatatypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FileSearchFormData;
import de.hsrm.thesis.filemanagement.shared.utility.ArrayUtility;

public class ImageTablePage extends FilesAndFoldersTablePage {

	private int m_imageHeight;
	private PagingForm m_pagingForm;
	private int m_maxRowCount;
	private int m_currentPage;
	private int m_maxPage;

	public ImageTablePage(Long folderId, int imageHeight)
			throws ProcessingException {
		super(folderId);
		m_imageHeight = imageHeight;
		m_pagingForm = new PagingForm(this);
		m_maxRowCount = 3;
		m_currentPage = 1;
		m_pagingForm.getPageField().setValue("" + m_currentPage);
		m_pagingForm.getPreviousButton().setEnabled(false);
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.Images;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ImageGallery");
	}

	private void setPageData() throws ProcessingException {
		int size = SERVICES.getService(IFileService.class).getNumberOfFiles(
				FileTypeCodeType.ImageCode.ID);
		m_maxPage = (int) Math.ceil(size * 1.0 / m_maxRowCount);
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		((FileSearchFormData) filter.getFormData()).getFileType().setValue(
				FileTypeCodeType.ImageCode.ID);

		Object[][] fileTableData = SERVICES.getService(IFileService.class)
				.getFiles((FileSearchFormData) filter.getFormData(),
						m_maxRowCount, m_currentPage);

		Long[] ids = extractIdsFromTableData(fileTableData);

		// dynamic column data
		Object[][] dynamicColumnData = SERVICES.getService(
				IMetadataService.class).getMetadataForFiles(ids,
				IStorageService.META_FILEPATH);

		// concat standard and dynamic column data
		Object[][] allTableData = ArrayUtility.concatArrays(fileTableData,
				dynamicColumnData);

		ColumnSpec[] columnSpecs = new ColumnSpec[1];
		columnSpecs[0] = new ColumnSpec(
				((Integer) IStorageService.META_FILEPATH.hashCode()).toString(),
				IStorageService.META_FILEPATH, DatatypeCodeType.StringCode.ID);

		updateDynamicColumns(columnSpecs);
		setPageData();

		return allTableData;
	}

	protected void updateDynamicColumns(ColumnSpec[] columnSpecs) {
		if (columnSpecs.length == 0) {
			return;
		}
		FilesAndFoldersTable table = getTable();
		m_injectedColumns = new ArrayList<IColumn<?>>();
		for (ColumnSpec spec : columnSpecs) {
			m_injectedColumns.add(createDynamicImageStringColumn(spec.getId(),
					spec.getText()));
		}
		table.resetColumnConfiguration();
	}

	private IColumn<?> createDynamicImageStringColumn(final String columnId,
			final String label) {
		return new AbstractStringColumn() {
			@Override
			protected String getConfiguredHeaderText() {
				return label;
			}

			@Override
			public String getColumnId() {
				return columnId;
			}

			@Override
			protected int getConfiguredWidth() {
				return 120;
			}

			@Override
			protected String execParseValue(ITableRow row, Object rawValue)
					throws ProcessingException {
				return super.execParseValue(row, rawValue);
			}

			@Override
			protected void execDecorateCell(Cell cell, ITableRow row)
					throws ProcessingException {
				String imageId = getValue(row);
				if (!Activator.getDefault().isImageCached(imageId)) {
					// load image content by image id
					byte[] content = SERVICES.getService(
							IImageProcessService.class).getImage(imageId);
					byte[] scaledImage = SERVICES.getService(
							IImageProcessService.class).scaleImage(content,
							m_imageHeight);
					Activator.getDefault().cacheImage(imageId, scaledImage);
				}
				cell.setIconId(imageId);
				cell.setText(null);
			}

		};
	}

	@Order(10.0)
	public class ImageTable extends FilesAndFoldersTable {

		@Override
		protected boolean getConfiguredHeaderVisible() {
			return false;
		}

		@Override
		protected boolean getConfiguredMultilineText() {
			return true;
		}

		@Override
		protected void execInitTable() throws ProcessingException {
			getTypistColumn().setVisible(false);
		}

		@Override
		protected int getConfiguredRowHeightHint() {
			return 150;
		}

		@Override
		protected void execRowClick(ITableRow row) throws ProcessingException {
			m_pagingForm.getHierarchyField()
					.setValue(
							SERVICES.getService(IFolderService.class)
									.getFolderHierarchy(
											getFileFolderIdColumn()
													.getSelectedValue()));

			Map<String, String> data = SERVICES.getService(ITikaService.class)
					.extractDataFromFile(
							SERVICES.getService(IFileService.class)
									.getServerFile(
											getFileFolderIdColumn()
													.getSelectedValue()));

			InformationField.Table table = m_pagingForm.getInformationField()
					.getTable();
			table.deleteAllRows();
			for (String key : data.keySet()) {
				if (data.get(key) != null) {
					ITableRow newRow = table.createRow();
					table.getInformationColumn().setValue(newRow, key);
					table.getValueColumn().setValue(newRow,
							data.get(key).toString());
					table.addRow(newRow);
				}
			}

			if (!m_pagingForm.isFormOpen()) {
				m_pagingForm.startNew();
			}
		}

	}

	@FormData
	public int getImageHeight() {
		return m_imageHeight;
	}

	@FormData
	public void setImageHeight(int imageHeight) {
		m_imageHeight = imageHeight;
	}

	/**
	 * @return the m_currentPage
	 */
	public int getM_currentPage() {
		return m_currentPage;
	}

	/**
	 * @param currentPage
	 *            the m_currentPage to set
	 */
	public void setCurrentPage(int currentPage) throws ProcessingException {
		if (currentPage == m_maxPage) {
			m_pagingForm.getNextButton().setEnabled(false);
			m_pagingForm.getPreviousButton().setEnabled(true);
		} else if (currentPage == 1) {
			m_pagingForm.getPreviousButton().setEnabled(false);
			m_pagingForm.getNextButton().setEnabled(true);
		} else {
			m_pagingForm.getPreviousButton().setEnabled(true);
			m_pagingForm.getNextButton().setEnabled(true);
		}
		this.m_currentPage = currentPage;
		m_pagingForm.getPageField().setValue("" + currentPage);
		reloadPage();
	}

}
