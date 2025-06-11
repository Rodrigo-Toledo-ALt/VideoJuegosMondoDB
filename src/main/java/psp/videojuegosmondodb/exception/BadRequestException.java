package psp.videojuegosmondodb.exception;

/**
 * Excepción lanzada cuando la solicitud es incorrecta
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}