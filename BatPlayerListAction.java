package com.player.act;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.player.data.player;

public class BatPlayerListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String link = "http://www.ncdinos.com/stats/batterstats?season=2018&teamCode=NC";
		Document doc = Jsoup.connect(link).get();
		Elements rows = doc.select("div.wrap_teamleague01 tbody tr");

		player batter = new player();
		ArrayList<player> list = new ArrayList<player>();

//		System.out.println(rows);
//		System.out.println(rows.get(0).getElementsByTag("td").get(0).text());
		for (Element row : rows) {
			Elements tds = row.getElementsByTag("td");
//			System.out.println(tds.text());
			
			batter.setRank(tds.get(0).text());
			batter.setName(tds.get(1).text());
			batter.setTeam(tds.get(2).text());
			batter.setPosition(tds.get(3).text());
			batter.setGameCount(tds.get(4).text());
			batter.setAB(tds.get(5).text());
			batter.setR(tds.get(6).text());
			batter.setH(tds.get(7).text());
			batter.setB2(tds.get(8).text());
			batter.setB3(tds.get(9).text());
			batter.setHR(tds.get(10).text());
			batter.setRBI(tds.get(11).text());
			batter.setSB(tds.get(12).text());
			batter.setBB(tds.get(13).text());
			batter.setSO(tds.get(14).text());
			batter.setGIDP(tds.get(15).text());
			batter.setAVG(tds.get(16).text());
			batter.setSLG(tds.get(17).text());
			batter.setOBP(tds.get(18).text());
			
			list.add(batter);
		}
		link = "http://www.ncdinos.com/stats/batterstats?season=2018&teamCode=NC&page=2";
		doc = Jsoup.connect(link).get();
		rows = doc.select("div.wrap_teamleague01 tbody tr");
		
		for (Element row : rows) {
			Elements tds = row.getElementsByTag("td");
//			System.out.println(tds.text());
			
			batter.setRank(tds.get(0).text());
			batter.setName(tds.get(1).text());
			batter.setTeam(tds.get(2).text());
			batter.setPosition(tds.get(3).text());
			batter.setGameCount(tds.get(4).text());
			batter.setAB(tds.get(5).text());
			batter.setR(tds.get(6).text());
			batter.setH(tds.get(7).text());
			batter.setB2(tds.get(8).text());
			batter.setB3(tds.get(9).text());
			batter.setHR(tds.get(10).text());
			batter.setRBI(tds.get(11).text());
			batter.setSB(tds.get(12).text());
			batter.setBB(tds.get(13).text());
			batter.setSO(tds.get(14).text());
			batter.setGIDP(tds.get(15).text());
			batter.setAVG(tds.get(16).text());
			batter.setSLG(tds.get(17).text());
			batter.setOBP(tds.get(18).text());
			
			list.add(batter);
		}
		
		request.setAttribute("list", list);
		
		ActionForward forward = new ActionForward();

		forward.setRedirect(false);
		forward.setPath("/player/playerStats.jsp");
		System.out.println(forward.getPath());
		
		return forward;
	}

}
