namespace Maximus.IRD.Samples.KeycloakClient
{
    /// Base for our exceptions
    public class AuthException : System.Exception
    {
        public AuthException() { }
        public AuthException(string message) : base(message) { }
        public AuthException(string message, System.Exception inner) : base(message, inner) { }
    }

    /// thrown when response from the server was not usable
    public class UnknownResponseException : AuthException
    {
        public UnknownResponseException() { }
        public UnknownResponseException(string message) : base(message) { }
        public UnknownResponseException(string message, System.Exception inner) : base(message, inner) { }
    }

    /// thrown when the login process cannot be completed, no retry possible
    public class LoginException : AuthException
    {
        public LoginException() { }
        public LoginException(string message) : base(message) { }
        public LoginException(string message, System.Exception inner) : base(message, inner) { }
    }

    /// thrown internally to indicate that while a request failed a subsequent
    /// request may succeed
    public class CanRetryException : AuthException
    {
        public CanRetryException() { }
        public CanRetryException(string message) : base(message) { }
        public CanRetryException(string message, System.Exception inner) : base(message, inner) { }
    }
}