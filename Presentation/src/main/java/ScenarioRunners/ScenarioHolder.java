package ScenarioRunners;

import lombok.Getter;
import lombok.Setter;

public class ScenarioHolder<T> {
    @Getter
    @Setter
    private T value;

    public ScenarioHolder(T value) {
        this.value = value;
    }
}
