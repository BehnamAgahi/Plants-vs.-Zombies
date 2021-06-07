package model.item;

import utils.Row;

public interface OnUpdateItems {
    /**
     * Update is a method for updating card state
     *
     * @param row Handler
     */
    void update(Row row);
}