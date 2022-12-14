package in.com.rays.ORSprojectt4.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.com.rays.ORSprojectt4.bean.BaseBean;
import in.com.rays.ORSprojectt4.bean.UserBean;
import in.com.rays.ORSprojectt4.utility.DataUtility;
import in.com.rays.ORSprojectt4.utility.DataValidator;
import in.com.rays.ORSprojectt4.utility.ServletUtility;

/**
 * @author yashita
 *
 */
public abstract class BaseCtl extends HttpServlet {

	public static final String OP_SAVE = "Save";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String OP_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "Next";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_LOG_OUT = "Logout";
	public static final String OP_UPDATE = "Update";
	public static final String OP_RESET = "Reset";

	// Success message key constant
	public static final String MSG_SUCCESS = "success";

	// Error message key constant
	public static final String MSG_ERROR = "error";

	/**
	 * Validates input data entered by User
	 *
	 */
	protected boolean validate(HttpServletRequest request) {
	System.out.println("basectl validate");
		return true;
	}

	/**
	 * Loads list and other data required to display at HTML form
	 *
	 */
	protected void preload(HttpServletRequest request) {
		System.out.println("base ctl preload");
	}

	/**
	 * Populates bean object from request parameters
	 *
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("base ctl populate bean ");
		return null;
	}

	/**
	 * Populates Generic attributes in DTO
	 *
	 */
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {
		System.out.println("base ctl populate dto in");

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserBean userbean = (UserBean) request.getSession().getAttribute("user");

		if (userbean == null) {
			// If record is created without login
			createdBy = "root";
			modifiedBy = "root";
		} else {

			modifiedBy = userbean.getLogin();

			// If record is created first time
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}

		}

		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}

		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());

		return dto;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("service in");

		// Load the preloaded data required to display at HTML form
		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		// Check if operation is not DELETE, VIEW, CANCEL, and NULL then
		// perform input data validation

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
			// Check validation, If fail then send back to page with error
			// messages

			if (!validate(request)) {
				System.out.println("base ctl not validate");
				BaseBean bean = (BaseBean) populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}
		System.out.println("supr.service ");
		super.service(request, response);
	}

	/**
	 * Returns the VIEW page of this Controller
	 *
	 */
	// Returns the VIEW page of this Controller
	protected abstract String getView();

}
