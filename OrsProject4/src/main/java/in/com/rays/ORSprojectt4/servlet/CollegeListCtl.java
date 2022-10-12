package in.com.rays.ORSprojectt4.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.com.rays.ORSprojectt4.bean.BaseBean;
import in.com.rays.ORSprojectt4.bean.CollegeBean;
import in.com.rays.ORSprojectt4.bean.RoleBean;
import in.com.rays.ORSprojectt4.exception.ApplicationException;
import in.com.rays.ORSprojectt4.model.CollegeModel;
import in.com.rays.ORSprojectt4.utility.DataUtility;
import in.com.rays.ORSprojectt4.utility.PropertyReader;
import in.com.rays.ORSprojectt4.utility.ServletUtility;

/**
 * @author yashita
 *
 */
@WebServlet(name = "CollegeListCtl", urlPatterns = { "/ctl/CollegeListCtl" })
public class CollegeListCtl extends BaseCtl {

	// private static Logger log = Logger.getLogger(CollegeListCtl.class);
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("college list preload");
		CollegeModel mdoel = new CollegeModel();
		
		try {
			List cList = mdoel.list();
			System.out.println(cList.isEmpty());
			request.setAttribute("namee", cList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("college list populate bean");
		CollegeBean bean = new CollegeBean();

		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));

		return bean;
	}

	/**
	 * Contains display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("college list do get in");
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		CollegeBean bean = (CollegeBean) populateBean(request);
		CollegeModel model = new CollegeModel();

		List list = null;

		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("college list model.search");
		} catch (ApplicationException e) {
			// log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}System.out.println("college list do get out");

		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}

		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains submit logic
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("college list do post in");
		// log.debug("CollegeListCtl doPost Start");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		String[] ids = request.getParameterValues("ids");

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		CollegeBean bean = (CollegeBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		CollegeModel model = new CollegeModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)
					|| OP_RESET.equalsIgnoreCase(op) ) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					System.out.println("college list op search");
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					System.out.println("college list op next");
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					System.out.println("college list previous");
					pageNo--;
				} else if (OP_RESET.equalsIgnoreCase(op)) {
					System.out.println("college list reset");
					ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
					return;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				System.out.println("college list new");
				ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				System.out.println("college list delete");
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					CollegeBean deletebean = new CollegeBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}

			list = model.search(bean, pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);

			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			// log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}System.out.println("college list do post out");
		// log.debug("CollegeListCtl doGet End");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_LIST_VIEW;
	}

}
