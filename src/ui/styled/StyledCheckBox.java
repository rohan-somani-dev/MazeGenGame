/*
* Date: Jan 16, 2026
* Name: StyledCheckBox
* Description: 
* Author: RohanSomani
*/

package ui.styled;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Setup;
import ui.themes.VisualType;
import utilities.ImageUtils;

public class StyledCheckBox extends JPanel {

	private final JLabel label;
	private final JCheckBox checkBox;

	public StyledCheckBox(String text, ActionListener actionListener) {
		setLayout(new BorderLayout());

		setBackground(Setup.getColor(VisualType.BACKGROUND));

		label = new JLabel(text);
		label.setFont(Setup.REGULAR);
		label.setForeground(Setup.getColor(VisualType.WALL));

		checkBox = new JCheckBox();
		checkBox.setBackground(Setup.getColor(VisualType.BACKGROUND));
		checkBox.setForeground(Setup.getColor(VisualType.WALL));
		checkBox.addActionListener(actionListener);
		
		try {
			initIcons();
		} catch (IOException e) {
			Setup.handleError(e);
		}

		add(label, BorderLayout.WEST);
		add(checkBox, BorderLayout.EAST);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkBox.setSelected(!checkBox.isSelected());
			}
		});

		this.setMaximumSize(new Dimension(1000, 40));

	}

	public void initIcons() throws IOException {
		ImageIcon selected = ImageUtils.setupIcon("resources/icons/selected.png", 40,
				Setup.getColor(VisualType.WALL));
		checkBox.setSelectedIcon(selected);

		ImageIcon unchecked = ImageUtils.setupIcon("resources/icons/unchecked_box.png", 40,
				Setup.getColor(VisualType.WALL));
		checkBox.setIcon(unchecked);
	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}

	public void setSelected(boolean b) {
		checkBox.setSelected(b);
	}
	
	

}
