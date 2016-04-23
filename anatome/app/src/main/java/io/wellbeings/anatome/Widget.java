package io.wellbeings.anatome;

/**
 * Interface ensures semantic link
 * between interactive sub-sections,
 * enforces a more coherent MVC pattern
 * within classes.
 */
interface Widget {

    /**
     * Initialise any visual elements with the correct
     * graphics and text.
     */
    void initGUI();

    /**
     * Add any event-driven functionality, which
     * is usually messy within code.
     */
    void attachListeners();

}
