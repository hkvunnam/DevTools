package hariKrishna.devTools;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v113.emulation.Emulation;

public class Emulat {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		ChromeDriver driver = new ChromeDriver();
		DevTools devtools = driver.getDevTools();
		devtools.createSession();
		devtools.send(Emulation.setAutoDarkModeOverride(Optional.of(false)));
		devtools.send(Emulation.setDeviceMetricsOverride(412, 914, 39, true, Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty()));
		devtools.send(Emulation.clearGeolocationOverride());
		devtools.send(Emulation.setGeolocationOverride(Optional.of((Number) 35), Optional.of((Number) 135),
				Optional.of((Number) 1)));
		driver.get("http://www.google.com");
		driver.findElement(By.name("q")).sendKeys(Keys.chord("Places to vist", Keys.ENTER));
		File file = driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "\\reports\\screenshots\\Emulation\\"
				+ Emulat.class.getSimpleName() + ".png"));
		Thread.sleep(3000);
		driver.close();
	}

}
