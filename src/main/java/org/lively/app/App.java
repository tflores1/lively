package org.lively.app;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Version;

import org.apache.commons.lang3.StringEscapeUtils;
import org.lively.app.auth.Session;
import org.lively.app.auth.User;
import org.lively.app.data.Settings;
import org.lively.app.datatypes.TempCollection;

import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.http.Cookie;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Route.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Lively app!
 * 
 */
public class App {

	Version version = new Version("2.3.23");
	// private final DefaultObjectWrapperBuilder builder = new
	// DefaultObjectWrapperBuilder(
	// version);
	private final Configuration cfg;
	/* replace tempCollection with User object and Session object */
	private final User user;
	private final Session session;
	private final Settings settings;

	// private TempCollection collection = new TempCollection();

	public static void main(String[] args) throws IOException {
		new App("mongodb://141.112.17.80");
	}

	@SuppressWarnings("deprecation")
	public App(String mongoURI) throws IOException {
		final MongoClient appClient = new MongoClient(new MongoClientURI(
				mongoURI));
		final DB database = appClient.getDB("lively");
		// collection.getCollection();

		user = new User(database);
		session = new Session(database);
		settings = new Settings(database);

		cfg = createFreemarkerConfiguration();
		initializeRoutes();
	}

	abstract class FreemarkerBasedRoute extends Route {
		final Template template;

		/**
		 * Constructor
		 * 
		 * @param path
		 *            The route path which is used for matching. (e.g. /hello,
		 *            users/:name)
		 */
		protected FreemarkerBasedRoute(final String path,
				final String templateName) throws IOException {
			super(path);
			template = cfg.getTemplate(templateName);
		}

		@Override
		public Object handle(Request request, Response response) {
			StringWriter writer = new StringWriter();
			try {
				doHandle(request, response, writer);
			} catch (Exception e) {
				e.printStackTrace();
				response.redirect("/internal_error");
			}
			return writer;
		}

		protected abstract void doHandle(final Request request,
				final Response response, final Writer writer)
				throws IOException, TemplateException;

	}

