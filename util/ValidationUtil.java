package com.report.ro.util;

import org.springframework.stereotype.Component;
import com.report.ro.exception.BusinessException;
import com.report.ro.dto.SoftSkillDTO;
import com.report.ro.dto.TechnicalSkillDTO;

@Component
public class ValidationUtil {
 public void validateEmail(String email) {
     String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
     if (email == null || !email.matches(emailRegex)) {
         throw new BusinessException("Invalid email format");
     }
 }

 public void validatePassword(String password) {
     if (password == null || password.length() < 8) {
         throw new BusinessException("Password must be at least 8 characters long");
     }
 }

 public void validateScore(Integer score, String fieldName) {
     if (score != null && (score < 0 || score > 100)) {
         throw new BusinessException(fieldName + " must be between 0 and 100");
     }
 }
 

  
  public boolean validateSkill(TechnicalSkillDTO skillDto) {
      if (skillDto == null) {
          return false;
      }
      if (skillDto.getName() == null || skillDto.getName().trim().isEmpty()) {
          return false;
      }
      if (skillDto.getProficiencyLevel() == null) {
          return false;
      }
      return true;
  }

  public boolean validateSkill(SoftSkillDTO skillDto) {
      if (skillDto == null) {
          return false;
      }
      if (skillDto.getName() == null || skillDto.getName().trim().isEmpty()) {
          return false;
      }
      if (skillDto.getProficiencyLevel() == null) {
          return false;
      }
      return true;
  }
}



