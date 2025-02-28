package devices.unit;

import devices.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;

class StandardDeviceTests {

	@Mock
	FailingPolicy failingPolicyStub;
	Device d;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		d = new StandardDevice(failingPolicyStub);
	}

	@Nested
	class WithAlwaysFailingPolicy {
		@Mock
		FailingPolicy alwaysFailingPolicyStub;

		@BeforeEach
		void init() {
			MockitoAnnotations.openMocks(this);
			when(alwaysFailingPolicyStub.attemptOn()).thenReturn(false);
			when(alwaysFailingPolicyStub.policyName()).thenReturn("AlwaysFailingPolicy");
			d = new StandardDevice(alwaysFailingPolicyStub);
		}

		@Test
		void testDeviceDoNotTurnOnAccordinglyToFailingPolicy() {
			assertThrows(IllegalStateException.class, () -> d.on());
		}

	}

	@Nested
	class WithNeverFailingPolicy {
		@Mock
		FailingPolicy neverFailingPolicyStub;

		@BeforeEach
		void init() {
			MockitoAnnotations.openMocks(this);
			when(neverFailingPolicyStub.attemptOn()).thenReturn(true);
			when(neverFailingPolicyStub.policyName()).thenReturn("NeverFailingPolicy");
			d = new StandardDevice(neverFailingPolicyStub);
		}

		@Test
		void testDeviceTurnOnAccordinglyToFailingPolicy() {
			assertDoesNotThrow(() -> d.on());
		}

	}

	@Test
	void testDeviceCannotBeCreatedWithoutFailingPolicy() {
		assertThrows(NullPointerException.class, () -> new StandardDevice(null));
	}

	@Test
	void testDeviceIsInitiallyOff() {
		d = new StandardDevice(failingPolicyStub);
		assertFalse(d.isOn());
	}
}
