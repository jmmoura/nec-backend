package org.cong.nec.alarmclock;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AlarmClockController.PATH)
@AllArgsConstructor
public class AlarmClockController {

    public static final String PATH = "/api/v1/alarm-clock";

    @GetMapping("/trigger")
    public String triggerAlarm() {
        return "Alarm triggered!";
    }


}
