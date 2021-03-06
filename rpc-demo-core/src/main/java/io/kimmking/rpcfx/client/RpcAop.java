package io.kimmking.rpcfx.client;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Modifier;

public class RpcAop {

    public static <T> T create(final Class<T> serviceClass) throws NoSuchMethodException, IllegalAccessException, InstantiationException {

        Class<?> type = new ByteBuddy().subclass(Object.class).implement(serviceClass)
        .method(ElementMatchers.any()).intercept(MethodDelegation.to(RemoteCall.class)).make()
                        .load(serviceClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).getLoaded();
        ;
        return (T)type.newInstance();

    }

}
