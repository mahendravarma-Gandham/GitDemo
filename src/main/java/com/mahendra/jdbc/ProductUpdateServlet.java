package com.mahendra.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection con;
    PreparedStatement ps;
    
    public void init() {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","sisindri9");
			ps=con.prepareStatement("update product set price=? where id=?");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		int price=Integer.parseInt(request.getParameter("price"));
		
		try {
			ps.setInt(1, price);
			ps.setInt(2, id);
			
			int result = ps.executeUpdate();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print("<b>"+result+" Products Updated"+"</b>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doGet(request, response);
	}
	
	public void destroy() {
		try {
			ps.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
