package com.nguyenhung.bta.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDTO {
	@NotBlank(message = "Hãy nhập tên người dùng")
	private String name;
	@NotBlank(message = "Hãy nhập email")
	private String email;
	@Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "Số điện thoại không hợp lệ")
	@NotBlank(message = "Hãy nhập số điện thoại")
	private String phone;
	private String image;
}
