package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PicarMemberDAO;
import dao.PicarMemberDAOImpl;
import model.PicarMember;

@WebServlet(name = "PicarController", urlPatterns = {"/login","/logout","/login_input","/member_save"})
public class PicarController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		int lastIndex = uri.lastIndexOf("/");
		String action = uri.substring(lastIndex+1);
	
		//로그인 입력 
		if(action.equals("login_input")) {
			
			RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
			rd.forward(req, resp);
			
		}else if(action.equals("login")) {
			String id = req.getParameter("id");
			String password = req.getParameter("password");
			
			PicarMemberDAO dao = new PicarMemberDAOImpl();
			PicarMember picarmember = dao.selectById(id, password);
			
			
			if(picarmember !=null){
				HttpSession session = req.getSession();
				session.setAttribute("picarmember", picarmember);
				
				RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
				rd.forward(req, resp);
								
			}else{				
				
				req.setAttribute("message", "유효하지 않는 아이디이거나, 비밀번호가 일치하지 않습니다.");
								
				RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
				rd.forward(req, resp);
				
			}			
			
		}else if(action.equals("logout")) {			
			
			HttpSession session = req.getSession();
			session.removeAttribute("picarmember");
			
			RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
			rd.forward(req, resp);	
		
		}else if(action.equals("member_save")) {
			PicarMemberDAO dao = new PicarMemberDAOImpl();
			PicarMember picarMember = new PicarMember();
			
			picarMember.setId(req.getParameter("id"));
			picarMember.setPassword(req.getParameter("password"));
			picarMember.setName(req.getParameter("name"));
			picarMember.setPhone(req.getParameter("phone"));
			picarMember.setLicense(Integer.parseInt(req.getParameter("license")));
			picarMember.setValidate(req.getParameter("validdate"));
			
		
			boolean result = dao.insert(picarMember);
			System.out.println(result);
		
			RequestDispatcher rd =req.getRequestDispatcher("/membership.jsp");
			rd.forward(req, resp);
		
		
		
		
		}
	}	
}
