package com.housingboard.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.housingboard.dao.AdsDao;
import com.housingboard.dao.AdsDaoImpl;
import com.housingboard.model.Ads;


@WebServlet(urlPatterns = "/ads/*")
public class AdsController extends HttpServlet {
	int userId;
	private static final long serialVersionUID = 1L;
	
	private AdsDaoImpl adsDao = new AdsDaoImpl();
	
	public AdsController() 
	{
		super();
		
	}

	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 System.out.println("Hello world!");
			doGet(request, response);
			HttpSession session = request.getSession(false);	
	        if(session.getAttribute("userAuthToken") == null) {
	        	RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
	        }else {
	        	 try {
	        		 userId = Integer.parseInt(session.getAttribute("userAuthToken").toString());
		    			String[] values = request.getRequestURI().split("/");
		    	        System.out.print(values); 
		    	        System.out.print(values[3]);
	        		switch (values[3]) {
			            case "listAds":
			            	listAds(request, response, userId);
			                break;
			            case "AdminlistAds":
			            	AdminlistAds(request, response);
			                break;
			            case "insert":
			                insertAds(request, response, userId);
			                break;
			            case "delete":
			            	System.out.println("in delete");
			            	int id = Integer.parseInt(request.getParameter("id"));
			                deleteAds(request, response,id);
			                break;
			            case "update":
			                updateAds(request, response, userId);
			                break;
			            default:
			                listAds(request, response, userId);
			                break;
	        			}
			        } catch (SQLException ex) {
						
			            throw new ServletException(ex);
			        }
	        }

	    }
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    }
	 
	    private void listAds(HttpServletRequest request, HttpServletResponse response,int userId)
	            throws SQLException, IOException, ServletException {
	        List<Ads> listAds = adsDao.listAllAds(userId);
	        request.setAttribute("listAds", listAds);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/AdList.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	    private void AdminlistAds(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        List<Ads> listAds = adsDao.AdminlistAllAds();
	        request.setAttribute("AdminAdsList", listAds);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/allAds.jsp");
	        dispatcher.forward(request, response);
	    }
	 
	    private void insertAds(HttpServletRequest request, HttpServletResponse response, int userId)
	            throws SQLException, IOException {
	    	System.out.println("Inside ads");
	        String title = request.getParameter("title");
	        String imageUrl = request.getParameter("imageUrl");	        
	        String description = request.getParameter("description");
	        String community = request.getParameter("community");
	        String preferences = request.getParameter("preferences");
	        String leasingtype = request.getParameter("leasingtype");
	        boolean sharing = (request.getParameter("sharing").toString().equals("YES") ? true : false);
	        int apartmentTypeId =  Integer.parseInt(request.getParameter("apartmentTypeId"));
	        Ads ads = new Ads(title, imageUrl, userId,  true, description,
	    		community, preferences, leasingtype, sharing, apartmentTypeId);
	        
	        boolean flag = adsDao.insertAds(ads);
	        
	        String redirectURL = "http://localhost:8080/HousingBoard/AdList.jsp";
	        response.sendRedirect(redirectURL);
	    }
	 
	    private void updateAds(HttpServletRequest request, HttpServletResponse response, int usId)
	            throws SQLException, IOException {
	        String title = request.getParameter("title");
	        String imageUrl = request.getParameter("imageUrl");
	        int userId = Integer.parseInt(request.getParameter("userId"));
	        String description = request.getParameter("description");
	        String community = request.getParameter("community");
	        String preferences = request.getParameter("preferences");
	        String leasingtype = request.getParameter("leasingtype");
	        boolean sharing = (request.getParameter("sharing").toString().equals("YES") ? true : false);
	        String is = request.getParameter(String.valueOf("isAvailable"));
	        boolean isAvailable = Boolean.valueOf(is);
	        int apartmentTypeId =  Integer.parseInt(request.getParameter("apartmentTypeId"));
	        Ads ads = new Ads(title, imageUrl, userId,isAvailable , description,
	    			          community, preferences, leasingtype, sharing, apartmentTypeId);
	        adsDao.updateAds(ads, usId);
	        response.sendRedirect("list");
	    }	 
	    private void deleteAds(HttpServletRequest request, HttpServletResponse response, int id)
	            throws SQLException, IOException,ServletException {
	    	String viewUrl;
	    	HttpSession session = request.getSession(false);	
	    	System.out.println("in delete");
	        Ads ads = new Ads(id);
	        adsDao.deleteAds(ads, id);
	        viewUrl = "/deleteSuccess.jsp";
	        session.setAttribute("message", "Deleted successfully");
	        RequestDispatcher dispatcher = request.getRequestDispatcher(viewUrl);
			dispatcher.forward(request, response);	
	    }
}


