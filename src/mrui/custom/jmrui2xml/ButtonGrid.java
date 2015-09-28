package mrui.custom.jmrui2xml;

import java.awt.Color;
import java.awt.GridLayout; //imports GridLayout library
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
//imports JButton library

//imports JFrame library

@SuppressWarnings("serial")
public class ButtonGrid extends JPanel {
	private int width, height, px, py;// px,py retaiin the coordinates of
										// the
	// previously clicked cell
	private Cell[][] grid;
	private JTextField txtLabel;

	public ButtonGrid(int width, int height) {

		this.width = width;
		this.height = height;
		setSize(600, 600); // sets appropriate size for frame
		setLayout(new GridLayout(height, width)); // set layout
		grid = new Cell[width][height]; // allocate the size of grid

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				grid[x][y] = new Cell(600 / width, 600 / height);
				grid[x][y].setCoord(x, y);
				grid[x][y].addCellListener(new BtnListener(x, y));
				grid[x][y].setText(grid[x][y].getLabel());
				grid[x][y].setBackground(Color.WHITE);

				add(grid[x][y]); // adds button to grid
			}
		}

		setVisible(true); // makes frame visible

	}

	public JPanel externalPanel() {
		JPanel externalPanel = new JPanel();
		JLabel label = new JLabel("Label");
		label.setSize(40, 20);
		externalPanel.add(label);
		txtLabel = new JTextField("***");
		txtLabel.setSize(60, 20);
		externalPanel.add(txtLabel);
		externalPanel.setBounds(610, 90, 200, 100);
		externalPanel.setLayout(new GridLayout(2, 1));
		return externalPanel;

	}

	public String getText() {

		return txtLabel.getText();

	}

	public void setText(String input) {
		txtLabel.setText(input);

	};

	public Cell getCell(int x, int y) {
		return grid[x][y];

	}

	public void clearAllExceptInputs(int xi, int yj) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x != xi || y != yj) {

					grid[x][y].setSelected(false);

				}

			}
		}

	}

	public int[] getCurrentPos() {
		int[] temp = new int[2];
		temp[0] = px;
		temp[1] = py;
		return temp;

	}

	public void setLabel(int x, int y) {

		grid[x][y].setLabel(getText());

	}

	public String[][] getLabelz() {
		String[][] labelz = new String[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				labelz[x][y] = grid[x][y].getLabel();
			}
		}
		return labelz;
	}

	/**
	 * sets everything back the way it was before the first click, except the
	 * labels, of course
	 */
	public void goToDefault() {
		// setLabel(getCurrentPos()[0], getCurrentPos()[1]);
		// clearAllExceptInputs(90000, 90000);
		// this.px = 9999;
		// txtLabel.setText("");
	}

	class BtnListener implements ActionListener {
		int x, y;

		public BtnListener(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (grid[x][y].isSelected()) {

				if (x != px || y != py) {
					grid[px][py].setLabel(getText());
					grid[px][py].setText(getText());
				}

				clearAllExceptInputs(x, y);

				setText(grid[x][y].getLabel());
				px = x;
				py = y;

			}
			if (!grid[x][y].isSelected()) {

				if (x == px || y == py) {
					grid[x][y].setLabel(getText());
				}

				setText("");
			}

		}

	}

}