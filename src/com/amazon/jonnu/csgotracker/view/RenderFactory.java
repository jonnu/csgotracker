package com.amazon.jonnu.csgotracker.view;

public interface RenderFactory {
    <T> RenderChain<T> getRenderChain(Class<T> typeToRender);
}
