package ui;

import config.Setup;
import ui.styled.StyledCheckBox;
import ui.styled.ThemeDropDown;
import ui.themes.VisualType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//TODO split settings into ui and logic

/**
 * @author RohanSomani
 * @name Settings
 * @date 2026-01-02
 */
public class Settings extends JDialog {
	// BEFORE YOU WRITE ANYTHING ADD A DESCRIPTION!!

	final Window parent;

	final Runnable requestUpdate;

  private StyledCheckBox rainbowPath;

  private StyledCheckBox bestPathGen;

	/**
	 * initialize a settings dialog with a dropdown for theme selection and
	 * other options (?) TODO fix styling cuz wtf is going on
	 *
	 * @param parent
	 *            the parent of the dialog for it to be anchored to.
	 * @param requestUpdate
	 *            the function to call when an update has been made.
	 */
	public Settings(Window parent, Runnable requestUpdate, Runnable settingsClosed) {
		super(parent, "Settings");
		setBackground(Setup.getColor(VisualType.BACKGROUND));

		JPanel contentHolder = new JPanel();
		contentHolder.setBackground(Setup.getColor(VisualType.BACKGROUND));
		contentHolder.setLayout(new BoxLayout(contentHolder, BoxLayout.Y_AXIS));

		this.parent = parent;
		this.requestUpdate = requestUpdate;
		setupDialog();
		ThemeDropDown themeChooser = new ThemeDropDown(requestUpdate);
		contentHolder.add(themeChooser);

		contentHolder.add(Box.createVerticalStrut(12));
		
		rainbowPath = new StyledCheckBox("Draw Rainbow Path", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
		    toggleRainbowPath(); 
      }

		}
				);
    rainbowPath.setSelected(Setup.drawRainbowPath);
		contentHolder.add(rainbowPath);
		
		
		contentHolder.add(Box.createVerticalStrut(12));
		
		bestPathGen = new StyledCheckBox("Use Best Path Generation (slower)", new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e){
        toggleBestPathGen(); 
      }

    });
    bestPathGen.setSelected(Setup.useAStar);
		contentHolder.add(bestPathGen);
		
		contentHolder.add(Box.createVerticalStrut(12));
		
		StyledCheckBox invincible = new StyledCheckBox("ARE YOU INVINCIBLE?? (does nothing)", null);
		contentHolder.add(invincible);
		
		contentHolder.add(Box.createVerticalStrut(12));
		

		contentHolder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closing(settingsClosed);
			}
		});

		add(contentHolder);
		
		pack();

	}


  private void setupDialog() {
		setAlwaysOnTop(true);
		setResizable(true);
		setPreferredSize(new Dimension(400, 400));
		setLocationRelativeTo(parent);
		setBackground(Setup.getColor(VisualType.BACKGROUND));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private void closing(Runnable settingsClosed) {
		settingsClosed.run();
		this.dispose();
	}

  private void toggleRainbowPath(){
    Setup.drawRainbowPath = rainbowPath.isSelected(); 
    requestUpdate.run(); 
  }
	
	protected void toggleBestPathGen() {
    Setup.useAStar = bestPathGen.isSelected();  
  }
	

}
