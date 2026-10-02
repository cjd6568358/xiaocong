package bolts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Task<TResult> {
    private static volatile UnobservedExceptionHandler unobservedExceptionHandler;
    private boolean cancelled;
    private boolean complete;
    private Exception error;
    private boolean errorHasBeenObserved;
    private TResult result;
    private UnobservedErrorNotifier unobservedErrorNotifier;
    public static final ExecutorService BACKGROUND_EXECUTOR = BoltsExecutors.background();
    private static final Executor IMMEDIATE_EXECUTOR = BoltsExecutors.immediate();
    public static final Executor UI_THREAD_EXECUTOR = AndroidExecutors.uiThread();
    private static Task<?> TASK_NULL = new Task<>((Object) null);
    private static Task<Boolean> TASK_TRUE = new Task<>(true);
    private static Task<Boolean> TASK_FALSE = new Task<>(false);
    private static Task<?> TASK_CANCELLED = new Task<>(true);
    private final Object lock = new Object();
    private List<Continuation<TResult, Void>> continuations = new ArrayList();

    public interface UnobservedExceptionHandler {
        void unobservedException(Task<?> task, UnobservedTaskException unobservedTaskException);
    }

    public static UnobservedExceptionHandler getUnobservedExceptionHandler() {
        return unobservedExceptionHandler;
    }

    Task() {
    }

    private Task(TResult result) {
        trySetResult(result);
    }

    private Task(boolean cancelled) {
        if (cancelled) {
            trySetCancelled();
        } else {
            trySetResult(null);
        }
    }

    public boolean isCompleted() {
        boolean z;
        synchronized (this.lock) {
            z = this.complete;
        }
        return z;
    }

    public boolean isCancelled() {
        boolean z;
        synchronized (this.lock) {
            z = this.cancelled;
        }
        return z;
    }

    public boolean isFaulted() {
        boolean z;
        synchronized (this.lock) {
            z = getError() != null;
        }
        return z;
    }

    public TResult getResult() {
        TResult tresult;
        synchronized (this.lock) {
            tresult = this.result;
        }
        return tresult;
    }

    public Exception getError() {
        Exception exc;
        synchronized (this.lock) {
            if (this.error != null) {
                this.errorHasBeenObserved = true;
                if (this.unobservedErrorNotifier != null) {
                    this.unobservedErrorNotifier.setObserved();
                    this.unobservedErrorNotifier = null;
                }
            }
            exc = this.error;
        }
        return exc;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <TResult> Task<TResult> forResult(TResult tresult) {
        if (tresult == 0) {
            return (Task<TResult>) TASK_NULL;
        }
        if (tresult instanceof Boolean) {
            return ((Boolean) tresult).booleanValue() ? (Task<TResult>) TASK_TRUE : (Task<TResult>) TASK_FALSE;
        }
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        taskCompletionSource.setResult(tresult);
        return taskCompletionSource.getTask();
    }

    public static <TResult> Task<TResult> forError(Exception error) {
        TaskCompletionSource<TResult> tcs = new TaskCompletionSource<>();
        tcs.setError(error);
        return tcs.getTask();
    }

    public static <TResult> Task<TResult> call(Callable<TResult> callable, Executor executor) {
        return call(callable, executor, null);
    }

    public static <TResult> Task<TResult> call(final Callable<TResult> callable, Executor executor, final CancellationToken ct) {
        final TaskCompletionSource<TResult> tcs = new TaskCompletionSource<>();
        try {
            executor.execute(new Runnable() { // from class: bolts.Task.4
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    if (ct != null && ct.isCancellationRequested()) {
                        tcs.setCancelled();
                        return;
                    }
                    try {
                        tcs.setResult(callable.call());
                    } catch (CancellationException e) {
                        tcs.setCancelled();
                    } catch (Exception e2) {
                        tcs.setError(e2);
                    }
                }
            });
        } catch (Exception e) {
            tcs.setError(new ExecutorException(e));
        }
        return tcs.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(final Continuation<TResult, TContinuationResult> continuation, final Executor executor, final CancellationToken ct) {
        boolean completed;
        final TaskCompletionSource<TContinuationResult> tcs = new TaskCompletionSource<>();
        synchronized (this.lock) {
            completed = isCompleted();
            if (!completed) {
                this.continuations.add(new Continuation<TResult, Void>() { // from class: bolts.Task.10
                    @Override // bolts.Continuation
                    public Void then(Task<TResult> task) {
                        Task.completeImmediately(tcs, continuation, task, executor, ct);
                        return null;
                    }
                });
            }
        }
        if (completed) {
            completeImmediately(tcs, continuation, this, executor, ct);
        }
        return tcs.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWith(Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(continuation, IMMEDIATE_EXECUTOR, null);
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(final Continuation<TResult, Task<TContinuationResult>> continuation, final Executor executor, final CancellationToken ct) {
        boolean completed;
        final TaskCompletionSource<TContinuationResult> tcs = new TaskCompletionSource<>();
        synchronized (this.lock) {
            completed = isCompleted();
            if (!completed) {
                this.continuations.add(new Continuation<TResult, Void>() { // from class: bolts.Task.11
                    @Override // bolts.Continuation
                    public Void then(Task<TResult> task) {
                        Task.completeAfterTask(tcs, continuation, task, executor, ct);
                        return null;
                    }
                });
            }
        }
        if (completed) {
            completeAfterTask(tcs, continuation, this, executor, ct);
        }
        return tcs.getTask();
    }

    public <TContinuationResult> Task<TContinuationResult> continueWithTask(Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(continuation, IMMEDIATE_EXECUTOR, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <TContinuationResult, TResult> void completeImmediately(final TaskCompletionSource<TContinuationResult> tcs, final Continuation<TResult, TContinuationResult> continuation, final Task<TResult> task, Executor executor, final CancellationToken ct) {
        try {
            executor.execute(new Runnable() { // from class: bolts.Task.14
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public void run() {
                    if (ct != null && ct.isCancellationRequested()) {
                        tcs.setCancelled();
                        return;
                    }
                    try {
                        tcs.setResult(continuation.then(task));
                    } catch (CancellationException e) {
                        tcs.setCancelled();
                    } catch (Exception e2) {
                        tcs.setError(e2);
                    }
                }
            });
        } catch (Exception e) {
            tcs.setError(new ExecutorException(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <TContinuationResult, TResult> void completeAfterTask(final TaskCompletionSource<TContinuationResult> tcs, final Continuation<TResult, Task<TContinuationResult>> continuation, final Task<TResult> task, Executor executor, final CancellationToken ct) {
        try {
            executor.execute(new Runnable() { // from class: bolts.Task.15
                @Override // java.lang.Runnable
                public void run() {
                    if (ct != null && ct.isCancellationRequested()) {
                        tcs.setCancelled();
                        return;
                    }
                    try {
                        Task task2 = (Task) continuation.then(task);
                        if (task2 == null) {
                            tcs.setResult(null);
                        } else {
                            task2.continueWith(new Continuation<TContinuationResult, Void>() { // from class: bolts.Task.15.1
                                @Override // bolts.Continuation
                                public Void then(Task<TContinuationResult> task3) {
                                    if ((ct != null && ct.isCancellationRequested()) || task3.isCancelled()) {
                                        tcs.setCancelled();
                                    } else if (task3.isFaulted()) {
                                        tcs.setError(task3.getError());
                                    } else {
                                        tcs.setResult(task3.getResult());
                                    }
                                    return null;
                                }
                            });
                        }
                    } catch (CancellationException e) {
                        tcs.setCancelled();
                    } catch (Exception e2) {
                        tcs.setError(e2);
                    }
                }
            });
        } catch (Exception e) {
            tcs.setError(new ExecutorException(e));
        }
    }

    private void runContinuations() {
        synchronized (this.lock) {
            for (Continuation<TResult, ?> continuation : this.continuations) {
                try {
                    continuation.then(this);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            this.continuations = null;
        }
    }

    boolean trySetCancelled() {
        boolean z = true;
        synchronized (this.lock) {
            if (this.complete) {
                z = false;
            } else {
                this.complete = true;
                this.cancelled = true;
                this.lock.notifyAll();
                runContinuations();
            }
        }
        return z;
    }

    boolean trySetResult(TResult result) {
        boolean z = true;
        synchronized (this.lock) {
            if (this.complete) {
                z = false;
            } else {
                this.complete = true;
                this.result = result;
                this.lock.notifyAll();
                runContinuations();
            }
        }
        return z;
    }

    boolean trySetError(Exception error) {
        synchronized (this.lock) {
            if (this.complete) {
                return false;
            }
            this.complete = true;
            this.error = error;
            this.errorHasBeenObserved = false;
            this.lock.notifyAll();
            runContinuations();
            if (!this.errorHasBeenObserved && getUnobservedExceptionHandler() != null) {
                this.unobservedErrorNotifier = new UnobservedErrorNotifier(this);
            }
            return true;
        }
    }
}
