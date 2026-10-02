package com.xiaocong.smarthome.pickerview.view;

import android.view.View;
import com.xiaocong.smarthome.pickerview.adapter.ArrayWheelAdapter;
import com.xiaocong.smarthome.pickerview.adapter.NumericWheelAdapter;
import com.xiaocong.smarthome.pickerview.listener.ISelectTimeCallback;
import com.xiaocong.smarthome.pickerview.utils.ChinaDate;
import com.xiaocong.smarthome.pickerview.utils.LunarCalendar;
import com.xiaocong.smarthome.uilib.R;
import com.xiaocong.smarthome.wheelview.listener.OnItemSelectedListener;
import com.xiaocong.smarthome.wheelview.view.WheelView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int currentYear;
    private int dividerColor;
    private WheelView.DividerType dividerType;
    private int gravity;
    private float lineSpacingMultiplier;
    private ISelectTimeCallback mSelectChangeCallback;
    private int textColorCenter;
    private int textColorOut;
    private int textSize;
    private boolean[] type;
    private View view;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_minutes;
    private WheelView wv_month;
    private WheelView wv_seconds;
    private WheelView wv_year;
    private int startYear = 1900;
    private int endYear = 2100;
    private int startMonth = 1;
    private int endMonth = 12;
    private int startDay = 1;
    private int endDay = 31;
    private boolean isLunarCalendar = false;

    public WheelTime(View view, boolean[] type, int gravity, int textSize) {
        this.view = view;
        this.type = type;
        this.gravity = gravity;
        this.textSize = textSize;
        setView(view);
    }

    public void setLunarMode(boolean isLunarCalendar) {
        this.isLunarCalendar = isLunarCalendar;
    }

    public void setPicker(int year, int month, int day, int h, int m, int s) {
        if (this.isLunarCalendar) {
            int[] lunar = LunarCalendar.solarToLunar(year, month + 1, day);
            setLunar(lunar[0], lunar[1] - 1, lunar[2], lunar[3] == 1, h, m, s);
        } else {
            setSolar(year, month, day, h, m, s);
        }
    }

    private void setLunar(int year, int month, int day, boolean isLeap, int h, int m, int s) {
        this.wv_year = (WheelView) this.view.findViewById(R.id.year);
        this.wv_year.setAdapter(new ArrayWheelAdapter(ChinaDate.getYears(this.startYear, this.endYear)));
        this.wv_year.setLabel("");
        this.wv_year.setCurrentItem(year - this.startYear);
        this.wv_year.setGravity(this.gravity);
        this.wv_month = (WheelView) this.view.findViewById(R.id.month);
        this.wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year)));
        this.wv_month.setLabel("");
        int leapMonth = ChinaDate.leapMonth(year);
        if (leapMonth != 0 && (month > leapMonth - 1 || isLeap)) {
            this.wv_month.setCurrentItem(month + 1);
        } else {
            this.wv_month.setCurrentItem(month);
        }
        this.wv_month.setGravity(this.gravity);
        this.wv_day = (WheelView) this.view.findViewById(R.id.day);
        if (ChinaDate.leapMonth(year) == 0) {
            this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year, month))));
        } else {
            this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year))));
        }
        this.wv_day.setLabel("");
        this.wv_day.setCurrentItem(day - 1);
        this.wv_day.setGravity(this.gravity);
        this.wv_hours = (WheelView) this.view.findViewById(R.id.hour);
        this.wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        this.wv_hours.setCurrentItem(h);
        this.wv_hours.setGravity(this.gravity);
        this.wv_minutes = (WheelView) this.view.findViewById(R.id.min);
        this.wv_minutes.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_minutes.setCurrentItem(m);
        this.wv_minutes.setGravity(this.gravity);
        this.wv_seconds = (WheelView) this.view.findViewById(R.id.second);
        this.wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_seconds.setCurrentItem(m);
        this.wv_seconds.setGravity(this.gravity);
        this.wv_year.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.xiaocong.smarthome.pickerview.view.WheelTime.1
            @Override // com.xiaocong.smarthome.wheelview.listener.OnItemSelectedListener
            public void onItemSelected(int index) {
                int maxItem;
                int year_num = index + WheelTime.this.startYear;
                WheelTime.this.wv_month.setAdapter(new ArrayWheelAdapter(ChinaDate.getMonths(year_num)));
                if (ChinaDate.leapMonth(year_num) == 0 || WheelTime.this.wv_month.getCurrentItem() <= ChinaDate.leapMonth(year_num) - 1) {
                    WheelTime.this.wv_month.setCurrentItem(WheelTime.this.wv_month.getCurrentItem());
                } else {
                    WheelTime.this.wv_month.setCurrentItem(WheelTime.this.wv_month.getCurrentItem() + 1);
                }
                if (ChinaDate.leapMonth(year_num) == 0 || WheelTime.this.wv_month.getCurrentItem() <= ChinaDate.leapMonth(year_num) - 1) {
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, WheelTime.this.wv_month.getCurrentItem() + 1))));
                    maxItem = ChinaDate.monthDays(year_num, WheelTime.this.wv_month.getCurrentItem() + 1);
                } else if (WheelTime.this.wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                    maxItem = ChinaDate.leapDays(year_num);
                } else {
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, WheelTime.this.wv_month.getCurrentItem()))));
                    maxItem = ChinaDate.monthDays(year_num, WheelTime.this.wv_month.getCurrentItem());
                }
                if (WheelTime.this.wv_day.getCurrentItem() > maxItem - 1) {
                    WheelTime.this.wv_day.setCurrentItem(maxItem - 1);
                }
                if (WheelTime.this.mSelectChangeCallback != null) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        this.wv_month.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.xiaocong.smarthome.pickerview.view.WheelTime.2
            @Override // com.xiaocong.smarthome.wheelview.listener.OnItemSelectedListener
            public void onItemSelected(int index) {
                int maxItem;
                int year_num = WheelTime.this.wv_year.getCurrentItem() + WheelTime.this.startYear;
                if (ChinaDate.leapMonth(year_num) != 0 && index > ChinaDate.leapMonth(year_num) - 1) {
                    if (WheelTime.this.wv_month.getCurrentItem() == ChinaDate.leapMonth(year_num) + 1) {
                        WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.leapDays(year_num))));
                        maxItem = ChinaDate.leapDays(year_num);
                    } else {
                        WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, index))));
                        maxItem = ChinaDate.monthDays(year_num, index);
                    }
                } else {
                    WheelTime.this.wv_day.setAdapter(new ArrayWheelAdapter(ChinaDate.getLunarDays(ChinaDate.monthDays(year_num, index + 1))));
                    maxItem = ChinaDate.monthDays(year_num, index + 1);
                }
                if (WheelTime.this.wv_day.getCurrentItem() > maxItem - 1) {
                    WheelTime.this.wv_day.setCurrentItem(maxItem - 1);
                }
                if (WheelTime.this.mSelectChangeCallback != null) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        setChangedListener(this.wv_day);
        setChangedListener(this.wv_hours);
        setChangedListener(this.wv_minutes);
        setChangedListener(this.wv_seconds);
        if (this.type.length != 6) {
            throw new RuntimeException("type[] length is not 6");
        }
        this.wv_year.setVisibility(this.type[0] ? 0 : 8);
        this.wv_month.setVisibility(this.type[1] ? 0 : 8);
        this.wv_day.setVisibility(this.type[2] ? 0 : 8);
        this.wv_hours.setVisibility(this.type[3] ? 0 : 8);
        this.wv_minutes.setVisibility(this.type[4] ? 0 : 8);
        this.wv_seconds.setVisibility(this.type[5] ? 0 : 8);
        setContentTextSize();
    }

    private void setSolar(int year, int month, int day, int h, int m, int s) {
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};
        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);
        this.currentYear = year;
        this.wv_year = (WheelView) this.view.findViewById(R.id.year);
        this.wv_year.setAdapter(new NumericWheelAdapter(this.startYear, this.endYear));
        this.wv_year.setCurrentItem(year - this.startYear);
        this.wv_year.setGravity(this.gravity);
        this.wv_month = (WheelView) this.view.findViewById(R.id.month);
        if (this.startYear == this.endYear) {
            this.wv_month.setAdapter(new NumericWheelAdapter(this.startMonth, this.endMonth));
            this.wv_month.setCurrentItem((month + 1) - this.startMonth);
        } else if (year == this.startYear) {
            this.wv_month.setAdapter(new NumericWheelAdapter(this.startMonth, 12));
            this.wv_month.setCurrentItem((month + 1) - this.startMonth);
        } else if (year == this.endYear) {
            this.wv_month.setAdapter(new NumericWheelAdapter(1, this.endMonth));
            this.wv_month.setCurrentItem(month);
        } else {
            this.wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            this.wv_month.setCurrentItem(month);
        }
        this.wv_month.setGravity(this.gravity);
        this.wv_day = (WheelView) this.view.findViewById(R.id.day);
        if (this.startYear == this.endYear && this.startMonth == this.endMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                if (this.endDay > 31) {
                    this.endDay = 31;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (this.endDay > 30) {
                    this.endDay = 30;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            } else if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                if (this.endDay > 29) {
                    this.endDay = 29;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            } else {
                if (this.endDay > 28) {
                    this.endDay = 28;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, this.endDay));
            }
            this.wv_day.setCurrentItem(day - this.startDay);
        } else if (year == this.startYear && month + 1 == this.startMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 30));
            } else if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 29));
            } else {
                this.wv_day.setAdapter(new NumericWheelAdapter(this.startDay, 28));
            }
            this.wv_day.setCurrentItem(day - this.startDay);
        } else if (year == this.endYear && month + 1 == this.endMonth) {
            if (list_big.contains(String.valueOf(month + 1))) {
                if (this.endDay > 31) {
                    this.endDay = 31;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                if (this.endDay > 30) {
                    this.endDay = 30;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            } else if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                if (this.endDay > 29) {
                    this.endDay = 29;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            } else {
                if (this.endDay > 28) {
                    this.endDay = 28;
                }
                this.wv_day.setAdapter(new NumericWheelAdapter(1, this.endDay));
            }
            this.wv_day.setCurrentItem(day - 1);
        } else {
            if (list_big.contains(String.valueOf(month + 1))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 31));
            } else if (list_little.contains(String.valueOf(month + 1))) {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 30));
            } else if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            } else {
                this.wv_day.setAdapter(new NumericWheelAdapter(1, 28));
            }
            this.wv_day.setCurrentItem(day - 1);
        }
        this.wv_day.setGravity(this.gravity);
        this.wv_hours = (WheelView) this.view.findViewById(R.id.hour);
        this.wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        this.wv_hours.setCurrentItem(h);
        this.wv_hours.setGravity(this.gravity);
        this.wv_minutes = (WheelView) this.view.findViewById(R.id.min);
        this.wv_minutes.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_minutes.setCurrentItem(m);
        this.wv_minutes.setGravity(this.gravity);
        this.wv_seconds = (WheelView) this.view.findViewById(R.id.second);
        this.wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
        this.wv_seconds.setCurrentItem(s);
        this.wv_seconds.setGravity(this.gravity);
        this.wv_year.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.xiaocong.smarthome.pickerview.view.WheelTime.3
            @Override // com.xiaocong.smarthome.wheelview.listener.OnItemSelectedListener
            public void onItemSelected(int index) {
                int year_num = index + WheelTime.this.startYear;
                WheelTime.this.currentYear = year_num;
                int currentMonthItem = WheelTime.this.wv_month.getCurrentItem();
                if (WheelTime.this.startYear == WheelTime.this.endYear) {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(WheelTime.this.startMonth, WheelTime.this.endMonth));
                    if (currentMonthItem > WheelTime.this.wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = WheelTime.this.wv_month.getAdapter().getItemsCount() - 1;
                        WheelTime.this.wv_month.setCurrentItem(currentMonthItem);
                    }
                    int monthNum = currentMonthItem + WheelTime.this.startMonth;
                    if (WheelTime.this.startMonth == WheelTime.this.endMonth) {
                        WheelTime.this.setReDay(year_num, monthNum, WheelTime.this.startDay, WheelTime.this.endDay, list_big, list_little);
                    } else if (monthNum == WheelTime.this.startMonth) {
                        WheelTime.this.setReDay(year_num, monthNum, WheelTime.this.startDay, 31, list_big, list_little);
                    } else if (monthNum == WheelTime.this.endMonth) {
                        WheelTime.this.setReDay(year_num, monthNum, 1, WheelTime.this.endDay, list_big, list_little);
                    } else {
                        WheelTime.this.setReDay(year_num, monthNum, 1, 31, list_big, list_little);
                    }
                } else if (year_num == WheelTime.this.startYear) {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(WheelTime.this.startMonth, 12));
                    if (currentMonthItem > WheelTime.this.wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = WheelTime.this.wv_month.getAdapter().getItemsCount() - 1;
                        WheelTime.this.wv_month.setCurrentItem(currentMonthItem);
                    }
                    int month2 = currentMonthItem + WheelTime.this.startMonth;
                    if (month2 == WheelTime.this.startMonth) {
                        WheelTime.this.setReDay(year_num, month2, WheelTime.this.startDay, 31, list_big, list_little);
                    } else {
                        WheelTime.this.setReDay(year_num, month2, 1, 31, list_big, list_little);
                    }
                } else if (year_num == WheelTime.this.endYear) {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(1, WheelTime.this.endMonth));
                    if (currentMonthItem > WheelTime.this.wv_month.getAdapter().getItemsCount() - 1) {
                        currentMonthItem = WheelTime.this.wv_month.getAdapter().getItemsCount() - 1;
                        WheelTime.this.wv_month.setCurrentItem(currentMonthItem);
                    }
                    int monthNum2 = currentMonthItem + 1;
                    if (monthNum2 == WheelTime.this.endMonth) {
                        WheelTime.this.setReDay(year_num, monthNum2, 1, WheelTime.this.endDay, list_big, list_little);
                    } else {
                        WheelTime.this.setReDay(year_num, monthNum2, 1, 31, list_big, list_little);
                    }
                } else {
                    WheelTime.this.wv_month.setAdapter(new NumericWheelAdapter(1, 12));
                    WheelTime.this.setReDay(year_num, WheelTime.this.wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                }
                if (WheelTime.this.mSelectChangeCallback != null) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        this.wv_month.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.xiaocong.smarthome.pickerview.view.WheelTime.4
            @Override // com.xiaocong.smarthome.wheelview.listener.OnItemSelectedListener
            public void onItemSelected(int index) {
                int month_num = index + 1;
                if (WheelTime.this.startYear == WheelTime.this.endYear) {
                    int month_num2 = (WheelTime.this.startMonth + month_num) - 1;
                    if (WheelTime.this.startMonth == WheelTime.this.endMonth) {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num2, WheelTime.this.startDay, WheelTime.this.endDay, list_big, list_little);
                    } else if (WheelTime.this.startMonth == month_num2) {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num2, WheelTime.this.startDay, 31, list_big, list_little);
                    } else if (WheelTime.this.endMonth == month_num2) {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num2, 1, WheelTime.this.endDay, list_big, list_little);
                    } else {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num2, 1, 31, list_big, list_little);
                    }
                } else if (WheelTime.this.currentYear == WheelTime.this.startYear) {
                    int month_num3 = (WheelTime.this.startMonth + month_num) - 1;
                    if (month_num3 == WheelTime.this.startMonth) {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num3, WheelTime.this.startDay, 31, list_big, list_little);
                    } else {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, month_num3, 1, 31, list_big, list_little);
                    }
                } else if (WheelTime.this.currentYear == WheelTime.this.endYear) {
                    if (month_num == WheelTime.this.endMonth) {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, WheelTime.this.wv_month.getCurrentItem() + 1, 1, WheelTime.this.endDay, list_big, list_little);
                    } else {
                        WheelTime.this.setReDay(WheelTime.this.currentYear, WheelTime.this.wv_month.getCurrentItem() + 1, 1, 31, list_big, list_little);
                    }
                } else {
                    WheelTime.this.setReDay(WheelTime.this.currentYear, month_num, 1, 31, list_big, list_little);
                }
                if (WheelTime.this.mSelectChangeCallback != null) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            }
        });
        setChangedListener(this.wv_day);
        setChangedListener(this.wv_hours);
        setChangedListener(this.wv_minutes);
        setChangedListener(this.wv_seconds);
        if (this.type.length != 6) {
            throw new IllegalArgumentException("type[] length is not 6");
        }
        this.wv_year.setVisibility(this.type[0] ? 0 : 8);
        this.wv_month.setVisibility(this.type[1] ? 0 : 8);
        this.wv_day.setVisibility(this.type[2] ? 0 : 8);
        this.wv_hours.setVisibility(this.type[3] ? 0 : 8);
        this.wv_minutes.setVisibility(this.type[4] ? 0 : 8);
        this.wv_seconds.setVisibility(this.type[5] ? 0 : 8);
        setContentTextSize();
    }

    private void setChangedListener(WheelView wheelView) {
        if (this.mSelectChangeCallback != null) {
            wheelView.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.xiaocong.smarthome.pickerview.view.WheelTime.5
                @Override // com.xiaocong.smarthome.wheelview.listener.OnItemSelectedListener
                public void onItemSelected(int index) {
                    WheelTime.this.mSelectChangeCallback.onTimeSelectChanged();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setReDay(int year_num, int monthNum, int startD, int endD, List<String> list_big, List<String> list_little) {
        int currentItem = this.wv_day.getCurrentItem();
        if (list_big.contains(String.valueOf(monthNum))) {
            if (endD > 31) {
                endD = 31;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
        } else if (list_little.contains(String.valueOf(monthNum))) {
            if (endD > 30) {
                endD = 30;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
        } else if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0) {
            if (endD > 29) {
                endD = 29;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
        } else {
            if (endD > 28) {
                endD = 28;
            }
            this.wv_day.setAdapter(new NumericWheelAdapter(startD, endD));
        }
        if (currentItem > this.wv_day.getAdapter().getItemsCount() - 1) {
            int currentItem2 = this.wv_day.getAdapter().getItemsCount() - 1;
            this.wv_day.setCurrentItem(currentItem2);
        }
    }

    private void setContentTextSize() {
        this.wv_day.setTextSize(this.textSize);
        this.wv_month.setTextSize(this.textSize);
        this.wv_year.setTextSize(this.textSize);
        this.wv_hours.setTextSize(this.textSize);
        this.wv_minutes.setTextSize(this.textSize);
        this.wv_seconds.setTextSize(this.textSize);
    }

    private void setTextColorOut() {
        this.wv_day.setTextColorOut(this.textColorOut);
        this.wv_month.setTextColorOut(this.textColorOut);
        this.wv_year.setTextColorOut(this.textColorOut);
        this.wv_hours.setTextColorOut(this.textColorOut);
        this.wv_minutes.setTextColorOut(this.textColorOut);
        this.wv_seconds.setTextColorOut(this.textColorOut);
    }

    private void setTextColorCenter() {
        this.wv_day.setTextColorCenter(this.textColorCenter);
        this.wv_month.setTextColorCenter(this.textColorCenter);
        this.wv_year.setTextColorCenter(this.textColorCenter);
        this.wv_hours.setTextColorCenter(this.textColorCenter);
        this.wv_minutes.setTextColorCenter(this.textColorCenter);
        this.wv_seconds.setTextColorCenter(this.textColorCenter);
    }

    private void setDividerColor() {
        this.wv_day.setDividerColor(this.dividerColor);
        this.wv_month.setDividerColor(this.dividerColor);
        this.wv_year.setDividerColor(this.dividerColor);
        this.wv_hours.setDividerColor(this.dividerColor);
        this.wv_minutes.setDividerColor(this.dividerColor);
        this.wv_seconds.setDividerColor(this.dividerColor);
    }

    private void setDividerType() {
        this.wv_day.setDividerType(this.dividerType);
        this.wv_month.setDividerType(this.dividerType);
        this.wv_year.setDividerType(this.dividerType);
        this.wv_hours.setDividerType(this.dividerType);
        this.wv_minutes.setDividerType(this.dividerType);
        this.wv_seconds.setDividerType(this.dividerType);
    }

    private void setLineSpacingMultiplier() {
        this.wv_day.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_month.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_year.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_hours.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_minutes.setLineSpacingMultiplier(this.lineSpacingMultiplier);
        this.wv_seconds.setLineSpacingMultiplier(this.lineSpacingMultiplier);
    }

    public void setLabels(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds) {
        if (!this.isLunarCalendar) {
            if (label_year != null) {
                this.wv_year.setLabel(label_year);
            } else {
                this.wv_year.setLabel(this.view.getContext().getString(R.string.pickerview_year));
            }
            if (label_month != null) {
                this.wv_month.setLabel(label_month);
            } else {
                this.wv_month.setLabel(this.view.getContext().getString(R.string.pickerview_month));
            }
            if (label_day != null) {
                this.wv_day.setLabel(label_day);
            } else {
                this.wv_day.setLabel(this.view.getContext().getString(R.string.pickerview_day));
            }
            if (label_hours != null) {
                this.wv_hours.setLabel(label_hours);
            } else {
                this.wv_hours.setLabel(this.view.getContext().getString(R.string.pickerview_hours));
            }
            if (label_mins != null) {
                this.wv_minutes.setLabel(label_mins);
            } else {
                this.wv_minutes.setLabel(this.view.getContext().getString(R.string.pickerview_minutes));
            }
            if (label_seconds != null) {
                this.wv_seconds.setLabel(label_seconds);
            } else {
                this.wv_seconds.setLabel(this.view.getContext().getString(R.string.pickerview_seconds));
            }
        }
    }

    public void setTextXOffset(int x_offset_year, int x_offset_month, int x_offset_day, int x_offset_hours, int x_offset_minutes, int x_offset_seconds) {
        this.wv_day.setTextXOffset(x_offset_year);
        this.wv_month.setTextXOffset(x_offset_month);
        this.wv_year.setTextXOffset(x_offset_day);
        this.wv_hours.setTextXOffset(x_offset_hours);
        this.wv_minutes.setTextXOffset(x_offset_minutes);
        this.wv_seconds.setTextXOffset(x_offset_seconds);
    }

    public void setCyclic(boolean cyclic) {
        this.wv_year.setCyclic(cyclic);
        this.wv_month.setCyclic(cyclic);
        this.wv_day.setCyclic(cyclic);
        this.wv_hours.setCyclic(cyclic);
        this.wv_minutes.setCyclic(cyclic);
        this.wv_seconds.setCyclic(cyclic);
    }

    public String getTime() {
        if (this.isLunarCalendar) {
            return getLunarTime();
        }
        StringBuilder sb = new StringBuilder();
        if (this.currentYear == this.startYear) {
            if (this.wv_month.getCurrentItem() + this.startMonth == this.startMonth) {
                sb.append(this.wv_year.getCurrentItem() + this.startYear).append("-").append(this.wv_month.getCurrentItem() + this.startMonth).append("-").append(this.wv_day.getCurrentItem() + this.startDay).append(" ").append(this.wv_hours.getCurrentItem()).append(":").append(this.wv_minutes.getCurrentItem()).append(":").append(this.wv_seconds.getCurrentItem());
            } else {
                sb.append(this.wv_year.getCurrentItem() + this.startYear).append("-").append(this.wv_month.getCurrentItem() + this.startMonth).append("-").append(this.wv_day.getCurrentItem() + 1).append(" ").append(this.wv_hours.getCurrentItem()).append(":").append(this.wv_minutes.getCurrentItem()).append(":").append(this.wv_seconds.getCurrentItem());
            }
        } else {
            sb.append(this.wv_year.getCurrentItem() + this.startYear).append("-").append(this.wv_month.getCurrentItem() + 1).append("-").append(this.wv_day.getCurrentItem() + 1).append(" ").append(this.wv_hours.getCurrentItem()).append(":").append(this.wv_minutes.getCurrentItem()).append(":").append(this.wv_seconds.getCurrentItem());
        }
        return sb.toString();
    }

    private String getLunarTime() {
        int month;
        StringBuilder sb = new StringBuilder();
        int year = this.wv_year.getCurrentItem() + this.startYear;
        boolean isLeapMonth = false;
        if (ChinaDate.leapMonth(year) == 0 || (this.wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) <= 0) {
            month = this.wv_month.getCurrentItem() + 1;
        } else if ((this.wv_month.getCurrentItem() + 1) - ChinaDate.leapMonth(year) == 1) {
            month = this.wv_month.getCurrentItem();
            isLeapMonth = true;
        } else {
            month = this.wv_month.getCurrentItem();
        }
        int day = this.wv_day.getCurrentItem() + 1;
        int[] solar = LunarCalendar.lunarToSolar(year, month, day, isLeapMonth);
        sb.append(solar[0]).append("-").append(solar[1]).append("-").append(solar[2]).append(" ").append(this.wv_hours.getCurrentItem()).append(":").append(this.wv_minutes.getCurrentItem()).append(":").append(this.wv_seconds.getCurrentItem());
        return sb.toString();
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setRangDate(Calendar startDate, Calendar endDate) {
        if (startDate == null && endDate != null) {
            int year = endDate.get(1);
            int month = endDate.get(2) + 1;
            int day = endDate.get(5);
            if (year > this.startYear) {
                this.endYear = year;
                this.endMonth = month;
                this.endDay = day;
                return;
            } else {
                if (year == this.startYear) {
                    if (month > this.startMonth) {
                        this.endYear = year;
                        this.endMonth = month;
                        this.endDay = day;
                        return;
                    } else {
                        if (month == this.startMonth && day > this.startDay) {
                            this.endYear = year;
                            this.endMonth = month;
                            this.endDay = day;
                            return;
                        }
                        return;
                    }
                }
                return;
            }
        }
        if (startDate != null && endDate == null) {
            int year2 = startDate.get(1);
            int month2 = startDate.get(2) + 1;
            int day2 = startDate.get(5);
            if (year2 < this.endYear) {
                this.startMonth = month2;
                this.startDay = day2;
                this.startYear = year2;
                return;
            } else {
                if (year2 == this.endYear) {
                    if (month2 < this.endMonth) {
                        this.startMonth = month2;
                        this.startDay = day2;
                        this.startYear = year2;
                        return;
                    } else {
                        if (month2 == this.endMonth && day2 < this.endDay) {
                            this.startMonth = month2;
                            this.startDay = day2;
                            this.startYear = year2;
                            return;
                        }
                        return;
                    }
                }
                return;
            }
        }
        if (startDate != null && endDate != null) {
            this.startYear = startDate.get(1);
            this.endYear = endDate.get(1);
            this.startMonth = startDate.get(2) + 1;
            this.endMonth = endDate.get(2) + 1;
            this.startDay = startDate.get(5);
            this.endDay = endDate.get(5);
        }
    }

    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    public void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }

    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    public void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    public void isCenterLabel(boolean isCenterLabel) {
        this.wv_day.isCenterLabel(isCenterLabel);
        this.wv_month.isCenterLabel(isCenterLabel);
        this.wv_year.isCenterLabel(isCenterLabel);
        this.wv_hours.isCenterLabel(isCenterLabel);
        this.wv_minutes.isCenterLabel(isCenterLabel);
        this.wv_seconds.isCenterLabel(isCenterLabel);
    }

    public void setSelectChangeCallback(ISelectTimeCallback mSelectChangeCallback) {
        this.mSelectChangeCallback = mSelectChangeCallback;
    }
}
