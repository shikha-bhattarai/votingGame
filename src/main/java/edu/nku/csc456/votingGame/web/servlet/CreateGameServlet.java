// CreateGameServlet.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.servlet;

import edu.nku.csc456.votingGame.web.listener.MysqlContextListener;
import edu.nku.csc456.votingGame.web.model.Game;
import edu.nku.csc456.votingGame.web.repository.GameRepository;
import edu.nku.csc456.votingGame.web.repository.PlayerRepository;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/creategame"})
public class CreateGameServlet extends HttpServlet {
	GameRepository grepo;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		grepo = (GameRepository) config.getServletContext().getAttribute(MysqlContextListener.GAME_REPOSITORY_KEY);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");

		if (action.equals("creategame")) {
			HttpSession session = req.getSession(false);
			String g_creator = (String) session.getAttribute("u_name");
			Game g = grepo.getGame(g_creator);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(g);
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			resp.flushBuffer();

			session.setAttribute("result", "created");
			session.setAttribute("g_id", g.getG_id());
			session.setAttribute("f_name", g.getG_creator());
			session.setAttribute("isStarted", g.isStarted());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		//PlayerRepository prepo = (PlayerRepository) getServletContext().getAttribute(MysqlContextListener.PLAYER_REPOSITORY_KEY);
		GameRepository grepo = (GameRepository) getServletContext().getAttribute(MysqlContextListener.GAME_REPOSITORY_KEY);
		String action = req.getParameter("action");

		// called by ng-voting-game creategame() function
		if (action.equals("creategame")) {
			String g_creator = (String) session.getAttribute("u_name");
			Game g = grepo.newGame(g_creator);

			/*String e_mail = req.getParameter("e_mail");
			String f_name = req.getParameter("f_name");
			String l_name = req.getParameter("l_name");
			String u_name= req.getParameter("u_name").toLowerCase();
			// calls UserRepository findUser() method
			Player p = prepo.findPlayer(u_name);
			// register a user that does not exist
			if (p.getU_name().equals("")) {
				// calls UserRepository saveUser() method
				prepo.savePlayer(e_mail, f_name, l_name, u_name);
				ImmutableMap<String,String> responseMap = ImmutableMap.<String, String>builder()
						.put("result", "registerSuccess")
						.put("e_mail", e_mail)
						.put("f_name", f_name)
						.put("l_name", l_name)
						.put("u_name", u_name)
						.build();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(responseMap);

				resp.setContentType("application/json");
				resp.getWriter().write(json);
				resp.flushBuffer();

				session.setAttribute("AUTHENTICATED", "true");
				session.setAttribute("e_mail", e_mail);
				session.setAttribute("f_name", f_name);
				session.setAttribute("l_name", l_name);
				session.setAttribute("u_name", u_name);

			} else {
				// do not register existing user, auto login
				/*ImmutableMap<String,String> responseMap = ImmutableMap.<String, String>builder()
						.put("result", "alreadyRegistered")
						.build();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(responseMap);

				resp.setContentType("application/json");
				resp.getWriter().write(json);
				resp.flushBuffer();

				session.setAttribute("AUTHENTICATED", "true");
				session.setAttribute("e_mail", e_mail);
				session.setAttribute("f_name", f_name);
				session.setAttribute("l_name", l_name);
				session.setAttribute("u_name", u_name);
			}
		// called by ng-voting-game login() function
		*/} /*else if (action.equals("login")) {
			String u_name = req.getParameter("u_name").toLowerCase();
			// calls PlayerRepository findPlayer method
			Player p = prepo.findPlayer(u_name);

			if (p.getU_name().equals("")) {
				// user does not exist
				ImmutableMap<String,String> responseMap = ImmutableMap.<String, String>builder()
						.put("result", "notRegistered")
						.build();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(responseMap);

				resp.setContentType("application/json");
				resp.getWriter().write(json);
				resp.flushBuffer();
			} else {
				// user exists
				// results to ng-chattycathy login() funtion
				ImmutableMap<String,String> responseMap = ImmutableMap.<String, String>builder()
						.put("result", "loginSuccess")
						.build();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json = gson.toJson(responseMap);

				resp.setContentType("application/json");
				resp.getWriter().write(json);
				resp.flushBuffer();

				session.setAttribute("AUTHENTICATED", "true");
				session.setAttribute("e_mail", p.getE_mail());
				session.setAttribute("f_name", p.getF_name());
				session.setAttribute("l_name", p.getL_name());
				session.setAttribute("u_name", u_name);
				session.setAttribute("g_won", p.getG_won());
			}
		}
	*/}
}