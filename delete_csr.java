package Lifestyle.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

public class delete_csr extends JPanel implements ActionListener  
{
	JLabel
			l_id = new JLabel("Sales Representative ID"),
			l_name = new JLabel("Name"),
			l_add = new JLabel("Address"),
			l_phone = new JLabel("Telephone"),
			l_email = new JLabel("Email Address"),
			l_dname	= new JLabel("Department");

	JLabel
			ld_name = new JLabel(""),
			ld_add = new JLabel(""),
			ld_phone = new JLabel(""),
			ld_email = new JLabel(""),
			ld_dname = new JLabel("");

	JComboBox
			cb_dname = new JComboBox(),
			cb_cust_id = new JComboBox();

	JButton
			b_del = new JButton("Delete Record",new ImageIcon("Res/Delete24.gif")),
			b_refresh = new JButton("Refresh",new ImageIcon("Res/Refresh24.gif"));

	JPanel
			fields = new JPanel(),			
			buttons = new JPanel();
	
	cust_sales_rep 
			csr = new cust_sales_rep(desktop.con);

	ArrayList a;
	ResultSet rs;

	int id;
		String name,add,phone,email,dname;


	public delete_csr()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
						
			fields.setLayout(new GridLayout(6,2,10,10));
			fields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),"Delete Details"));
			fields.add(l_id);
			fields.add(cb_cust_id);
			fields.add(l_name);
			fields.add(ld_name);
			fields.add(l_add);
			fields.add(ld_add);
			fields.add(l_phone);
			fields.add(ld_phone);
			fields.add(l_email);
			fields.add(ld_email);
			fields.add(l_dname);
			fields.add(ld_dname);			

			buttons.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

			buttons.add(b_del);		
			buttons.add(b_refresh);

			addDetails();
			getId();

			add(fields);
			add(buttons);

			b_del.addActionListener(this);			
			b_refresh.addActionListener(this);
			cb_cust_id.addActionListener(this);
			setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
			if(e.getSource()==cb_cust_id)
			{
				try
				{
					PreparedStatement ps = csr.con.prepareStatement("select name,address,phone,email,dname from cust_sales_rep where csr_id=?");
					ps.setString(1,cb_cust_id.getSelectedItem().toString());
					ResultSet rs= ps.executeQuery();
					while(rs.next())
					{
						ld_name.setText(rs.getString(1));
						ld_add.setText(rs.getString(2));
						ld_phone.setText(rs.getString(3));
						ld_email.setText(rs.getString(4));		
						ld_dname.setText(rs.getString(5));
					}
					ps.close();
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Cb ID error",0);
				}
			}
			if(e.getSource()==b_del)
			{
				int res = JOptionPane.showConfirmDialog(this,"Are U sure want to Delete This Record? \n CSR Name :  "+ld_name.getText(),"Deleting Record",JOptionPane.YES_NO_OPTION);
				if(res==0)
				{
					int r;
					try
					{
						id = Integer.parseInt(cb_cust_id.getSelectedItem().toString());
					
						 r = csr.deleteCsr(id);
						JOptionPane.showMessageDialog(this,"Record has been Deleted Successfully!!!...","Updating Record",1);

						cb_cust_id.removeActionListener(this);
						cb_cust_id.removeAllItems();

						addDetails();
						getId();
						cb_cust_id.addActionListener(this);						
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Cb ID error",0);
					}
				}
			}
			if(e.getSource()==b_refresh)
			{
				cb_cust_id.removeActionListener(this);
				cb_cust_id.removeAllItems();				
				getId();
				addDetails();
				cb_cust_id.addActionListener(this);	
			}
	}

	public void getId()
	{
		try
		{
			Statement stmt = csr.con.createStatement();
			ResultSet rs = stmt.executeQuery("select csr_id from cust_sales_rep");
			while(rs.next())
			{
				cb_cust_id.addItem(rs.getString(1));
			}				
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Id error",0);
		}	
	}

	public void addDetails()
	{
		try
		{
			Statement stmt = csr.con.createStatement();
			ResultSet rs = stmt.executeQuery("select name,address,phone,email,dname from cust_sales_rep");
			rs.next();
			
				ld_name.setText(rs.getString(1));
				ld_add.setText(rs.getString(2));
				ld_phone.setText(rs.getString(3));
				ld_email.setText(rs.getString(4));
				ld_dname.setText(rs.getString(5));
							
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Id error",0);
		}
	}
}
