package BLogic;

import java.sql.*;
import java.util.*;
import java.sql.Date;
import javax.swing.table.*;

public class Bill  
{
	private String bill_id;
	private String cust_name;
	private String cust_phone;
	private String cust_email;
	private int bill_amount;
	private java.sql.Date bill_date;

	public Connection con;
	ResultSet rs;

	public Bill(Connection con)
	{
		this.con = con;
	}	
	

	public void addCustomerDetails() throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select bill_id,cname,phone,email,bill,bill_date from bill");
		rs.next();
		rs.updateString(1,bill_id);
		rs.updateString(2,cust_name);
		rs.updateString(3,cust_phone);
		rs.updateString(4,cust_email);
		rs.updateInt(5,bill_amount);
		rs.updateDate(6,bill_date);
		rs.insertRow();
		stmt.close();
	}

	public Vector giveCustomerDetails(Vector d,int l) throws Exception
	{
		Statement stmt = con.createStatement();			
		rs = stmt.executeQuery("select * from bill");
		while(rs.next())
		{
			String arr [] = new String[l];
			arr[0] = rs.getString(1);	
			arr[1] = rs.getString(2);
			arr[2] = rs.getString(3);
			arr[3] = rs.getString(4);
			arr[4] = rs.getString(5);
			arr[5] = rs.getDate(6).toString();
			d.addElement(arr);				
		}
		stmt.close();
		return d;		
	}

	public String getBillId()
	{
		return bill_id;
	}
	public void setBillId(String bill_id)
	{
		this.bill_id = bill_id;
	}	

	public String getCustName()
	{
		return cust_name;
	}
	public void setCustName(String cust_name)
	{
		this.cust_name = cust_name;
	}

	public String getCustPhone()
	{
		return cust_phone;
	}
	public void setCustPhone(String cust_phone)
	{
		this.cust_phone = cust_phone;
	}

	public String getCustEmail()
	{
		return cust_email;
	}
	public void setCustEmail(String cust_email)
	{
		this.cust_email = cust_email;
	}	

	public java.sql.Date getBillDate()
	{
		return bill_date;
	}
	public void setBillDate(java.sql.Date bill_date)
	{
		this.bill_date = bill_date;
	}

	public int getBillAmount()
	{
		return bill_amount;
	}
	public void setBillAmount(int bill_amount)
	{
		this.bill_amount = bill_amount;
	}
}
