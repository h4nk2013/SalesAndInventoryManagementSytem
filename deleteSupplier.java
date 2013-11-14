package Lifestyle.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

class deleteSupplier extends JPanel implements ActionListener
{
	JLabel 
		l_id = new JLabel("Supplier Id"),
		l_sname = new JLabel("Supplier Name"),
		l_brand = new JLabel("Supplier Brand"),
		l_add = new JLabel("Address"),
		l_city = new JLabel("City"),
		l_phone = new JLabel("Phone No."),
		l_email = new JLabel("E-mail");

	//------------Labels to Display Values at Runtime-------

	JLabel
		ld_sid = new JLabel(""),
		ld_city = new JLabel(""),
		ld_phone = new JLabel(""),
		ld_email = new JLabel("");

	JComboBox
		cb_sname = new JComboBox(),
		cb_brand = new JComboBox();
	
	JButton
		b_delete = new JButton("Delete Supplier",new ImageIcon("Res/Delete24.gif")),
		b_refresh = new JButton("Refresh",new ImageIcon("Res/refresh24.gif"));		
	
	JPanel
		fields = new JPanel(),
		supp_details = new JPanel(),
		s_city = new JPanel(),
		checkbox = new JPanel(),
		buttons = new JPanel();

	supplier 
		s_details = new supplier(desktop.con);

	JEditorPane
		ep = new JEditorPane();

	JScrollPane
		sp_ep = new JScrollPane(ep);
	
	ArrayList
		a,b ;


	public deleteSupplier()
	{
		setSize(1015,660);
		setLayout(new FlowLayout(FlowLayout.CENTER,25,20));
		setVisible(true);	
		
		setSname();
		addDetails();
		addControls();
	}

