# realestate-tracing-method
Aspecto para tracing a nivel metodo


## Modo de uso

Agregar en la configuracion, el tracer debe estar previamente configurado.

```java
@Bean
public TracingAspect tracingAspect(Tracer tracer) {
  return new TracingAspect(tracer);
}
```
