package de.gruppe6.hotel.frontend.base.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.gruppe6.hotel.util.Log;

public class Gruppe6JPictureBox extends Gruppe6JComponent implements IGruppe6JObject {

	@java.io.Serial
	private static final long serialVersionUID = -4843288660769954480L;
	private final Dimension dimension = new Dimension(100, 100);

	private int iHeight, iWidth, posX = 0, posY = 0;

	private Image image = null;
	private SizeMode sizeMode = SizeMode.ZOOM;

	public Gruppe6JPictureBox() {
		setPreferredSize(dimension);
		setOpaque(false);
	}

	public Gruppe6JPictureBox(String location) {
		this();
		location = location.toLowerCase();
		try {
			if (location != null && !location.isBlank())
				setImage(ImageIO.read(this.getClass().getResource(location)));
		} catch (Exception e) {
			Log.sendMessage(location);
			Log.sendException(e);
		}
	}

	public Gruppe6JPictureBox(Image image) {

		this();
		if (image != null)
			setImage(image);
	}

	@Override
	public void paintComponent(Graphics g) {

		if (image == null)
			return;

		switch (getSizeMode()) {
		case NORMAL, ORIGINAL:
			setOriginal();
			break;
		case ZOOM:
			aspectRatio();
			setCenterPosition();
			break;
		case STRETCH:
			setStretched();
			break;
		case CENTER:
			setOriginal();
			setCenterPosition();
			break;
		default:
			setStretched();
			break;
		}
		g.setColor(Color.GREEN);
		while (!g.drawImage(image, posX, posY, iWidth, iHeight, null))
			;
	}

	public void setColored(String res, Color color) {
		try {
			setColored(ImageIO.read(getClass().getResource(res)), color);
		} catch (IOException e) {
			Log.sendException(e);
		}
	}

	public void setColored(BufferedImage image, Color color) {
		BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D gr = img.createGraphics();
		gr.setXORMode(color);
		gr.drawImage(image, null, 0, 0);
		gr.dispose();
		setImage(img);
	}

	public Image getImage() {

		return image;
	}

	public SizeMode getSizeMode() {

		return sizeMode;
	}

	public void setImage(String path) throws IOException {
		this.image = ImageIO.read(this.getClass().getResource(path.toLowerCase()));
		setOriginal();
	}

	public void setImage(Image image) {

		this.image = image;
		setOriginal();
	}

	public void setSizeMode(SizeMode sizeMode) {

		this.sizeMode = sizeMode;
	}

	public void setOriginal() {
		this.setSize(iWidth, iHeight);
		iHeight = image.getHeight(getFocusCycleRootAncestor());
		iWidth = image.getWidth(getFocusCycleRootAncestor());
		posX = 0;
		posY = 0;
	}

	public void setStretched() {
		iHeight = this.getHeight();
		iWidth = this.getWidth();
	}

	public void setCenterPosition() {

		posX = this.getWidth() / 2 - iWidth / 2;
		posY = this.getHeight() / 2 - iHeight / 2;

	}

	@Override
	public void refresh() {

		validate();
		repaint();
	}

	private void aspectRatio() {
		if (image == null)
			return;
		int height = 1080, width = 1920;
		if (this.getHeight() != 0)
			height = this.getHeight();
		else if (getParent().getHeight() != 0)
			height = getParent().getHeight();

		if (this.getWidth() != 0)
			width = this.getWidth();
		else if (getParent().getWidth() != 0)
			width = getParent().getWidth();

		if (height <= width) {
			iWidth = Math.round((iWidth * height) / iHeight);
			iHeight = height;
		} else {
			iHeight = Math.round((iHeight * width) / iWidth);
			iWidth = width;
		}
	}
}