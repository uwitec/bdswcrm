package com.sw.cms.util;

public class MoneyUtil {

	/** ��д���� */
	private static final String[] NUMBERS = { "��", "Ҽ", "��", "��", "��", "��",
			"½", "��", "��", "��" };

	/** �������ֵĵ�λ */
	private static final String[] IUNIT = { "Ԫ", "ʰ", "��", "Ǫ", "��", "ʰ", "��",
			"Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ" };

	/** С�����ֵĵ�λ */
	private static final String[] DUNIT = { "��", "��", "��" };

	/**
	 * �õ���д��
	 */
	public static String toChinese(String str) {
		if(str == null || str.equals("") || str.equals("null") || str.equals("0") || str.equals("0.0") || str.equals("0.00")){
			return "";
		}

		str = str.replaceAll(",", "");// ȥ��","
		String integerStr;// ������������
		String decimalStr;// С����������

		// ��ʼ���������������ֺ�С������
		if (str.indexOf(".") > 0) {
			integerStr = str.substring(0, str.indexOf("."));
			decimalStr = str.substring(str.indexOf(".") + 1);
		} else if (str.indexOf(".") == 0) {
			integerStr = "";
			decimalStr = str.substring(1);
		} else {
			integerStr = str;
			decimalStr = "";
		}
		// integerStrȥ����0������ȥ��decimalStr��β0(����������ȥ)
		if (!integerStr.equals("")) {
			integerStr = Long.toString(Long.parseLong(integerStr));
			if (integerStr.equals("0")) {
				integerStr = "";
			}
		}
		// overflow��������������ֱ�ӷ���
		if (integerStr.length() > IUNIT.length) {
			System.out.println(str + ":������������");
			return str;
		}

		int[] integers = toArray(integerStr);// ������������
		boolean isMust5 = isMust5(integerStr);// ������λ
		int[] decimals = toArray(decimalStr);// С����������
		
		String returnStr = getChineseInteger(integers, isMust5) + getChineseDecimal(decimals);
		
		String lastStr = returnStr.substring(returnStr.length()-1, returnStr.length());
		
		if(!lastStr.equals("��") && !lastStr.equals("��"))
			returnStr += "��";
		
		return returnStr;
	}

	/**
	 * �������ֺ�С������ת��Ϊ���飬�Ӹ�λ����λ
	 */
	private static int[] toArray(String number) {
		int[] array = new int[number.length()];
		for (int i = 0; i < number.length(); i++) {
			array[i] = Integer.parseInt(number.substring(i, i + 1));
		}
		return array;
	}

	/**
	 * �õ����Ľ����������֡�
	 */
	private static String getChineseInteger(int[] integers, boolean isMust5) {
		StringBuffer chineseInteger = new StringBuffer("");
		int length = integers.length;
		for (int i = 0; i < length; i++) {
			// 0�����ڹؼ�λ�ã�1234(��)5678(��)9012(��)3456(Ԫ)
			// ���������10(ʰԪ��ҼʰԪ��Ҽʰ��Ԫ��ʰ��Ԫ)
			String key = "";
			if (integers[i] == 0) {
				if ((length - i) == 13)// ��(��)(����)
					key = IUNIT[4];
				else if ((length - i) == 9)// ��(����)
					key = IUNIT[8];
				else if ((length - i) == 5 && isMust5)// ��(������)
					key = IUNIT[4];
				else if ((length - i) == 1)// Ԫ(����)
					key = IUNIT[0];
				// 0����0ʱ���㣬���������һλ
				if ((length - i) > 1 && integers[i + 1] != 0)
					key += NUMBERS[0];
			}
			chineseInteger.append(integers[i] == 0 ? key
					: (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
		}
		return chineseInteger.toString();
	}

	/**
	 * �õ����Ľ���С�����֡�
	 */
	private static String getChineseDecimal(int[] decimals) {
		StringBuffer chineseDecimal = new StringBuffer("");
		for (int i = 0; i < decimals.length; i++) {
			// ��ȥ3λС��֮���
			if (i == 3)
				break;
			chineseDecimal.append(decimals[i] == 0 ? ""
					: (NUMBERS[decimals[i]] + DUNIT[i]));
		}
		return chineseDecimal.toString();
	}

	/**
	 * �жϵ�5λ���ֵĵ�λ"��"�Ƿ�Ӧ�ӡ�
	 */
	private static boolean isMust5(String integerStr) {
		int length = integerStr.length();
		if (length > 4) {
			String subInteger = "";
			if (length > 8) {
				// ȡ�ôӵ�λ������5����8λ���ִ�
				subInteger = integerStr.substring(length - 8, length - 4);
			} else {
				subInteger = integerStr.substring(0, length - 4);
			}
			return Integer.parseInt(subInteger) > 0;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		String number = "10";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
		number = "2351.1";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
		number = "1234567890123456.12366";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
		number = "0.0798";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
		number = "10,001,000.09";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
		number = "01.107700";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
	}

}
