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

public class makeBill extends JInternalFrame implements ActionListener
{
	JLabel
		l_cust_type = new JLabel("Customer Type"),
		l_cust_id  = new JLabel("Customer Id"),
		l_cust_name = new JLabel("Customer Name"),
		l_cust_phone = new JLabel("Customer Phone"),
		l_cust_email = new JLabel("Customer Email"),
		
		l_pname = new JLabel("Product Name"),
		l_supp = new JLabel("Supplier"),
		l_brand = new JLabel("Brand"),
		l_qty = new JLabel("Quantity"),
		l_price = new JLabel("Price"),
		l_tot_price = new JLabel("Total Price"),
		l_date = new JLabel("Date"),
		l_total = new JLabel("Total Amount Rs. "),
		ld_total = new JLabel("");

	 JTextField		
		t_cust_id = new JTextField(10),
		t_cust_name = new JTextField(10),
		t_cust_phone = new JTextField(10),
		t_cust_email = new JTextField(10),
		t_qty = new JTextField(10),
		t_price = new JTextField(10),
		t_tot_price = new JTextField(10);
		

	JComboBox
		cb_cust_type = new JComboBox(),
		cb_pname = new JComboBox(),
		cb_supp = new JComboBox(),
		cb_brand = new JComboBox();

	JButton
		b_add = new JButton("Add Product",new ImageIcon("Res/Add24.gif")),
		b_remove = new JButton("Remove Product",new ImageIcon("Res/remove.gif")),
		b_confirm = new JButton("Confirm Order",new ImageIcon("Res/ShoppingCart_R.gif"));

	Bill 
		b; 

	Product 
		p = new Product(desktop.con);

	sales 
		sale = new sales(desktop.con);

	JTable
		bill_table = new JTable();

	JScrollPane
		sp = new JScrollPane(bill_table);

	JPanel
		front_fields = new JPanel(),
		back_fields = new JPanel(),
		prod_data = new JPanel(),
		buttons = new JPanel(),
		table = new JPanel(),	
		bill = new JPanel();

	JSpinner
		s = new JSpinner();		

	SpinnerDateModel
		sdl = new SpinnerDateModel();

	TableColumn
		column = null;

	ArrayList a;
	
	int count,i;
	int qty,price,tot_price;
	String prod_name,prod_supp,prod_brand,pdate;
	String id,id_mid,dname;
	int prod_id=0;
	static String bill_id;
	int amount = 0;
	
	String cname,phone,email;

	java.sql.Date date;

	table_demo
		mod = new table_demo();
	
	Vector 
		data =new Vector();

