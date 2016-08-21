namespace Maximus.IRD.Samples.DXCClient
{
    /// Base for DXC Client exceptions
    public class DXCException : System.Exception
    {
        public int Status { get; }
        public string RefId { get; }
        public DXCException() { }
        public DXCException(string message) : base(message) { }
        public DXCException(string message, System.Exception inner) : base(message, inner) { }
        public DXCException(string message, int status, string refId) : base(message)
        {
            this.Status = status;
            this.RefId = refId;
        }
    }

    /// thrown when service reject the content type submitted
    public class AuthorizationException : DXCException
    {
        public AuthorizationException() { }
        public AuthorizationException(string message) : base(message) { }
        public AuthorizationException(string message, System.Exception inner) : base(message, inner) { }
        public AuthorizationException(string message, int status, string refId) : base(message, status, refId) { }
    }

    /// thrown when service reject the content type submitted
    public class ParameterException : DXCException
    {
        public ParameterException() { }
        public ParameterException(string message) : base(message) { }
        public ParameterException(string message, System.Exception inner) : base(message, inner) { }
        public ParameterException(string message, int status, string refId) : base(message, status, refId) { }
}

    /// thrown when service reject the content type submitted
    public class ContentNotAcceptableException : DXCException
    {
        public ContentNotAcceptableException() { }
        public ContentNotAcceptableException(string message) : base(message) { }
        public ContentNotAcceptableException(string message, System.Exception inner) : base(message, inner) { }
        public ContentNotAcceptableException(string message, int status, string refId) : base(message, status, refId) { }
    }

    /// thrown when case or document not found
    public class NotFoundException : DXCException
    {
        public NotFoundException() { }
        public NotFoundException(string message) : base(message) { }
        public NotFoundException(string message, System.Exception inner) : base(message, inner) { }
        public NotFoundException(string message, int status, string refId) : base(message, status, refId) { }
    }
}
