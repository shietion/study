package cn.edu.hfut.dmic.webcollector.crawler.lagou;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.edu.hfut.dmic.webcollector.util.Constant;
import cn.edu.hfut.dmic.webcollector.util.JDBCHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JobCrawler {

	public static void main(String[] args) {
		JDBCHelper.createMariaDBTemplate(Constant.JDBCTEMP).execute("delete from position_tab_tmp");
		HtmlUnitDriver driver = new HtmlUnitDriver();
		for (int i = 1; i <= 10; i++) {
			driver.get("http://www.lagou.com/jobs/positionAjax.json?gj=3-5%E5%B9%B4&px=default&city=%E6%AD%A6%E6%B1%89&kd=java&pageNo="
					+ i);
			String source = driver.getPageSource();
			parse(source);
		}
	}

	public void crawler() {
		JDBCHelper.createMariaDBTemplate(Constant.JDBCTEMP).execute("delete from position_tab_tmp");
		HtmlUnitDriver driver = new HtmlUnitDriver();
		for (int i = 1; i <= 10; i++) {
			driver.get("http://www.lagou.com/jobs/positionAjax.json?gj=3-5%E5%B9%B4&px=default&city=%E6%AD%A6%E6%B1%89&kd=java&pageNo="
					+ i);
			String source = driver.getPageSource();
			parse(source);
		}
	}

	public static void parse(String source) {
		JSONObject json = new JSONObject();
		json = JSON.parseObject(source);
		JSONArray data = json.getJSONObject("content").getJSONArray("result");
		JdbcTemplate tmplate = JDBCHelper.getJdbcTemplate(Constant.JDBCTEMP);
		for (int i = 0; i < data.size(); i++) {
			JSONObject obj = (JSONObject) data.get(i);
			String position_id = obj.getString("positionId");
			String position_name = obj.getString("positionName");
			String position_source = "lagou";
			String need_year = "3-5年";
			String r_salary = obj.getString("salary");
			String p_createtime = obj.getString("createTime");
			String com_name = obj.getString("companyName");
			String p_education = obj.getString("education");
			String post_detail = "http://www.lagou.com/jobs/" + position_id + ".html";
			System.out.println(position_name + "\t" + com_name + "\t" + r_salary + "\t" + p_education + "\t"
					+ p_createtime + "\t" + post_detail);

			String sql = " insert into position_tab_tmp (position_source,position_id,position_name,need_year,r_salary,p_createtime,"
					+ "com_name,p_education,post_detail) values ('"
					+ position_source
					+ "','"
					+ position_id
					+ "','"
					+ position_name
					+ "','"
					+ need_year
					+ "',"
					+ "'"
					+ r_salary
					+ "','"
					+ p_createtime
					+ "','"
					+ com_name + "','" + p_education + "','" + post_detail + "')";

			tmplate.execute(sql);

		}
		StringBuffer sb = new StringBuffer();
		sb.append("insert into position_tab select * from  position_tab_tmp tp where tp.position_id not in ");
		sb.append("(select position_id from position_tab where position_source='lagou')");
		tmplate.execute(sb.toString());
	}
}
