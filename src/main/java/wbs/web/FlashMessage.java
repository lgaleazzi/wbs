package wbs.web;

/*
 * Class to manage flash messages.
 * 'SUCCESS' and 'DANGER' correspond to CSS classes 'success'(formatted green) and 'danger'(formatted red) in Bootstrap
 */

public class FlashMessage {
    private String message;
    private Status status;

    public FlashMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status {
        SUCCESS, DANGER
    }
}