package io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractAuthorizationCodeExchangeResponse implements Map<String, Object> {

    private final Map<String, Object> delegate;

    protected AbstractAuthorizationCodeExchangeResponse(@NotNull Map<String, Object> values) {
        this();

        Preconditions.checkNotNull(values, "Cannot create response from null values.");
        this.delegate.putAll(values);
    }

    protected AbstractAuthorizationCodeExchangeResponse() {
        this.delegate = new HashMap<>();
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.delegate.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.delegate.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return this.delegate.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.delegate.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.delegate.putAll(m);
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.delegate.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.delegate.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.delegate.entrySet();
    }
}
