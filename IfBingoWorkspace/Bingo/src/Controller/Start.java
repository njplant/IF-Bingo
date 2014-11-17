package Controller;

import View.StartMenu;

public class Start {
	public static void main(String[] args) {
		StartMenu view = new StartMenu();
		StartMenuCont cont = new StartMenuCont(view);
	}
}
