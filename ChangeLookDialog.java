package Lifestyle.gui;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.net.*;
import BLogic.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class ChangeLookDialog extends JDialog implements ActionListener, ListSelectionListener{

	JRadioButton rb_metal = new JRadioButton("Metal Looks");
	JRadioButton rb_Nonmetal = new JRadioButton("Non Metal Looks");
	String metals[] = new String [] {"Oceain Blue", "Default Look", "Fancy Theme", "White Satin", "Moody Blue"};
	String nonmetals[] = new String [] {"Windows", "Windows Classic", "Motif", "System Look"};
	JList looks = new JList();
	JScrollPane sp = new JScrollPane(looks);
	JButton b_ok = new JButton("Ok");
	JButton b_cancel = new JButton("Cancel");
	JPanel p_center = new JPanel(),
		p_rbs = new JPanel(),
		p_buttons = new JPanel();
	JFrame f;
	ButtonGroup bg = new ButtonGroup();
	LookAndFeel oldLook;

	public ChangeLookDialog(JFrame f) {
		oldLook = UIManager.getLookAndFeel();
		this.f = f;
		setModal(true);
		setTitle("Change Look");
		bg.add(rb_metal);
		bg.add(rb_Nonmetal);
		p_rbs.add(rb_metal);
		p_rbs.add(rb_Nonmetal);
		p_buttons.add(b_ok);
		p_buttons.add(b_cancel);
		p_center.setLayout(new BorderLayout(10,10));
		p_center.add(sp);
		p_center.add(p_rbs, BorderLayout.NORTH);
		p_center.add(p_buttons, BorderLayout.SOUTH);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		setResizable(false);
		add(p_center);
		rb_metal.addActionListener(this);
		rb_Nonmetal.addActionListener(this);
		b_ok.addActionListener(this);
		b_cancel.addActionListener(this);
		looks.addListSelectionListener(this);
		pack();
		getRootPane().setDefaultButton(b_ok);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(b_ok)) {
			setVisible(false);
			dispose();
		}
		if(e.getSource().equals(b_cancel)) {
		try{
			UIManager.setLookAndFeel(oldLook);}catch(Exception ex){}
			setVisible(false);
			dispose();
			
		}
		if(rb_metal.isSelected()) {
			looks.setListData(metals);
		} else {
			looks.setListData(nonmetals);
		}
		
	}
	
	public void changeToMetal() throws Exception{
		switch(looks.getSelectedIndex()) {
			case 0:
				MetalLookAndFeel.setCurrentTheme(new OceanTheme());
				break;
			case 1:
				MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
				break;
			case 2:
				MetalLookAndFeel.setCurrentTheme(new MyTheme());
				break;
			case 3:
				MetalLookAndFeel.setCurrentTheme(new WhiteSatinTheme());
				break;
			case 4:
				MetalLookAndFeel.setCurrentTheme(new MoodyBlueTheme());
				break;
		}
		UIManager.setLookAndFeel(new MetalLookAndFeel());
	}
	
	public void changeToNonMetal() throws Exception{
		switch(looks.getSelectedIndex()) {
			case 0:
				UIManager.setLookAndFeel(new WindowsLookAndFeel());
				break;
			case 1:
				UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
				break;
			case 2:
				UIManager.setLookAndFeel(new MotifLookAndFeel());
				break;
			case 3:
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				break;
		}
		
	}
	
	public void valueChanged(ListSelectionEvent e){
		try {
			if(rb_metal.isSelected()) {
				changeToMetal();
			} else {
				changeToNonMetal();
			}
			SwingUtilities.updateComponentTreeUI(f);
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex, "Error", 0);
		}
	}
}