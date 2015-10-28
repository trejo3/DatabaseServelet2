

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayCustomer
 */
@WebServlet("/Customers")
public class Customers extends HttpServlet {
	
	static Connection conn;
	static String name, Lname;
	static int cID;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Customers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*PrintWriter out = response.getWriter();
		 * request.setAttribute("message","Hello World");
		 * getServletContext().getRequestDispatcher("/Output.jsp").forward(request,response);*/
		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		out.println("<html><head>");
		out.println("<h1>Customers</h1>");
		
		try{
			String url = "jdbc:oracle:thin:testuserdb/password@localhost";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Properties props = new Properties();
			props.setProperty("user", "testuserdb");
			props.setProperty("password", "password");
			
			try{
				conn = DriverManager.getConnection(url,props);
			} catch (SQLException e){
				String msg = e.getMessage();
				out.println("<p>"+msg);
			}
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select customer_id, cust_first_name, cust_last_name from Demo_Customers");
			
			out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css\" integrity=\"sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==\" crossorigin=\"anonymous\">");
			out.println("</head>");
			
			out.println("<div class=\"container\">");
			out.println("<table class=\"table table-striped\">");
			out.println("<th>First Name</th>");
			out.println("<th>Last Name</th>");
			
			while (rs.next()){
				name = rs.getString("CUST_FIRST_NAME");
				Lname = rs.getString("CUST_LAST_NAME");
				cID = rs.getInt("CUSTOMER_ID");
				
				out.println("<tr><td><a href=http://localhost:8080/DatabaseServelet2/CustomerDetails?custID="+cID+">"+name+"</a></td><td>"+Lname+"</td></tr>");
			}
			
			out.println("</table>");
			out.println("</div>");
			conn.close();
		}
		catch (Exception e){
			String msg = e.getMessage();
			out.println("<p>" + msg);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
