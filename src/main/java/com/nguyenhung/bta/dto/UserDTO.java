package com.nguyenhung.bta.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {
	@NotBlank(message = "Hãy nhập tên người dùng")
	private String name;
	@NotBlank(message = "Hãy nhập tên tài khoản")
	private String username;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Mật khẩu chứa ít nhất 1 ký tự hoa, thường và số")
	@NotBlank(message = "Hãy nhập mật khẩu")
	private String password;
	private String confPassword;
	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Hãy nhập đúng định dạng email")
	@NotBlank(message = "Hãy nhập email")
	private String email;
	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", username=" + username + ", password=" + password + ", confPassword="
				+ confPassword + ", email=" + email + "]";
	}
	
	
}
