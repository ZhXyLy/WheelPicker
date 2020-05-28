package com.jx.wheelpicker.widget.lasted;

/**
 * @author zhaoxl
 * @date 19/2/15
 */
public interface DialogBuilder {

    /**
     * 设置提示文字
     */
    DialogBuilder setTitle(CharSequence title);

    /**
     * 提示标题的文字大小
     *
     * @param titleTextSize dp
     */
    DialogBuilder setTitleTextSize(float titleTextSize);

    /**
     * 滚轮文字大小
     *
     * @param itemTextSize dp
     */
    DialogBuilder setItemTextSize(float itemTextSize);

    /**
     * 设置Item间隔
     *
     * @param itemSpace
     * @return
     */
    DialogBuilder setItemSpace(int itemSpace);

}
