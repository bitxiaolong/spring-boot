package com.itjnshu.testdemo.controller;


import com.itjnshu.testdemo.dto.AccessTokenDto;
import com.itjnshu.testdemo.dto.GithubUser;
import com.itjnshu.testdemo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.it}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name  = "state") String state){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
       accessTokenDto.setClient_id(clientId);
       accessTokenDto.setRedirect_uri(redirectUri);
       accessTokenDto.setCode(code);
       accessTokenDto.setState(state);
       accessTokenDto.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
