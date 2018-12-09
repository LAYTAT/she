package team.ecust.she.common;

public final class RandomNo {
	private static RandomNo randomNo;
	public static RandomNo getDefaultRandomNo() {
		if(randomNo == null)
			randomNo = new RandomNo();
		return randomNo;
	}
	
	private final static char[]CHARACTER = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
			'o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N',
			'O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'}; 
	
	private RandomNo() {
		
	}
	
	/**获取指定长度的随机编号，字符范围[A-Za-z0-9], 0 < length <20 else return ""*/
	public String getRandomNo(int length) {
		if(length <= 0 || length > 20)
			return "";
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < length; i++)
			buffer.append(getRandomChar());
		return buffer.toString();
	}
	
	private char getRandomChar() {
		return CHARACTER[(int)(Math.random()*CHARACTER.length)];
	}
}