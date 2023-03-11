package io.opentelemetry.zipkin.example;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.context.Scope;

public class ZipkinExample {

    // The Tracer we'll use for the example
    private final Tracer tracer;

    public ZipkinExample(TracerProvider tracerProvider) {
        tracer = tracerProvider.get("io.opentelemetry.example.ZipkinExample");
    }

    // This method instruments doWork() method
    public void login() {
        // Generate span
        Span span = tracer.spanBuilder("login").startSpan();
        try (Scope scope = span.makeCurrent()) {
            // Add some Event to the span
            span.addEvent("Login Event");
            // execute my use case - here we simulate a wait
            authenticate();
            generateAPIKey();
            fetchDashboardDetails();
            getFavourites();
        } finally {
            span.end();
        }
    }


    void authenticate() {
        Span childSpan = tracer.spanBuilder("authenticateUser")
                // NOTE: setParent(...) is not required;
                // `Span.current()` is automatically added as the parent
                .startSpan();
        try(Scope scope = childSpan.makeCurrent()) {
            try {
                childSpan.setAttribute("User", "AdminUser");
                Thread.sleep(350);
            } catch (InterruptedException e) {
                // ignore in an example
            }
        } finally {
            childSpan.end();
        }
    }

    void generateAPIKey() {
        Span childSpan = tracer.spanBuilder("generateAPIKey")
                // NOTE: setParent(...) is not required;
                // `Span.current()` is automatically added as the parent
                .startSpan();
        try(Scope scope = childSpan.makeCurrent()) {
            try {
                childSpan.setAttribute("Key", "Generated Key- Yshkjd713gjkg1kj4h");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // ignore in an example
            }
        } finally {
            childSpan.end();
        }
    }

    void fetchDashboardDetails() {
        Span childSpan = tracer.spanBuilder("fetchDashboardDetails")
                // NOTE: setParent(...) is not required;
                // `Span.current()` is automatically added as the parent
                .startSpan();
        try(Scope scope = childSpan.makeCurrent()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // ignore in an example
            }
        } finally {
            childSpan.end();
        }
    }

    private void getFavourites() {
        Span childSpan = tracer.spanBuilder("getFavourites")
                // NOTE: setParent(...) is not required;
                // `Span.current()` is automatically added as the parent
                .startSpan();
        try(Scope scope = childSpan.makeCurrent()) {
            try {
                childSpan.setStatus(StatusCode.ERROR);
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // ignore in an example
            }
        } finally {
            childSpan.end();
        }
    }

    public static void main(String[] args) {

        // it is important to initialize the OpenTelemetry SDK as early as possible in your process.
        OpenTelemetry openTelemetry = ExampleConfiguration.initializeOpenTelemetry();

        TracerProvider tracerProvider = openTelemetry.getTracerProvider();

        // start example
        ZipkinExample example = new ZipkinExample(tracerProvider);
        example.login();

        System.out.println("Bye");
    }
}
