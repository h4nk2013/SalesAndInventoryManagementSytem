package Lifestyle.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

public class custRepDetails extends JInternalFrame
{
	JComboBox
		cb_cust_id = new JComboBox();

	JTabbedPane
		tp = new JTabbedPane();

	cust_sales_rep 
		csr = new cust_sales_rep(desktop.con);
	
	public custRepDetails()
	{
		setSize(1015,650);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setLayout(new BorderLayout());
		setTitle("Sales Representative Details");

		addControls();
		setVisible(true);
	}

	public void addControls()
	{
		tp.add("Add NewCSR ",new add_csr());
		tp.add("Update CSR Details",new update_csr());
		tp.add("Delete CSR Details",new delete_csr());		

		add(tp);
	}
}
