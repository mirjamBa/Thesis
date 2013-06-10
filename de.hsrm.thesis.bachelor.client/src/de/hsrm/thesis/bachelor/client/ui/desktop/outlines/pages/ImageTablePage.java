package de.hsrm.thesis.bachelor.client.ui.desktop.outlines.pages;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.shared.IFileService;
import de.hsrm.thesis.bachelor.shared.services.ILogoProcessService;

public class ImageTablePage extends FileTablePage {

  public static byte[] scaleImageToHeight(byte[] content, int height) {
    final String fileExtension = "png";
    if (content == null || height <= 0 || StringUtility.isNullOrEmpty(fileExtension)) {
      return content;
    }
    try {
      BufferedImage image = ImageIO.read(new ByteArrayInputStream(content));
      if (image == null) {
        return content;
      }
      int originalWidth = image.getWidth();
      int originalHeight = image.getHeight();
      if (originalHeight != height) {
        float fl = (float) height / originalHeight;

        AffineTransform tx = new AffineTransform();
        tx.scale(fl, fl);

        int newWidth = (int) (fl * originalWidth);
        int newHeight = (int) (fl * originalHeight);

        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawImage(image, tx, null);
        g.dispose();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(scaledImage, fileExtension, bos);
        return bos.toByteArray();
      }
    }
    catch (IOException e) {
//      LOG.warn("Could not scale image.", e);
    }
    return content;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("LogoTable");
  }

  @Override
  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
    return SERVICES.getService(IFileService.class).getImages();
//    return SERVICES.getService(ILogoProcessService.class).getLogoTableData();
  }

  @Order(10.0)
  public class ImageTable extends FileTable {

    @Override
    protected boolean getConfiguredMultilineText() {
      return true;
    }

    @Order(50.0)
    public class ImageColumn extends FilePathColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("FilePath");
      }

      @Override
      protected boolean getConfiguredVisible() {
        return true;
      }

      @Override
      protected int getConfiguredWidth() {
        return 400;
      }

      @Override
      protected void execDecorateCell(Cell cell, ITableRow row) throws ProcessingException {
        String imageId = getValue(row);
        if (!de.hsrm.thesis.bachelor.client.Activator.getDefault().isImageCached(imageId)) {
          // load image content by image id
          byte[] content = SERVICES.getService(ILogoProcessService.class).getLogo(imageId);
          byte[] scaledImage = scaleImageToHeight(content, 250);
          de.hsrm.thesis.bachelor.client.Activator.getDefault().cacheImage(imageId, scaledImage);
        }
        cell.setIconId(imageId);
        cell.setText(null);
      }
    }

  }
}
