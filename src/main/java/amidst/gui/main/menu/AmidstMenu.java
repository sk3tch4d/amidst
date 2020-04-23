package amidst.gui.main.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.MenuElement;

import amidst.documentation.AmidstThread;
import amidst.documentation.CalledOnlyBy;
import amidst.documentation.NotThreadSafe;
import amidst.gui.main.viewer.ViewerFacade;

@NotThreadSafe
public class AmidstMenu {
	private final JMenuBar menuBar;
	private final JMenu worldMenu;
	private final JMenuItem savePlayerLocationsMenu;
	private final JMenuItem reloadPlayerLocationsMenu;
	private final LayersMenu layersMenu;

	@CalledOnlyBy(AmidstThread.EDT)
	public AmidstMenu(
			JMenuBar menuBar,
			JMenu worldMenu,
			JMenuItem savePlayerLocationsMenu,
			JMenuItem reloadPlayerLocationsMenu,
			LayersMenu layersMenu) {
		this.menuBar = menuBar;
		this.worldMenu = worldMenu;
		this.savePlayerLocationsMenu = savePlayerLocationsMenu;
		this.reloadPlayerLocationsMenu = reloadPlayerLocationsMenu;
		this.layersMenu = layersMenu;
	}

	@CalledOnlyBy(AmidstThread.EDT)
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
	@CalledOnlyBy(AmidstThread.EDT)
	public void setMenuItemsEnabled(String[] textRepresentations, boolean enabled) {
		setMenuItemsEnabled(menuBar, textRepresentations, enabled);
	}
	
	private static void setMenuItemsEnabled(MenuElement menuElement, String[] textRepresentations, boolean enabled) {
		MenuElement[] elements = menuElement.getSubElements();
		if(elements != null) {
			for(MenuElement element : elements) {
				if(element instanceof JMenuItem) {
					for(String s : textRepresentations) {
						if(((JMenuItem) element).getText().equals(s)) {
							((JMenuItem) element).setEnabled(enabled);
						}
					}
				}
				setMenuItemsEnabled(element, textRepresentations, enabled);
			}
		}
	}

	@CalledOnlyBy(AmidstThread.EDT)
	public void set(ViewerFacade viewerFacade) {
		worldMenu.setEnabled(true);
		savePlayerLocationsMenu.setEnabled(viewerFacade.canSavePlayerLocations());
		reloadPlayerLocationsMenu.setEnabled(viewerFacade.canLoadPlayerLocations());
		layersMenu.init(viewerFacade);
	}

	@CalledOnlyBy(AmidstThread.EDT)
	public void clear() {
		worldMenu.setEnabled(false);
		savePlayerLocationsMenu.setEnabled(false);
		reloadPlayerLocationsMenu.setEnabled(false);
		layersMenu.disable();
	}
}
