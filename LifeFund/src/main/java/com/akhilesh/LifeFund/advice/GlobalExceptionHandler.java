package com.akhilesh.LifeFund.advice;

import com.akhilesh.LifeFund.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //-----------------------------
    // User Exceptions
    //-----------------------------

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex){

        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GoogleLoginRequiredException.class)
    public ResponseEntity<ApiResponse<Void>> handleGoogleLogin(GoogleLoginRequiredException ex){

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailExists(EmailAlreadyExistsException ex){

        ApiError error = new ApiError(HttpStatus.CONFLICT, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handlePhoneExists(PhoneNumberAlreadyExistsException ex){

        ApiError error = new ApiError(HttpStatus.CONFLICT, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.CONFLICT);
    }

    //-----------------------------
    // Campaign
    //-----------------------------

    @ExceptionHandler(CampaignNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCampaignNotFound(CampaignNotFoundException ex){

        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.NOT_FOUND);
    }

    //-----------------------------
    // Donation
    //-----------------------------

    @ExceptionHandler(DonationNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleDonationNotFound(DonationNotFoundException ex){

        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.NOT_FOUND);
    }

    //-----------------------------
    // Authentication
    //-----------------------------

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex){

        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "Invalid Email or Password");

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameNotFound(UsernameNotFoundException ex){

        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.NOT_FOUND);
    }

    //-----------------------------
    // JWT
    //-----------------------------

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredJwt(ExpiredJwtException ex){

        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "JWT Token Expired");

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponse<Void>> handleSignature(SignatureException ex){

        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "Invalid JWT Signature");

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleMalformed(MalformedJwtException ex){

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Malformed JWT Token");

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnsupported(UnsupportedJwtException ex){

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Unsupported JWT");

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex){

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.BAD_REQUEST);
    }

    //-----------------------------
    // OAuth
    //-----------------------------

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleOAuth(OAuth2AuthenticationException ex){

        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.UNAUTHORIZED);
    }

    //-----------------------------
    // Validation
    //-----------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex){

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, message);

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMessage(HttpMessageNotReadableException ex){

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Invalid Request Body");

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissing(MissingServletRequestParameterException ex){

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.BAD_REQUEST);
    }

    //-----------------------------
    // Database
    //-----------------------------

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDatabase(DataIntegrityViolationException ex){

        ApiError error = new ApiError(HttpStatus.CONFLICT, "Database Constraint Violated");

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.CONFLICT);
    }

    //-----------------------------
    // File Upload
    //-----------------------------

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleFile(MaxUploadSizeExceededException ex){

        ApiError error = new ApiError(HttpStatus.PAYLOAD_TOO_LARGE, "Uploaded File Too Large");

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.PAYLOAD_TOO_LARGE);
    }

    //-----------------------------
    // Generic
    //-----------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex){

        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}