package com.example.springsecurity.service;

import com.example.springsecurity.config.SecurityEventsCollector;
import com.example.springsecurity.entity.Permission;
import com.example.springsecurity.entity.Role;
import com.example.springsecurity.entity.User;
import com.example.springsecurity.model.ApiModels.*;
import com.example.springsecurity.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SpringSecurityDemoService {

  private final SecurityEventsCollector eventsCollector;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public SpringSecurityDemoService(SecurityEventsCollector eventsCollector,
                                   PasswordEncoder passwordEncoder,
                                   UserRepository userRepository) {
    this.eventsCollector = eventsCollector;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public SecurityConceptsResult securityConceptsDemo() {
    return new SecurityConceptsResult(List.of(
        new SecurityConceptItem(
            "Authentication",
            "Process of verifying the identity of a user or system",
            "Username/password, JWT tokens, OAuth2"
        ),
        new SecurityConceptItem(
            "Authorization",
            "Process of determining what authenticated users are allowed to do",
            "Role-based access control (RBAC), permission-based"
        ),
        new SecurityConceptItem(
            "Principal",
            "The currently authenticated user in Spring Security",
            "Authentication.getPrincipal() returns UserDetails"
        ),
        new SecurityConceptItem(
            "GrantedAuthority",
            "Represents a permission or role granted to a user",
            "ROLE_ADMIN, ROLE_USER, read_permission"
        ),
        new SecurityConceptItem(
            "SecurityContext",
            "Holds security information for the current request thread",
            "SecurityContextHolder.getContext()"
        ),
        new SecurityConceptItem(
            "SecurityFilterChain",
            "Chain of filters that process each HTTP request",
            "UsernamePasswordAuthenticationFilter, JwtFilter, etc."
        )
    ));
  }

  public FilterChainResult filterChainDemo() {
    return new FilterChainResult(List.of(
        new FilterChainItem(
            "1",
            "ChannelProcessingFilter",
            "Determines if the request should be HTTPS"
        ),
        new FilterChainItem(
            "2",
            "WebAsyncManagerIntegrationFilter",
            "Provides integration between SecurityContextHolder and WebAsyncManager"
        ),
        new FilterChainItem(
            "3",
            "SecurityContextPersistenceFilter",
            "Loads SecurityContext at start of request and saves at end"
        ),
        new FilterChainItem(
            "4",
            "HeaderWriterFilter",
            "Adds security headers like X-Frame-Options, X-XSS-Protection"
        ),
        new FilterChainItem(
            "5",
            "CorsFilter",
            "Handles Cross-Origin Resource Sharing (CORS)"
        ),
        new FilterChainItem(
            "6",
            "CsrfFilter",
            "Protects against Cross-Site Request Forgery attacks"
        ),
        new FilterChainItem(
            "7",
            "LogoutFilter",
            "Processes logout requests"
        ),
        new FilterChainItem(
            "8",
            "UsernamePasswordAuthenticationFilter",
            "Processes form-based login (username/password)"
        ),
        new FilterChainItem(
            "9",
            "BasicAuthenticationFilter",
            "Processes HTTP Basic authentication"
        ),
        new FilterChainItem(
            "10",
            "RequestCacheAwareFilter",
            "Handles request caching for saved requests after authentication"
        ),
        new FilterChainItem(
            "11",
            "SecurityContextHolderAwareRequestFilter",
            "Wraps HttpServletRequest with security-aware implementation"
        ),
        new FilterChainItem(
            "12",
            "AnonymousAuthenticationFilter",
            "Provides anonymous authentication if no other auth exists"
        ),
        new FilterChainItem(
            "13",
            "SessionManagementFilter",
            "Handles session fixation protection and session management"
        ),
        new FilterChainItem(
            "14",
            "ExceptionTranslationFilter",
            "Converts Spring Security exceptions to HTTP responses"
        ),
        new FilterChainItem(
            "15",
            "FilterSecurityInterceptor",
            "Authorizes requests using AccessDecisionManager"
        )
    ));
  }

  public ArchitectureOverview architectureDemo() {
    return new ArchitectureOverview(
        "Form-based login with in-memory authentication support. Can be extended to JWT, OAuth2.",
        "Method-level security with @PreAuthorize, @Secured. Role-based access control (RBAC).",
        "Session-based authentication. Can be configured for stateless JWT tokens.",
        "CSRF protection enabled for form-based. Can be disabled for stateless APIs."
    );
  }

  public UserDetailsResult userDetailsDemo(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

    Set<String> permissions = user.getRoles().stream()
        .flatMap(role -> role.getPermissions().stream())
        .map(Permission::getName)
        .collect(Collectors.toSet());

    List<String> roleNames = user.getRoles().stream()
        .map(Role::getName)
        .toList();

    return new UserDetailsResult(
        user.getUsername(),
        user.getEmail(),
        roleNames,
        permissions.stream().sorted().toList(),
        user.getEnabled()
    );
  }

  public PasswordEncoderInfo passwordEncoderDemo() {
    String examplePassword = "password123";
    String encoded = passwordEncoder.encode(examplePassword);

    return new PasswordEncoderInfo(
        "BCrypt",
        "Automatically generated salt, 10 rounds by default (adjustable)",
        encoded
    );
  }

  public AuthVsAuthorizationResult authVsAuthorizationDemo() {
    return new AuthVsAuthorizationResult(List.of(
        new AuthVsAuthorizationItem(
            "Purpose",
            "Who are you? (verify identity)",
            "What can you do? (check permissions)"
        ),
        new AuthVsAuthorizationItem(
            "When it happens",
            "First - on each request",
            "After authentication - when accessing protected resources"
        ),
        new AuthVsAuthorizationItem(
            "Spring Security classes",
            "Authentication, AuthenticationManager, AuthenticationProvider",
            "AccessDecisionManager, SecurityExpressionHandler"
        ),
        new AuthVsAuthorizationItem(
            "Common mechanism",
            "Username/password, JWT, OAuth2, SAML",
            "Roles, permissions, @PreAuthorize, @Secured"
        ),
        new AuthVsAuthorizationItem(
            "Failure behavior",
            "401 Unauthorized - not logged in",
            "403 Forbidden - logged in but not permitted"
        ),
        new AuthVsAuthorizationItem(
            "Example",
            "User logs in with credentials",
            "User tries to delete another user's data"
        )
    ));
  }

  public MethodSecurityResult methodSecurityDemo() {
    return new MethodSecurityResult(List.of(
        new MethodSecurityItem(
            "@Secured",
            "Simple role-based check. Supports role names only.",
            "@Secured(\"ROLE_ADMIN\")"
        ),
        new MethodSecurityItem(
            "@PreAuthorize",
            "SpEL-based check before method execution. More flexible.",
            "@PreAuthorize(\"hasRole('ADMIN') or #userId == authentication.name\")"
        ),
        new MethodSecurityItem(
            "@PostAuthorize",
            "SpEL-based check after method execution. Useful for filtering return values.",
            "@PostAuthorize(\"returnObject.owner == authentication.name\")"
        ),
        new MethodSecurityItem(
            "@PreFilter",
            "Filters collection arguments before method execution.",
            "@PreFilter(\"filterObject.owner == authentication.name\")"
        ),
        new MethodSecurityItem(
            "@PostFilter",
            "Filters collection return values after method execution.",
            "@PostFilter(\"filterObject.visibility == 'PUBLIC'\")"
        ),
        new MethodSecurityItem(
            "hasRole('ROLE')",
            "Checks if user has specified role. Automatically adds ROLE_ prefix.",
            "hasRole('ADMIN')"
        ),
        new MethodSecurityItem(
            "hasAuthority('PERM')",
            "Checks if user has specified authority (permission). No prefix added.",
            "hasAuthority('USER_WRITE')"
        ),
        new MethodSecurityItem(
            "isAuthenticated()",
            "Returns true if user is authenticated (not anonymous).",
            "isAuthenticated()"
        ),
        new MethodSecurityItem(
            "isAnonymous()",
            "Returns true if user is anonymous (not authenticated).",
            "isAnonymous()"
        ),
        new MethodSecurityItem(
            "permitAll()",
            "Always grants access regardless of authentication.",
            "permitAll()"
        ),
        new MethodSecurityItem(
            "denyAll()",
            "Always denies access regardless of authentication.",
            "denyAll()"
        )
    ));
  }

  public SecurityConfigResult securityConfigDemo() {
    return new SecurityConfigResult(List.of(
        new SecurityConfigItem(
            "passwordEncoder",
            "BCryptPasswordEncoder()",
            "Encrypts passwords using BCrypt hashing algorithm"
        ),
        new SecurityConfigItem(
            "csrf().disable()",
            "For REST APIs with stateless auth",
            "Disables CSRF protection (not recommended for form-based apps)"
        ),
        new SecurityConfigItem(
            "sessionManagement().sessionCreationPolicy(STATELESS)",
            "For JWT/stateless auth",
            "Prevents session creation, forces stateless authentication"
        ),
        new SecurityConfigItem(
            "authorizeHttpRequests()",
            "Defines endpoint access rules",
            "Configures which endpoints require authentication/authorization"
        ),
        new SecurityConfigItem(
            "formLogin()",
            "Enables form-based login",
            "Provides default login page and processing"
        ),
        new SecurityConfigItem(
            "httpBasic()",
            "Enables HTTP Basic authentication",
            "Supports Authorization header with Basic credentials"
        ),
        new SecurityConfigItem(
            "logout()",
            "Configures logout behavior",
            "Defines logout URL, success handler, etc."
        ),
        new SecurityConfigItem(
            "requestMatchers().permitAll()",
            "Allows public access to specific endpoints",
            "Endpoints like /login, /register, /public/**"
        ),
        new SecurityConfigItem(
            "anyRequest().authenticated()",
            "Requires authentication for all other requests",
            "Default: everything else needs to be logged in"
        )
    ));
  }

  public SecurityEventsResult securityEventsDemo() {
    List<SecurityEventsCollector.SecurityEvent> events = eventsCollector.getEvents();

    return new SecurityEventsResult(
        events.stream()
            .map(e -> new SecurityEventItem(
                e.eventType(),
                e.timestamp().toString(),
                e.username(),
                e.details()
            ))
            .toList()
    );
  }

  public GlossaryResult glossaryDemo() {
    return new GlossaryResult(List.of(
        new GlossaryItem(
            "Authentication",
            "The process of verifying a user's identity (Who are you?)"
        ),
        new GlossaryItem(
            "Authorization",
            "The process of determining access rights (What can you do?)"
        ),
        new GlossaryItem(
            "Principal",
            "The authenticated user in Spring Security (Authentication.getPrincipal())"
        ),
        new GlossaryItem(
            "GrantedAuthority",
            "Represents a permission or role granted to a user (e.g., ROLE_ADMIN)"
        ),
        new GlossaryItem(
            "SecurityContext",
            "Holds security information for the current request thread"
        ),
        new GlossaryItem(
            "SecurityFilterChain",
            "Chain of filters that process each HTTP request for security"
        ),
        new GlossaryItem(
            "UserDetails",
            "Core interface representing user information for authentication"
        ),
        new GlossaryItem(
            "UserDetailsService",
            "Interface for loading user-specific data by username"
        ),
        new GlossaryItem(
            "PasswordEncoder",
            "Interface for encoding and verifying passwords (BCrypt, etc.)"
        ),
        new GlossaryItem(
            "CSRF",
            "Cross-Site Request Forgery protection - prevents unauthorized actions"
        ),
        new GlossaryItem(
            "CORS",
            "Cross-Origin Resource Sharing - controls API access from other domains"
        ),
        new GlossaryItem(
            "JWT",
            "JSON Web Token - stateless authentication token (not covered in this demo)"
        ),
        new GlossaryItem(
            "RBAC",
            "Role-Based Access Control - authorization based on user roles"
        ),
        new GlossaryItem(
            "@Secured",
            "Annotation for simple role-based method security"
        ),
        new GlossaryItem(
            "@PreAuthorize",
            "Annotation for SpEL-based method security before execution"
        ),
        new GlossaryItem(
            "BCrypt",
            "Password hashing algorithm with built-in salt, widely used"
        ),
        new GlossaryItem(
            "Session Management",
            "Configuration for HTTP sessions (timeout, fixation protection, etc.)"
        )
    ));
  }

  public EndpointListResult endpointsDemo() {
    return new EndpointListResult(List.of(
        new EndpointItem("GET", "/api/security/concepts", "Security concepts overview", "No"),
        new EndpointItem("GET", "/api/security/filter-chain", "Security filter chain", "No"),
        new EndpointItem("GET", "/api/security/architecture", "Architecture overview", "No"),
        new EndpointItem("GET", "/api/security/auth-vs-authorization", "Auth vs Authorization", "No"),
        new EndpointItem("GET", "/api/security/method-security", "Method security annotations", "No"),
        new EndpointItem("GET", "/api/security/password-encoder", "Password encoder demo", "No"),
        new EndpointItem("GET", "/api/security/config", "Security config options", "No"),
        new EndpointItem("GET", "/api/security/glossary", "Glossary of terms", "No"),
        new EndpointItem("GET", "/api/security/events", "Security events log", "No"),
        new EndpointItem("POST", "/api/auth/login", "Authenticate user", "No"),
        new EndpointItem("GET", "/api/users", "List all users", "Yes"),
        new EndpointItem("GET", "/api/users/{id}", "Get user by ID", "Yes"),
        new EndpointItem("GET", "/api/users/me", "Get current user", "Yes"),
        new EndpointItem("POST", "/api/users", "Create user", "Yes"),
        new EndpointItem("PUT", "/api/users/{id}", "Update user", "Yes"),
        new EndpointItem("DELETE", "/api/users/{id}", "Delete user", "Yes"),
        new EndpointItem("PUT", "/api/users/{id}/password", "Update password", "Yes"),
        new EndpointItem("POST", "/api/users/{id}/roles/{roleId}", "Assign role to user", "Yes"),
        new EndpointItem("DELETE", "/api/users/{id}/roles/{roleId}", "Remove role from user", "Yes"),
        new EndpointItem("PUT", "/api/users/{id}/enable", "Enable user", "Yes"),
        new EndpointItem("PUT", "/api/users/{id}/disable", "Disable user", "Yes"),
        new EndpointItem("GET", "/api/roles", "List all roles", "Yes"),
        new EndpointItem("GET", "/api/roles/{id}", "Get role by ID", "Yes"),
        new EndpointItem("POST", "/api/roles", "Create role", "Yes"),
        new EndpointItem("DELETE", "/api/roles/{id}", "Delete role", "Yes"),
        new EndpointItem("POST", "/api/roles/{roleId}/permissions/{permId}", "Add permission to role", "Yes"),
        new EndpointItem("DELETE", "/api/roles/{roleId}/permissions/{permId}", "Remove permission", "Yes"),
        new EndpointItem("GET", "/api/permissions", "List all permissions", "Yes"),
        new EndpointItem("POST", "/api/permissions", "Create permission", "Yes"),
        new EndpointItem("DELETE", "/api/permissions/{id}", "Delete permission", "Yes"),
        new EndpointItem("GET", "/api/security/user-details/{username}", "User details with roles/permissions", "No"),
        new EndpointItem("GET", "/api/security/endpoints", "List all endpoints", "No")
    ));
  }
}
