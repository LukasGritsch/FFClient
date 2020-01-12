package ffclient;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;

public class DemoApplicationConfiguration implements ApplicationConfiguration {

	public void configure(Application application) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(WebClient.PAGE_TITLE, "FF Sellrain Admin");
		application.addStyleSheet( "my.application.aquablue", "/theme.css" );
		properties.put( WebClient.THEME_ID, "my.application.aquablue" );
		application.addEntryPoint("/client", DemoApplication.class, properties );
	}

}
