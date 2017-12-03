package presentation.tools;

public class InputCheck {

	/**
	 * 字符串是否为n位数字，若n<=0则表示不检查位数
	 * @param s 检查的字符串
	 * @param n 数字位数
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
	
	public static boolean isLegal(String s) {
		char[] list = {'-',';','%','[',']','@','!','*','/','\\','|'};
		String[] keyword = {"select", "insert", "delete", "from", "count", "drop table", "update", "truncate", "xp_cmdshell", "exec", "master", "netlocalgroup administrators", "net user", "or", "and"};
		if (s == null || "".equals(s)) return false;
		for (char c : list) if (s.contains(c+"")) return false;
		for (String str : keyword) if (s.toLowerCase().contains(str)) return false;
		return true;
	}
}