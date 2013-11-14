package BLogic;

import java.sql.*;
import java.util.*;
import java.sql.Date;
import javax.swing.table.*;

public class sales  
{
	private String bill_id;
	private int prod_id;
	private String prod_name;
	private String prod_supp;
	private String prod_brand;
	private java.sql.Date date_time;
	private int prod_qty;
	private int prod_price;
	private int total_price;
	private String dept_name;

	public Connection con;
	ResultSet rs;

	public sales(Connection con)
	{
		this.con = con;
	}

	public void addBillDetails() throws Exception
	{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery("select bill_id,prod_id,prod_name,prod_supp,prod_brand,dateTime,prod_qty,prod_price,total_price,sub_dname from sales");
		rs.next();
		rs.updateString(1,bill_id);
		rs.updateInt(2,prod_id);
		rs.updateString(3,prod_name);
		rs.updateString(4,prod_supp);
		rs.updateString(5,prod_brand);
		rs.updateDate(6,date_time);
		rs.updateInt(7,prod_qty);
		rs.updateInt(8,prod_price);
		rs.updateInt(9,total_price);
		rs.updateString(10,dept_name);
		rs.insertRow();
		stmt.close();
	}

	public Vector giveSalesDetails(Vector d,int length) throws Exception
	{
			Statement stmt = con.createStatement();			
			rs = stmt.executeQuery("select * from sales");
			while(rs.next())
			{
				String arr [] = new String[length];
				arr[0] = rs.getString(1);	
				arr[1] = rs.getString(2);
				arr[2] = rs.getString(3);
				arr[3] = rs.getString(4);
				arr[4] = rs.getString(5);
				arr[5] = rs.getDate(6).toString();
				arr[6] = rs.getString(7);
				arr[7] = rs.getString(8);
				arr[8] = rs.getString(9);
				arr[9] = rs.getString(10);
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

	public int getProdId()
	{
		return prod_id;
	}
	public void setProdId(int prod_id)
	{
		this.prod_id = prod_id;
	}

	public String getProdName()
	{
		return prod_name;
	}
	public void setProdName(String prod_name)
	{
		this.prod_name = prod_name;
	}
	
	public String getProdSupp()
	{
		return prod_supp;
	}

	public void setProdSupp(String prod_supp)
	{
		this.prod_supp = prod_supp;
	}

	public String getProdBrand()
	{
		return prod_brand;
	}
	public void setProdBrand(String prod_brand)
	{
		this.prod_brand = prod_brand;
	}

	public java.sql.Date getDateTime()
	{
		return date_time;
	}
	public void setDateTime(java.sql.Date date_time)
	{
		this.date_time = date_time;
	}

	public int getProdQty()
	{
		return prod_qty;
	}
	public void setProdQty(int prod_qty)
	{
		this.prod_qty = prod_qty;
	}

	public int getProdPrice()
	{
		return prod_price;
	}
	public void setProdPrice(int prod_price)
	{
		this.prod_price = prod_price;
	}

	public int getTotalPrice()
	{
		return total_price;
	}
	public void setTotalPrice(int total_price)
	{
		this.total_price = total_price;
	}

	public String getDeptName()
	{
		return dept_name;
	}
	public void setDeptName(String dept_name)
	{
		this.dept_name = dept_name;
	}
}
