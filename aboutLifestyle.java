package Lifestyle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import BLogic.*;

class aboutLifestyle extends JInternalFrame
{

	JLabel
		l_history = new JLabel("<html><b><center>Lifestyle is part of the Landmark Group, a Dubai-based retail chain.With over 30 years’ experience in retailing, the Group has become the foremost retailer in the Gulf.<br><br> Positioned as a trendy, youthful and vibrant brand that offers customers a wide variety of merchandise at exceptional value for money, Lifestyle began operations in 1998 with its first store in Chennai in 1999 and now has 13 Lifestyle stores, 5 Home Centres and 1 Babyshop store across Chennai, Hyderabad, Bangalore, Gurgaon, Delhi, Mumbai and Ahmedabad.<br><br>Lifestyle has also been awarded the ICICI-KSA Technopak Award for Retail Excellence in 2005, the Reid & Taylor Retailer of the Year Award for 2006 and more recently, the Lycra Images Fashion Award for the Most Admired Large Format Retailer of the Year in 2006.");

	public aboutLifestyle()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((dim.width-400)/2,(dim.height-400)/2,400,400);

		setLayout(new BorderLayout());
		setTitle("About Lifestyle History");
		setIconifiable(true);
		setClosable(true);
		setVisible(true);

		addControls();
	}
	public void addControls()
	{
		l_history.setFont(new Font("Verdana",Font.PLAIN,12));
		add(l_history);
	}
}
