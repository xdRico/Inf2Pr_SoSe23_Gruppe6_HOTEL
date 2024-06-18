package de.gruppe6.hotel.backend.base.enviroment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.base.io.IOutput;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPictureBox;

import javax.swing.*;

/**
 * Game map class for the game map
 *
 *
 */
public class GameMap extends Gruppe6JPanel {

	@java.io.Serial
	private static final long serialVersionUID = 2029703928641514204L;

	protected List<MapField> fields = new ArrayList<MapField>();
	protected List<Property> properties = new ArrayList<Property>();
	protected int maxPlayers = 4;
	protected boolean isLocked = false;
	protected Gruppe6JPictureBox pictureBox;
	protected IOutput output;

	public GameMap(IOutput output) {
		this.output = output;
	}

	public void init(Graphics g) {
		if (getParent() == null) {
			throw new IllegalStateException("No Parent set");
		}
		this.setLayout(new OverlayLayout(this));
		this.setPreferredSize(getParent().getSize());
		this.setBackground(Color.GREEN);

	}

	/**
	 * Method to lock the game map
	 */
	public void start() {
		isLocked = true;
		for (MapField field : fields) {
			field.lock();
		}
		output.startMap(this);

	}

	public Gruppe6JPictureBox getPictureBox() {
		return pictureBox;
	}

	/**
	 * Method getField that you can't go over the game map(fields)
	 * 
	 * @param field - field are the game fields
	 * @return int-if the player goes over the map
	 */

	public MapField getField(int field) {
		while (field >= fields.size())
			field -= fields.size();
		return fields.get(field);
	}

	/**
	 * method of index field
	 * 
	 * @param field- map field
	 * @return int-Index of the field
	 */
	public int indexOf(MapField field) {
		return fields.indexOf(field);
	}

	/**
	 * method for the fields list
	 * 
	 * @return - fields
	 */
	public List<MapField> getFields() {
		return fields;
	}

	/**
	 * a list properties
	 * 
	 * @return
	 */

	public List<Property> getProperties() {
		return properties;
	}

	/**
	 * method for max players 4
	 * 
	 * @return int-maxPlayers
	 */
	public int maxPlayers() {
		return maxPlayers;
	}
}
