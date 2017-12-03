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
	
	public static boolean isLegal(String s) {
		char[] list = {'-',';','%','[',']','@','!','*','/','\\','|'};
		String[] keyword = {"select", "insert", "delete", "from", "count", "drop table", "update", "truncate", "xp_cmdshell", "exec", "master", "netlocalgroup administrators", "net user", "or", "and"};
		if (s == null || "".equals(s)) return false;
		for (char c : list) if (s.contains(c+"")) return false;
		for (String str : keyword) if (s.toLowerCase().contains(str)) return false;
		return true;
	}
}