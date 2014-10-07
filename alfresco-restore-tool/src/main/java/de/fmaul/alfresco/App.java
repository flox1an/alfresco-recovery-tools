package de.fmaul.alfresco;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	public static void main(String[] args) {
		BasicConfigurator.configure();

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

		ctx.scan("de.fmaul.alfresco");
		ctx.refresh();

		RestoreService restoreService = ctx.getBean(RestoreService.class);
		restoreService.runRestore();
	}

}