	private void initializeRoutes() throws IOException {
		// template for the dashboard homepage
		get(new FreemarkerBasedRoute("/", "homepage.ftl") {
			@Override
			public void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {
				String username = session
						.findUserNameBySessionId(getSessionCookie(request));
				// String username = collection
				// .getUserBySessionId(getSessionCookie(request));

				// load the data for the dashboard
				// this will be the various api interfaces for each user
				Map<String, String> map = new HashMap<String, String>();
				SimpleHash root = new SimpleHash(map);

				if (username != null) {
					root.put("username", username);
				}

				template.process(root, writer);
			}
		});

		/**
		 * Initialize a route to various links in the dashboard
		 * 
		 */

		// handle user signup
		post(new FreemarkerBasedRoute("/signup", "signup.ftl") {
			@Override
			protected void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {
				String email = request.queryParams("email");
				String username = request.queryParams("username");
				String password = request.queryParams("password");
				String verify = request.queryParams("verify");

				HashMap<String, String> root = new HashMap<String, String>();
				root.put("username", StringEscapeUtils.escapeHtml4(username));
				root.put("email", StringEscapeUtils.escapeHtml4(email));

				if (validateSignup(username, password, verify, email, root)) {
					// good user
					System.out.println("Signup: Creating user with: "
							+ username + " " + password);
					if (!user.addUser(username, password, email)) {
						// if (!collection.validateNewUser(username)) {
						// duplicate user
						root.put("username_error",
								"Oops, that username is taken!");
						template.process(root, writer);
					} else {
						// good user, let's start a session
						// collection.addSession(username);
						String sessionID = session.startSession(username);
						// String sessionID = collection.getSessionId(username);

						response.raw().addCookie(
								new Cookie("session", sessionID));

						response.redirect("/welcome");
					}
				} else {
					// weird signup issues?
					System.out.println("Something happened here?");
					template.process(root, writer);
				}
			}
		});

		// present signup form to users
		get(new FreemarkerBasedRoute("/signup", "signup.ftl") {
			@Override
			protected void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {

				Map<String, String> map = new HashMap<String, String>();
				SimpleHash root = new SimpleHash(map);

				// initialize values for the form.
				root.put("username", "");
				root.put("password", "");
				root.put("email", "");
				root.put("password_error", "");
				root.put("username_error", "");
				root.put("email_error", "");
				root.put("verify_error", "");

				/*
				 * redirect form submittal to a dashboard creation page add
				 * devices, get familiar, skip option?
				 */

				template.process(root, writer);
			}
		});

		get(new FreemarkerBasedRoute("/welcome", "welcome.ftl") {
			@Override
			protected void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {

				String cookie = getSessionCookie(request);
				// System.out.println(cookie);

				String username = session.findUserNameBySessionId(cookie);
				// String username = collection.getUserBySessionId(cookie);
				// String username = "Guest";
				// System.out.println(username);

				if (username == null) {
					System.out
							.println("Oops, we can't identify the user, redirecting to signup");
					response.redirect("/signup");
					// username = "Guest";

				} else {
					Map<String, String> map = new HashMap<String, String>();
					SimpleHash root = new SimpleHash(map);

					root.put("username", username);

					template.process(root, writer);
				}
			}
		});

		// present the login page
		get(new FreemarkerBasedRoute("/login", "login.ftl") {
			@Override
			protected void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {
				Map<String, String> map = new HashMap<String, String>();
				SimpleHash root = new SimpleHash(map);

				root.put("username", "");
				root.put("login_error", "");

				template.process(root, writer);
			}
		});

		// process output coming from login form. On success redirect folks to
		// the welcome page
		// on failure, just return an error and let them try again.
		post(new FreemarkerBasedRoute("/login", "login.ftl") {
			@Override
			protected void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {

				String username = request.queryParams("username");
				String password = request.queryParams("password");

				System.out.println("Login: User submitted: " + username + "  "
						+ password);

				DBObject thisUser = user.validateLogin(username, password);

				// if (collection.validateLogin(username, password)) {
				if (thisUser != null) {

					// valid user, let's log them in
					// collection.addSession(username);
					String sessionID = session.startSession(thisUser.get("_id")
							.toString());
					// String sessionId = collection.getSessionId(username);

					if (sessionID == null) {
						response.redirect("/internal_error");
					} else {
						// set the cookie for the user's browser
						response.raw().addCookie(
								new Cookie("session", sessionID));

						response.redirect("/welcome");
					}
				} else {
					Map<String, String> map = new HashMap<String, String>();
					SimpleHash root = new SimpleHash(map);

					root.put("username",
							StringEscapeUtils.escapeHtml4(username));
					root.put("password", "");
					root.put("login_error", "Invalid Login");
					template.process(root, writer);
				}
			}
		});

		// allows the user to logout
		get(new FreemarkerBasedRoute("/logout", "signup.ftl") {
			@Override
			protected void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {

				String sessionID = getSessionCookie(request);

				if (sessionID == null) {
					// no session to end
					response.redirect("/login");
				} else {
					// removes session from collection
					session.endSession(sessionID);
					// collection.endSession(sessionID);

					// delete the cookie
					Cookie c = getSessionCookieActual(request);
					c.setMaxAge(0);

					response.raw().addCookie(c);

					response.redirect("/");
				}
			}
		});

		// used to process internal errors
		get(new FreemarkerBasedRoute("/internal_error", "error_template.ftl") {
			@Override
			protected void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {
				Map<String, String> map = new HashMap<String, String>();
				SimpleHash root = new SimpleHash(map);

				root.put("error", "System has encountered an error.");
				template.process(root, writer);
			}
		});

		// used to fill fields in the settings page
		get(new FreemarkerBasedRoute("/settings", "settings.ftl") {
			@Override
			protected void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {

				String sessionID = getSessionCookie(request);
				String username = session.findUserNameBySessionId(sessionID);

				String email = user.getEmail(username);

				Map<String, String> map = new HashMap<String, String>();
				SimpleHash root = new SimpleHash(map);

				if (email == null) {
					email = "";
					root.put("email", email);
					template.process(root, writer);
				} else {
					root.put("email", email);
					root.put("username", username);
					template.process(root, writer);
				}
			}

		});

		// update the password for the user account
		post(new FreemarkerBasedRoute("/settings", "settings.ftl") {
			@Override
			protected void doHandle(Request request, Response response,
					Writer writer) throws IOException, TemplateException {
				Map<String, String> map = new HashMap<String, String>();
				SimpleHash root = new SimpleHash(map);

				String sessionID = getSessionCookie(request);
				String username = session.findUserNameBySessionId(sessionID);

				String newPass = request.queryParams("new_password");

				if (!user.updatePassword(username, newPass)) {
					root.put("error", "Something went wrong, try again later!");
					template.process(root, writer);
				} else {

					root.put("password_success",
							"Thanks! Your password has now been updated!");
					template.process(root, writer);
				}
			}
		});
	}

	// helps get session cookie as string
	private String getSessionCookie(final Request request) {
		if (request.raw().getCookies() == null) {
			return null;
		}
		for (Cookie cookie : request.raw().getCookies()) {
			if (cookie.getName().equals("session")) {
				return cookie.getValue();
			}
		}
		return null;
	}

	// helps get session cookie as string
	private Cookie getSessionCookieActual(final Request request) {
		if (request.raw().getCookies() == null) {
			return null;
		}
		for (Cookie cookie : request.raw().getCookies()) {
			if (cookie.getName().equals("session")) {
				return cookie;
			}
		}
		return null;
	}

	// validates that the registration form has been filled out right and
	// username conforms
	public boolean validateSignup(String username, String password,
			String verify, String email, HashMap<String, String> errors) {
		String USER_RE = "^[a-zA-Z0-9_-]{3,20}$";
		String PASS_RE = "^.{3,20}$";
		String EMAIL_RE = "^[\\S]+@[\\S]+\\.[\\S]+$";

		errors.put("username_error", "");
		errors.put("password_error", "");
		errors.put("verify_error", "");
		errors.put("email_error", "");

		if (!username.matches(USER_RE)) {
			errors.put("username_error",
					"invalid username. try just letters and numbers");
			return false;
		}

		if (!password.matches(PASS_RE)) {
			errors.put("password_error", "invalid password.");
			return false;
		}

		if (!password.equals(verify)) {
			errors.put("verify_error", "password must match");
			return false;
		}

		if (!email.equals("")) {
			if (!email.matches(EMAIL_RE)) {
				errors.put("email_error", "Invalid Email Address");
				return false;
			}
		}

		return true;
	}

	private Configuration createFreemarkerConfiguration() {
		Configuration retVal = new Configuration(version);
		retVal.setClassForTemplateLoading(App.class, "/freemarker");
		return retVal;
	}
}
