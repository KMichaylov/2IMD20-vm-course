function main() {
    timestamp1 = 1727049051000;
    timestamp2 = 1712345678900;
    timestamp3 = 1700000000000;

    println(timeStampToDateString(timestamp1));
    println(timeStampToDateString(timestamp2));
    println(timeStampToDateString(timestamp3));
}

function timeStampToDateString(timestamp) {
    MILLIS_IN_SECOND = 1000;
    MILLIS_IN_MINUTE = 60 * MILLIS_IN_SECOND;
    MILLIS_IN_HOUR = 60 * MILLIS_IN_MINUTE;
    MILLIS_IN_DAY = 24 * MILLIS_IN_HOUR;

    remainingMillis = timestamp;

    totalSeconds = remainingMillis / MILLIS_IN_SECOND;
    seconds = modulo(totalSeconds, 60);
    totalMinutes = totalSeconds / 60;
    minutes = modulo(totalMinutes, 60);
    totalHours = totalMinutes / 60;
    hours = modulo(totalHours, 24);

    totalDays = totalHours / 24;

    year = 1970;
    while (totalDays >= daysInYear(year)) {
        totalDays = totalDays - daysInYear(year);
        year = year + 1;
    }

    month = 1;
    while (totalDays >= daysInMonth(year, month)) {
        totalDays = totalDays - daysInMonth(year, month);
        month = month + 1;
    }

    day = totalDays + 1;

    finalString = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    
    return finalString;
}

function modulo(dividend, divisor) {
    remain = ((dividend - (divisor * ((dividend / divisor) + 1))) + divisor);
    return remain;
}

function isLeapYear(year) {
    return (modulo(year, 4) == 0) && (modulo(year, 100) != 0) || modulo(year, 400) == 0;
}

function daysInYear(year) {
    if (isLeapYear(year)) {
        return 366;
    } else {
        return 365;
    }
}

function daysInGeneralMonths(month) {
    months = new();
    months[0] = 31;
    months[1] = 28;
    months[2] = 31;
    months[3] = 30;
    months[4] = 31;
    months[5] = 30;
    months[6] = 31;
    months[7] = 31;
    months[8] = 30;
    months[9] = 31;
    months[10] = 30;
    months[11] = 31;

    if (month < 0 || month > 11) {
        return 0;
    }

    return months[month - 1];
}

function daysInMonth(year, month) {
    if (month == 2 && isLeapYear(year)) {
        return 29;
    } else {
        return daysInGeneralMonths(month);
    }
}
