package com.uroria.gameapi.task;

import com.uroria.gameapi.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AsyncTask<T> {

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(0);
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncTask.class);

    private final Function<Integer, ? extends T> function;
    private final Executor backSync;
    private final long time;
    private final TimeUnit timeUnit;

    AsyncTask(Supplier<? extends T> supplier, Executor backSync) {
        this(supplier, backSync, 0, TimeUnit.MILLISECONDS);
    }

    public AsyncTask(Supplier<? extends T> supplier, Executor backSync, long time, TimeUnit timeUnit) {
        this(ignored -> supplier.get(), backSync, time, timeUnit);
    }

    public AsyncTask(Function<Integer, ? extends T> function, Executor backSync, long time, TimeUnit timeUnit) {
        this.function = function;
        this.backSync = backSync;
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public void run(Consumer<? super T> success) {
        run(success, this::onError);
    }

    public void run(Consumer<? super T> success, Consumer<? super Throwable> error) {
        var future = time == 0 ? submit() : submitLater(time, timeUnit);
        future.whenComplete((onSuccess, onError) -> backSync.execute(() -> {
            if (future.isCompletedExceptionally()) {
                error.accept(onError);
                return;
            }
            success.accept(onSuccess);
        }));
    }

    public void run(BiConsumer<? super T, ? super ScheduledFuture<?>> success) {
        run(success, (throwable, runnable) -> this.onError(throwable));
    }

    public void run(BiConsumer<? super T, ? super ScheduledFuture<?>> success, BiConsumer<? super Throwable, ? super ScheduledFuture<?>> error) {
        submitTimer(success, error);
    }


    private CompletableFuture<T> submit() {
        var future = new CompletableFuture<T>();
        processFuture(future, 1);
        return future;
    }

    private CompletableFuture<T> submitLater(long time, TimeUnit unit) {
        var future = new CompletableFuture<T>();
        EXECUTOR_SERVICE.schedule(() -> processFuture(future, 1), time, unit);
        return future;
    }

    private void submitTimer(BiConsumer<? super T, ? super ScheduledFuture<?>> success, BiConsumer<? super Throwable, ? super ScheduledFuture<?>> error) {

        var scheduledFuture = new AtomicReference<ScheduledFuture<?>>(null);
        var counter = new AtomicInteger(1);

        scheduledFuture.set(EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {

            var future = new CompletableFuture<T>();
            processFuture(future, counter.getAndIncrement());

            future.whenComplete(((result, throwable) -> backSync.execute(() -> {
                if (future.isCompletedExceptionally()) {
                    error.accept(throwable, scheduledFuture.get());
                    return;
                }

                success.accept(result, scheduledFuture.get());
            })));

        }, time, time, timeUnit));
    }

    private void processFuture(CompletableFuture<? super T> future, Integer counter) {
        var result = execute(counter).join();
        if (result.second() != null) {
            future.completeExceptionally(result.second());
        } else {
            future.complete(result.first());
        }
    }

    private ForkJoinTask<Pair<T, Throwable>> execute(Integer counter) {
        return ForkJoinPool.commonPool().submit(ForkJoinTask.adapt(() -> {
            try {
                return new Pair<>(function.apply(counter), null);
            } catch (Throwable throwable) {
                return new Pair<>(null, throwable);
            }
        }));
    }

    private void onError(Throwable throwable) {
        LOGGER.error("Unhandled exception in AsyncAction", throwable);
    }

}
