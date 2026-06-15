package com.org.kodewala;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserMngmt {

	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/27th_oct_batch", "root", "Faisal@1998");
			
			Statement stmt = con.createStatement();
			String query = "select * from user_info";
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String userName = rs.getString(2);
				String userId = rs.getString(3);
				String status = rs.getString(4);
				
				System.out.println("Id: " + id);
				System.out.println("User Name: " + userName);
				System.out.println("User Id: " + userId);
				System.out.println("Status: " + status);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
