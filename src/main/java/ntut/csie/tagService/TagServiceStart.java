package ntut.csie.tagService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import ntut.csie.tagService.gateways.database.SqlDatabaseHelper;
import ntut.csie.tagService.useCase.DomainEventListener;

@SuppressWarnings("serial")
public class TagServiceStart extends HttpServlet implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Tag Service Start!");
		SqlDatabaseHelper sqlDatabaseHelper = new SqlDatabaseHelper();
		sqlDatabaseHelper.initialize();
		ApplicationContext context = ApplicationContext.getInstance();
		DomainEventListener.getInstance().init(context.newTagRepository(), context.newAssignedTagRepository());
	}
}