package ScenarioResults;

import lombok.Getter;

public abstract class ScenarioResult {
    public static class Success extends ScenarioResult {
        public Success() { }
    }

    @Getter
    public static class Failure extends ScenarioResult {
        private String message;
        public Failure(String message) {
            this.message = message;
        }
    }

    private ScenarioResult() { }
}