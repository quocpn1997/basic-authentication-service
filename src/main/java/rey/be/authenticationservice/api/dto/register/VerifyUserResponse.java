package rey.be.authenticationservice.api.dto.register;

public record VerifyUserResponse(
        String message,
        String maskedDestination
) {

    private static final String SENT_MESSAGE = "Verification code sent";

    private static final String MASK = "***";

    public static VerifyUserResponse sent(String destination) {
        return new VerifyUserResponse(SENT_MESSAGE, mask(destination));
    }

    private static String mask(String destination) {
        var at = destination.indexOf('@');
        if (at <= 1) {
            return at < 0 ? MASK : MASK + destination.substring(at);
        }
        return destination.charAt(0) + MASK + destination.substring(at);
    }
}
