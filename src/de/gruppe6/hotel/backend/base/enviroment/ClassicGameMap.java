package de.gruppe6.hotel.backend.base.enviroment;

import java.util.ArrayList;

import de.gruppe6.hotel.backend.base.io.IOutput;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPictureBox;
import de.gruppe6.hotel.util.Log;

public class ClassicGameMap extends GameMap {

	@java.io.Serial
	private static final long serialVersionUID = 5622798373358337979L;

	public ClassicGameMap(IOutput output) {
		super(output);
		pictureBox = new Gruppe6JPictureBox("/de/gruppe6/hotel/assets/enviroment/GameMap.png");
	}

	@Override
	public void start() {
		Log.sendMessage("Creating classic Map..!");
		add(new Gruppe6JPictureBox("/de/gruppe6/hotel/assets/enviroment/bank.png"));
		add(new Gruppe6JPictureBox("/de/gruppe6/hotel/assets/enviroment/city_hall.png"));
		fields = new ArrayList<MapField>();
		Property fujiyama = new Property("Fujiyama", 1000, 100, this);
		properties.add(fujiyama);
		int i = 1;
		fujiyama.addBuilding(fujiyama.getName() + "_" + i++, 2200, 100);
		fujiyama.addBuilding(fujiyama.getName() + "_" + i++, 1400, 100);
		fujiyama.addBuilding(fujiyama.getName() + "_" + i++, 1400, 200);
		fujiyama.addPark(fujiyama.getName() + "_Park", 500, 400);
		validate();
		repaint();
		Property boomerang = new Property("Boomerang", 500, 100, this);
		properties.add(boomerang);
		boomerang.addBuilding("Boomerang_1", 1800, 400);
		boomerang.addPark("Boomerang_Park", 250, 600);

		Property letoile = new Property("L'Etoile", 3000, 250, this);
		properties.add(letoile);
		i = 1;
		letoile.addBuilding(letoile.getName() + "_" + i++, 3300, 150);
		letoile.addBuilding(letoile.getName() + "_" + i++, 2200, 300);
		letoile.addBuilding(letoile.getName() + "_" + i++, 1800, 300);
		letoile.addBuilding(letoile.getName() + "_" + i++, 1800, 300);
		letoile.addBuilding(letoile.getName() + "_" + i++, 1800, 450);
		letoile.addPark(letoile.getName() + "_Park", 4000, 750);

		Property president = new Property("President", 3500, 250, this);
		properties.add(president);
		i = 1;
		president.addBuilding(president.getName() + "_" + i++, 5000, 200);
		president.addBuilding(president.getName() + "_" + i++, 3000, 400);
		president.addBuilding(president.getName() + "_" + i++, 2250, 600);
		president.addBuilding(president.getName() + "_" + i++, 1750, 800);
		president.addPark(president.getName() + "_Park", 5000, 1100);

		Property royal = new Property("Royal", 2500, 200, this);
		properties.add(royal);
		i = 1;
		royal.addBuilding(royal.getName() + "_" + i++, 3600, 150);
		royal.addBuilding(royal.getName() + "_" + i++, 2600, 300);
		royal.addBuilding(royal.getName() + "_" + i++, 1800, 300);
		royal.addBuilding(royal.getName() + "_" + i++, 1800, 450);
		royal.addPark(royal.getName() + "_Park", 3000, 600);

		Property waikiki = new Property("Waikiki", 2500, 200, this);
		properties.add(waikiki);
		i = 1;
		waikiki.addBuilding(waikiki.getName() + "_" + i++, 3500, 200);
		waikiki.addBuilding(waikiki.getName() + "_" + i++, 2500, 350);
		waikiki.addBuilding(waikiki.getName() + "_" + i++, 2500, 500);
		waikiki.addBuilding(waikiki.getName() + "_" + i++, 1750, 500);
		waikiki.addBuilding(waikiki.getName() + "_" + i++, 1750, 650);
		waikiki.addPark(waikiki.getName() + "_Park", 2500, 1000);

		Property tajmahal = new Property("Taj-Mahal", 1500, 100, this);
		properties.add(tajmahal);
		i = 1;
		tajmahal.addBuilding(tajmahal.getName() + "_" + i++, 2400, 100);
		tajmahal.addBuilding(tajmahal.getName() + "_" + i++, 1000, 100);
		tajmahal.addBuilding(tajmahal.getName() + "_" + i++, 500, 200);
		tajmahal.addPark(tajmahal.getName() + "_Park", 1000, 300);

		Property safari = new Property("Safari", 2000, 150, this);
		properties.add(safari);
		i = 1;
		safari.addBuilding(safari.getName() + "_" + i++, 2600, 100);
		safari.addBuilding(safari.getName() + "_" + i++, 1200, 100);
		safari.addBuilding(safari.getName() + "_" + i++, 1200, 250);
		safari.addPark(safari.getName() + "_Park", 2000, 500);

		for (i = 0; i < 33; i++) {
			MapFieldType type;
			switch (i) {
			case 0:
				type = MapFieldType.StartField;
				break;
			case 7:
				type = MapFieldType.BankField;
				break;
			case 6, 19, 31:
				type = MapFieldType.FreeStairField;
				break;
			case 1, 3, 5, 8, 13, 15, 17, 20, 23, 26, 28, 29, 32:
				type = MapFieldType.BuildField;
				break;
			case 11, 25:
				type = MapFieldType.FreeBuildingField;
				break;
			case 27:
				type = MapFieldType.CityHallField;
				break;
			default:
				type = MapFieldType.BuyField;
				break;
			}
			fields.add(new MapField(i, type));

		}

		for (i = 1; i <= 6; i++) {
			fields.get(i).setProperty(fujiyama, FieldSide.right);
		}
		for (i = 2; i <= 5; i++) {
			fields.get(i).setProperty(boomerang, FieldSide.left);

		}
		for (i = 9; i <= 11; i++) {
			fields.get(i).setProperty(letoile, FieldSide.right);

		}
		for (i = 10; i <= 16; i++) {
			fields.get(i).setProperty(president, FieldSide.left);
		}
		for (i = 12; i <= 21; i++) {
			fields.get(i).setProperty(royal, FieldSide.right);

		}
		for (i = 17; i <= 21; i++) {
			fields.get(i).setProperty(waikiki, FieldSide.left);

		}
		for (i = 22; i <= 25; i++) {
			fields.get(i).setProperty(letoile, FieldSide.right);
		}
		for (i = 22; i <= 26; i++) {
			fields.get(i).setProperty(tajmahal, FieldSide.left);

		}
		for (i = 28; i <= 32; i++) {
			fields.get(i).setProperty(safari, FieldSide.left);

		}
		for (i = 30; i <= 31; i++) {
			fields.get(i).setProperty(letoile, FieldSide.right);
		}

		Log.sendMessage("Finished creating classic Map:");

		for (i = 0; i <= 32; i++)
			Log.sendMessage("[" + i + "] " + fields.get(i).toString());
		validate();
		// repaint();
		add(new Gruppe6JPictureBox("/de/gruppe6/hotel/assets/enviroment/mapfield.png"));
		super.start();
	}
}
