package Lifestyle.gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import BLogic.*;

class updateSupplier extends JPanel implements ActionListener
{
	JLabel 
		l_id = new JLabel("Supplier Id"),
		l_sname = new JLabel("Supplier Name"),
		l_brand = new JLabel("Supplier Brand"),
		l_add = new JLabel("Address"),
		l_city = new JLabel("City"),
		l_phone = new JLabel("Phone No."),
		l_email = new JLabel("E-mail"),

		ld_sid = new JLabel("");
	
	JTextField
		t_sname = new JTextField(12),
		t_brand = new JTextField(12),
		t_add = new JTextField(12),
		t_city = new JTextField(12),
		t_phone = new JTextField(15),
		t_email = new JTextField(15);

	JComboBox
		cb_sname = new JComboBox(),
		cb_brand = new JComboBox();	

	JButton
		b_update = new JButton("Update Supplier",new ImageIcon("Res/UII_Icons/24x24/user_add.png")),
		b_refresh = new JButton("Refresh",new ImageIcon("Res/refresh24.gif"));		
	

	JPanel
		fields = new JPanel(),
		supp_details = new JPanel(),
		s_city = new JPanel(),
		checkbox = new JPanel(),
		buttons = new JPanel(),
		brand_fields = new JPanel(),
		table = new JPanel();

	supplier 
		s_details = new supplier(desktop.con);
	

	//---------------------------

	
	JEditorPane
		ep = new JEditorPane();

	JScrollPane
		sp_ep = new JScrollPane(ep);
	
	ArrayList
		a,b ;	
	
	public updateSupplier()
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
		s_city.add(t_city);
		s_city.add(l_phone);
		s_city.add(t_phone);
		s_city.add(l_email);
		s_city.add(t_email);

		fields.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),""));
		supp_details.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),""));
		s_city.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()),""));
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		
		buttons.add(b_update);
		buttons.add(b_refresh);

		//-----------Setting Fonts-----------
		ld_sid.setFont(new Font("Verdana",Font.BOLD,12));
		t_city.setFont(new Font("Verdana",Font.BOLD,12));
		t_phone.setFont(new Font("Verdana",Font.BOLD,12));
		t_email.setFont(new Font("Verdana",Font.BOLD,12));

		ep.setFont(new Font("Verdana",Font.BOLD,12));

		cb_brand.setEditable(true);

		t_phone.addKeyListener(new validate());
		cb_sname.addActionListener(this);
		cb_brand.addActionListener(this);
		b_update.addActionListener(this);
		b_refresh.addActionListener(this);


		add(fields);
		add(supp_details);
		add(s_city);
		add(buttons);
	}

	public void actionPerformed(ActionEvent e)
	{
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
					t_city.setText(rs.getString(4).trim());
					t_phone.setText(rs.getString(5).trim());
					t_email.setText(rs.getString(6).trim());
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
					t_city.setText(rs.getString(4).trim());
					t_phone.setText(rs.getString(5).trim());
					t_email.setText(rs.getString(6).trim());
				}
				stmt.close();
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getStackTrace(),"cb_brand error",0);
			}
		}
		
		if (e.getSource()==b_update)
		{
			try
			{
				if(t_city.getText().length()==0 | ep.getText().length()==0 && t_phone.getText().length()==0 && t_email.getText().length()==0)
				{
					JOptionPane.showMessageDialog(null,"Please Enter All Details","Error Ocurred!!!...",3);
				}
				else 
				{
					s_details.setSuppId(Integer.parseInt(ld_sid.getText()));
					s_details.setSuppName(cb_sname.getSelectedItem().toString());
					s_details.setSuppBrand(cb_brand.getSelectedItem().toString());
					s_details.setSuppAdd(ep.getText());
					s_details.setSuppCity(t_city.getText());
					s_details.setSuppPhone(t_phone.getText());
					s_details.setSuppEmail(t_email.getText());

					try
					{
						s_details.updateSupplier();
						JOptionPane.showMessageDialog(this,"Supplier Has been Updated Successfully!!!...","Update Supplier",1);
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null,ex.getStackTrace(),"update Supplier error",0);
					}
				}
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,ex.getStackTrace(),"update button error",0);
			}
		}

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
				t_city.setText(rs.getString(4).trim());
				t_phone.setText(rs.getString(5).trim());
				t_email.setText(rs.getString(6).trim());
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

	class validate extends KeyAdapter implements KeyListener
	{
		long phone;		
		public void keyReleased(KeyEvent e)
		{
			try
			{
				if(e.getSource()==t_phone)
				{
					phone = new Long(Long.parseLong(t_phone.getText()));					
				}				
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(null,"Enter Digits Only!!!!....","Error Ocurred!!..",2);				
			}			
		}		
	}
}
