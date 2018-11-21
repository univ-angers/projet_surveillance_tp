package login.submit.registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginRegister
 */
@WebServlet("/LoginRegister")
public class LoginRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Authentification.jsp").forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName=request.getParameter("email");
		String password=request.getParameter("password");
	    String submitType=request.getParameter("submit");
	    CustomerDAO cd=new CustmerDAOImpl();
	    Customer c=cd.getCustomer(userName, password);
	    if(submitType.equals("login") && c!=null && c.getEmail()!=null) {
	    request.setAttribute("message", c.getEmail());
	    request.getRequestDispatcher("Inscription.jsp").forward(request, response);
	    	
	    }else {
	    	request.setAttribute("message", "Data not found");
	    	request.getRequestDispatcher("Authentification.jsp").forward(request, response);
	    }
		
	}

}
