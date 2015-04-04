//package memcache;
//
//import internal.dto.AirportEntity;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//import com.alisoft.xplatform.asf.cache.ICache;
//import com.alisoft.xplatform.asf.cache.impl.DefaultCacheImpl;
////import com.connection.SqlConnectionManager;
//import com.mysql.jdbc.Connection;
//
//public class MemcacheClient {
//
//	static MemcacheClient cacheClient = null;
//
//	private static ICache<String, Object> cache = null;
//
//	private MemcacheClient() {
//	}
//
//	public static MemcacheClient getCacheInstance() {
//		if (cacheClient == null) {
//			if (cacheClient == null)
//				cacheClient = new MemcacheClient();
//
//			if (cache == null)
//				cache = new DefaultCacheImpl();
//
//			initializeCache();
//		}
//
//		return cacheClient;
//	}
//
//	public static ICache<String, Object> getCache() {
//		return cache;
//	}
//
//	public String get(String key) {
//		return (String) cache.get(key);
//	}
//
//	private static void initializeCache() {
//		//SqlConnectionManager myConnection = new SqlConnectionManager();
//		Connection con = myConnection.getConnection();
//		PreparedStatement pStmt = null;
//		ResultSet rs = null;
//		AirportEntity airport = new AirportEntity();
//
//		String query = "select * from large_airport_data";
//
//		try {
//			pStmt = con.prepareStatement(query);
//			rs = pStmt.executeQuery();
//			while (rs.next()) {
//				airport.setAirportCode(rs.getString(1));
//				airport.setAirportType(rs.getString(2));
//				airport.setAirportName(rs.getString(3));
//				airport.setCity(rs.getString(4));
//				airport.setLatitude(rs.getString(5));
//				airport.setLongitude(rs.getString(6));
//				airport.setGpsCode(rs.getString(7));
//				airport.setIataCode(rs.getString(8));
//
//				cache.put("Lat" + rs.getString(5), rs.getString(1));
//				cache.put("Lon" + rs.getString(6), rs.getString(1));
//				cache.put("Air" + rs.getString(1), airport);
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		MemcacheClient client = MemcacheClient.getCacheInstance();
//		MemcacheClient.initializeCache();
//	}
//
//}
