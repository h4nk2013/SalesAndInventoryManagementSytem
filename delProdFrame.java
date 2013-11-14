package Lifestyle.gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.sql.*;
import java.awt.event.*;
import BLogic.*;

class delProdFrame extends JInternalFrame implements ActionListener
{
	//=======Components 4 Delete Products=======

	JLabel
		l_head = new JLabel("Delete Products"),
		l_pid = new JLabel("Product ID"),
		l_pname = new JLabel("Product Name"),
		l_sname = new JLabel("Product Supplier"),
		l_bname = new JLabel("Brand Name"),
		l_qty = new JLabel("Quantity"),
		l_price = new JLabel("Price"),
		l_tot_price = new JLabel("Total Price"),
		l_dept = new JLabel("SubDepartment no."),
		l_dname = new JLabel("SubDepartment Name"),

		//------------Labels to Display Values at Runtime-------

		ld_pid = new JLabel(""),
		ld_supp = new JLabel(""),
		ld_qty = new JLabel(""),
		ld_price = new JLabel(""),
		ld_tot_price = new JLabel(""),
		ld_deptno = new JLabel(""),
		ld_dname = new JLabel("");

	JComboBox
		cb_pname = new JComboBox(),
		cb_brand = new JComboBox();

	JButton
		b_del = new JButton("Delete Product",new ImageIcon("Res/Delete24.gif")),
		b_cancel=new JButton("Cancel",new ImageIcon("Res/cloapp.gif"));

	JPanel
		fields = new JPanel(),
		buttons = new JPanel();

	Product 
		p = new Product(desktop.con);

	ArrayList a;

//	Connection con;

	public delProdFrame()
	{
		setTitle("Delete Products");
		setLayout(new FlowLayout(FlowLayout.RIGHT,25,20));
		setVisible(true);

		this.getContentPane().add(fields);
		this.getContentPane().add(buttons);

		setIconifiable(true);
		setClosable(true);
		setMaximizable(true);
		addControls();

		setProdName();
		this.pack();		
	}
	public void addControls()
	{
		//=======Adding Components========

		fields.add(l_pid);
		fields.add(ld_pid);
		fields.add(l_pname);
		fields.add(cb_pname);
		fields.add(l_sname);
		fields.add(ld_supp);		
		fields.add(l_bname);
		fields.add(cb_brand);
		fields.add(l_qty);
		fields.add(ld_qty);
		fields.add(l_price);
		fields.add(ld_price);
		fields.add(l_tot_price);
		fields.add(ld_tot_price);
		fields.add(l_dept);
		fields.add(ld_deptno);
		fields.add(l_dname);
		fields.add(ld_dname);

		buttons.add(b_del);
		buttons.add(b_cancel);

		//======Registering Listeners=======	

		cb_pname.addActionListener(this);
		cb_brand.addActionListener(this);
		b_del.addActionListener(this);
		b_cancel.addActionListener(this);

		//=======Setting Layouts=========

		fields.setLayout(new GridLayout(9,2,10,10));
		fields.setBorder(BorderFactory.createEmptyBorder());
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		buttons.setBorder(BorderFactory.createTitledBorder(""));
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_cancel)
		{
			this.dispose();
		}

		if(e.getSource()==cb_pname)
		{
			cb_brand.removeActionListener(this);
			cb_brand.removeAllItems();
			try
			{
				Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);				
				ResultSet rs= stmt.executeQuery("select prod_id,prod_supp,prod_brand,prod_qty,prod_price,total_price,sub_deptno,sub_dname from prod where prod_name='"+ cb_pname.getSelectedItem()+"'");
				if(rs.next())
				{
					ld_pid.setText(Integer.toString(rs.getInt(1)));		
					ld_supp.setText(rs.getString(2));
					ld_qty.setText(rs.getString(4));
					ld_price.setText(rs.getString(5));
					ld_tot_price.setText(rs.getString(6));
					ld_deptno.setText(rs.getString(7));
					ld_dname.setText(rs.getString(8));
				}
				rs.previous();
				while(rs.next())
				{
					cb_brand.addItem(rs.getString(3));
				}

				stmt.close();				
				cb_brand.addActionListener(this);					
			}									
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getStackTrace(),"cd_section Error",0);
			}
		}

		if(e.getSource()==cb_brand)
		{
			try
			{
				PreparedStatement ps= p.con.prepareStatement("select prod_id,prod_supp,prod_qty,prod_price,total_price,sub_deptno,sub_dname from prod where prod_name=? and prod_brand=?");
				ps.setString(1,cb_pname.getSelectedItem().toString());
				ps.setString(2,cb_brand.getSelectedItem().toString());			
				ResultSet rs= ps.executeQuery();
				while(rs.next())
				{
					ld_pid.setText(Integer.toString(rs.getInt(1)));
					ld_supp.setText(rs.getString(2));
					ld_qty.setText(rs.getString(3));
					ld_price.setText(rs.getString(4));
					ld_tot_price.setText(rs.getString(5));
					ld_deptno.setText(rs.getString(6));
					ld_dname.setText(rs.getString(7));
				}
				ps.close();		
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(this,ex.getMessage(),"cd_section Error",0);
			}
		}

		if(e.getSource()==b_del)
		{
			int res = JOptionPane.showConfirmDialog(this,"Are U sure want to Delete This Product? \n Product Name :  "+cb_pname.getSelectedItem().toString()+ "\n Product Brand :  "+cb_brand.getSelectedItem().toString(),"Deleting Product",JOptionPane.YES_NO_OPTION);
			if(res==0)
			{
				try
				{
					PreparedStatement ps = p.con.prepareStatement("delete from prod where prod_id =? and prod_brand=?");
					ps.setString(1,ld_pid.getText());
					ps.setString(2,cb_brand.getSelectedItem().toString());

					int i = ps.executeUpdate();
					ps.close();
					cb_pname.removeActionListener(this);
					cb_brand.removeActionListener(this);

					Statement stmt = p.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					cb_pname.removeAllItems();
					cb_brand.removeAllItems();
					ResultSet rs = stmt.executeQuery("select prod_id,prod_qty,prod_price,total_price,sub_deptno,sub_dname,prod_name,prod_brand,prod_supp from prod order by prod_id"); 
					if(rs.next())
					{
						ld_pid.setText(Integer.toString(rs.getInt(1)));
						ld_qty.setText(Integer.toString(rs.getInt(2)));
						ld_price.setText(rs.getString(3));
						ld_tot_price.setText(rs.getString(4));
						ld_deptno.setText(rs.getString(5));
						ld_dname.setText(rs.getString(6));
						ld_supp.setText(rs.getString(9));
					}
					rs.previous();
					while(rs.next())
					{
						cb_pname.addItem(rs.getString(7));
						cb_brand.addItem(rs.getString(8));						
					}						
					stmt.close();

					cb_pname.addActionListener(this);
					cb_brand.addActionListener(this);
					JOptionPane.showMessageDialog(this,"Product Has Been Deleted Successfully!!!...","Delete Product",1);						
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(this,ex.getMessage(),"b_del Error",0);
				}
			}
		}
	}
	
	public void setProdName()
	{
		try
		{
			a =	p.getPName();
			for(int i=0;i<a.size();i++)
			{
				cb_pname.addItem(a.get(i));
			}	
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getStackTrace(),"error",0);
		}
	}	
}