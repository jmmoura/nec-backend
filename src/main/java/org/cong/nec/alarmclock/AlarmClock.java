package org.cong.nec.alarmclock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class AlarmClock {

        private static final Logger logger = LoggerFactory.getLogger(AlarmClock.class);

        /**
         * Scheduled method to log a wake-up message every 14 minutes.
         */
        @Scheduled(cron = "0 */14 * * * *")
        public void wakeup() {
            LocalDateTime date = LocalDateTime.now();
            logger.info("Wake up! Date and time: {}", date);

        }
}
