package devices;

import java.util.Random;
import java.util.random.RandomGenerator;

public class RandomFailing implements FailingPolicy {
	private RandomGenerator random;
	private boolean failed = false;

	void RandomFailing(RandomGenerator random) {
		this.random = random;
	}

	void RandomFailing() {
		RandomFailing(new Random());
	}

    @Override
    public boolean attemptOn() {
        this.failed = this.failed || random.nextBoolean();
        return !this.failed;
    }

    @Override
    public void reset() {
        this.failed = false;
    }

    @Override
    public String policyName() {
        return "random";
    }
}
