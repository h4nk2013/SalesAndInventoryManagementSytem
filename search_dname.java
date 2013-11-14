package Lifestyle.gui;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

class search_dname extends JPanel implements ActionListener
{
	JLabel
		l_search_dname = new JLabel("Select A Department");

	JComboBox
		cb_dname = new JComboBox();
	
	JButton
		b_dname = new JButton("Search",new ImageIcon("Res/Search_R.gif"));

	Product
		p = new Product(desktop.con);

	JTable
		table_dname = new JTable();

	JScrollPane
		sp_dname = new JScrollPane(table_dname);

	JPanel
		fields = new JPanel(),
		table = new JPanel();

	TableColumn
		column = null;

	String dname;

	public search_dname()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

		fields.setLayout(new GridLayout(1,2,12,12));
		fields.add(l_search_dname);
		fields.add(cb_dname);		

		table.add(sp_dname);
		
		table_dname.setModel(new table_demo());
		table_dname.setRowHeight(25);
		table_dname.setFont(new Font("Verdana",Font.PLAIN,12));
		table_dname.setDragEnabled(false);
		table_dname.setRowMargin(3);		
		
		sorting();
		add(fields);
		add(b_dname);
		add(table);							

		sp_dname.setPreferredSize(new Dimension(950,400));		

		b_dname.addActionListener(this);
		cb_dname.addActionListener(this);
	}

	public void sorting()
	{
		cb_dname.addItem("BABY SHOP");
		cb_dname.addItem("SHOE MART");
		cb_dname.addItem("SPLASH");
		cb_dname.addItem("HOME CENTER");
		cb_dname.addItem("LIFESTYLE");				
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_dname)
		{
				table_dname.setModel(new table_demo());
				for(int i=0;i<10;i++)
				{
					column = table_dname.getColumnModel().getColumn(i);
					if(i==0 || i==4 || i==5||i==7||i==6||i==8)
					column.setPreferredWidth(5);
					else if(i==1 || i==2|| i==3  || i==9||i==10)
					column.setPreferredWidth(65);			
				}
		}

		if(e.getSource()==cb_dname)
		{
				table_dname.setModel(new table_demo());
				for(int i=0;i<10;i++)
				{
					column = table_dname.getColumnModel().getColumn(i);
					if(i==0 || i==4 || i==5||i==7||i==6||i==8)
					column.setPreferredWidth(5);
					else if(i==1 || i==2|| i==3  || i==9||i==10)
					column.setPreferredWidth(65);			
				}
		}
	}	

	class table_demo extends AbstractTableModel
	{
		Vector
			data = new Vector();	
		
		String col [] = {"Id","Prod Name","Prod Supplier","Prod Brand","Quantity","Price","Total Price","Date","Department No","Department Name"};
		
		public table_demo()
		{
			try
			{
				dname = (String)cb_dname.getSelectedItem();
				p.searchDeptName(data,col.length,dname);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Dept Error",0);
			}
		}

		public Object getValueAt(int r , int c)
		{
			String arr [] = (String[]) data.elementAt(r);
			return arr[c];
		}
		public int getColumnCount()
		{
			return col.length;
		}
		public int getRowCount()
		{
			return data.size();
		}
		public String getColumnName(int c)
		{
			return col[c].toString();
		}
	}
}
