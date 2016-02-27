package org.jenkinsci.plugins.pollmailboxtrigger.mail.utils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import static java.util.Objects.*;
import static org.apache.commons.lang.StringUtils.*;
import static org.jenkinsci.plugins.pollmailboxtrigger.mail.utils.Stringify.stringify;

/**
 * Quick and dirty Properties implementation. (But with shorthand get/put methods).
 *
 * @author Nick Grealy
 */
@SuppressWarnings("unused")
public class CustomProperties {

    private Map<String, String> delegate = new HashMap<String, String>();

    public CustomProperties() {
    }

    public CustomProperties(Properties properties1) {
        putAll(properties1);
    }

    public CustomProperties(Map<String, String> properties1) {
        putAll(properties1);
    }

    public CustomProperties(String properties) {
        putAll(properties);
    }

    public static CustomProperties read(String properties) {
        Properties p = new Properties();
        try {
            if (nonNull(properties) && !isBlank((properties.trim()))) {
                p.load(new StringReader(properties));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new CustomProperties(p);
    }

    public void putAll(String properties) {
        putAll(read(properties));
    }

    public void putAll(CustomProperties p2) {
        this.delegate.putAll(p2.getMap());
    }

    public void putAll(CustomProperties p2, String prefix) {
        putAll(p2.getMap(), prefix);
    }

    public void putAll(Properties p2) {
        putAll(p2, null);
    }

    public void putAll(Properties p2, String prefix) {
        Map<String, String> tmp = new HashMap<String, String>();
        for (String key : p2.stringPropertyNames()) {
            tmp.put(key, p2.getProperty(key));
        }
        putAll(tmp, prefix);
    }

    public void putAll(Map<String, String> p2, String prefix) {
        if (isNull(prefix)) {
            prefix = "";
        }
        final Iterator<Map.Entry<String, String>> entries = p2.entrySet().iterator();
        while (entries.hasNext()) {
            final Map.Entry<String, String> next = entries.next();
            this.delegate.put(prefix + next.getKey(), next.getValue());
        }
    }

    public Properties getProperties() {
        Properties p = new Properties();
        for (Map.Entry entry : delegate.entrySet()) {
            if (entry != null && entry.getKey() != null && entry.getValue() != null) {
                p.put(entry.getKey(), entry.getValue());
            }
        }
        return p;
    }

    public Map<String, String> getMap() {
        return delegate;
    }

    public boolean has(String o) {
        return delegate.containsKey(o);
    }

    public boolean has(Enum o) {
        return has(o.name());
    }

    public String get(Enum o) {
        return get(o.name());
    }

    public CustomProperties put(Enum s, String s2) {
        put(s.name(), s2);
        return this;
    }

    public String remove(Enum o) {
        return remove(o.name());
    }

    public CustomProperties putIfBlank(Enum s, String s2) {
        return putIfBlank(s.name(), s2);
    }

    public CustomProperties putIfBlank(String s, String s2) {
        String existingValue = get(s);
        if (!has(s) || isNull(existingValue) || isBlank(existingValue)) {
            return put(s, s2);
        }
        return null;
    }

    public void removeBlanks() {
        List<String> removekeys = new ArrayList<String>();
        for (String key : delegate.keySet()){
            String value = delegate.get(key);
            if (isBlank(value)){
                removekeys.add(key);
            }
        }
        for (String removekey : removekeys){
            delegate.remove(removekey);
        }
    }

    public String toString() {
        return stringify(delegate);
    }

    /* delegate methods */

    public int size() {
        return delegate.size();
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public boolean containsKey(String o) {
        return delegate.containsKey(o);
    }

    public boolean containsValue(String o) {
        return delegate.containsValue(o);
    }

    public String get(String o) {
        return delegate.get(o);
    }

    public CustomProperties put(String s, String s2) {
        delegate.put(s, s2);
        return this;
    }

    public String remove(String o) {
        return delegate.remove(o);
    }

    public void putAll(Map<? extends String, ? extends String> map) {
        delegate.putAll(map);
    }

    public void clear() {
        delegate.clear();
    }

    public Set<String> keySet() {
        return delegate.keySet();
    }

    public Collection<String> values() {
        return delegate.values();
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return delegate.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomProperties)) return false;
        CustomProperties that = (CustomProperties) o;
        return delegate.equals(that.delegate);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }
}
