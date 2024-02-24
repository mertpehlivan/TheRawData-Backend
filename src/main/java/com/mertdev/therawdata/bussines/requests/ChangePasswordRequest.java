package com.mertdev.therawdata.bussines.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;
}
