package tc.oc.test;

import javax.annotation.Nullable;

public class TestThread extends Thread {

    public static TestThread start(TestCodeBlock body) {
        final TestThread thread = new TestThread(body);
        thread.start();
        return thread;
    }

    public static TestThread join(TestCodeBlock body) throws Throwable {
        final TestThread thread = new TestThread(body);
        thread.start();
        thread.assertJoin();
        return thread;
    }

    private final TestCodeBlock body;
    private volatile @Nullable Throwable exception;

    public TestThread(TestCodeBlock body) {
        this.body = body;
    }

    @Override
    public void run() {
        try {
            body.run();
        } catch(Throwable ex) {
            this.exception = ex;
        }
    }

    public void assertJoin() throws Throwable {
        join();

        if(exception != null) {
            if(exception instanceof AssertionError) {
                throw exception;
            }
            throw new AssertionError("Thread " + getName() + " terminated with an exception", exception);
        }
    }
}
