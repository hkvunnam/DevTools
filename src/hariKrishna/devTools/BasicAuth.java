package hariKrishna.devTools;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v113.network.Network;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

public class BasicAuth {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ChromeDriver driver = new ChromeDriver();
		DevTools devtools = driver.getDevTools();
		devtools.createSession();
		devtools.send(Network.emulateNetworkConditions(false, 100, 10000, 10000, Optional.empty()));
		Predicate<URI> predicateuri = uri -> uri.getHost().contains("httpbin");
		((HasAuthentication)driver).register(predicateuri, UsernameAndPassword.of("foo", "bar"));
		driver.get("http://httpbin.org/basic-auth/foo/bar");
		File file = driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "\\reports\\screenshots\\BasicAuth\\"
				+ Emulat.class.getSimpleName() + ".png"));
		driver.navigate().to("https://admin:admin@the-internet.herokuapp.com/basic_auth");
		file = driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "\\reports\\screenshots\\BasicAuth\\"
				+ Emulat.class.getSimpleName() + "1.png"));
		LogEntries entries = driver.manage().logs().get(LogType.BROWSER);
		List<LogEntry> entry = entries.getAll();
		entry.stream().forEach(s->System.out.println(s));
		driver.close();
	}

}
