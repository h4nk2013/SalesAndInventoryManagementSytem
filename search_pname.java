package Lifestyle.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

class search_pname extends JPanel implements ActionListener 
{
	JLabel
		l_search_pname = new JLabel("Enter Product Name ");

	JButton
		b_pname = new JButton("Search",new ImageIcon("Res/Search_R.gif"));

	JTextField
		t_search_pname = new JTextField(15);

	Product p = new Product(desktop.con);

	JTable
		table_pname = new JTable();

	JScrollPane 
		sp_pname = new JScrollPane(table_pname);

	JPanel
		fields = new JPanel(),
		table = new JPanel(),
		buttons = new JPanel();	

	TableColumn
		column = null;

	String name;

	
	public search_pname()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,	20,20));
		
		fields.setLayout(new GridLayout(2,2,10,10));
		fields.add(l_search_pname);
		fields.add(t_search_pname);

		buttons.add(b_pname);
		
		table.add(sp_pname);
		
		table_pname.setModel(new table_demo());
		table_pname.setRowHeight(25);
		table_pname.setFont(new Font("Verdana",Font.PLAIN,12));
		table_pname.setDragEnabled(false);
		table_pname.setRowMargin(3);

		add(fields);
		add(buttons);
		add(table);

		sp_pname.setPreferredSize(new Dimension(975,400));

		t_search_pname.setFont(new Font("verdana",Font.BOLD,12));
		t_search_pname.setForeground(Color.BLUE);

		b_pname.addActionListener(this);
		t_search_pname.addKeyListener(new validate());
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_pname)
		{
			if(t_search_pname.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"Enter the Product Name","Product Name",2);
				t_search_pname.requestFocusInWindow();
			}
			else
			{
				table_pname.setModel(new table_demo());
				for(int i=0;i<10;i++)
				{
					column = table_pname.getColumnModel().getColumn(i);
					if(i==0 || i==4 || i==5|| i==6)
					column.setPreferredWidth(10);
					else if(i==1 || i==2|| i==3 ||i==7 ||i==8)
					column.setPreferredWidth(65);			
					else if (i==9)
					{
						column.setPreferredWidth(30);
					}
				}
			}	
		}
	}

	class validate extends KeyAdapter
	{
		String total,phone;
		String pname;
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_search_pname.getText())
				{
					total = t_search_pname.getText();
				}
				table_pname.setModel(new table_demo());
				for(int i=0;i<10;i++)
				{
					column = table_pname.getColumnModel().getColumn(i);
					if(i==0 || i==4 || i==5||i==7||i==6||i==8)
					column.setPreferredWidth(5);
					else if(i==1 || i==2|| i==3  || i==9||i==10)
					column.setPreferredWidth(65);			
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Characters Only!!!!....","Error Ocurred!!..",2);				
			}
		}
	}

	class table_demo extends AbstractTableModel 
	{
		String col [] = {"Id","Prod Name","Prod Supplier","Prod Brand","Quantity","Price","Total Price","Date","Department No","Department Name"};
		Vector data;
		public table_demo()
		{
			data = new Vector();
			try
			{
					name = t_search_pname.getText();
					p.searchProductName(data,col.length,name);				
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Search Table Error",0);
			}
		}
		public Object getValueAt(int r,int c)
		{
			String arr[] = (String[]) data.elementAt(r);
			return arr[c];
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
