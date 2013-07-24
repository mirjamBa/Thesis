package de.hsrm.perfunctio.core.server.services;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.scout.commons.IOUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;

import de.hsrm.perfunctio.core.shared.services.IImageProcessService;

public class ImageProcessService extends AbstractService implements IImageProcessService {

	@Override
	public byte[] getImage(String imageId) throws ProcessingException {
		try {
			return IOUtility.getContent(imageId);
		} catch (ProcessingException e) {
			// nop
		}
		return null;
	}
	
	@Override
	public byte[] scaleImage(byte[] content, int height) {
		//FIXME not only png
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
		} catch (IOException e) {
			// LOG.warn("Could not scale image.", e);
		}
		return content;
	}
}
