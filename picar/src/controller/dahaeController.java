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

@WebServlet(name = "dahaeController", urlPatterns = {"/login","/logout","/login_input","/member_save","/sign_up","/idcheck","/idinput"})
public class dahaeController extends HttpServlet {

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
	
		//�α��� �Է� 
		if(action.equals("login_input")) {
			
			RequestDispatcher rd = req.getRequestDispatcher("/jsp/base/login.jsp");
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
				
				req.setAttribute("message", "��ȿ���� �ʴ� ���̵��̰ų�, ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
								
				RequestDispatcher rd = req.getRequestDispatcher("/jsp/base/login.jsp");
				rd.forward(req, resp);
				
			}			
			
		}else if(action.equals("logout")) {			
			
			HttpSession session = req.getSession();
			session.removeAttribute("picarmember");
			
			RequestDispatcher rd = req.getRequestDispatcher("/jsp/base/login.jsp");
			rd.forward(req, resp);	
		
		//ȸ������
		}else if(action.equals("member_save")) {			
			PicarMemberDAO dao = new PicarMemberDAOImpl();
			PicarMember picarMember = new PicarMember();
			picarMember.setId(req.getParameter("id"));
			picarMember.setPassword(req.getParameter("password"));
			picarMember.setName(req.getParameter("name"));
			picarMember.setPhone(req.getParameter("phone"));
			picarMember.setLicense(Integer.parseInt(req.getParameter("license")));
			picarMember.setValidate(req.getParameter("validate"));
					
			boolean result = dao.insert(picarMember);
			System.out.println(result);
		
			RequestDispatcher rd =req.getRequestDispatcher("/index.jsp");
			rd.forward(req, resp);
	
		//ȸ������ �Է�ȭ��
		}else if(action.equals("sign_up")) {
			RequestDispatcher rd = req.getRequestDispatcher("/jsp/base/membership.jsp");
			rd.forward(req, resp);
									
		//���̵� �Է�ȭ��
		}else if(action.equals("idinput")) {
			
			RequestDispatcher rd = req.getRequestDispatcher("/jsp/base/membership.jsp");
			rd.forward(req, resp);
	
		//�ߺ�üũ
		}else if(action.equals("idcheck")) {

			PicarMemberDAO dao = new PicarMemberDAOImpl();
			int count = dao.checkById(req.getParameter("id"));
						
			if(count==0) 
			{
				req.setAttribute("message", "��� �Ҽ� �ִ� ���̵��Դϴ�.");
			}else {
				req.setAttribute("message", "��� �Ҽ� ���� ���̵��Դϴ�.");				
			}

			RequestDispatcher rd = req.getRequestDispatcher("/jsp/base/idcheckResult.jsp");
			rd.forward(req, resp);	
			
		}
	}	
}
