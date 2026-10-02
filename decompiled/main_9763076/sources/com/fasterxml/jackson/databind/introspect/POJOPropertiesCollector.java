package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class POJOPropertiesCollector {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final AnnotatedClass _classDef;
    protected final MapperConfig<?> _config;
    protected final boolean _forSerialization;
    protected HashSet<String> _ignoredPropertyNames;
    protected LinkedHashMap<Object, AnnotatedMember> _injectables;
    protected final String _mutatorPrefix;
    protected final JavaType _type;
    protected final VisibilityChecker<?> _visibilityChecker;
    protected final LinkedHashMap<String, POJOPropertyBuilder> _properties = new LinkedHashMap<>();
    protected LinkedList<POJOPropertyBuilder> _creatorProperties = null;
    protected LinkedList<AnnotatedMember> _anyGetters = null;
    protected LinkedList<AnnotatedMethod> _anySetters = null;
    protected LinkedList<AnnotatedMethod> _jsonValueGetters = null;

    protected POJOPropertiesCollector(MapperConfig<?> config, boolean forSerialization, JavaType type, AnnotatedClass classDef, String mutatorPrefix) {
        this._config = config;
        this._forSerialization = forSerialization;
        this._type = type;
        this._classDef = classDef;
        this._mutatorPrefix = mutatorPrefix == null ? "set" : mutatorPrefix;
        this._annotationIntrospector = config.isAnnotationProcessingEnabled() ? this._config.getAnnotationIntrospector() : null;
        if (this._annotationIntrospector == null) {
            this._visibilityChecker = this._config.getDefaultVisibilityChecker();
        } else {
            this._visibilityChecker = this._annotationIntrospector.findAutoDetectVisibility(classDef, this._config.getDefaultVisibilityChecker());
        }
    }

    public MapperConfig<?> getConfig() {
        return this._config;
    }

    public JavaType getType() {
        return this._type;
    }

    public AnnotatedClass getClassDef() {
        return this._classDef;
    }

    public AnnotationIntrospector getAnnotationIntrospector() {
        return this._annotationIntrospector;
    }

    public List<BeanPropertyDefinition> getProperties() {
        return new ArrayList(this._properties.values());
    }

    public Map<Object, AnnotatedMember> getInjectables() {
        return this._injectables;
    }

    public AnnotatedMethod getJsonValueMethod() {
        if (this._jsonValueGetters == null) {
            return null;
        }
        if (this._jsonValueGetters.size() > 1) {
            reportProblem("Multiple value properties defined (" + this._jsonValueGetters.get(0) + " vs " + this._jsonValueGetters.get(1) + ")");
        }
        return this._jsonValueGetters.get(0);
    }

    public AnnotatedMember getAnyGetter() {
        if (this._anyGetters == null) {
            return null;
        }
        if (this._anyGetters.size() > 1) {
            reportProblem("Multiple 'any-getters' defined (" + this._anyGetters.get(0) + " vs " + this._anyGetters.get(1) + ")");
        }
        return this._anyGetters.getFirst();
    }

    public AnnotatedMethod getAnySetterMethod() {
        if (this._anySetters == null) {
            return null;
        }
        if (this._anySetters.size() > 1) {
            reportProblem("Multiple 'any-setters' defined (" + this._anySetters.get(0) + " vs " + this._anySetters.get(1) + ")");
        }
        return this._anySetters.getFirst();
    }

    public Set<String> getIgnoredPropertyNames() {
        return this._ignoredPropertyNames;
    }

    public ObjectIdInfo getObjectIdInfo() {
        if (this._annotationIntrospector == null) {
            return null;
        }
        return this._annotationIntrospector.findObjectIdInfo(this._classDef);
    }

    public Class<?> findPOJOBuilderClass() {
        return this._annotationIntrospector.findPOJOBuilder(this._classDef);
    }

    protected Map<String, POJOPropertyBuilder> getPropertyMap() {
        return this._properties;
    }

    public POJOPropertiesCollector collect() {
        this._properties.clear();
        _addFields();
        _addMethods();
        _addCreators();
        _addInjectables();
        _removeUnwantedProperties();
        _renameProperties();
        PropertyNamingStrategy naming = this._config.getPropertyNamingStrategy();
        if (naming != null) {
            _renameUsing(naming);
        }
        for (POJOPropertyBuilder property : this._properties.values()) {
            property.trimByVisibility();
        }
        for (POJOPropertyBuilder property2 : this._properties.values()) {
            property2.mergeAnnotations(this._forSerialization);
        }
        _sortProperties();
        return this;
    }

    protected void _sortProperties() {
        boolean sort;
        Map<String, POJOPropertyBuilder> all;
        AnnotationIntrospector intr = this._annotationIntrospector;
        Boolean alpha = intr == null ? null : intr.findSerializationSortAlphabetically(this._classDef);
        if (alpha == null) {
            sort = this._config.shouldSortPropertiesAlphabetically();
        } else {
            sort = alpha.booleanValue();
        }
        String[] propertyOrder = intr != null ? intr.findSerializationPropertyOrder(this._classDef) : null;
        if (sort || this._creatorProperties != null || propertyOrder != null) {
            int size = this._properties.size();
            if (sort) {
                all = new TreeMap<>();
            } else {
                all = new LinkedHashMap<>(size + size);
            }
            for (POJOPropertyBuilder prop : this._properties.values()) {
                all.put(prop.getName(), prop);
            }
            Map<String, POJOPropertyBuilder> ordered = new LinkedHashMap<>(size + size);
            if (propertyOrder != null) {
                String[] arr$ = propertyOrder;
                for (String name : arr$) {
                    POJOPropertyBuilder w = all.get(name);
                    if (w == null) {
                        for (POJOPropertyBuilder prop2 : this._properties.values()) {
                            if (name.equals(prop2.getInternalName())) {
                                w = prop2;
                                name = prop2.getName();
                                break;
                            }
                        }
                    }
                    if (w != null) {
                        ordered.put(name, w);
                    }
                }
            }
            if (this._creatorProperties != null) {
                for (POJOPropertyBuilder prop3 : this._creatorProperties) {
                    ordered.put(prop3.getName(), prop3);
                }
            }
            ordered.putAll(all);
            this._properties.clear();
            this._properties.putAll(ordered);
        }
    }

    protected void _addFields() {
        String explName;
        AnnotationIntrospector ai = this._annotationIntrospector;
        for (AnnotatedField f : this._classDef.fields()) {
            String implName = f.getName();
            if (ai == null) {
                explName = null;
            } else if (this._forSerialization) {
                explName = ai.findSerializationName(f);
            } else {
                explName = ai.findDeserializationName(f);
            }
            if (Constants.MAIN_VERSION_TAG.equals(explName)) {
                explName = implName;
            }
            boolean visible = explName != null;
            if (!visible) {
                visible = this._visibilityChecker.isFieldVisible(f);
            }
            boolean ignored = ai != null && ai.hasIgnoreMarker(f);
            _property(implName).addField(f, explName, visible, ignored);
        }
    }

    protected void _addCreators() {
        AnnotationIntrospector ai = this._annotationIntrospector;
        if (ai != null) {
            for (AnnotatedConstructor ctor : this._classDef.getConstructors()) {
                if (this._creatorProperties == null) {
                    this._creatorProperties = new LinkedList<>();
                }
                int len = ctor.getParameterCount();
                for (int i = 0; i < len; i++) {
                    AnnotatedParameter param = ctor.getParameter(i);
                    String name = ai.findDeserializationName(param);
                    if (name != null) {
                        POJOPropertyBuilder prop = _property(name);
                        prop.addCtor(param, name, true, false);
                        this._creatorProperties.add(prop);
                    }
                }
            }
            for (AnnotatedMethod factory : this._classDef.getStaticMethods()) {
                if (this._creatorProperties == null) {
                    this._creatorProperties = new LinkedList<>();
                }
                int len2 = factory.getParameterCount();
                for (int i2 = 0; i2 < len2; i2++) {
                    AnnotatedParameter param2 = factory.getParameter(i2);
                    String name2 = ai.findDeserializationName(param2);
                    if (name2 != null) {
                        POJOPropertyBuilder prop2 = _property(name2);
                        prop2.addCtor(param2, name2, true, false);
                        this._creatorProperties.add(prop2);
                    }
                }
            }
        }
    }

    protected void _addMethods() {
        AnnotationIntrospector ai = this._annotationIntrospector;
        for (AnnotatedMethod m : this._classDef.memberMethods()) {
            int argCount = m.getParameterCount();
            if (argCount == 0) {
                _addGetterMethod(m, ai);
            } else if (argCount == 1) {
                _addSetterMethod(m, ai);
            } else if (argCount == 2 && ai != null && ai.hasAnySetterAnnotation(m)) {
                if (this._anySetters == null) {
                    this._anySetters = new LinkedList<>();
                }
                this._anySetters.add(m);
            }
        }
    }

    protected void _addGetterMethod(AnnotatedMethod m, AnnotationIntrospector ai) {
        String implName;
        boolean visible;
        if (ai != null) {
            if (ai.hasAnyGetterAnnotation(m)) {
                if (this._anyGetters == null) {
                    this._anyGetters = new LinkedList<>();
                }
                this._anyGetters.add(m);
                return;
            } else if (ai.hasAsValueAnnotation(m)) {
                if (this._jsonValueGetters == null) {
                    this._jsonValueGetters = new LinkedList<>();
                }
                this._jsonValueGetters.add(m);
                return;
            }
        }
        String explName = ai == null ? null : ai.findSerializationName(m);
        if (explName == null) {
            implName = BeanUtil.okNameForRegularGetter(m, m.getName());
            if (implName == null) {
                implName = BeanUtil.okNameForIsGetter(m, m.getName());
                if (implName != null) {
                    visible = this._visibilityChecker.isIsGetterVisible(m);
                } else {
                    return;
                }
            } else {
                visible = this._visibilityChecker.isGetterVisible(m);
            }
        } else {
            implName = BeanUtil.okNameForGetter(m);
            if (implName == null) {
                implName = m.getName();
            }
            if (explName.length() == 0) {
                explName = implName;
            }
            visible = true;
        }
        boolean ignore = ai == null ? false : ai.hasIgnoreMarker(m);
        _property(implName).addGetter(m, explName, visible, ignore);
    }

    protected void _addSetterMethod(AnnotatedMethod m, AnnotationIntrospector ai) {
        String implName;
        boolean visible;
        String explName = ai == null ? null : ai.findDeserializationName(m);
        if (explName == null) {
            implName = BeanUtil.okNameForMutator(m, this._mutatorPrefix);
            if (implName != null) {
                visible = this._visibilityChecker.isSetterVisible(m);
            } else {
                return;
            }
        } else {
            implName = BeanUtil.okNameForMutator(m, this._mutatorPrefix);
            if (implName == null) {
                implName = m.getName();
            }
            if (explName.length() == 0) {
                explName = implName;
            }
            visible = true;
        }
        boolean ignore = ai == null ? false : ai.hasIgnoreMarker(m);
        _property(implName).addSetter(m, explName, visible, ignore);
    }

    protected void _addInjectables() {
        AnnotationIntrospector ai = this._annotationIntrospector;
        if (ai != null) {
            for (AnnotatedMember f : this._classDef.fields()) {
                _doAddInjectable(ai.findInjectableValueId(f), f);
            }
            for (AnnotatedMethod m : this._classDef.memberMethods()) {
                if (m.getParameterCount() == 1) {
                    _doAddInjectable(ai.findInjectableValueId(m), m);
                }
            }
        }
    }

    protected void _doAddInjectable(Object id, AnnotatedMember m) {
        if (id != null) {
            if (this._injectables == null) {
                this._injectables = new LinkedHashMap<>();
            }
            AnnotatedMember prev = this._injectables.put(id, m);
            if (prev != null) {
                String type = id == null ? "[null]" : id.getClass().getName();
                throw new IllegalArgumentException("Duplicate injectable value with id '" + String.valueOf(id) + "' (of type " + type + ")");
            }
        }
    }

    protected void _removeUnwantedProperties() {
        Iterator<Map.Entry<String, POJOPropertyBuilder>> it = this._properties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, POJOPropertyBuilder> entry = it.next();
            POJOPropertyBuilder prop = entry.getValue();
            if (!prop.anyVisible()) {
                it.remove();
            } else {
                if (prop.anyIgnorals()) {
                    if (!prop.isExplicitlyIncluded()) {
                        it.remove();
                        _addIgnored(prop.getName());
                    } else {
                        prop.removeIgnored();
                        if (!this._forSerialization && !prop.couldDeserialize()) {
                            _addIgnored(prop.getName());
                        }
                    }
                }
                prop.removeNonVisible();
            }
        }
    }

    private void _addIgnored(String name) {
        if (!this._forSerialization) {
            if (this._ignoredPropertyNames == null) {
                this._ignoredPropertyNames = new HashSet<>();
            }
            this._ignoredPropertyNames.add(name);
        }
    }

    protected void _renameProperties() {
        Iterator<Map.Entry<String, POJOPropertyBuilder>> it = this._properties.entrySet().iterator();
        LinkedList<POJOPropertyBuilder> renamed = null;
        while (it.hasNext()) {
            Map.Entry<String, POJOPropertyBuilder> entry = it.next();
            POJOPropertyBuilder prop = entry.getValue();
            String newName = prop.findNewName();
            if (newName != null) {
                if (renamed == null) {
                    renamed = new LinkedList<>();
                }
                renamed.add(prop.withName(newName));
                it.remove();
            }
        }
        if (renamed != null) {
            for (POJOPropertyBuilder prop2 : renamed) {
                String name = prop2.getName();
                POJOPropertyBuilder old = this._properties.get(name);
                if (old == null) {
                    this._properties.put(name, prop2);
                } else {
                    old.addAll(prop2);
                }
            }
        }
    }

    protected void _renameUsing(PropertyNamingStrategy naming) {
        POJOPropertyBuilder[] props = (POJOPropertyBuilder[]) this._properties.values().toArray(new POJOPropertyBuilder[this._properties.size()]);
        this._properties.clear();
        for (POJOPropertyBuilder prop : props) {
            String name = prop.getName();
            if (this._forSerialization) {
                if (prop.hasGetter()) {
                    name = naming.nameForGetterMethod(this._config, prop.getGetter(), name);
                } else if (prop.hasField()) {
                    name = naming.nameForField(this._config, prop.getField(), name);
                }
            } else if (prop.hasSetter()) {
                name = naming.nameForSetterMethod(this._config, prop.getSetter(), name);
            } else if (prop.hasConstructorParameter()) {
                name = naming.nameForConstructorParameter(this._config, prop.getConstructorParameter(), name);
            } else if (prop.hasField()) {
                name = naming.nameForField(this._config, prop.getField(), name);
            } else if (prop.hasGetter()) {
                name = naming.nameForGetterMethod(this._config, prop.getGetter(), name);
            }
            if (!name.equals(prop.getName())) {
                prop = prop.withName(name);
            }
            POJOPropertyBuilder old = this._properties.get(name);
            if (old == null) {
                this._properties.put(name, prop);
            } else {
                old.addAll(prop);
            }
        }
    }

    protected void reportProblem(String msg) {
        throw new IllegalArgumentException("Problem with definition of " + this._classDef + ": " + msg);
    }

    protected POJOPropertyBuilder _property(String implName) {
        POJOPropertyBuilder prop = this._properties.get(implName);
        if (prop == null) {
            POJOPropertyBuilder prop2 = new POJOPropertyBuilder(implName, this._annotationIntrospector, this._forSerialization);
            this._properties.put(implName, prop2);
            return prop2;
        }
        return prop;
    }
}
