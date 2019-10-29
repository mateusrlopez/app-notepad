package model.entities;

import javafx.scene.control.MenuItem;

public class FileMenuItem extends MenuItem {
	public FileMenuItem(String text) {
		super(text);
	}
	
	@Override 
	public boolean equals(Object o) {
		if(o instanceof FileMenuItem)
			return ((FileMenuItem)o).getText().equalsIgnoreCase(getText());
		return false;
	}
}
