package com.iiht.evaluation.eloan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.eloan.dao.ConnectionDao;
import com.iiht.evaluation.eloan.model.ApprovedLoan;
import com.iiht.evaluation.eloan.model.LoanInfo;
import com.iiht.evaluation.eloan.model.User;
import com.mysql.cj.xdevapi.Statement;




@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
private ConnectionDao connDao;
	
	public void setConnDao(ConnectionDao connDao) {
		this.connDao = connDao;
	}
	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");
		System.out.println(jdbcURL + jdbcUsername + jdbcPassword);
		this.connDao = new ConnectionDao(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		String viewName = "";
		try {
			switch (action) {
			case "registernewuser":
				viewName=registernewuser(request,response);
				break;
			case "validate":
				viewName=validate(request,response);
				break;
			case "placeloan":
				viewName=placeloan(request,response);
				break;
			case "application1":
				viewName=application1(request,response);
				break;
			case "editLoanProcess"  :
				viewName=editLoanProcess(request,response);
				break;
			case "registeruser":
				viewName=registerUser(request,response);
				break;
			case "register":
				viewName = register(request, response);
				break;
			case "application":
				viewName = application(request, response);
				break;
			case "trackloan":
				viewName = trackloan(request, response);
				break;
			case "editloan":
				viewName = editloan(request, response);
				break;	
			case  "displaystatus" :
				viewName=displaystatus(request,response);
				break;
			default : viewName = "notfound.jsp"; break;	
			}
		} catch (Exception ex) {
			
			throw new ServletException(ex.getMessage());
		}
			RequestDispatcher dispatch = request.getRequestDispatcher(viewName);
			dispatch.forward(request, response);
	}
	private String validate(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, ClassNotFoundException {
		/* write the code to validate the user */
		System.out.println("Entered here to validate user" );
		
		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		String uname = (String)request.getParameter("username");
		String pwd = (String)request.getParameter("pwd");
				
		System.out.println(uname);
		System.out.println(pwd);
		
		
		if(user.getUsername().contentEquals("admin") && user.getPassword().contentEquals("admin")) {
			return "adminhome1.jsp";  
		}
		else {
			boolean check = this.connDao.validate(user);
			if(check) {
				System.out.println("Entered validate and going to userhome1 jsp");
			return "userhome1.jsp";
			}
			System.out.println("Entered validate and going to register jsp");
			return "register.jsp";
		}
	}
	private String placeloan(HttpServletRequest request, HttpServletResponse response) throws ServletException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
	/* write the code to place the loan information */
		LoanInfo loan = new LoanInfo("0", request.getParameter("purpose"), Integer.valueOf(request.getParameter("amtrequest")), request.getParameter("doa"), 
				request.getParameter("bstructure"), request.getParameter("bindicator"), request.getParameter("taxpayer"),
				request.getParameter("address"), request.getParameter("email"), request.getParameter("mobile"),"Initiated");
		this.connDao.addLoan(loan);
		
		return "userhome1.jsp";
	}
	private String application1(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
	/* write the code to display the loan application page */
		//ccount num f records in loaninfo and display+1 as app num
		request.setAttribute("applno", this.connDao.totalAppliedLoan()+1);
		return "application.jsp";
	}
	private String editLoanProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, ClassNotFoundException {
		// TODO Auto-generated method stub
		/* write the code to edit the loan info */
		LocalDate today = LocalDate.now();
		LoanInfo loan = new LoanInfo(request.getParameter("applno"), request.getParameter("purpose"), Integer.valueOf(request.getParameter("amtrequest")), 
				request.getParameter("doa"), request.getParameter("bstructure"), request.getParameter("bindicator"), request.getParameter("taxpayer"),
				request.getParameter("address"), request.getParameter("email"), request.getParameter("mobile"),"Initiated");
		this.connDao.editLoan(loan);
		
		return "userhome1.jsp";
	}
	private String registerUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		/* write the code to redirect page to read the user details */
		
		request.setAttribute("applno", this.connDao.totalAppliedLoan()+1);
		/*System.out.println("Entered here to register user:" +"applno"); */
		return "newuserui.jsp"; 
	}
	private String registernewuser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, ClassNotFoundException {
		// TODO Auto-generated method stub
		/* write the code to create the new user account read from user 
		   and return to index page */
		User newUser = new User(request.getParameter("username"), request.getParameter("password"));
		if(this.connDao.addNewUser(newUser)) {
			System.out.println("\nUser Already Exists\n");
			return "index.jsp";
		}
		throw new SQLException("Username already in use.");		
	}
	
	private String register(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		/* write the code to redirect to register page */
		
		return "register.jsp";
	}
	private String displaystatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, ClassNotFoundException {
		// TODO Auto-generated method stub
		/* write the code the display the loan status based on the given application
		   number 
		*/ 
		String applno = request.getParameter("applno");
		this.connDao.checkLoan(applno);
		request.setAttribute("status", this.connDao.getLoanStatus(applno));
		return "loanDetails.jsp";
	}

	private String editloan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
	/* write a code to return to editloan page */
		LoanInfo loan = new LoanInfo();
		String applno = request.getParameter("applno");
		this.connDao.checkLoan(applno);
		
		String loanStatus = this.connDao.getLoanStatus(applno);
		
		if(loanStatus.equals("Initiated"))
		{
			loan = this.connDao.getLoan(applno);
			request.setAttribute("loan", loan);
			return "editloanui.jsp";
		}
		
		throw new SQLException("Cannot Edit the Loan as it is in '"+loanStatus+"' status.");
	}

	private String trackloan(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	/* write a code to return to trackloan page */
		
		return "trackloan.jsp";
	}

	private String application(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	/* write a code to return to trackloan page */
		return "trackloan.jsp";
	}
}