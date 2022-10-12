package in.com.rays.ORSprojectt4.utility;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import in.com.rays.ORSprojectt4.bean.DropdownListBean;

/**
 * @author yashita
 *
 */
public class HTMLUtility {
	/**
	 * Create HTML SELECT list from MAP paramters values v
	 * 
	 */
	public static String getList(String name, String selectedVal, HashMap<String, String> map) {
		System.out.println("html utility get list hashmap");
		StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'>");

		Set<String> keys = map.keySet();
		String val = null;
		boolean select = true;
		if (select) {

			sb.append(
					"<option style='width: 172px;  height: 35px;' selected value=''>-------------Select-------------</option>");
		}
		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * Create HTML SELECT List from List parameter
	 * 
	 */
	public static String getList(String name, String selectedVal, List list) {
		System.out.println("html utility get list list");
		Collections.sort(list);

		StringBuffer sb = new StringBuffer("<select class='form-control' name='" + name + "'>");

		boolean select = true;
		if (select) {

			sb.append(
					"<option style='width: 172px;  height: 35px;' selected value=''>-------------Select-------------</option>");
		}

		List<DropdownListBean> dd = (List<DropdownListBean>) list;
		String key = null;
		String val = null;

		for (DropdownListBean obj : dd) {
			key = obj.getKey();
			val = obj.getValue();

			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * Returns Error Message with HTML tag and CSS
	 * 
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		System.out.println("html utility error message");
		String msg = ServletUtility.getErrorMessage(request);
		if (!DataValidator.isNull(msg)) {
			msg = "<p class='st-error-header'>" + msg + "</p>";
		}
		return msg;
	}

	/**
	 * Returns Success Message with HTML tag and CSS
	 * 
	 */
	public static String getSuccessMessage(HttpServletRequest request) {
		System.out.println("html utility succes message");
		String msg = ServletUtility.getSuccessMessage(request);
		if (!DataValidator.isNull(msg)) {
			msg = "<p class='st-success-header'>" + msg + "</p>";
		}
		return msg;
	}
}