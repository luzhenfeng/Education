package com.megvii.facetrack.utils;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 请确保调用的地方在主线程。
 */
public class ThreadUtil {
    private static final int S_CORE_POOL_SIZE = 3;
    private static final int CORE_POOL_SIZE = 2;
    private static final int MAXIMUM_POOL_SIZE = 8;
    private static final int KEEP_ALIVE = 1;

    protected static final Handler handler = new Handler(Looper.getMainLooper());

    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<>(128);
    private static final BlockingQueue<Runnable> uiWorkQueue = new LinkedBlockingQueue<>(8);

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(
                @NonNull
                        Runnable r) {
            return new Thread(r,
                    "ThreadUtil -- NET #" + mCount.getAndIncrement());
        }
    };

    private static final ThreadFactory uiThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r,
                    "ThreadUtil -- UI #" + mCount.getAndIncrement());
        }
    };

    private static final ThreadFactory msgThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(
                @NonNull
                        Runnable r) {
            return new Thread(r,
                    "ThreadUtil -- MSG #" + mCount.getAndIncrement());
        }
    };

    private static final ThreadFactory gcThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(
                @NonNull
                        Runnable r) {
            return new Thread(r,
                    "ThreadUtil -- GC #" + mCount.getAndIncrement());
        }
    };

    private static final ExecutorService sExecutor = new ThreadPoolExecutor(
            S_CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            sWorkQueue, sThreadFactory, new DiscardOldestPolicy());

    private static final ThreadPoolExecutor uiExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            uiWorkQueue, uiThreadFactory, new DiscardOldestPolicy());

    /**
     * 负责消息附件的copy工作和大文件发送，必须单线程一次执行任务。其他任务禁止使用此线程池！！！
     */
    private static final ExecutorService msgExecutor = Executors
            .newSingleThreadExecutor(msgThreadFactory);

    private static final ExecutorService gcExecutor = Executors
            .newSingleThreadExecutor(gcThreadFactory);

    public static void execGC() {
        gcExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.gc();
            }
        });
    }

    public static void exec(Runnable command) {
        exec(sExecutor, command);
    }

    public static <T> void exec(Producer<T> producer,
                                Consumer<T> consumer) {
        exec(sExecutor, producer, consumer);
    }

    public static <T> void postOnMainThread(final Consumer<T> consumer,
                                            final T devilFruit) {
        if (consumer != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        consumer.accept(devilFruit);
                    } catch (Throwable t) {
                        Log.e("", t.getMessage());
                    }
                }
            });
        }
    }

    public static void postOnMainThread(Runnable runnable) {
        if (null == runnable) {
            return;
        }
        try {
            handler.post(runnable);
        } catch (Throwable t) {
            Log.e("", t.getMessage());
        }
    }

    public static void postOnMainThreadDelayed(Runnable runnable,
                                               long delayMills) {
        if (runnable != null) {
            handler.postDelayed(runnable, delayMills);
        }
    }

    public static void removeRunnable(Runnable runnable) {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    /**
     * 将consumer用软引用缓存起来，避免consumer导致的内存泄露。
     */
    public static <T> Future<?> exec(ExecutorService exec,
                                     final Producer<T> producer, final Consumer<T> consumer) {
        return exec.submit(new Runnable() {
            @Override
            public void run() {
                T devilFruit = null;
                try {
                    if (null != producer) {
                        devilFruit = producer.send();
                    }
                } catch (Throwable t) {
                    Log.e("", t.getMessage());
                } finally {
                    if (consumer != null) {
                        final T fDevilFruit = devilFruit;
                        postOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                consumer.accept(fDevilFruit);
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 将consumer用软引用缓存起来，避免consumer导致的内存泄露。
     */
    public static Future<?> exec(ExecutorService exec,
                                 final Runnable runnable) {
        return exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null != runnable) {
                        runnable.run();
                    }
                } catch (Throwable t) {
                    Log.e("", t.getMessage());
                }
            }
        });
    }

    public static void execUi(final Runnable command) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    command.run();
                } catch (Throwable t) {
                    Log.e("", t.getMessage());
                }
            }
        };
        uiExecutor.execute(runnable);
    }

    /**
     * 推荐使用此接口执行本地Task.
     */
    @SafeVarargs
    public static <A, B, C> void execUi(AsyncTask<A, B, C> task, A... params) {
        if (null == task) {
            return;
        }
        task.executeOnExecutor(uiExecutor, params);
    }

    /**
     * 推荐使用此接口执行本地Task.
     */
    @SafeVarargs
    public static <T extends AsyncTask, A> void execForTask(Executor exec, T task,
                                                            A... params) {
        if (task == null) {
            return;
        }
        task.executeOnExecutor(exec, params);
    }

    public static <T> void execUi(Producer<T> producer, Consumer<T> consumer) {
        exec(uiExecutor, producer, consumer);
    }

    public static void execInUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    public static void execInUiThreadIfNeed(Runnable runnable) {
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread()
                .getId()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }
}
