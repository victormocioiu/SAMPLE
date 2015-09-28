package mrui.custom.jmrui2xml;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

public class Cell extends JToggleButton {
	/**
	 * 
	 */
	private int x, y;
	private String label = new String("***");

	public Cell(int width, int height) {
		setSize(width, height);
		setBackground(Color.BLUE);
		setMargin(new Insets(0, 0, 0, 0));
		// setBorder(null);
	}

	public void setCoord(int x, int y) {

		this.x = x;
		this.y = y;

	}

	public int[] getCoord() {
		int[] coord = new int[2];
		coord[0] = this.x;
		coord[1] = this.y;
		return coord;

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	void addCellListener(ActionListener listenForMouse) {
		this.addActionListener(listenForMouse);
	}
}
