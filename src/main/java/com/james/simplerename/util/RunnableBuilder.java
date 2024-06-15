package com.james.simplerename.util;

import com.james.simplerename.Main;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

@RequiredArgsConstructor
public class RunnableBuilder {

    public static final BukkitScheduler scheduler = Bukkit.getScheduler();

    private final JavaPlugin plugin;
    private Runnable runnable;

    private int taskId;

    public static RunnableBuilder forPlugin(JavaPlugin plugin) {
        return new RunnableBuilder(plugin);
    }

    public static RunnableBuilder bind(Runnable runnable) {
        return forPlugin(Main.getInstance()).with(runnable);
    }

    public RunnableBuilder with(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public void cancel() {
        scheduler.cancelTask(taskId);
        taskId = -1;
    }

    public boolean isCancelled() {
        return taskId == -1;
    }

    public boolean isQueued() {
        return !isCancelled() && scheduler.isQueued(taskId);
    }

    public boolean isRunning() {
        return !isCancelled() && scheduler.isCurrentlyRunning(taskId);
    }

    public int runSync() {
        return taskId = scheduler.runTask(plugin, runnable).getTaskId();
    }

    public int runSyncLater(long delay) {
        return taskId = scheduler.runTaskLater(plugin, runnable, delay).getTaskId();
    }

    public int runSyncTimer(long delay, long interval) {
        return taskId = scheduler.runTaskTimer(plugin, runnable, delay, interval).getTaskId();
    }

    public int runAsync() {
        return taskId = scheduler.runTaskAsynchronously(plugin, runnable).getTaskId();
    }

    public int runAsyncLater(long delay) {
        return taskId = scheduler.runTaskLaterAsynchronously(plugin, runnable, delay).getTaskId();
    }

    public int runAsyncTimer(long delay, long interval) {
        return taskId = scheduler.runTaskTimerAsynchronously(plugin, runnable, delay, interval).getTaskId();
    }
}