	public makeBill()
	{
		setSize(1015,650);
		setTitle("Bill and Customer Details");
		setLayout(new FlowLayout(FlowLayout.CENTER,20,20));		
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setVisible(true);		

		addControls();
		autoGenerate();
		setProdName();
	}
	public void addControls()
	{
		//=========Setting Spinner==========
		s.setModel(sdl);
		date = new java.sql.Date(sdl.getDate().getTime());
		//=========Setting ComboBox=========
		cb_cust_type.addItem("Non-Regular Customer");
		cb_cust_type.addItem("Regular Customer");

		//=========Setting Fonts============
		t_cust_id.setFont(new Font("Verdana",Font.BOLD,12));
		t_cust_name.setFont(new Font("Verdana",Font.BOLD,12));
		t_cust_phone.setFont(new Font("Verdana",Font.BOLD,12));
		t_cust_email.setFont(new Font("Verdana",Font.BOLD,12));

		//==========Setting Layout===========
		front_fields.setLayout(new GridLayout(2,2,10,10));
		back_fields.setLayout(new GridLayout(2,2,10,10));

		front_fields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder())));
		back_fields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder())));

		add(l_cust_type);
		add(cb_cust_type);
		
		front_fields.add(l_cust_id);
		front_fields.add(t_cust_id);		
		front_fields.add(l_cust_name);
		front_fields.add(t_cust_name);

		back_fields.add(l_cust_phone);
		back_fields.add(t_cust_phone);
		back_fields.add(l_cust_email);
		back_fields.add(t_cust_email);

		//=======Prod Data Panel========
		prod_data.setLayout(new GridLayout(7,2,10,10));

		prod_data.add(l_pname);
		prod_data.add(cb_pname);
		prod_data.add(l_supp);
		prod_data.add(cb_supp);
		prod_data.add(l_brand);
		prod_data.add(cb_brand);
		prod_data.add(l_qty);
		prod_data.add(t_qty);
		prod_data.add(l_price);
		prod_data.add(t_price);
		prod_data.add(l_tot_price);
		prod_data.add(t_tot_price);
		prod_data.add(l_date);
		prod_data.add(s);

		//======Bill Panel===========
		bill.setLayout(new GridLayout(1,2,10,10));
		bill.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder())));
		bill.add(l_total);
		bill.add(ld_total);

		//======Table Panel===========
		table.setLayout(new BorderLayout());
		table.add(sp);

		//=======Setting Font===========
		t_cust_id.setBackground(Color.BLACK);
		t_cust_id.setForeground(Color.WHITE);

		ld_total.setForeground(Color.RED);
		ld_total.setFont(new Font("Verdana",Font.BOLD,14));

		t_cust_id.setEnabled(false);		
		t_price.setEditable(false);
		t_tot_price.setEditable(false);

		t_qty.setFont(new Font("Verdana",Font.BOLD,12));
		t_price.setFont(new Font("Verdana",Font.BOLD,12));
		t_tot_price.setFont(new Font("Verdana",Font.BOLD,12));

		//-------Setting Table Prodperties-----------
		bill_table.setRowHeight(20);		
		bill_table.setFont(new Font("Verdana",Font.PLAIN,12));
		bill_table.setDragEnabled(false);
		bill_table.setRowMargin(2);
		bill_table.setModel(mod);
	
		//====================================
		sp.setPreferredSize(new Dimension(950,180));		

		//=============Hiding Customer Details==========
		l_cust_id.setVisible(false);
		t_cust_id.setVisible(false);
		l_cust_name.setVisible(false);
		t_cust_name.setVisible(false);
		l_cust_phone.setVisible(false);
		t_cust_phone.setVisible(false);
		l_cust_email.setVisible(false);
		t_cust_email.setVisible(false);			
		
		//----------Adding Listeners---------
		cb_cust_type.addActionListener(this);
		cb_pname.addActionListener(this);
		cb_supp.addActionListener(this);
		cb_brand.addActionListener(this);
		b_add.addActionListener(this);
		b_remove.addActionListener(this);
		b_confirm.addActionListener(this);

		//=========Adding KeyListeners============
		t_qty.addKeyListener(new validate());
		t_cust_phone.addKeyListener(new validate_phone());
			
		this.getContentPane().add(front_fields);
		this.getContentPane().add(back_fields);
		this.getContentPane().add(prod_data);
		add(b_add);
		add(b_remove);
		add(b_confirm);
		this.getContentPane().add(bill);
		this.getContentPane().add(table);
	}	

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==cb_cust_type)
		{
			if(cb_cust_type.getSelectedIndex()==1)
			{
				l_cust_id.setVisible(true);
				t_cust_id.setVisible(true);
				l_cust_name.setVisible(true);
				t_cust_name.setVisible(true);
				l_cust_phone.setVisible(true);
				t_cust_phone.setVisible(true);
				l_cust_email.setVisible(true);
				t_cust_email.setVisible(true);
			}
			else
			{
				l_cust_id.setVisible(false);
				t_cust_id.setVisible(false);
				l_cust_name.setVisible(false);
				t_cust_name.setVisible(false);
				l_cust_phone.setVisible(false);
				t_cust_phone.setVisible(false);
				l_cust_email.setVisible(false);
				t_cust_email.setVisible(false);				
			}
		}
		
		if(e.getSource()==cb_pname)
		{
			cb_brand.removeActionListener(this);
			cb_brand.removeAllItems();
			cb_supp.removeActionListener(this);
			cb_supp.removeAllItems();
			try
			{
				Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs= stmt.executeQuery("select prod_brand,prod_supp,prod_price from prod where prod_name='"+ cb_pname.getSelectedItem().toString()+"'");
				if(rs.next())
				{
					t_price.setText(String.valueOf(rs.getInt(3)));
				}
				rs.previous();
				while(rs.next())
				{
					cb_brand.addItem(rs.getString(1));	
					cb_supp.addItem(rs.getString(2));
				}								
				stmt.close();

				cb_brand.addActionListener(this);
				cb_supp.addActionListener(this);
			}			
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"cd_Pname Error",0);
			}
		}
		if(e.getSource()==cb_supp)
		{
			cb_brand.removeActionListener(this);
			cb_brand.removeAllItems();
			try
			{
				Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs= stmt.executeQuery("select prod_brand,prod_price from prod where prod_name='"+ cb_pname.getSelectedItem().toString()+"' and prod_supp='"+cb_supp.getSelectedItem().toString()+"'");
				if(rs.next())
				{
					t_price.setText(String.valueOf(rs.getInt(2)));
				}
				rs.previous();
				while(rs.next())
				{
					cb_brand.addItem(rs.getString(1));
				}
				cb_brand.addActionListener(this);
				stmt.close();
			}	
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"cd_Supp Error",0);
			}
		}
		
		if(e.getSource()==cb_brand)
		{
			try
			{
				PreparedStatement ps = p.con.prepareStatement("select prod_price,total_price from prod where prod_brand=?");
				ps.setString(1,cb_brand.getSelectedItem().toString());
				ResultSet rs= ps.executeQuery();
				if(rs.next())
				{
					t_price.setText(String.valueOf(rs.getInt(1)));					
				}
				ps.close();
			}
			catch (Exception ex)
			{	
				JOptionPane.showMessageDialog(this,ex.getStackTrace(),"cd_brand Error",0);
			}	
		}
		if(e.getSource()==b_add)
		{
			if(t_cust_name.getText().equals("") && cb_cust_type.getSelectedItem().equals("Regular Customer"))
			{
				JOptionPane.showMessageDialog(this,"Enter the Customer Name","Save Error",3);
				t_cust_name.requestFocusInWindow();
			}
			else if(t_cust_phone.getText().equals("") && t_cust_email.getText().equals("") && cb_cust_type.getSelectedItem().equals("Regular Customer"))
			{
				JOptionPane.showMessageDialog(this,"Enter Customer Phone Or Email","Save Error",3);
				t_cust_phone.requestFocusInWindow();
			}
			else if(t_qty.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"Enter the Quantity of Products","Error",2);
				t_qty.requestFocusInWindow();
			}
			else if (Integer.parseInt(t_qty.getText()) < 0)
			{
				JOptionPane.showMessageDialog(this,"Quantity should be Greater than 0","Error",2);
				t_qty.setText("");
				t_qty.requestFocusInWindow();
			}
			else if(Integer.parseInt(t_qty.getText()) > getQty())
			{
				JOptionPane.showMessageDialog(this,"Sorry, Quantity is " + getQty() + " only in Stock!!!...\nPlease Enter Valid Quantity");
				t_qty.setText("");
				t_tot_price.setText("");
				t_qty.requestFocusInWindow();
			}
			else
			{
				getData();
				int t= mod.addData();
				ld_total.setText(Integer.toString(t));
				t_tot_price.setText("");
				t_qty.setText("");
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
			}
		}

		if(e.getSource()==b_remove)
		{
			int i= mod.removeData();
			ld_total.setText(Integer.toString(i));
		}
		
		if(e.getSource()==b_confirm)
		{
			try
			{
				if(t_cust_name.getText().equals("") && cb_cust_type.getSelectedItem().equals("Regular Customer"))
				{
					JOptionPane.showMessageDialog(this,"Enter the Customer Name","Save Error",3);
					t_cust_name.requestFocusInWindow();
				}
				else if(t_cust_phone.getText().equals("") && t_cust_email.getText().equals("") && cb_cust_type.getSelectedItem().equals("Regular Customer"))
				{
					JOptionPane.showMessageDialog(this,"Enter Customer Phone Or Email","Save Error",3);
					t_cust_phone.requestFocusInWindow();
				}
				else
				{
					int r = JOptionPane.showConfirmDialog(this,"Do'u want to Save the Details? \n Click YES to Generate Invoice","Invoice Preparation",JOptionPane.YES_NO_OPTION);
					if(r==0)
					{
						b = new Bill(desktop.con);
						b.setBillId(t_cust_id.getText());
						b.setCustName(t_cust_name.getText());
						b.setCustPhone(t_cust_phone.getText());
						b.setCustEmail(t_cust_email.getText());
						b.setBillAmount(Integer.parseInt(ld_total.getText()));
						b.setBillDate(date);
				
						b.addCustomerDetails();
	
						for(int i=0;i<data.size();i++)
						{
							sale = new sales(desktop.con);
	
							sale.setBillId(t_cust_id.getText());
							sale.setProdId(Integer.parseInt(mod.getValueAt(i,0).toString()));							
							sale.setProdName(mod.getValueAt(i,1).toString());
							sale.setProdSupp(mod.getValueAt(i,2).toString());
							sale.setProdBrand(mod.getValueAt(i,3).toString());
							sale.setDateTime(date);
							sale.setProdQty(Integer.parseInt(mod.getValueAt(i,5).toString()));
							sale.setProdPrice(Integer.parseInt(mod.getValueAt(i,6).toString()));
							sale.setTotalPrice(Integer.parseInt(mod.getValueAt(i,7).toString()));
							sale.setDeptName(mod.getValueAt(i,8).toString());

							sale.addBillDetails();	
						}
						this.dispose();
						desktop.d.add(new finalBill(data,t_cust_id.getText(),ld_total.getText()));
					}	
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,"No Product(s) Added!!!...","Error in confirming order",0);
			}		
		}
	}
	
	public void autoGenerate()
	{
		try
		{
			Statement stmt = p.con.createStatement();
			ResultSet rs = stmt.executeQuery("select max(bill_id) from bill");
			while(rs.next())
			{
					id = rs.getString(1);
					id_mid =id.substring(1);
                    count = Integer.parseInt(id_mid);
                    if(count >=1 && count <9)
                    {
                        count++;
                        t_cust_id.setText("B0"+count);
                    }
                    else if(count>=9 && count <999)
                    {
                        count++;
                        t_cust_id.setText("B"+count);
                    }				
			}
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getStackTrace(),"auto_id Error",0);
		}
	}

	public void setProdName()
	{
		try
		{
			a = p.getPName();
			for(int i=0;i<a.size();i++)
			{
				cb_pname.addItem(a.get(i));
			}	
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getMessage(),"error",0);
		}	
	}

	class validate extends KeyAdapter implements KeyListener
	{
		int total;
		long phone;
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_qty)
				{
					total = Integer.parseInt(t_qty.getText()) * Integer.parseInt(t_price.getText());
					t_tot_price.setText(Integer.toString(total));
				}				
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);
				t_tot_price.setText("");
				t_qty.setText("");				
			}
		}
	}

	class validate_phone extends KeyAdapter implements KeyListener
	{
		long phone;
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_cust_phone)
				{
					phone = Long.parseLong(t_cust_phone.getText());	
				}	
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);
				t_cust_phone.setText("");
			}
			
		}
	}


	public void getData()
	{
		try
		{
			PreparedStatement ps = p.con.prepareStatement("select prod_id,prod_name,sub_dname from prod where prod_name = ? and prod_brand=? and prod_supp=?");
			ps.setString(1,cb_pname.getSelectedItem().toString());
			ps.setString(2,cb_brand.getSelectedItem().toString());
			ps.setString(3,cb_supp.getSelectedItem().toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			prod_id = Integer.parseInt(rs.getString(1));
			dname = rs.getString(3);
			ps.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getStackTrace(),"getData Error",0);
		}
	}

	public int getQty()
	{
		try
		{
			PreparedStatement ps = p.con.prepareStatement("select prod_id,prod_name,prod_qty from prod where prod_name = ? and prod_brand=?");
			ps.setString(1,cb_pname.getSelectedItem().toString());
			ps.setString(2,cb_brand.getSelectedItem().toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			qty = rs.getInt(3);
			ps.close();			
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getStackTrace(),"getData Error",0);
		}
		return qty;
	}

	class table_demo extends AbstractTableModel
	{
		public String col [] = {"Id","Name","Supplier","Brand","Date Time","Quantity","Price","Total Price","Department Name"};		
			
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
		public int addData()
		{			
			String arr [] = new String[col.length];				
			arr[0] = Integer.toString(prod_id);
			arr[1] = cb_pname.getSelectedItem().toString();
			arr[2] = cb_supp.getSelectedItem().toString();
			arr[3] = cb_brand.getSelectedItem().toString();
			arr[4] = date.toString();
			arr[5] = t_qty.getText();
			arr[6] = t_price.getText();
			arr[7] = t_tot_price.getText();
			arr[8] = dname;
			data.addElement(arr);
			this.fireTableRowsInserted(0,data.size());
			amount = amount + Integer.parseInt(arr[7]);
			return amount;
		}
		public int removeData()
		{
			if(bill_table.isRowSelected(bill_table.getSelectedRow()))
			{
				String arr [] = new String[col.length];
				arr[0] = Integer.toString(prod_id);
				arr[1] = cb_pname.getSelectedItem().toString();
				arr[2] = cb_supp.getSelectedItem().toString();
				arr[3] = cb_brand.getSelectedItem().toString();
				arr[4] = date.toString();
				arr[5] = t_qty.getText();
				arr[6] = t_price.getText();
				arr[7] = mod.getValueAt(bill_table.getSelectedRow(),7).toString();
				arr[8] = dname;
				amount = amount - Integer.parseInt(arr[7]);		
				data.removeElementAt(bill_table.getSelectedRow());
				this.fireTableRowsDeleted(0,data.size());
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Please Select A Row","Row not Selected",3);
				return amount;
			}
			return amount;
		}
	}
}
