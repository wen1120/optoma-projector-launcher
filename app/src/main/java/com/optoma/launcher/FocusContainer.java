package com.optoma.launcher;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FocusContainer extends FocusComponent {
    private List<FocusComponent> children = new ArrayList<>();
    private int focus = 0;
    public static int ORIENTATION_HORIZONTAL = 0;
    public static int ORIENTATION_VERTICAL = 1;
    private int orientation = ORIENTATION_HORIZONTAL;
    private boolean isCircular = true;

    public FocusContainer() {
        super(null);
    }

    public void add(View view) {
        children.add(new FocusComponent(view));
    }

    public void add(FocusComponent cpn) {
        children.add(cpn);
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    @Override
    public View getFocusedView() {
        final View view = children.get(focus).getFocusedView();
        return view;
    }

    public void focusRight() {
        if(orientation == ORIENTATION_HORIZONTAL) {
            focusNext();
        } else {
            children.get(focus).focusRight();
        }
    }

    public void focusLeft() {
        if(orientation == ORIENTATION_HORIZONTAL) {
            focusPrevious();
        } else {
            children.get(focus).focusLeft();
        }
    }

    public void focusUp() {
        if(orientation == ORIENTATION_VERTICAL) {
            focusPrevious();
        } else {
            children.get(focus).focusUp();
        }
    }

    public void focusDown() {
        if(orientation == ORIENTATION_VERTICAL) {
            focusNext();
        } else {
            children.get(focus).focusDown();
        }
    }

    private void focusNext() {
        if(isCircular) {
            focus = (focus + 1) % children.size();
        } else {
            if (focus < children.size()-1) {
                focus++;
            }
        }
    }

    private void focusPrevious() {
        if(isCircular) {
            if(focus==0) {
                focus = children.size() - 1;
            } else {
                focus--;
            }
        } else {
            if (focus > 0) {
                focus--;
            }
        }
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
