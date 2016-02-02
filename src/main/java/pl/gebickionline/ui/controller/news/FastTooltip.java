package pl.gebickionline.ui.controller.news;

import javafx.animation.*;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.lang.reflect.Field;

/**
 * Created by Łukasz on 2016-02-02.
 */
public class FastTooltip extends Tooltip {
    public FastTooltip(String text, double delay) {
        super(text);

        try {
            Field fieldBehavior = Tooltip.class.getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(this);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(delay)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FastTooltip(String text) {
        this(text, 250);
    }
}
