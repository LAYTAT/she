package team.ecust.she.model;


/**华东理工大学教务处模型类, 对应数据库里的ecust表
 * */
public final class Ecust {
/**性别的枚举。*/
	public enum Gender {
		MALE,
		FEMALE
	}
private String studentNo;
private String identityCard;
private String studentName;
private Gender  sex;
private String major;

public Ecust(String studentNo){
	this.studentNo=studentNo;
	this.identityCard=null;
	this.studentName=null;
	this.sex=null;
	this.major=null;
}

public String getStudentNo() {
	return studentNo;
}

public void setStudentNo(String studentNo) {
	this.studentNo = studentNo;
}

public String getIdentityCard() {
	return identityCard;
}

public void setIdentityCard(String identityCard) {
	this.identityCard = identityCard;
}

public String getStudentName() {
	return studentName;
}

public void setStudentName(String studentName) {
	this.studentName = studentName;
}

public Gender getSex() {
	return sex;
}

public void setSex(Gender sex) {
	this.sex = sex;
}

public String getMajor() {
	return major;
}

public void setMajor(String major) {
	this.major = major;
}

/**
* 根据学生性别枚举返回对应存储在数据库里的字符串。
* @return 性别对应的字符串，如果性别为空，则返回空对象
*/
public String switchEcustSexToString() {
	if(sex == null)
		return null;
	switch (sex) {
	case MALE:   return "male";
	case FEMALE: return "female";
	default: return null;
	}
}

/**
* 根据学生性别字符串改变对应对象的性别枚举属性。
* @param sex 需要转换的性别字符串，为空对象或不合法则不改变对象的属性
*/
public void switchEcustSexToEnum(String sex) {
	if(sex == null)
		return;
	switch (sex) {
	case "male":   this.sex = Gender.MALE;
		break;
	case "female": this.sex = Gender.FEMALE;
		break;
	default: return;
	}
}
	
}
