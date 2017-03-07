package com.shbw.util.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**    
 * 用于身份认证    
*/     
public class SmtpAuth extends javax.mail.Authenticator {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
    private String user, password;      
     
    /**    
     *     
     * @param getuser    
     * @param getpassword    
     */     
    public void setUserinfo(String getuser, String getpassword) {      
        user = getuser;      
        password = getpassword;      
    }      
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {      
    	log.info("用户{}正在认证。", user);
        return new javax.mail.PasswordAuthentication(user, password);      
    }      
    public String getPassword() {      
        return password;      
    }      
    public void setPassword(String password) {      
        this.password = password;      
    }      
    public String getUser() {      
        return user;      
    }      
    public void setUser(String user) {      
        this.user = user;      
    }      
}   
