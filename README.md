# by Laurence Trippen

At first you must create an class which represents the 
Java Properties files as an object. In this example the class will take the name Configuration.
The class needs the *@ConfigFile* annotation with an path as parameter.
The library will recognize the *@ConfigFile* annotation.

All class members you want to save in the properties file must be annotated
with *@ConfigProperty* and additional getter and setter must be added.

## The Configuration class

```java
import com.laurencetrippen.jpg.ConfigFile;
import com.laurencetrippen.jpg.ConfigProperty;

@ConfigFile(path = "config/app.properties")
public class Configuration {

	@ConfigProperty
	private int port;

	@ConfigProperty
	private String ip;

	@ConfigProperty
	private float timeout;

	@ConfigProperty
	private double delay;

	@ConfigProperty
	private boolean lazyLoading;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public float getTimeout() {
		return timeout;
	}

	public void setTimeout(float timeout) {
		this.timeout = timeout;
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	public boolean isLazyLoading() {
		return lazyLoading;
	}

	public void setLazyLoading(boolean lazyLoading) {
		this.lazyLoading = lazyLoading;
	}
}
```

To use and manage the configuration class, you must instantiate a ConfigManager.
The ConfigManager class is a generic class that requires the configuration class as a parameter
In addition the constructor of the ConfigMananger class needs the class type 
of the desired configuration class.

After the creation of an ConfigManager object you need to call the
*generateConfig()* method to generate the Java properties file in
the filesystem.

After this you can load the properties file in the represtional object
which we defined as Configuraion class in this example.

With the represtional object you can manipulate the data with getters and setter. 
Last but not least, you can also save the data using the *save()* method
of the ConfigManager. ;-)

## The Test class
```java
import com.laurencetrippen.jpg.ConfigManager;

public class Tester {

	public static void main(String[] args) {
		ConfigManager<Configuration> cm = new ConfigManager<>(Configuration.class);
		cm.generateConfig();
		
		Configuration cfg = cm.load();
		
		cfg.setPort(880);
		cfg.setIp("255.255.255.255");
		cfg.setTimeout(20.2f);
		cfg.setDelay(20.4054);
		cfg.setLazyLoading(true);
		
		cm.save(cfg);
	}
}
```
