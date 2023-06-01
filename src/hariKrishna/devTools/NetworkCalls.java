package hariKrishna.devTools;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v113.fetch.Fetch;
import org.openqa.selenium.devtools.v113.network.Network;
import org.openqa.selenium.devtools.v113.network.model.Request;
import org.openqa.selenium.devtools.v113.network.model.Response;

import com.google.common.collect.ImmutableList;

public class NetworkCalls {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		DevTools devtools = driver.getDevTools();
		devtools.createSession();
		devtools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devtools.addListener(Network.requestWillBeSent(), request -> {
			Request req = request.getRequest();
			System.out.println(req.getUrl());
		});
		devtools.addListener(Network.responseReceived(), response -> {
			Response res = response.getResponse();
			System.out.println(res.getUrl() + " with response Code: " + res.getStatus());
		});
		devtools.send(Network.setBlockedURLs(ImmutableList.of("*.jpeg*", "*.jpg*", "*.gif*", "*.css*", "*.png*")));
		devtools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		devtools.addListener(Fetch.requestPaused(), request -> {
			Request req = request.getRequest();
			System.out.println(req.getUrl());
			if (req.getUrl().contains("amazon")) {
				String url = req.getUrl().replace(".com", ".in");
				System.out.println(req.getUrl());
				devtools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(url), Optional.empty(),
						Optional.empty(), Optional.empty(), Optional.empty()));
			} else {
				devtools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(req.getUrl()), Optional.empty(),
						Optional.empty(), Optional.empty(), Optional.empty()));
			}
		});
		driver.get("https://www.amazon.com");
		File file = driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "\\reports\\screenshots\\Network\\"
				+ NetworkCalls.class.getSimpleName() + ".png"));
		Thread.sleep(3000);
		driver.close();

	}

}
