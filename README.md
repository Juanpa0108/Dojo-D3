# Proyecto Java basico

Aplicacion de consola con Java 17.

## Requisitos

- JDK 17 o superior
- (Opcional) Maven, si quieres usar `pom.xml`

## Ejecutar sin Maven

```bash
javac -d out src/main/java/com/ejemplo/App.java
java -cp out com.ejemplo.App
```

Con un nombre:

```bash
java -cp out com.ejemplo.App Ana
```

## Ejecutar con Maven

```bash
mvn -q compile exec:java -Dexec.mainClass=com.ejemplo.App
```
