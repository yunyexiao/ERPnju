package presentation.tools;

public class InputCheck {

	/**
	 * �ַ����Ƿ�Ϊnλ���֣���n<=0���ʾ�����λ��
	 * @param s �����ַ���
	 * @param n ����λ��
	 * @return
	 */
	public static boolean isAllNumber(String s, int n) {
		if (s == null || "".equals(s)) return false;
		if (n > 0 && s.length() != n) return false;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) < '0' || s.charAt(i) > '9') return false;
		}
		return true;
	}
	/**
	 * �ж��Ƿ�Ϊһ��С��
	 * @param s
	 * @return
	 */
	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isLegal(String s) {
		char[] list = {'-',';','%','[',']','@','!','*','/','\\','|'};
		String[] keyword = {"select", "insert", "delete", "from", "count", "drop table", "update", "truncate", "xp_cmdshell", "exec", "master", "netlocalgroup administrators", "net user", "or", "and"};
		if (s == null || "".equals(s)) return false;
		for (char c : list) if (s.contains(c+"")) return false;
		for (String str : keyword) if (s.toLowerCase().contains(str)) return false;
		return true;
	}
	
	public static boolean isLegalOrBlank(String s) {
		char[] list = {'-',';','%','[',']','@','!','*','/','\\','|'};
		String[] keyword = {"select", "insert", "delete", "from", "count", "drop table", "update", "truncate", "xp_cmdshell", "exec", "master", "netlocalgroup administrators", "net user", "or", "and"};
		if (s == null) return false;
		if ("".equals(s)) return true;
		for (char c : list) if (s.contains(c+"")) return false;
		for (String str : keyword) if (s.toLowerCase().contains(str)) return false;
		return true;
	}
	/**
	 * �ַ����Ƿ�Ϊnλ���ֻ���ĸ����n<=0���ʾ�����λ��
	 * @param s �����ַ���
	 * @param n ����λ��
	 * @return
	 */
	public static boolean isAlnum(String s, int n) {
		if (s == null || "".equals(s)) return false;
		if (! isLegal(s)) return false;
		if (n > 0 && s.length() != n) return false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c < '0') return false;
			if (c > '9' && c < 'A') return false;
			if (c > 'Z' && c < 'a') return false;
			if (c > 'z') return false;
		}
		return true;
	}
}
