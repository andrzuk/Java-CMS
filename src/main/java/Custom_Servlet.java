import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "custom", urlPatterns = {"/custom/*"})
@MultipartConfig

public class Custom_Servlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	int BUFFER_LENGTH = 4096;
       
    public Custom_Servlet() {
    
    	super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    String filePath = request.getRequestURI();
	    
	    File file = new File(System.getenv("OPENSHIFT_DATA_DIR") + filePath.replace("/custom/", ""));
	    InputStream input = new FileInputStream(file);
	 
	    response.setContentLength((int) file.length());
	    response.setContentType("text/css");
	 
	    OutputStream output = response.getOutputStream();
	    byte[] bytes = new byte[BUFFER_LENGTH];
	    int read = 0;
	    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
	        output.write(bytes, 0, read);
	        output.flush();
	    }
	 
	    input.close();
	    output.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
}
