package BLogic;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class supplier
{
	private int supp_id;
	private String supp_name;
	private String supp_brand;
	private String supp_add;
	private String supp_city;
	private String supp_phone;
	private String supp_email;

	public Connection con;
	public ResultSet rs;
	int i;

	public supplier(Connection con)
	{
		this.con = con;
	}

	public int autoGenerate() throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select max(sid) from supplier");
		while(rs.next())
		{
			i = rs.getInt(1)+1;			
		}
		stmt.close();
		return i;
	}

	public void insertSupplier() throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery("select sid,sname,bname,sadd,scity,sphone,semail from supplier");
		rs.last();
		supp_id = rs.getInt(1);
		supp_id = supp_id + 1;

		rs.moveToInsertRow();		
		rs.updateInt(1,supp_id);
		rs.updateString(2,supp_name);
		rs.updateString(3,supp_brand);
		rs.updateString(4,supp_add);
		rs.updateString(5,supp_city);
		rs.updateString(6,supp_phone);
		rs.updateString(7,supp_email);

		rs.insertRow();
		stmt.close();

	}

	public void updateSupplier() throws Exception
	{
		PreparedStatement ps = con.prepareStatement("update supplier set sname=?, bname=?,sadd=?,scity=?,sphone=?,semail=? from supplier where sid=? and sname=?");
		ps.setString(1,supp_name);		
		ps.setString(2,supp_brand);
		ps.setString(3,supp_add);
		ps.setString(4,supp_city);
		ps.setString(5,supp_phone);
		ps.setString(6,supp_email);
		ps.setInt(7,supp_id);
		ps.setString(8,supp_name);
		
		int rs = ps.executeUpdate();
		ps.close();
	}

	public int getSuppId()
	{
		return supp_id;
	}
	public void setSuppId(int supp_id)
	{
		this.supp_id=supp_id;
	}

	public String getSuppName()
	{
		return supp_name;
	}
	public void setSuppName(String supp_name)
	{
		this.supp_name=supp_name;
	}

	public String getSuppBrand()
	{
		return supp_brand;
	}
	public void setSuppBrand(String supp_brand)
	{
		this.supp_brand =supp_brand;
	}

	public String getSuppAdd()
	{
		return supp_add;
	}
	public void setSuppAdd(String supp_add)
	{
		this.supp_add=supp_add;
	}

	public String getSuppCity()
	{
		return supp_city;
	}
	public void setSuppCity(String supp_city)
	{
		this.supp_city=supp_city;
	}

	public String getSuppPhone()
	{
		return supp_phone;
	}
	public void setSuppPhone(String supp_phone)
	{
		this.supp_phone=supp_phone;
	}

	public String getSuppEmail()
	{
		return supp_email;
	}
	public void setSuppEmail(String supp_email)
	{
		this.supp_email=supp_email;
	}
	
	public Vector giveSupplierData(Vector d,int length) throws Exception
	{
			Statement stmt = con.createStatement();			
			rs = stmt.executeQuery("select * from supplier");
			while(rs.next())
			{
				String arr [] = new String[length];
				arr[0] = Integer.toString(rs.getInt(1));	
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = rs.getString(5);
				arr[5] = rs.getString(6);
				arr[6] = rs.getString(7);
				d.addElement(arr);				
			}
			stmt.close();
			return d;					
	}
	
	public Vector giveProductData(Vector d,int length) throws Exception
	{
			Statement stmt = con.createStatement();			
			rs = stmt.executeQuery("select * from prod");
			while(rs.next())
			{
				String arr [] = new String[length];
				arr[0] = Integer.toString(rs.getInt(1));	
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = Integer.toString(rs.getInt(5));
				arr[5] = Integer.toString(rs.getInt(6));
				arr[6] = Integer.toString(rs.getInt(7));
				arr[7] = rs.getDate(8).toString();
				arr[8] = rs.getString(9);
				arr[9] = rs.getString(10);
				d.addElement(arr);				
			}
			stmt.close();
			return d;					
	}

	public ArrayList addSname() throws Exception
	{
		ArrayList arr = new ArrayList();
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select distinct sname from supplier");
		while (rs.next())
		{
			arr.add(rs.getString(1));
		}
		stmt.close();
		return arr;
	}	
}

