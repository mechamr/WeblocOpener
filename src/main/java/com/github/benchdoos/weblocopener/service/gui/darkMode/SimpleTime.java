/*
 * (C) Copyright 2019.  Eugene Zrazhevsky and others.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Contributors:
 * Eugene Zrazhevsky <eugene.zrazhevsky@gmail.com>
 */

package com.github.benchdoos.weblocopener.service.gui.darkMode;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SimpleTime {
    private int hour;
    private int minute;

    public SimpleTime() {

    }

    public SimpleTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTime that = (SimpleTime) o;
        return hour == that.hour &&
                minute == that.minute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    public Date toDate() {
        final Calendar up = Calendar.getInstance();
        up.set(Calendar.HOUR, hour);
        up.set(Calendar.MINUTE, minute);
        return up.getTime();
    }

    @Override
    public String toString() {
        return "SimpleTime{" +
                "hour=" + hour +
                ", minute=" + minute +
                '}';
    }
}
