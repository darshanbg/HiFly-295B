package com.recommendation.memcache;

import java.util.HashSet;

public class BlockedAirportsCache {

	static BlockedAirportsCache blockedAirportCache = null;
	private HashSet<String> blockedSet;

	public HashSet<String> getBlockedSet() {
		return blockedSet;
	}

	private BlockedAirportsCache() {
		blockedSet = new HashSet<String>();
	}

	public static BlockedAirportsCache getInstance() {
		if (blockedAirportCache == null) {
			blockedAirportCache = new BlockedAirportsCache();
		}

		return blockedAirportCache;
	}
}
