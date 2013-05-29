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
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.bachelor.shared.services.ILogoProcessService;

public class LogoTableTablePage extends AbstractPageWithTable<LogoTableTablePage.Table> {

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
    return SERVICES.getService(ILogoProcessService.class).getLogoTableData();
  }

  @Order(10.0)
  public class Table extends AbstractTable {

    @Override
    protected boolean getConfiguredMultilineText() {
      return true;
    }

    public LogoColumn getLogoColumn() {
      return getColumnSet().getColumnByClass(LogoColumn.class);
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    @Order(10.0)
    public class NameColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Name");
      }

      @Override
      protected int getConfiguredWidth() {
        return 160;
      }
    }

    @Order(20.0)
    public class LogoColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Logo");
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
          de.hsrm.thesis.bachelor.client.Activator.getDefault().cacheImage(imageId, content);
        }
        cell.setIconId(imageId);
        cell.setText(null);
      }
    }
  }
}
