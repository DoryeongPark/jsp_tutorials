package doryeongpark.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@SuppressWarnings("serial")
public class MemberUpdateServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, 
						 HttpServletResponse response)
						 throws ServletException, IOException{
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try{
			
			ServletContext sc = this.getServletContext();
			
			Class.forName(sc.getInitParameter("driver"));
			
			conn = DriverManager.getConnection(sc.getInitParameter("url"),
											   sc.getInitParameter("username"),
											   sc.getInitParameter("password"));
			statement = conn.createStatement();
			
			resultSet = statement.executeQuery(
							"SELECT MNO, EMAIL, CRE_DATE FROM MEMBERS " + 
							"WHERE MNO=" + request.getParameter("no"));
			resultSet.next();
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<body><h1>회원정보</h1>");
			out.println("<form action='update' method='post'>");
			out.println("번호: <input type='text' name='no' " + 
						"value='" + request.getParameter("no") + "'" +
						"readonly><br>");
			out.println("이메일: <input type='text' name='email'" + 
						"value='" + resultSet.getString("EMAIL") + "'><br>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='취소'" + 
						"onclick='location.href=\"list\"'>");
			out.println("</form>");
			out.println("</body></html>");
			
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{ if(resultSet != null) resultSet.close();} catch(Exception e){}
			try{ if(statement != null) statement.close();} catch(Exception e){}
			try{ if(conn != null) conn.close();} catch(Exception e){}
		}
	}
}
