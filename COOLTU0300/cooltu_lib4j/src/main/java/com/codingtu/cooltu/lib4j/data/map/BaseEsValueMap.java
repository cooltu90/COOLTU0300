package com.codingtu.cooltu.lib4j.data.map;

import com.codingtu.cooltu.lib4j.es.BaseEs;
import com.codingtu.cooltu.lib4j.es.Es;

import java.util.ArrayList;
import java.util.List;

public class BaseEsValueMap<KEY, E> extends ValueMap<KEY, BaseEs<E>> {

    @Override
    protected BaseEs<E> newValue() {
        return Es.es();
    }
}
