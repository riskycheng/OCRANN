package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class OtsuThresholding {

	private static int[] get_histogram(double[] pixels) {
		int[] histogram = new int[256];
		Arrays.fill(histogram, 0);
		for (int i = 0; i < pixels.length; i++) {
			int color = (int) pixels[i];
			histogram[color]++;
		}
		return histogram;
	}

	public static int getThreshold(double[] pixels) {		
		int[] histogram = get_histogram(pixels);
		double wF = 0, wB = 0;
		double mF = 0, mB = 0;
		double varMax = 0;
		int threshold = 0;
		int total = pixels.length;
		double sumB = 0, sum = 0;
		for (int i = 0; i < 256; i++)
			sum += i * histogram[i];
		for (int i = 0; i < 256; i++) {
			wB += histogram[i];
			if (wB == 0)
				continue;
			wF = total - wB;
			if (wF == 0)
				break;
			sumB += (i * histogram[i]);
			mB = sumB / wB;
			mF = (sum - sumB) / wF;
			double varBetween = wB * wF * (mB - mF) * (mB - mF);
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = i;
			}
		}
		return threshold;
	}

	public static ArrayList<Byte> convertToBinary(double[] pixels) {
		// TODO: Take ArrayList instead of double[]
		ArrayList<Byte>ret = new ArrayList<Byte>();
		int threshold = getThreshold(pixels);
		for (int i = 0; i < pixels.length; i++) {
			if ((int)pixels[i] > threshold)
				ret.add((byte)1);
			else
				ret.add((byte)0);
		}
		return ret;
	}

}
