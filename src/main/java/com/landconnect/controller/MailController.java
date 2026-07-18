package com.landconnect.controller;

import com.landconnect.dto.response.ApiResponse;
import com.landconnect.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {
    private final MailService mailService;
 @PostMapping("/test")
    public ResponseEntity<ApiResponse> sendEmail(@RequestParam String to){
mailService.sendEmail( to, "Welcome to Land Connect",
        "Congratulations! Your email configuration is working successfully.");
    return ResponseEntity.ok(ApiResponse.builder()
            .success(true)
            .message("Test email sent successfully.")
            .data(null).build());
    }
    @PostMapping("/html/test")
    public  ResponseEntity<ApiResponse> sendHtmlEmail(@RequestParam String to){
        String html = """
<!DOCTYPE html>
<html>
<head>
    <style>
        body{
            font-family:Arial,sans-serif;
            background:#f4f4f4;
            padding:30px;
        }

        .container{
            max-width:600px;
            margin:auto;
            background:white;
            padding:30px;
            border-radius:10px;
            box-shadow:0 2px 10px rgba(0,0,0,0.2);
        }

        h1{
            color:#2E7D32;
        }

        p{
            font-size:16px;
            color:#444;
        }

        .btn{
            display:inline-block;
            margin-top:20px;
            padding:12px 25px;
            background:#2E7D32;
            color:white;
            text-decoration:none;
            border-radius:5px;
        }

        .footer{
            margin-top:30px;
            color:gray;
            font-size:13px;
        }
    </style>
</head>

<body>

<div class="container">

<h1>🏡 Welcome to Land Connect</h1>

<p>Hello,</p>

<p>
Congratulations!
Your email configuration is working successfully.
</p>

<p>
Thank you for joining Land Connect.
</p>

<a class="btn" href="http://localhost:3000">
Login
</a>

<div class="footer">

This is an automatically generated email.
Please do not reply.

</div>

</div>

</body>
</html>
""";
        mailService.sendHtmlEmail(
                to,
                "Welcome to Land Connect",
                html
        );
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true)
                .message("Test email sent successfully.")
                .data(null).build());
    }
}
