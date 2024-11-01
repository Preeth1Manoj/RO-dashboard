package com.report.ro.controller;

import org.springframework.http.ResponseEntity;
import com.report.ro.dto.response.ApiResponse;

public abstract class BaseController {
 
 protected <T> ResponseEntity<ApiResponse<T>> success(T data) {
     return ResponseEntity.ok(ApiResponse.success(data));
 }
 
 protected <T> ResponseEntity<ApiResponse<T>> error(String message) {
     return ResponseEntity.badRequest().body(ApiResponse.error(message));
 }
 
 protected ResponseEntity<ApiResponse<Void>> noContent() {
     return ResponseEntity.noContent().build();
 }
}