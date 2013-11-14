package Lifestyle.gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import BLogic.*;

class finalBill extends JInternalFrame implements ActionListener
{
	JLabel
		l_sales = new JLabel("			SALES INVOICE	"),
		l_name = new JLabel("	LIFESTYLE INTERNATIONAL PVT. LTD.	"),
		l_add1 = new JLabel("		639, R-MALL,L.B.S. MARG,	"),
		l_add2 = new JLabel("		MULUND(W.),Mumbai-400 080.	"),
		l_add3 = new JLabel("		www.lifestylestores.com"),
		l_bill_id = new JLabel("Invoice Id"),
		ld_bill_id = new JLabel(""),
		l_amount = new JLabel("Total Amount : "),
		ld_amount = new JLabel("");

	JButton
		b_print = new JButton("Print",new ImageIcon("Res/UII_Icons/24x24/printer.png"));

	Bill 
		b = new Bill(desktop.con);

	Product
		p = new Product(desktop.con);

	JTable
		bill_table = new JTable();

	JScrollPane
		sp = new JScrollPane(bill_table);	

	JSpinner
		s = new JSpinner();		

	SpinnerDateModel
		sdl = new SpinnerDateModel();

	TableColumn
		column = null;
	
	JPanel 
		p_company = new JPanel(),
		bill_details = new JPanel(),
		table = new JPanel();
	
	table_demo
		mod = new table_demo();

	String bill_id,amount;

	Vector data
		= new Vector();

	public finalBill(Vector data,String id,String amount)
	{
		setSize(1015,650);
		setTitle("Bill Details");
		setLayout(new FlowLayout(FlowLayout.CENTER,20,20));		
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setVisible(true);
		
		this.data = data;
		this.bill_id = id;
		this.amount = amount;

		addControls();	
	}	
	public void addControls()
	{
		//========p_company Panel=========
		p_company.setLayout(new GridLayout(5,1,10,10));
		p_company.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder())));
		p_company.add(l_name);
		p_company.add(l_add1);
		p_company.add(l_add2);
		p_company.add(l_add3);
		
		//=======Bill details Panel=========
		bill_details.setLayout(new GridLayout(1,4,10,10));
		bill_details.add(l_bill_id);
		bill_details.add(ld_bill_id);
		bill_details.add(l_amount);
		bill_details.add(ld_amount);

		//=======bill_table and table panel========
		bill_table.setModel(mod);
		bill_table.setRowHeight(25);		
		bill_table.setFont(new Font("Verdana",Font.BOLD,12));
		bill_table.setDragEnabled(false);
		bill_table.setRowMargin(3);	

		//=======Adding ActionListeners==========
		b_print.addActionListener(this);
		
		//========Setting fonts=============
		l_sales.setFont(new Font("Verdana",Font.BOLD,15));
		ld_amount.setFont(new Font("Verdana",Font.BOLD,15));
		ld_bill_id.setFont(new Font("Verdana",Font.BOLD,15));
		ld_amount.setForeground(Color.RED);
		ld_bill_id.setForeground(Color.BLUE);
		
		ld_amount.setText(amount);
		ld_bill_id.setText(bill_id);
		
		for (int i = 0; i < 9; i++) 
		{
			column = bill_table.getColumnModel().getColumn(i);
			if (i == 0)
			{
				column.setPreferredWidth(5); 
			} 
			else if(i==1 || i==2||i==3)
			{
				column.setPreferredWidth(120);
			}
			else if(i==5 || i==6 ||i==7)
			{
				column.setPreferredWidth(10);
			}
			else if(i==4)
			{
				column.setPreferredWidth(30);
			}
			else
			{
				column.setPreferredWidth(80);
			}
		}

		sp.setPreferredSize(new Dimension(940,300));
		table.add(sp);
		add(l_sales);
		this.getContentPane().add(p_company);
		this.getContentPane().add(bill_details);
		add(b_print);
		this.getContentPane().add(table);
	}	

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_print)
		{
			try
			{
				bill_table.print(JTable.PrintMode.FIT_WIDTH);	
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"Print Error",0);
			}	
		}
	}

	class table_demo extends AbstractTableModel
	{
		public String col [] = {"Item Code","Name","Supplier","Brand","Date Time","Qty","Price","Total Price","Department Name"};		
		
		public Object getValueAt(int c,int r)
		{
			String arr[] = (String[]) data.elementAt(c);
			return arr[r];
		}
		public int getRowCount()
		{
			return data.size();
		}
		public int getColumnCount()
		{	
			return col.length;
		}	
		public String getColumnName(int c)
		{
			return col[c].toString();
		}		
	}
}