	public void addControls()
	{
		//--------Setting Layout---------
		fields.setLayout(new GridLayout(4,2,12,12));

		fields.add(l_id);
		fields.add(ld_sid);
		fields.add(l_sname);
		fields.add(cb_sname);
		fields.add(l_brand);
		fields.add(cb_brand);
		fields.add(l_brand);
		fields.add(cb_brand);

		supp_details.setLayout(new GridLayout(1,2,12,12));

		supp_details.add(l_add);
		supp_details.add(sp_ep);
		
		ep.setContentType("text/plain");
		ep.setPreferredSize(new Dimension(150,80));

		s_city.setLayout(new GridLayout(3,2,12,12));
		s_city.add(l_city);
		s_city.add(ld_city);
		s_city.add(l_phone);
		s_city.add(ld_phone);
		s_city.add(l_email);
		s_city.add(ld_email);

		fields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),""));
		supp_details.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),""));
		s_city.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),""));
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		
		buttons.add(b_delete);
		buttons.add(b_refresh);

		//-----------Setting Fonts-----------
		ld_sid.setFont(new Font("Verdana",Font.BOLD,12));
		ld_city.setFont(new Font("Verdana",Font.BOLD,12));
		ld_phone.setFont(new Font("Verdana",Font.BOLD,12));
		ld_email.setFont(new Font("Verdana",Font.BOLD,12));

		ep.setFont(new Font("Verdana",Font.BOLD,12));

		ep.setEditable(false);

		cb_sname.addActionListener(this);
		cb_brand.addActionListener(this);
		b_delete.addActionListener(this);
		b_refresh.addActionListener(this);


		add(fields);
		add(supp_details);
		add(s_city);
		add(buttons);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b_refresh)
		{
			cb_sname.removeActionListener(this);
			cb_sname.removeAllItems();				
			cb_brand.removeActionListener(this);
			cb_brand.removeAllItems();			
			setSname();						
			addDetails();
			cb_sname.addActionListener(this);
			cb_brand.addActionListener(this);
		}

		if(e.getSource()==cb_sname)
		{
			cb_brand.removeActionListener(this);
			cb_brand.removeAllItems();
			try
			{
				Statement stmt = s_details.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);				
				String brand = (String)cb_sname.getSelectedItem();
				ResultSet rs= stmt.executeQuery("select sid,bname,sadd,scity,sphone,semail from supplier where sname= '"+ brand+ "'");
				if(rs.next())
				{
					ld_sid.setText(Integer.toString(rs.getInt(1)).trim());
					ep.setText(rs.getString(3).trim());
					ld_city.setText(rs.getString(4).trim());
					ld_phone.setText(rs.getString(5).trim());
					ld_email.setText(rs.getString(6).trim());
				}
				rs.previous();
				while (rs.next())
				{
					cb_brand.addItem(rs.getString(2).trim());
				}
				stmt.close();
				cb_brand.addActionListener(this);
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getStackTrace(),"cb_sname error",0);
			}
		}

		if (e.getSource()==cb_brand)
		{
			try
			{
				Statement stmt = s_details.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery("select sid,sname,sadd,scity,sphone,semail from supplier where bname = '" + cb_brand.getSelectedItem().toString()+ "' and sname = '"+cb_sname.getSelectedItem().toString()+"'");
				if (rs.next())
				{
					ld_sid.setText(Integer.toString(rs.getInt(1)).trim());
					ep.setText(rs.getString(3).trim());
					ld_city.setText(rs.getString(4).trim());
					ld_phone.setText(rs.getString(5).trim());
					ld_email.setText(rs.getString(6).trim());
				}
				stmt.close();
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getStackTrace(),"cb_brand error",0);
			}
		}

		if (e.getSource()==b_delete)
		{
			int res = JOptionPane.showConfirmDialog(this,"Are U sure want to Delete This Supplier? \n Supplier Name :  "+cb_sname.getSelectedItem().toString()+ "\n Supplier Brand :  "+cb_brand.getSelectedItem().toString(),"Deleting Product",JOptionPane.YES_NO_OPTION);
			if(res==0)
			{
				try
				{
					PreparedStatement ps = s_details.con.prepareStatement("delete from supplier where sid =? and bname=?");
					ps.setString(1,ld_sid.getText());
					ps.setString(2,cb_brand.getSelectedItem().toString());

					int i = ps.executeUpdate();
					ps.close();
					cb_sname.removeActionListener(this);
					cb_brand.removeActionListener(this);

					Statement stmt = s_details.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					cb_sname.removeAllItems();
					cb_brand.removeAllItems();
					ResultSet rs = stmt.executeQuery("select sid,sname,bname,sadd,scity,sphone,semail from supplier"); 
					if(rs.next())
					{
						ld_sid.setText(Integer.toString(rs.getInt(1)));
						ep.setText(rs.getString(4));
						ld_city.setText(rs.getString(5));
						ld_phone.setText(rs.getString(6));
						ld_email.setText(rs.getString(7));						
					}
					rs.previous();
					while(rs.next())
					{
						cb_sname.addItem(rs.getString(2));
						cb_brand.addItem(rs.getString(3));						
					}						
					stmt.close();

					cb_sname.addActionListener(this);
					cb_brand.addActionListener(this);
					JOptionPane.showMessageDialog(this,"Product Has Been Deleted Successfully!!!...","Delete Product",1);
				}	
				catch(Exception ex)
				{
					
				}
			}
		}
	}

	public void setSname()
	{
		try
		{
			a = s_details.addSname();
			for(int i=0;i<a.size();i++)
			{
				cb_sname.addItem(a.get(i));
			}
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null,ex.getStackTrace(),"Sname error",0);
		}
	}

	public void addDetails()
	{
		try
		{
			Statement stmt = s_details.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("select sid,bname,sadd,scity,sphone,semail from supplier where sname='" + cb_sname.getSelectedItem().toString()+"' order by sid");
			if(rs.next())
			{
				ld_sid.setText(Integer.toString(rs.getInt(1)).trim());
				ep.setText(rs.getString(3).trim());	
				ld_city.setText(rs.getString(4).trim());
				ld_phone.setText(rs.getString(5).trim());
				ld_email.setText(rs.getString(6).trim());
			}				
			rs.previous();
			while(rs.next())
			{
				cb_brand.addItem(rs.getString(2).trim());				
			}
			stmt.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,ex.getStackTrace(),"Id error",0);
		}
	}
}
