package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AnnotatedClass extends Annotated {
    private static final AnnotationMap[] NO_ANNOTATION_MAPS = new AnnotationMap[0];
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final Class<?> _class;
    protected AnnotationMap _classAnnotations;
    protected List<AnnotatedConstructor> _constructors;
    protected List<AnnotatedMethod> _creatorMethods;
    protected boolean _creatorsResolved = false;
    protected AnnotatedConstructor _defaultConstructor;
    protected List<AnnotatedField> _fields;
    protected AnnotatedMethodMap _memberMethods;
    protected final ClassIntrospector.MixInResolver _mixInResolver;
    protected final Class<?> _primaryMixIn;
    protected final List<Class<?>> _superTypes;

    private AnnotatedClass(Class<?> cls, List<Class<?>> superTypes, AnnotationIntrospector aintr, ClassIntrospector.MixInResolver mir, AnnotationMap classAnnotations) {
        this._class = cls;
        this._superTypes = superTypes;
        this._annotationIntrospector = aintr;
        this._mixInResolver = mir;
        this._primaryMixIn = this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor(this._class);
        this._classAnnotations = classAnnotations;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public AnnotatedClass withAnnotations(AnnotationMap ann) {
        return new AnnotatedClass(this._class, this._superTypes, this._annotationIntrospector, this._mixInResolver, ann);
    }

    public static AnnotatedClass construct(Class<?> cls, AnnotationIntrospector aintr, ClassIntrospector.MixInResolver mir) {
        return new AnnotatedClass(cls, ClassUtil.findSuperTypes(cls, null), aintr, mir, null);
    }

    public static AnnotatedClass constructWithoutSuperTypes(Class<?> cls, AnnotationIntrospector aintr, ClassIntrospector.MixInResolver mir) {
        return new AnnotatedClass(cls, Collections.emptyList(), aintr, mir, null);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Class<?> getAnnotated() {
        return this._class;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public int getModifiers() {
        return this._class.getModifiers();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public String getName() {
        return this._class.getName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        if (this._classAnnotations == null) {
            resolveClassAnnotations();
        }
        return (A) this._classAnnotations.get(cls);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Type getGenericType() {
        return this._class;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Class<?> getRawType() {
        return this._class;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    protected AnnotationMap getAllAnnotations() {
        if (this._classAnnotations == null) {
            resolveClassAnnotations();
        }
        return this._classAnnotations;
    }

    public Annotations getAnnotations() {
        if (this._classAnnotations == null) {
            resolveClassAnnotations();
        }
        return this._classAnnotations;
    }

    public boolean hasAnnotations() {
        if (this._classAnnotations == null) {
            resolveClassAnnotations();
        }
        return this._classAnnotations.size() > 0;
    }

    public AnnotatedConstructor getDefaultConstructor() {
        if (!this._creatorsResolved) {
            resolveCreators();
        }
        return this._defaultConstructor;
    }

    public List<AnnotatedConstructor> getConstructors() {
        if (!this._creatorsResolved) {
            resolveCreators();
        }
        return this._constructors;
    }

    public List<AnnotatedMethod> getStaticMethods() {
        if (!this._creatorsResolved) {
            resolveCreators();
        }
        return this._creatorMethods;
    }

    public Iterable<AnnotatedMethod> memberMethods() {
        if (this._memberMethods == null) {
            resolveMemberMethods();
        }
        return this._memberMethods;
    }

    public int getMemberMethodCount() {
        if (this._memberMethods == null) {
            resolveMemberMethods();
        }
        return this._memberMethods.size();
    }

    public AnnotatedMethod findMethod(String name, Class<?>[] paramTypes) {
        if (this._memberMethods == null) {
            resolveMemberMethods();
        }
        return this._memberMethods.find(name, paramTypes);
    }

    public int getFieldCount() {
        if (this._fields == null) {
            resolveFields();
        }
        return this._fields.size();
    }

    public Iterable<AnnotatedField> fields() {
        if (this._fields == null) {
            resolveFields();
        }
        return this._fields;
    }

    private void resolveClassAnnotations() {
        this._classAnnotations = new AnnotationMap();
        if (this._annotationIntrospector != null) {
            if (this._primaryMixIn != null) {
                _addClassMixIns(this._classAnnotations, this._class, this._primaryMixIn);
            }
            _addAnnotationsIfNotPresent(this._classAnnotations, this._class.getDeclaredAnnotations());
            for (Class<?> cls : this._superTypes) {
                _addClassMixIns(this._classAnnotations, cls);
                _addAnnotationsIfNotPresent(this._classAnnotations, cls.getDeclaredAnnotations());
            }
            _addClassMixIns(this._classAnnotations, Object.class);
        }
    }

    private void resolveCreators() {
        List<AnnotatedConstructor> constructors = null;
        Constructor<?>[] declaredCtors = this._class.getDeclaredConstructors();
        for (Constructor<?> ctor : declaredCtors) {
            if (ctor.getParameterTypes().length == 0) {
                this._defaultConstructor = _constructConstructor(ctor, true);
            } else {
                if (constructors == null) {
                    constructors = new ArrayList<>(Math.max(10, declaredCtors.length));
                }
                constructors.add(_constructConstructor(ctor, false));
            }
        }
        if (constructors == null) {
            this._constructors = Collections.emptyList();
        } else {
            this._constructors = constructors;
        }
        if (this._primaryMixIn != null && (this._defaultConstructor != null || !this._constructors.isEmpty())) {
            _addConstructorMixIns(this._primaryMixIn);
        }
        if (this._annotationIntrospector != null) {
            if (this._defaultConstructor != null && this._annotationIntrospector.hasIgnoreMarker(this._defaultConstructor)) {
                this._defaultConstructor = null;
            }
            if (this._constructors != null) {
                int i = this._constructors.size();
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    } else if (this._annotationIntrospector.hasIgnoreMarker(this._constructors.get(i))) {
                        this._constructors.remove(i);
                    }
                }
            }
        }
        List<AnnotatedMethod> creatorMethods = null;
        Method[] arr$ = this._class.getDeclaredMethods();
        for (Method m : arr$) {
            if (Modifier.isStatic(m.getModifiers())) {
                int argCount = m.getParameterTypes().length;
                if (argCount >= 1) {
                    if (creatorMethods == null) {
                        creatorMethods = new ArrayList<>(8);
                    }
                    creatorMethods.add(_constructCreatorMethod(m));
                }
            }
        }
        if (creatorMethods == null) {
            this._creatorMethods = Collections.emptyList();
        } else {
            this._creatorMethods = creatorMethods;
            if (this._primaryMixIn != null) {
                _addFactoryMixIns(this._primaryMixIn);
            }
            if (this._annotationIntrospector != null) {
                int i2 = this._creatorMethods.size();
                while (true) {
                    i2--;
                    if (i2 < 0) {
                        break;
                    } else if (this._annotationIntrospector.hasIgnoreMarker(this._creatorMethods.get(i2))) {
                        this._creatorMethods.remove(i2);
                    }
                }
            }
        }
        this._creatorsResolved = true;
    }

    private void resolveMemberMethods() {
        Class<?> mixin;
        this._memberMethods = new AnnotatedMethodMap();
        AnnotatedMethodMap mixins = new AnnotatedMethodMap();
        _addMemberMethods(this._class, this._memberMethods, this._primaryMixIn, mixins);
        for (Class<?> cls : this._superTypes) {
            Class<?> mixin2 = this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor(cls);
            _addMemberMethods(cls, this._memberMethods, mixin2, mixins);
        }
        if (this._mixInResolver != null && (mixin = this._mixInResolver.findMixInClassFor(Object.class)) != null) {
            _addMethodMixIns(this._class, this._memberMethods, mixin, mixins);
        }
        if (this._annotationIntrospector != null && !mixins.isEmpty()) {
            for (AnnotatedMethod mixIn : mixins) {
                try {
                    Method m = Object.class.getDeclaredMethod(mixIn.getName(), mixIn.getRawParameterTypes());
                    if (m != null) {
                        AnnotatedMethod am = _constructMethod(m);
                        _addMixOvers(mixIn.getAnnotated(), am, false);
                        this._memberMethods.add(am);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void resolveFields() {
        Map<String, AnnotatedField> foundFields = _findFields(this._class, null);
        if (foundFields == null || foundFields.size() == 0) {
            this._fields = Collections.emptyList();
        } else {
            this._fields = new ArrayList(foundFields.size());
            this._fields.addAll(foundFields.values());
        }
    }

    protected void _addClassMixIns(AnnotationMap annotations, Class<?> toMask) {
        if (this._mixInResolver != null) {
            _addClassMixIns(annotations, toMask, this._mixInResolver.findMixInClassFor(toMask));
        }
    }

    protected void _addClassMixIns(AnnotationMap annotations, Class<?> toMask, Class<?> mixin) {
        if (mixin != null) {
            _addAnnotationsIfNotPresent(annotations, mixin.getDeclaredAnnotations());
            for (Class<?> parent : ClassUtil.findSuperTypes(mixin, toMask)) {
                _addAnnotationsIfNotPresent(annotations, parent.getDeclaredAnnotations());
            }
        }
    }

    protected void _addConstructorMixIns(Class<?> mixin) {
        MemberKey[] ctorKeys = null;
        int ctorCount = this._constructors == null ? 0 : this._constructors.size();
        for (Constructor<?> ctor : mixin.getDeclaredConstructors()) {
            if (ctor.getParameterTypes().length == 0) {
                if (this._defaultConstructor != null) {
                    _addMixOvers(ctor, this._defaultConstructor, false);
                }
            } else {
                if (ctorKeys == null) {
                    ctorKeys = new MemberKey[ctorCount];
                    for (int i = 0; i < ctorCount; i++) {
                        ctorKeys[i] = new MemberKey(this._constructors.get(i).getAnnotated());
                    }
                }
                MemberKey key = new MemberKey(ctor);
                for (int i2 = 0; i2 < ctorCount; i2++) {
                    if (key.equals(ctorKeys[i2])) {
                        _addMixOvers(ctor, this._constructors.get(i2), true);
                        break;
                    }
                }
            }
        }
    }

    protected void _addFactoryMixIns(Class<?> mixin) {
        MemberKey[] methodKeys = null;
        int methodCount = this._creatorMethods.size();
        Method[] arr$ = mixin.getDeclaredMethods();
        for (Method m : arr$) {
            if (Modifier.isStatic(m.getModifiers()) && m.getParameterTypes().length != 0) {
                if (methodKeys == null) {
                    methodKeys = new MemberKey[methodCount];
                    for (int i = 0; i < methodCount; i++) {
                        methodKeys[i] = new MemberKey(this._creatorMethods.get(i).getAnnotated());
                    }
                }
                MemberKey key = new MemberKey(m);
                for (int i2 = 0; i2 < methodCount; i2++) {
                    if (key.equals(methodKeys[i2])) {
                        _addMixOvers(m, this._creatorMethods.get(i2), true);
                        break;
                    }
                }
            }
        }
    }

    protected void _addMemberMethods(Class<?> cls, AnnotatedMethodMap methods, Class<?> mixInCls, AnnotatedMethodMap mixIns) {
        if (mixInCls != null) {
            _addMethodMixIns(cls, methods, mixInCls, mixIns);
        }
        if (cls != null) {
            Method[] arr$ = cls.getDeclaredMethods();
            for (Method m : arr$) {
                if (_isIncludableMemberMethod(m)) {
                    AnnotatedMethod old = methods.find(m);
                    if (old == null) {
                        AnnotatedMethod newM = _constructMethod(m);
                        methods.add(newM);
                        AnnotatedMethod old2 = mixIns.remove(m);
                        if (old2 != null) {
                            _addMixOvers(old2.getAnnotated(), newM, false);
                        }
                    } else {
                        _addMixUnders(m, old);
                        if (old.getDeclaringClass().isInterface() && !m.getDeclaringClass().isInterface()) {
                            methods.add(old.withMethod(m));
                        }
                    }
                }
            }
        }
    }

    protected void _addMethodMixIns(Class<?> targetClass, AnnotatedMethodMap methods, Class<?> mixInCls, AnnotatedMethodMap mixIns) {
        List<Class<?>> parents = new ArrayList<>();
        parents.add(mixInCls);
        ClassUtil.findSuperTypes(mixInCls, targetClass, parents);
        for (Class<?> mixin : parents) {
            Method[] arr$ = mixin.getDeclaredMethods();
            for (Method m : arr$) {
                if (_isIncludableMemberMethod(m)) {
                    AnnotatedMethod am = methods.find(m);
                    if (am != null) {
                        _addMixUnders(m, am);
                    } else {
                        mixIns.add(_constructMethod(m));
                    }
                }
            }
        }
    }

    protected Map<String, AnnotatedField> _findFields(Class<?> c, Map<String, AnnotatedField> fields) {
        Class<?> mixin;
        Class<?> parent = c.getSuperclass();
        if (parent != null) {
            fields = _findFields(parent, fields);
            Field[] arr$ = c.getDeclaredFields();
            for (Field f : arr$) {
                if (_isIncludableField(f)) {
                    if (fields == null) {
                        fields = new LinkedHashMap<>();
                    }
                    fields.put(f.getName(), _constructField(f));
                }
            }
            if (this._mixInResolver != null && (mixin = this._mixInResolver.findMixInClassFor(c)) != null) {
                _addFieldMixIns(parent, mixin, fields);
            }
        }
        return fields;
    }

    protected void _addFieldMixIns(Class<?> targetClass, Class<?> mixInCls, Map<String, AnnotatedField> fields) {
        List<Class<?>> parents = new ArrayList<>();
        parents.add(mixInCls);
        ClassUtil.findSuperTypes(mixInCls, targetClass, parents);
        for (Class<?> mixin : parents) {
            Field[] arr$ = mixin.getDeclaredFields();
            for (Field mixinField : arr$) {
                if (_isIncludableField(mixinField)) {
                    String name = mixinField.getName();
                    AnnotatedField maskedField = fields.get(name);
                    if (maskedField != null) {
                        _addOrOverrideAnnotations(maskedField, mixinField.getDeclaredAnnotations());
                    }
                }
            }
        }
    }

    protected AnnotatedMethod _constructMethod(Method m) {
        return this._annotationIntrospector == null ? new AnnotatedMethod(m, _emptyAnnotationMap(), null) : new AnnotatedMethod(m, _collectRelevantAnnotations(m.getDeclaredAnnotations()), null);
    }

    protected AnnotatedConstructor _constructConstructor(Constructor<?> ctor, boolean defaultCtor) {
        if (this._annotationIntrospector == null) {
            return new AnnotatedConstructor(ctor, _emptyAnnotationMap(), _emptyAnnotationMaps(ctor.getParameterTypes().length));
        }
        if (defaultCtor) {
            return new AnnotatedConstructor(ctor, _collectRelevantAnnotations(ctor.getDeclaredAnnotations()), null);
        }
        Annotation[][] paramAnns = ctor.getParameterAnnotations();
        int paramCount = ctor.getParameterTypes().length;
        AnnotationMap[] resolvedAnnotations = null;
        if (paramCount != paramAnns.length) {
            Class<?> dc = ctor.getDeclaringClass();
            if (dc.isEnum() && paramCount == paramAnns.length + 2) {
                paramAnns = new Annotation[paramAnns.length + 2][];
                System.arraycopy(paramAnns, 0, paramAnns, 2, paramAnns.length);
                resolvedAnnotations = _collectRelevantAnnotations(paramAnns);
            } else if (dc.isMemberClass() && paramCount == paramAnns.length + 1) {
                paramAnns = new Annotation[paramAnns.length + 1][];
                System.arraycopy(paramAnns, 0, paramAnns, 1, paramAnns.length);
                resolvedAnnotations = _collectRelevantAnnotations(paramAnns);
            }
            if (resolvedAnnotations == null) {
                throw new IllegalStateException("Internal error: constructor for " + ctor.getDeclaringClass().getName() + " has mismatch: " + paramCount + " parameters; " + paramAnns.length + " sets of annotations");
            }
        } else {
            resolvedAnnotations = _collectRelevantAnnotations(paramAnns);
        }
        return new AnnotatedConstructor(ctor, _collectRelevantAnnotations(ctor.getDeclaredAnnotations()), resolvedAnnotations);
    }

    protected AnnotatedMethod _constructCreatorMethod(Method m) {
        return this._annotationIntrospector == null ? new AnnotatedMethod(m, _emptyAnnotationMap(), _emptyAnnotationMaps(m.getParameterTypes().length)) : new AnnotatedMethod(m, _collectRelevantAnnotations(m.getDeclaredAnnotations()), _collectRelevantAnnotations(m.getParameterAnnotations()));
    }

    protected AnnotatedField _constructField(Field f) {
        return this._annotationIntrospector == null ? new AnnotatedField(f, _emptyAnnotationMap()) : new AnnotatedField(f, _collectRelevantAnnotations(f.getDeclaredAnnotations()));
    }

    private AnnotationMap _emptyAnnotationMap() {
        return new AnnotationMap();
    }

    private AnnotationMap[] _emptyAnnotationMaps(int count) {
        if (count == 0) {
            return NO_ANNOTATION_MAPS;
        }
        AnnotationMap[] maps = new AnnotationMap[count];
        for (int i = 0; i < count; i++) {
            maps[i] = _emptyAnnotationMap();
        }
        return maps;
    }

    protected boolean _isIncludableMemberMethod(Method m) {
        if (Modifier.isStatic(m.getModifiers()) || m.isSynthetic() || m.isBridge()) {
            return false;
        }
        int pcount = m.getParameterTypes().length;
        return pcount <= 2;
    }

    private boolean _isIncludableField(Field f) {
        if (f.isSynthetic()) {
            return false;
        }
        int mods = f.getModifiers();
        return (Modifier.isStatic(mods) || Modifier.isTransient(mods)) ? false : true;
    }

    protected AnnotationMap[] _collectRelevantAnnotations(Annotation[][] anns) {
        int len = anns.length;
        AnnotationMap[] result = new AnnotationMap[len];
        for (int i = 0; i < len; i++) {
            result[i] = _collectRelevantAnnotations(anns[i]);
        }
        return result;
    }

    protected AnnotationMap _collectRelevantAnnotations(Annotation[] anns) {
        AnnotationMap annMap = new AnnotationMap();
        _addAnnotationsIfNotPresent(annMap, anns);
        return annMap;
    }

    private void _addAnnotationsIfNotPresent(AnnotationMap result, Annotation[] anns) {
        if (anns != null) {
            List<Annotation[]> bundles = null;
            for (Annotation ann : anns) {
                if (_isAnnotationBundle(ann)) {
                    if (bundles == null) {
                        bundles = new LinkedList<>();
                    }
                    bundles.add(ann.annotationType().getDeclaredAnnotations());
                } else {
                    result.addIfNotPresent(ann);
                }
            }
            if (bundles != null) {
                for (Annotation[] annotations : bundles) {
                    _addAnnotationsIfNotPresent(result, annotations);
                }
            }
        }
    }

    private void _addAnnotationsIfNotPresent(AnnotatedMember target, Annotation[] anns) {
        if (anns != null) {
            List<Annotation[]> bundles = null;
            for (Annotation ann : anns) {
                if (_isAnnotationBundle(ann)) {
                    if (bundles == null) {
                        bundles = new LinkedList<>();
                    }
                    bundles.add(ann.annotationType().getDeclaredAnnotations());
                } else {
                    target.addIfNotPresent(ann);
                }
            }
            if (bundles != null) {
                for (Annotation[] annotations : bundles) {
                    _addAnnotationsIfNotPresent(target, annotations);
                }
            }
        }
    }

    private void _addOrOverrideAnnotations(AnnotatedMember target, Annotation[] anns) {
        if (anns != null) {
            List<Annotation[]> bundles = null;
            for (Annotation ann : anns) {
                if (_isAnnotationBundle(ann)) {
                    if (bundles == null) {
                        bundles = new LinkedList<>();
                    }
                    bundles.add(ann.annotationType().getDeclaredAnnotations());
                } else {
                    target.addOrOverride(ann);
                }
            }
            if (bundles != null) {
                for (Annotation[] annotations : bundles) {
                    _addOrOverrideAnnotations(target, annotations);
                }
            }
        }
    }

    protected void _addMixOvers(Constructor<?> mixin, AnnotatedConstructor target, boolean addParamAnnotations) {
        _addOrOverrideAnnotations(target, mixin.getDeclaredAnnotations());
        if (addParamAnnotations) {
            Annotation[][] pa = mixin.getParameterAnnotations();
            int len = pa.length;
            for (int i = 0; i < len; i++) {
                Annotation[] arr$ = pa[i];
                for (Annotation a : arr$) {
                    target.addOrOverrideParam(i, a);
                }
            }
        }
    }

    protected void _addMixOvers(Method mixin, AnnotatedMethod target, boolean addParamAnnotations) {
        _addOrOverrideAnnotations(target, mixin.getDeclaredAnnotations());
        if (addParamAnnotations) {
            Annotation[][] pa = mixin.getParameterAnnotations();
            int len = pa.length;
            for (int i = 0; i < len; i++) {
                Annotation[] arr$ = pa[i];
                for (Annotation a : arr$) {
                    target.addOrOverrideParam(i, a);
                }
            }
        }
    }

    protected void _addMixUnders(Method src, AnnotatedMethod target) {
        _addAnnotationsIfNotPresent(target, src.getDeclaredAnnotations());
    }

    private final boolean _isAnnotationBundle(Annotation ann) {
        return this._annotationIntrospector != null && this._annotationIntrospector.isAnnotationBundle(ann);
    }

    public String toString() {
        return "[AnnotedClass " + this._class.getName() + "]";
    }
}
