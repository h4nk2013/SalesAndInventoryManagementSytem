package BLogic;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class cust_sales_rep
{
	private int csr_id;
	private String csr_name;
	private String csr_add;
	private String csr_phone;
	private String csr_email;
	private String csr_dname;

	public Connection con;
	ResultSet rs;
	ArrayList a;

	public cust_sales_rep(Connection con)
	{
		this.con = con;
	}
	
	public void newCsr(int i,String n,String a,String p,String e,String d) throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select * from cust_sales_rep");
		rs.next();
		rs.updateInt(1,i);
		rs.updateString(2,n);
		rs.updateString(3,a);
		rs.updateString(4,p);
		rs.updateString(5,e);
		rs.updateString(6,d);
		rs.insertRow();
		stmt.close();
	}

	public void updateCsr(String n,String a,String p,String e,String d,int i) throws Exception
	{
		PreparedStatement ps = con.prepareStatement("update cust_sales_rep set name=?,address=?,phone=?,email=?,dname=? where csr_id=?");
		ps.setString(1,n);
		ps.setString(2,a);
		ps.setString(3,p);
		ps.setString(4,e);
		ps.setString(5,d);
		ps.setInt(6,i);
		
		int rs = ps.executeUpdate();
		ps.close();
	}

	public int deleteCsr(int i) throws Exception
	{
		PreparedStatement ps = con.prepareStatement("delete from cust_sales_rep where csr_id=?");
		ps.setInt(1,i);
		int rs = ps.executeUpdate();
		ps.close();
		return rs;
	}

	public Vector giveCsrData(Vector d,int length) throws Exception
	{
			Statement stmt = con.createStatement();			
			rs = stmt.executeQuery("select * from cust_sales_rep");
			while(rs.next())
			{
				String arr [] = new String[length];
				arr[0] = rs.getString(1);	
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = rs.getString(5);
				arr[5] = rs.getString(6);
				d.addElement(arr);				
			}
			stmt.close();
			return d;					
	}

	public int getCsrId()
	{
		return csr_id;
	}
	public void setCsrId(int csr_id)
	{
		this.csr_id = csr_id;
	}

	public String getCsrName()
	{
		return csr_name;
	}
	public void setCsrName(String csr_name)
	{
		this.csr_name=csr_name;
	}

	public String getCsrAdd()
	{
		return csr_add;
	}
	public void setCsrAdd(String csr_add)
	{
		this.csr_add = csr_add;
	}

	public String getCsrPhone()
	{
		return csr_phone;
	}
	public void setCsrPhone(String csr_phone)
	{
		this.csr_phone = csr_phone;
	}

	public String getCsrEmail()
	{
		return csr_email;
	}
	public void setCsrEmail(String csr_email)
	{
		this.csr_email = csr_email;
	}

	public String getCsrDname()
	{
		return csr_dname;
	}
	public void setCsrDname(String csr_dname)
	{
		this.csr_dname = csr_dname;
	}
}
