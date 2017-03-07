//非空验证
function isNull(test){
	if(test==''){
		return true;
	}else{
		return false;
	}
}

//数字验证
function isNum(test){
	var reg=new RegExp('^[0-9]*$');
	if(reg.test(test)){
		return true;
	}else{
		return false;
	}
}

//验证联系方式
function isPhoneOrTel(phone){
	var regTel=new RegExp("^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$");
	var regPhone=new RegExp(/^\d{11}$/);
	if(regTel.test(phone)||regPhone.test(phone)){
		return true;
	}else{
		return false;
	}
}

//验证邮箱
function isEmail(test){
	var reg=new RegExp('^\w+[-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$');
	if(reg.test(test)){
		return true;
	}else{
		return false;
	}
}
//验证邮编
function isPostCode(test){
	var reg = new RegExp("^[1-9][0-9]{5}$");
	if(reg.test(test)){
		return true;
	}else{
		return false;
	}
}
//验证文本长度(name位字段值，length为对应数据库字段长度)
function isLength(name,length){
	if(name.length<=length){
		return true;
	}else{
		return false;
	}
}