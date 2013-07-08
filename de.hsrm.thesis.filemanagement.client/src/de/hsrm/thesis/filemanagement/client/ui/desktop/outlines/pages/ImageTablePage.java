package de.hsrm.thesis.filemanagement.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.Icons;
import de.hsrm.thesis.filemanagement.shared.services.IFileService;
import de.hsrm.thesis.filemanagement.shared.services.code.FileTypeCodeType;
import de.hsrm.thesis.filemanagement.shared.services.formdata.FileSearchFormData;

public class ImageTablePage extends FilesAndFoldersTablePage {

	private int m_imageHeight;
	
	public ImageTablePage(Long folderId, int imageHeight){
		super(folderId);
		m_imageHeight = imageHeight;
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.Images;
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ImageGallery");
	}

	@Override
	protected Object[][] execLoadTableData(SearchFilter filter)
			throws ProcessingException {
		((FileSearchFormData) filter.getFormData()).getFileType().setValue(
				FileTypeCodeType.ImageCode.ID);

		Object[][] files = SERVICES.getService(IFileService.class).getFiles(
				(FileSearchFormData) filter.getFormData());
		return files;
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

//		@Order(50.0)
//		public class ImageColumn extends FilePathColumn {
//
//			@Override
//			protected boolean getConfiguredDisplayable() {
//				return true;
//			}
//
//			@Override
//			protected String getConfiguredHeaderText() {
//				return TEXTS.get("FilePath");
//			}
//
//			@Override
//			protected boolean getConfiguredVisible() {
//				return true;
//			}
//
//			@Override
//			protected int getConfiguredWidth() {
//				return 400;
//			}
//
//			@Override
//			protected void execDecorateCell(Cell cell, ITableRow row)
//					throws ProcessingException {
//				String imageId = getValue(row);
//				if (!Activator.getDefault().isImageCached(imageId)) {
//					// load image content by image id
//					byte[] content = SERVICES.getService(
//							IImageProcessService.class).getImage(imageId);
//					byte[] scaledImage = SERVICES.getService(
//							IImageProcessService.class).scaleImage(content,
//							m_imageHeight);
//					Activator.getDefault().cacheImage(imageId, scaledImage);
//				}
//				cell.setIconId(imageId);
//				cell.setText(null);
//			}
//		}

	}

	@FormData
	public int getImageHeight() {
		return m_imageHeight;
	}

	@FormData
	public void setImageHeight(int imageHeight) {
		m_imageHeight = imageHeight;
	}
}
