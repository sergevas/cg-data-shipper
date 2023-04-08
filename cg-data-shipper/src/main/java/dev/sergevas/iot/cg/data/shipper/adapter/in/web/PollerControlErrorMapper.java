package dev.sergevas.iot.cg.data.shipper.adapter.in.web;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

@Provider
public class PollerControlErrorMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        JsonObjectBuilder entityBuilder = Json.createObjectBuilder()
                .add("exception_type", exception.getClass().getName())
                .add("exception_stacktrace", this.getStackTrace(exception));
        if (exception.getMessage() != null) {
            entityBuilder.add("exception_message", exception.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(entityBuilder.build())
                .build();
    }

    public String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}
