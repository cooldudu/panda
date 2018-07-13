package com.wms.core.utils.common;

import java.util.Random;

public class NumberUtil {
	public static int getRandom(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}
}
