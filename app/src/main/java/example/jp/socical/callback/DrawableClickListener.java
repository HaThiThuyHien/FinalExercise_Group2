package example.jp.socical.callback;

/**
 * Created by Hien_BRSE on 12/8/2016.
 */

public interface  DrawableClickListener {

    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    public void onClick(DrawablePosition target);
}
