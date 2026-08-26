package com.helptrickbd.class1.core.analytics.domain

/**
 * Standard Analytics Event & Parameter Constants.
 */
object AnalyticsEvents {
    // Event Names
    const val EVENT_BOOK_OPENED = "nctb_book_opened"
    const val EVENT_CHAPTER_OPENED = "nctb_chapter_opened"
    const val EVENT_FULL_BOOK_OPENED = "nctb_full_book_opened"
    const val EVENT_READING_PROGRESS = "nctb_reading_progress"
    const val EVENT_LAYOUT_SWITCHED = "nctb_layout_switched"
    const val EVENT_THEME_CHANGED = "nctb_theme_changed"
    const val EVENT_NOTIFICATION_OPENED = "nctb_notification_opened"
    const val EVENT_SEARCH_PERFORMED = "nctb_search_performed"

    // Parameter Names
    const val PARAM_BOOK_ID = "book_id"
    const val PARAM_BOOK_TITLE = "book_title"
    const val PARAM_CHAPTER_ID = "chapter_id"
    const val PARAM_UNIT_NO = "unit_no"
    const val PARAM_PAGE_NUMBER = "page_number"
    const val PARAM_TOTAL_PAGES = "total_pages"
    const val PARAM_LAYOUT_MODE = "layout_mode"
    const val PARAM_THEME_NAME = "theme_name"
    const val PARAM_SEARCH_QUERY = "search_query"
    const val PARAM_TARGET_CLASS = "target_class"
}
