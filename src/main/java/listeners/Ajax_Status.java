package listeners;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ajax/get_status.php")

public class Ajax_Status extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public Ajax_Status() {

    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/text");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();
		out.print(request.getParameter("id"));
		out.close();
	}
}
