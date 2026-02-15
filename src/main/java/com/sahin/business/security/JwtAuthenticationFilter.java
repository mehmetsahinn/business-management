package com.sahin.business.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header;
        String token;
        String username;

        header = request.getHeader("Authorization");

        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }
        token = header.substring(7);
        try {
            username = jwtService.getUsernameByToken(token);
            System.out.println("Username from token: " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("UserDetails loaded: " + userDetails.getUsername());
                if (userDetails != null && !jwtService.isTokenExpired(token)) {
                    System.out.println("Token geçerli, authentication ayarlanıyor...");

                    String role = (String) jwtService.getClaimsByKey(token, "role");
                    System.out.println("Token'dan çekilen role: " + role);

                    // Security çalışma mantığı role bilgileriyle yetki işlemleri için bu tipe ihtiyacı var
                    List<GrantedAuthority> authorities;

                    if (role != null && !role.isEmpty()) {
                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                        authorities = Collections.singletonList(authority);
                        System.out.println("Authority oluşturuldu: ROLE_" + role);
                    } else {
                        authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
                        System.out.println("Role token'da bulunamadı, UserDetails'dan alındı");
                    }

                    // 3. Adım: Authentication nesnesini oluştururken authorities'i ekle
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

                    authentication.setDetails(userDetails);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException e) {
            System.out.println("Token süresi dolmuştur : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Genel bir hata oluştu : " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}