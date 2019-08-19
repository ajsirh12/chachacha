package controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentJoinListDAO;
import dao.CommentJoinListDAOImpl;
import dao.PicarMemberDAO;
import dao.PicarMemberDAOImpl;
import dao.QuestionDAO;
import dao.QuestionDAO1;
import dao.QuestionDAOImpl;
import dao.QuestionDAOImpl1;
import model.CommentJoinList;
import model.PicarMember;
import model.Question;
import page2.PageManager;
import page2.PageSQL;

@WebServlet(name = "KangController", urlPatterns = {"/question_list","/question_insert","/question_req_list","/question_input","/question_req_insert",
													"/question_detail","/question_detail2","/question_delete","/question_update","/question_req_admin_list"})
public class KangController extends HttpServlet {

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
		
		if(action.equals("question_insert")) {
			//�α��� �������� �̵��ϴ� �ڵ� �־��ּ���
			RequestDispatcher rd = req.getRequestDispatcher("/jsp/base/input.jsp");
			rd.forward(req, resp);
		}
		else if(action.equals("question_req_insert")) {
			
			QuestionDAO1 questionDAO = new QuestionDAOImpl1();
			Question question = new Question();
			
			question.setQuestTitle(req.getParameter("questTitle"));
			question.setQuestText(req.getParameter("questText"));	
			question.setMemberNum(Integer.parseInt(req.getParameter("memberNum")));
			
			boolean result = questionDAO.insert(question);
			
			System.out.println(question);
			
			
			RequestDispatcher rd = req.getRequestDispatcher("question_req_list?reqPage=1");
			rd.forward(req, resp);
		}
		
		else if(action.equals("question_list")) {
			
			QuestionDAO1 questionDAO = new QuestionDAOImpl1();
			List<Question> questions = questionDAO.selectAll();
			
			req.setAttribute("questions", questions);
			
			System.out.println(questions);
			
			RequestDispatcher rd = req.getRequestDispatcher("jsp/base/question.jsp");
			rd.forward(req, resp);	
		}
		
		else if(action.equals("question_req_list")) {
			
			int requestPage = Integer.parseInt(req.getParameter("reqPage"));
			PageManager pm = new PageManager(requestPage);

			CommentJoinListDAO commentJoinListDAO = new CommentJoinListDAOImpl();
			List<CommentJoinList> commentJoinLists = commentJoinListDAO.selectAll(pm.getPageRowResullt().getRowStartNumber(), pm.getPageRowResullt().getRowEndNumber());
			
			req.setAttribute("commentJoinLists", commentJoinLists);
			
			req.setAttribute("pageGroupResult", pm.getPageGroupResult(PageSQL.COMMENTJOINLIST_SELECT_ALL_COUNT));
			
			//System.out.println(commentJoinLists);
			
			RequestDispatcher rd = req.getRequestDispatcher("jsp/base/question2.jsp");
			rd.forward(req, resp);
			 
		}
		
		else if(action.equals("question_detail")) {
			
			int questnum = Integer.parseInt(req.getParameter("questnum"));
			
			CommentJoinListDAO commentJoinListDAO = new CommentJoinListDAOImpl();
			CommentJoinList commentJoinList = commentJoinListDAO.selectByQuestnum(questnum);
			
			//System.out.println(commentJoinList);
			
			req.setAttribute("commentJoinList", commentJoinList);
			
			RequestDispatcher rd = req.getRequestDispatcher("/jsp/base/commentc1.jsp");
			rd.forward(req, resp);
			
		}
		
		else if(action.equals("question_detail2")) {
			
			int questnum = Integer.parseInt(req.getParameter("questnum"));
			
			CommentJoinListDAO commentJoinListDAO = new CommentJoinListDAOImpl();
			CommentJoinList commentJoinList = commentJoinListDAO.selectByQuestnum(questnum);
			
			//System.out.println(commentJoinList);
			
			req.setAttribute("commentJoinList", commentJoinList);
			
			RequestDispatcher rd = req.getRequestDispatcher("/jsp/base/commentc2.jsp");
			rd.forward(req, resp);
			
		}
		
		else if(action.equals("question_delete")) {
			
			CommentJoinListDAO commentJoinListDAO = new CommentJoinListDAOImpl();
			int questnum = Integer.parseInt(req.getParameter("questnum"));
			boolean result = commentJoinListDAO.deleteByQuestnum(questnum);
			
			System.out.println(result);
			
			RequestDispatcher rd = req.getRequestDispatcher("question_req_list?reqPage=1");
			rd.forward(req, resp);
		}
		else if(action.equals("question_update")) {
			
			CommentJoinList commentJoinList = new CommentJoinList();
			
			commentJoinList.setQuestnum(Integer.parseInt(req.getParameter("questnum")));//������ ���� �κ� ���� �������� ��� ������ �ǳ� false�� ��			
			commentJoinList.setQuestTitle(req.getParameter("questTitle"));
			commentJoinList.setQuestText(req.getParameter("questText"));
			
			CommentJoinListDAO commentJoinListDAO = new CommentJoinListDAOImpl();
			boolean result = commentJoinListDAO.update(commentJoinList);
			
			System.out.println(result);
			System.out.println(commentJoinList);
			
			RequestDispatcher rd = req.getRequestDispatcher("question_req_list?reqPage=1");
			rd.forward(req, resp);
			
		}
		else if(action.equals("question_req_admin_list")) {
			
			int requestPage = Integer.parseInt(req.getParameter("reqPage"));
			PageManager pm = new PageManager(requestPage);

			CommentJoinListDAO commentJoinListDAO = new CommentJoinListDAOImpl();
			List<CommentJoinList> commentJoinLists = commentJoinListDAO.adminselectAll(pm.getPageRowResullt().getRowStartNumber(), pm.getPageRowResullt().getRowEndNumber());
			
			req.setAttribute("commentJoinLists", commentJoinLists);
			
			req.setAttribute("pageGroupResult", pm.getPageGroupResult(PageSQL.COMMENTJOINLIST_SELECT_ALL_COUNT));
			
			System.out.println(commentJoinLists);
			
			RequestDispatcher rd = req.getRequestDispatcher("jsp/admin/adminquestionlist.jsp");
			rd.forward(req, resp);
		}
		
	}
	
}
