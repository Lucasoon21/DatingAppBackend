package com.lukasz.wolski.DatingAppBackend

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.filter.OncePerRequestFilter
import kotlin.Throws
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.FilterChain
import io.jsonwebtoken.Claims
import org.springframework.security.core.context.SecurityContextHolder
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.lang.Exception
import java.util.HashMap
import java.util.stream.Collectors

class JWTAuthorizationFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val secretKey = "sekretnyKlucz"
        println(request.servletPath)
        if (request.servletPath == "/auth/login" || request.servletPath == "/auth/token/refresh") {
            filterChain.doFilter(request, response)
        } else {
            val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
            println(request.getHeader(HttpHeaders.AUTHORIZATION))
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    val token = authorizationHeader.substring("Bearer ".length)
                    val algorithm = Algorithm.HMAC256("sekretnyKlucz".toByteArray())
                    val verifier = JWT.require(algorithm).build()
                    val decodedJWT = verifier.verify(token)
                    val username = decodedJWT.subject
                    val authenticationToken = UsernamePasswordAuthenticationToken(username, null, null)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (err: Exception) {
                    println(err.message)
                    response.setHeader("error", err.message)
                    response.status = HttpStatus.FORBIDDEN.value()
                    // response.sendError(FORBIDDEN.value());
                    val error: MutableMap<String, String?> = HashMap()
                    error["error message"] = err.message
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, error)
                }
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }
    /*
    private val HEADER = "Authorization"
    private val PREFIX = "Bearer "
    private val SECRET = "sekretnyKlucz"
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            if (checkJWTToken(request, response)) {
                val claims = validateToken(request)
                if (claims["authorities"] != null) {
                    setUpSpringAuthentication(claims)
                } else {
                    SecurityContextHolder.clearContext()
                }
            } else {
                SecurityContextHolder.clearContext()
            }
            chain.doFilter(request, response)
        } catch (e: ExpiredJwtException) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.message)
            return
        } catch (e: UnsupportedJwtException) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.message)
            return
        } catch (e: MalformedJwtException) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.message)
            return
        }
    }

    private fun validateToken(request: HttpServletRequest): Claims {
        val jwtToken = request.getHeader(HEADER).replace(PREFIX, "")
        return Jwts.parser().setSigningKey(SECRET.toByteArray()).parseClaimsJws(jwtToken).body
    }

    private fun setUpSpringAuthentication(claims: Claims) {
        val authorities: List<String>? = claims["authorities"] as List<String>?
        val auth = UsernamePasswordAuthenticationToken(claims.subject, null,
            authorities!!.stream().map { role: String? -> SimpleGrantedAuthority(role) }.collect(Collectors.toList())
        )
        SecurityContextHolder.getContext().authentication = auth
    }

    private fun checkJWTToken(request: HttpServletRequest, res: HttpServletResponse): Boolean {
        val authenticationHeader = request.getHeader(HEADER)
        return if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) false else true
    }*/
}