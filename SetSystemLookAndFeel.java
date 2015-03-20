package ptyxiakh;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author kostas
 */
public class SetSystemLookAndFeel extends UIManager{
	/** 
	 * Gives to program the appearance of system.
	 * 
	 * throws Exception if the look and feel is unsupported.
	 * throws Exception if found an error during loading.
	 */
	public SetSystemLookAndFeel() {
		String laf = getSystemLookAndFeelClassName();
		
		try {
			setLookAndFeel(laf);
		} catch (UnsupportedLookAndFeelException exc) {
			System.err.println("Warning: UnsupportedLookAndFeel: " + laf);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException exc) {
			System.err.println("Error loading " + laf + ": " + exc);
		}
	}
}