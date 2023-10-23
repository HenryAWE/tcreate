package henryawe.tcreate;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class TCreateTasks {
    private TCreateTasks () {
    }

    public static <T extends Mob> void mobConvertTask (
            int conversionTime,
            @NotNull Mob entity,
            @NotNull EntityType<T> targetType,
            boolean copyData,
            Consumer<@NotNull T> afterConversion
    ) {
        if (entity.getPersistentData().getBoolean("TCreateConvertTask"))
            return;
        entity.getPersistentData().putBoolean("TCreateConvertTask", true);
        final var worldIn = entity.level;
        if (worldIn.isClientSide)
            return;
        THREAD_POOL.schedule(new SyncTickTask(worldIn.getGameTime(), conversionTime, worldIn::getGameTime) {
            @Override
            void whenSync () {
            }

            @Override
            void whenEnd () {
                final var target = entity.convertTo(targetType, copyData);
                if (target != null)
                    afterConversion.accept(target);
            }
        }, 0, TimeUnit.MILLISECONDS);
    }

    private static abstract class SyncTickTask implements Runnable {
        private final long beginTick;
        private final long durationTicks;
        private long passedTicks = 0;
        private final Supplier<Long> tickTimeProvider;

        protected SyncTickTask (long beginTick, long durationTicks, Supplier<Long> tickTimeProvider) {
            this.beginTick = beginTick;
            this.durationTicks = durationTicks;
            this.tickTimeProvider = tickTimeProvider;
        }

        abstract void whenSync ();

        abstract void whenEnd ();

        @Override
        public void run () {
            while (true) {
                final long currentTick = tickTimeProvider.get();
                final long runTicks = currentTick - beginTick;
                if (runTicks == durationTicks) {
                    whenEnd();
                    break;
                }
                if (passedTicks != runTicks) {
                    passedTicks = runTicks;
                    whenSync();
                }
            }

            LOGGER.debug("Finish task, duration ticks: {}, passed ticks: {}", durationTicks, passedTicks);
        }

        private static final Logger LOGGER = LogUtils.getLogger();
    }

    private static final ScheduledThreadPoolExecutor THREAD_POOL = new ScheduledThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            DefaultThreadFactory.INSTANCE
    );

    private static final class DefaultThreadFactory implements ThreadFactory {
        private DefaultThreadFactory () {
        }

        @Override
        @NotNull
        public Thread newThread (@NotNull Runnable r) {
            return new Thread(
                    Thread.currentThread().getThreadGroup(),
                    r,
                    "TCreateTasks-[" + CREATED_THREAD_COUNT.getAndIncrement() + ']'
            );
        }

        private static final AtomicInteger CREATED_THREAD_COUNT = new AtomicInteger(0);

        static final DefaultThreadFactory INSTANCE = new DefaultThreadFactory();
    }
}
