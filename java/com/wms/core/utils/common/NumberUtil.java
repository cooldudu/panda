package com.wms.core.utils.common;

import java.util.Random;

public class NumberUtil {
	public static int getRandom(int min, int max) {
		var random = new Random();
		var s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}
}
