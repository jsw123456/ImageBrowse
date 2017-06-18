package com.tanxin.imagebrowerloder.loading;

import android.content.Context;
import android.util.SparseArray;

import com.tanxin.imagebrowerloder.loading.circle.jump.CollisionLoadingRenderer;
import com.tanxin.imagebrowerloder.loading.circle.jump.DanceLoadingRenderer;
import com.tanxin.imagebrowerloder.loading.circle.jump.GuardLoadingRenderer;
import com.tanxin.imagebrowerloder.loading.circle.jump.SwapLoadingRenderer;
import com.tanxin.imagebrowerloder.loading.circle.rotate.GearLoadingRenderer;
import com.tanxin.imagebrowerloder.loading.circle.rotate.LevelLoadingRenderer;
import com.tanxin.imagebrowerloder.loading.circle.rotate.MaterialLoadingRenderer;
import com.tanxin.imagebrowerloder.loading.circle.rotate.WhorlLoadingRenderer;

import java.lang.reflect.Constructor;


public final class LoadingRendererFactory {
    private static final SparseArray<Class<? extends LoadingRenderer>> LOADING_RENDERERS = new SparseArray<>();

    static {
        //circle rotate
        LOADING_RENDERERS.put(0, MaterialLoadingRenderer.class);
        LOADING_RENDERERS.put(1, LevelLoadingRenderer.class);
        LOADING_RENDERERS.put(2, WhorlLoadingRenderer.class);
        LOADING_RENDERERS.put(3, GearLoadingRenderer.class);
        //circle jump
        LOADING_RENDERERS.put(4, SwapLoadingRenderer.class);
        LOADING_RENDERERS.put(5, GuardLoadingRenderer.class);
        LOADING_RENDERERS.put(6, DanceLoadingRenderer.class);
        LOADING_RENDERERS.put(7, CollisionLoadingRenderer.class);


    }

    private LoadingRendererFactory() {
    }

    public static LoadingRenderer createLoadingRenderer(Context context, int loadingRendererId) throws Exception {
        Class<?> loadingRendererClazz = LOADING_RENDERERS.get(loadingRendererId);
        Constructor<?>[] constructors = loadingRendererClazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes != null
                    && parameterTypes.length == 1
                    && parameterTypes[0].equals(Context.class)) {
                constructor.setAccessible(true);
                return (LoadingRenderer) constructor.newInstance(context);
            }
        }

        throw new InstantiationException();
    }
}
