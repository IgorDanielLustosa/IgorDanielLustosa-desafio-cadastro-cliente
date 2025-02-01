package com.example.desafio_cadastro_cliente.cadastros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Desabilitar CSRF para simplificação
                .authorizeRequests()
                .antMatchers("/Clientes/**").hasAnyRole("ADMIN", "USER") // Clientes podem acessar para visualização
                .antMatchers("/Clientes/cadastrar", "/Clientes/editar", "/Clientes/deletar").hasRole("ADMIN") // Apenas ADMIN pode criar, editar ou deletar
                .anyRequest().authenticated() // Todas as outras requisições precisam de autenticação
                .and()
                .httpBasic(); // Autenticação via HTTP Basic
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Configuração de autenticação em memória com as credenciais do desafio
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("123qwe!@#")).roles("ADMIN")
                .and()
                .withUser("padrão").password(passwordEncoder().encode("123qwe123")).roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
