package rkp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class myservlet
 */
@WebServlet("/myservlet")
public class myservlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public myservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String acc="";
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/mydb";
		String user="root";
		String pass="root";
		
		String[] access=request.getParameterValues("access");
		for(String a:access)
		{
			acc=acc+a;
		}
		
		String tag=request.getParameter("tagline");
		String feature=request.getParameter("pocket");
		String color=request.getParameter("color");
		
		System.out.println(""+acc);
		System.out.println(""+tag);
		System.out.println(""+feature);
		System.out.println(""+color);
		try
		{
			Class.forName(driver);
			Connection conn=(Connection)DriverManager.getConnection(url, user, pass);
			if(conn!=null) {
				System.out.println("Connection Successful");
				Statement st=(Statement)conn.createStatement();
				String query=String.format("insert into tshirt(acc,tag,feature,color) values('%s','%s','%s','%s')",acc,tag,feature,color);
				int num=st.executeUpdate(query);
				PrintWriter out=response.getWriter();
				
				String s="<table border=1><tr><th>OrderNo</th><th>ACCESSORIES<th/><th>Tagline</th></tr>";
				String q="select orderno,acc,tag from tshirt";
				ResultSet rs=st.executeQuery(q);
				while(rs.next())
				{
					s+="<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td></tr>";
				}
				s+="</table>";
				out.println(s);
				
			}else
			{
				System.out.println("Connection failed");
			}
		}
		catch(Exception e1)
		{
		e1.printStackTrace();	
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
