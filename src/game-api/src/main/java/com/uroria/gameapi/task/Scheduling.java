package com.uroria.gameapi.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Scheduling {

    private static JavaPlugin plugin;

    private Scheduling() {
    }

    public static <T> AsyncTask<T> runTask(Supplier<? extends T> action) {
        return new AsyncTask<>(action, Scheduling::backSync);
    }

    public static <T> AsyncTask<T> runTaskLater(Supplier<? extends T> action, long time, TimeUnit unit) {
        return new AsyncTask<>(action, Scheduling::backSync, time, unit);
    }

    public static <T> AsyncTask<T> runTaskTimer(Function<Integer, ? extends T> action, long time, TimeUnit unit) {
        return new AsyncTask<>(action, Scheduling::backSync, time, unit);
    }

    /**
     * when return true inside the function the task is canceled otherwise the task keeps running within the specified time and timeunit
     *
     * @param action which runs in the specified time
     * @param time   which runs the action
     * @param unit   which runs the action in combination with time
     */
    public static void runDefaultTaskTimer(Function<Integer, Boolean> action, long time, TimeUnit unit) {
        new AsyncTask<>(action, Scheduling::backSync, time, unit).run((aBoolean, scheduledFuture) -> {
            if (aBoolean) scheduledFuture.cancel(false);
        });
    }

    public static <T> CompletableFuture<T> innerSync(Supplier<? extends T> supplier) {
        final var future = new CompletableFuture<T>();
        Scheduling.backSync(() -> future.complete(supplier.get()));
        return future;
    }

    public static void innerSync(Runnable runnable) {
        Scheduling.backSync(runnable);
    }

    private static void backSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }


    public static void initialize(JavaPlugin javaPlugin) {
        if (Scheduling.plugin == null) Scheduling.plugin = javaPlugin;
    }
}
