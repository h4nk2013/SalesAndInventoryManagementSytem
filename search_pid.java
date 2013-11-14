package Lifestyle.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;
import BLogic.*;
class search_pid extends JPanel implements ActionListener
{
	JLabel
		l_pid = new JLabel("Enter Product Id");

	JTextField
		t_pid = new JTextField(10);

	JButton
		b_pid = new JButton("Search",new ImageIcon("Res/Search_R.gif"));

	Product
		p = new Product(desktop.con);

	JTable
		table_pid = new JTable();

	JScrollPane
		sp_pid = new JScrollPane(table_pid);

	TableColumn
		column = null;

	JPanel
		fields = new JPanel(),
		table = new JPanel(),
		buttons = new JPanel();
		
	public search_pid()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,20,20));		
		
		fields.setLayout(new GridLayout(1,2,10,10));
		fields.add(l_pid);
		fields.add(t_pid);

		buttons.setLayout(new FlowLayout());
		buttons.add(b_pid);

		table.add(sp_pid);

		table_pid.setModel(new table_demo());
		table_pid.setRowHeight(25);
		table_pid.setFont(new Font("Verdana",Font.PLAIN,12));
		table_pid.setDragEnabled(false);
		table_pid.setRowMargin(3);

		t_pid.setFont(new Font("Verdana",Font.BOLD,12));
		t_pid.setForeground(Color.BLUE);

		t_pid.addKeyListener(new validate());

		sp_pid.setPreferredSize(new Dimension(950,400));

		b_pid.addActionListener(this);
		b_pid.requestFocusInWindow();

		add(fields);
		add(buttons);
		add(table);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_pid)
		{			
			if(t_pid.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"Enter the Product ID","Product Name",2);
				t_pid.requestFocusInWindow();
			}
			else
			{
				table_pid.setModel(new table_demo());
				for(int i=0;i<10;i++)
				{
					column = table_pid.getColumnModel().getColumn(i);
					if(i==0 || i==4 || i==5||i==7||i==6||i==8)
					column.setPreferredWidth(5);
					else if(i==1 || i==2|| i==3  || i==9||i==10)
					column.setPreferredWidth(65);			
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
				if(e.getSource()==t_pid.getText())
				{
					total = t_pid.getText();
				}
				table_pid.setModel(new table_demo());
				for(int i=0;i<10;i++)
				{
					column = table_pid.getColumnModel().getColumn(i);
					if(i==0 || i==4 || i==5||i==7||i==6||i==8)
					column.setPreferredWidth(5);
					else if(i==1 || i==2|| i==3  || i==9||i==10)
					column.setPreferredWidth(65);			
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);				
			}
		}
	}

	class table_demo extends AbstractTableModel
	{
		String id;
		Vector 
			data = new Vector();

		String col [] = {"Id","Prod Name","Prod Supplier","Prod Brand","Quantity","Price","Total Price","Date","Department No","Department Name"};

		public table_demo()
		{
			try
			{
				id = t_pid.getText();
				p.searchProdId(data,col.length,id);	
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Please Enter Valid Product Id","Search Table Error",0);
				t_pid.setText("");
				t_pid.requestFocusInWindow();
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
