package ResultTypes;

import lombok.Getter;

public abstract class TransactionResult {
    public static class Success extends TransactionResult {
        public Success() { }
    }

    @Getter
    public static class Failure extends TransactionResult {
        private String message;
        public Failure(String message) {
            this.message = message;
        }
    }

    private TransactionResult() { }
}
