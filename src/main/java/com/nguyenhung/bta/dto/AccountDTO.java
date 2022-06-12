package com.nguyenhung.bta.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AccountDTO {
	@NotBlank(message = "Hãy nhập tên tài khoản")
	private String username;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Mật khẩu chứa ít nhất 1 ký tự hoa, thường và số")
	@NotBlank(message = "Hãy nhập mật khẩu")
	private String password;
}
