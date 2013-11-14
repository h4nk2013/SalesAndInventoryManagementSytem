package Lifestyle.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;
class supplierDetails extends JInternalFrame 
{
	JTabbedPane
		tp = new JTabbedPane();	

	public supplierDetails()
	{
		setSize(1015,650);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setLayout(new BorderLayout());
		setTitle("Supplier Details");	
		
		addControls();
		setVisible(true);
	}

	public void addControls()
	{
		//==========tab_pane Panel=========
		
		tp.add("Add Supplier",new addSupplier());
		tp.add("Update Supplier",new updateSupplier());
		tp.add("Delete Supplier",new deleteSupplier());		
		
		//=======Adding Tabbed Pane==========
		add(tp);			
	}		
}
