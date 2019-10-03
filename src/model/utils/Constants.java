package model.utils;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public interface Constants {
	KeyCombination NEW_COMB = new KeyCodeCombination(KeyCode.N,KeyCodeCombination.CONTROL_DOWN);
	KeyCombination SAVE_COMB = new KeyCodeCombination(KeyCode.S,KeyCodeCombination.CONTROL_DOWN);
	KeyCombination SAVE_AS_COMB = new KeyCodeCombination(KeyCode.S,KeyCodeCombination.CONTROL_DOWN,KeyCodeCombination.SHIFT_DOWN);
	KeyCombination OPEN_COMB = new KeyCodeCombination(KeyCode.O,KeyCodeCombination.CONTROL_DOWN);
	String INITIAL_DIRECTORY = String.format("C:\\Users\\%s\\Documents", System.getProperty("user.name"));
}
