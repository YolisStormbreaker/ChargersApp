package com.yolisstorm.chargerslist.view

enum class RecyclerViewItemType(val type: Int) {

    Header(0),
    Item(1);

    companion object {
        fun getByValue(value: Int) = RecyclerViewItemType.entries[value]
    }

}