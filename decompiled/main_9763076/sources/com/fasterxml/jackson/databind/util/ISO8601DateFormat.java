package com.fasterxml.jackson.databind.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ISO8601DateFormat extends DateFormat {
    private static Calendar CALENDAR = new GregorianCalendar();
    private static NumberFormat NUMBER_FORMAT = new DecimalFormat();
    private static final long serialVersionUID = 1;

    public ISO8601DateFormat() {
        this.numberFormat = NUMBER_FORMAT;
        this.calendar = CALENDAR;
    }

    @Override // java.text.DateFormat
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        String value = ISO8601Utils.format(date);
        toAppendTo.append(value);
        return toAppendTo;
    }

    @Override // java.text.DateFormat
    public Date parse(String source, ParsePosition pos) {
        pos.setIndex(source.length());
        return ISO8601Utils.parse(source);
    }

    @Override // java.text.DateFormat, java.text.Format
    public Object clone() {
        return this;
    }
}
