# Java-App-Config-Library

```java
import com.laurencetrippen.jacl.reflect.ConfigFile;
import com.laurencetrippen.jacl.reflect.ConfigProperty;

@ConfigFile(path = "config/app.properties")
public class Configuration {

	@ConfigProperty
	private int port;

	@ConfigProperty
	private String ip;

	@ConfigProperty
	private float timeout;

	@ConfigProperty
	private double currency;

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

	public double getCurrency() {
		return currency;
	}

	public void setCurrency(double currency) {
		this.currency = currency;
	}

	public boolean isLazyLoading() {
		return lazyLoading;
	}

	public void setLazyLoading(boolean lazyLoading) {
		this.lazyLoading = lazyLoading;
	}

	@Override
	public String toString() {
		return "Configuration [port=" + port + ", ip=" + ip + ", timeout=" + timeout + ", currency=" + currency
				+ ", lazyLoading=" + lazyLoading + "]";
	}
}

import com.laurencetrippen.jacl.ConfigManager;

public class Tester {

	public static void main(String[] args) {
		ConfigManager<Configuration> cm = new ConfigManager<>(Configuration.class);
		cm.generateConfig();
		
		Configuration cfg = cm.load();
		
		cfg.setPort(880);
		cfg.setIp("255.255.255.255");
		cfg.setTimeout(20.2f);
		cfg.setCurrency(20.4054);
		cfg.setLazyLoading(true);
		
		cm.save(cfg);
	}
}
```
