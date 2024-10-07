package com.oracle.truffle.sl.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.sl.SLException;
import com.oracle.truffle.sl.runtime.SLObject;

@NodeInfo(shortName = "deleteProperty")
public abstract class SLObjectDeletePropertyBuiltin  extends SLBuiltinNode {

    @Specialization
    public boolean del(Object obj, Object key,
                           @CachedLibrary(limit = "1") DynamicObjectLibrary objlib
    ) {
        if (obj instanceof SLObject) {
            return objlib.removeKey((DynamicObject) obj, key);
        }
        throw new SLException("Not an object!", this);
    }
}


