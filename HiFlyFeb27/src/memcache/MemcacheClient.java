package memcache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.alisoft.xplatform.asf.cache.ICache;
import com.alisoft.xplatform.asf.cache.impl.DefaultCacheImpl;

public class MemcacheClient {

	static MemcacheClient cacheClient = null;

	private static ICache<String, Object> cache = null;

	private MemcacheClient() {

	}

	public static MemcacheClient getCacheInstance() {
		if (cacheClient == null) {
			if (cacheClient == null)
				cacheClient = new MemcacheClient();

			if (cache == null)
				cache = new DefaultCacheImpl();

			// initializeCache();
		}

		return cacheClient;
	}

	public static ICache<String, Object> getCache() {
		return cache;
	}

	public String get(String key) {
		return (String) cache.get(key);
	}

	private void initializeCache() {
		BufferedReader br = null;
		String filePath = "C:/Users/Darshan/Desktop/295A/airportsData.csv";
		try {
			String sCurrentLine;

			br = new BufferedReader(new FileReader(filePath));

			int i = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] attributeArray = sCurrentLine.split(",");

			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
