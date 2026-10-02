package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BaseSettings {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final ClassIntrospector _classIntrospector;
    protected final DateFormat _dateFormat;
    protected final HandlerInstantiator _handlerInstantiator;
    protected final Locale _locale;
    protected final PropertyNamingStrategy _propertyNamingStrategy;
    protected final TimeZone _timeZone;
    protected final TypeFactory _typeFactory;
    protected final TypeResolverBuilder<?> _typeResolverBuilder;
    protected final VisibilityChecker<?> _visibilityChecker;

    public BaseSettings(ClassIntrospector ci, AnnotationIntrospector ai, VisibilityChecker<?> vc, PropertyNamingStrategy pns, TypeFactory tf, TypeResolverBuilder<?> typer, DateFormat dateFormat, HandlerInstantiator hi, Locale locale, TimeZone tz) {
        this._classIntrospector = ci;
        this._annotationIntrospector = ai;
        this._visibilityChecker = vc;
        this._propertyNamingStrategy = pns;
        this._typeFactory = tf;
        this._typeResolverBuilder = typer;
        this._dateFormat = dateFormat;
        this._handlerInstantiator = hi;
        this._locale = locale;
        this._timeZone = tz;
    }

    public BaseSettings withClassIntrospector(ClassIntrospector ci) {
        return new BaseSettings(ci, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone);
    }

    public BaseSettings withAnnotationIntrospector(AnnotationIntrospector ai) {
        return new BaseSettings(this._classIntrospector, ai, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone);
    }

    public BaseSettings withInsertedAnnotationIntrospector(AnnotationIntrospector ai) {
        return withAnnotationIntrospector(AnnotationIntrospector.Pair.create(ai, this._annotationIntrospector));
    }

    public BaseSettings withAppendedAnnotationIntrospector(AnnotationIntrospector ai) {
        return withAnnotationIntrospector(AnnotationIntrospector.Pair.create(this._annotationIntrospector, ai));
    }

    public BaseSettings withVisibilityChecker(VisibilityChecker<?> vc) {
        return new BaseSettings(this._classIntrospector, this._annotationIntrospector, vc, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone);
    }

    public BaseSettings withVisibility(PropertyAccessor forMethod, JsonAutoDetect.Visibility visibility) {
        return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker.withVisibility(forMethod, visibility), this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone);
    }

    public BaseSettings withPropertyNamingStrategy(PropertyNamingStrategy pns) {
        return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, pns, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone);
    }

    public BaseSettings withTypeFactory(TypeFactory tf) {
        return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, tf, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone);
    }

    public BaseSettings withTypeResolverBuilder(TypeResolverBuilder<?> typer) {
        return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, typer, this._dateFormat, this._handlerInstantiator, this._locale, this._timeZone);
    }

    public BaseSettings withDateFormat(DateFormat df) {
        return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, df, this._handlerInstantiator, this._locale, this._timeZone);
    }

    public BaseSettings withHandlerInstantiator(HandlerInstantiator hi) {
        return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, hi, this._locale, this._timeZone);
    }

    public BaseSettings with(Locale l) {
        return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, this._dateFormat, this._handlerInstantiator, l, this._timeZone);
    }

    public BaseSettings with(TimeZone tz) {
        DateFormat df;
        if (tz == null) {
            throw new IllegalArgumentException();
        }
        DateFormat df2 = this._dateFormat;
        if (df2 instanceof StdDateFormat) {
            df = ((StdDateFormat) df2).withTimeZone(tz);
        } else {
            df = (DateFormat) df2.clone();
            df.setTimeZone(tz);
        }
        return new BaseSettings(this._classIntrospector, this._annotationIntrospector, this._visibilityChecker, this._propertyNamingStrategy, this._typeFactory, this._typeResolverBuilder, df, this._handlerInstantiator, this._locale, tz);
    }

    public ClassIntrospector getClassIntrospector() {
        return this._classIntrospector;
    }

    public AnnotationIntrospector getAnnotationIntrospector() {
        return this._annotationIntrospector;
    }

    public VisibilityChecker<?> getVisibilityChecker() {
        return this._visibilityChecker;
    }

    public PropertyNamingStrategy getPropertyNamingStrategy() {
        return this._propertyNamingStrategy;
    }

    public TypeFactory getTypeFactory() {
        return this._typeFactory;
    }

    public TypeResolverBuilder<?> getTypeResolverBuilder() {
        return this._typeResolverBuilder;
    }

    public DateFormat getDateFormat() {
        return this._dateFormat;
    }

    public HandlerInstantiator getHandlerInstantiator() {
        return this._handlerInstantiator;
    }

    public Locale getLocale() {
        return this._locale;
    }

    public TimeZone getTimeZone() {
        return this._timeZone;
    }
}
