package ba.sum.fsre.hepoc.security;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.service.CitizenService;
import ba.sum.fsre.hepoc.service.EmailService;
import ba.sum.fsre.hepoc.service.TwoFactorAuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final CitizenService citizenService;
    private final EmailService emailService;
    private final TwoFactorAuthService twoFactorAuthService;

    public CustomAuthenticationSuccessHandler(CitizenService citizenService, EmailService emailService, TwoFactorAuthService twoFactorAuthService) {
        this.citizenService = citizenService;
        this.emailService = emailService;
        this.twoFactorAuthService = twoFactorAuthService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CitizenDetails citizenDetails = (CitizenDetails) authentication.getPrincipal();
        Optional<Citizen> citizen = citizenService.findByJmbg(citizenDetails.getUsername());

        if (citizenDetails.isTwoFactorEnabled()) {
            String code = twoFactorAuthService.generateVerificationCode(citizenDetails.getUsername());
            emailService.sendEmail(citizen.get().getEmail(), "Your 2FA Code", "Welcome to HEPoC" + "\n\n" + "Your code is: " + code);
            response.sendRedirect("/2fa?jmbg=" + citizenDetails.getUsername()); // Redirect to 2FA verification page
        } else {
            response.sendRedirect("/index"); // Proceed to the original default page
        }
    }
}
