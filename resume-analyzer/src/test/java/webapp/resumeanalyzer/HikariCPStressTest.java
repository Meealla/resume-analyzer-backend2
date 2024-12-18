package webapp.resumeanalyzer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HikariCPStressTest {

    private static HikariDataSource dataSource;
    private static final int NUM_THREADS = 100;
    private static final AtomicInteger successfulConnections = new AtomicInteger(0);
    private static final AtomicInteger failedConnections = new AtomicInteger(0);


    @BeforeAll
    public static void setUp() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/test_db?useSSL=false");
        config.setUsername("postgres");
        config.setPassword("password");
        config.setMaximumPoolSize(NUM_THREADS * 2);
        config.setMinimumIdle(NUM_THREADS / 2);
        dataSource = new HikariDataSource(config);
    }

    @Test
    public void testHighConcurrency() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        List<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            tasks.add(() -> {
                try (Connection connection = dataSource.getConnection()) {
                    successfulConnections.incrementAndGet();
                } catch (SQLException e) {
                    failedConnections.incrementAndGet();
                    System.err.println("Connection failed: " + e.getMessage());
                }
            });
        }

        executor.invokeAll(tasks);
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS); // Ждем завершения всех потоков

        System.out.println("Successful connections: " + successfulConnections.get());
        System.out.println("Failed connections: " + failedConnections.get());
        assertThat(failedConnections.get(), equalTo(0)); // Убеждаемся, что все подключения успешные

    }


    @AfterAll
    public static void tearDown() {
        dataSource.close();
    }
}