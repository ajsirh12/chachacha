package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CarDAO;
import dao.CarDAOImpl;
import dao.CarListDAO;
import dao.CarListDAOImpl;
import dao.LocationDAO;
import dao.LocationDAOImpl;
import model.CarList;
import model.JoinInsert;
import model.Location;

@WebServlet(name = "EunJungController", urlPatterns = {"/insertcar","/registercar","/carlistloc","/carlists","/carlisty"})

public class EunJungController extends HttpServlet{

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			process(req, resp);
			System.out.println("doGet");
		}

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			process(req, resp);
			System.out.println("doPost");
		}

		private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
			
			String uri = req.getRequestURI();
			
			System.out.println(uri);
			
			int lastIndex = uri.lastIndexOf("/");
			String action = uri.substring(lastIndex+1);
			
			req.setCharacterEncoding("utf-8");
			
			if(action.equals("insertcar")) {
				RequestDispatcher rd = req.getRequestDispatcher("/jsp/admin/insertcar.jsp");
				rd.forward(req, resp);
			
			// �������
			}else if(action.equals("registercar")) {
				
				CarDAO dao = new CarDAOImpl();
				JoinInsert jis = new JoinInsert();
				
				jis.setCarNum(req.getParameter("carnum"));
				jis.setCarType(Integer.parseInt(req.getParameter("cartype")));
				jis.setCost(Integer.parseInt(req.getParameter("cost")));
				jis.setCarloc(Integer.parseInt(req.getParameter("carloc")));
				
				//����� ���ִ��� true, false�� Ȯ���ϱ� ���� ���
				boolean result = dao.insert(jis);
				
				System.out.println(jis);
				System.out.println(result);
				
				RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
				rd.forward(req, resp);			
			
			//�������� ����Ʈ
			}else if(action.equals("carlistloc")) {
				LocationDAO dao = new LocationDAOImpl();
				List<Location> loclist = dao.selectAll();
				
				req.setAttribute("carlocc", loclist);
				
				System.out.println(loclist);
				
				
				RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/carlistloc.jsp");
				rd.forward(req, resp);
				
			// ��������Ʈ	
			}/*else if(action.equals("carlists")) {
				
				int carloc = Integer.parseInt(req.getParameter("carloc"));
				
				CarListDAO dao = new CarListDAOImpl();
				
				CarList carlist = dao.selectByCarloc(carloc);
				
				req.setAttribute("carlist", carlist);
				
				System.out.println(carlist);
				
				RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/carlist.jsp");
				rd.forward(req, resp);
				
			//��������Ʈ
			}*/else if(action.equals("carlisty")) {
				
				int carloc = Integer.parseInt(req.getParameter("carloc"));
				
				CarListDAO dao = new CarListDAOImpl();
				List<CarList> carlists = dao.selectbyCarloc(carloc);
		
				req.setAttribute("carlists", carlists);
				
				RequestDispatcher rd = req.getRequestDispatcher("/jsp/client/carlist.jsp");
				rd.forward(req, resp);
			}	
			
			
		}			
}
