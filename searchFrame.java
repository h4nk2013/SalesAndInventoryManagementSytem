package Lifestyle.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

public class searchFrame extends JInternalFrame
{
	
	JTabbedPane
		tp = new JTabbedPane();	
	
	public searchFrame()
	{
		setSize(1015,650);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setLayout(new BorderLayout());
		setTitle("Searching");		
		
		addControls();
		setVisible(true);
	}


	public void addControls()
	{
		//==========tab_pane Panel=========
		
		tp.add("Search By Product Name",new search_pname());
		tp.add("Search By Department Name",new search_dname());
		tp.add("Search By Product ID",new search_pid());	
		tp.add("Search By Supplier Name",new search_supplier());
		
		//=======Adding Tabbed Pane==========
		add(tp);			
	}			
}
