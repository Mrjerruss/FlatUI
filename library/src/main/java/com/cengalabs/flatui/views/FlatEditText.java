package com.cengalabs.flatui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.cengalabs.flatui.Attributes;
import com.cengalabs.flatui.FlatUI;
import com.cengalabs.flatui.R;
import com.cengalabs.flatui.constants.Colors;

/**
 * Created with IntelliJ IDEA.
 * User: eluleci
 * Date: 24.10.2013
 * Time: 21:09
 */
public class FlatEditText extends EditText implements Colors, Attributes.AttributeChangeListener {

    private Attributes attributes;

    private int style = 0;

    private boolean hasOwnTextColor;
    private boolean hasOwnHintColor;

    public FlatEditText(Context context) {
        super(context);
        init(null);
    }

    public FlatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FlatEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (attributes == null)
            attributes = new Attributes(this);

        if (attrs != null) {

            // getting android default tags for textColor and textColorHint
            hasOwnTextColor = attrs.getAttributeValue(FlatUI.androidStyleNameSpace, "textColor") != null;
            hasOwnHintColor = attrs.getAttributeValue(FlatUI.androidStyleNameSpace, "textColorHint") != null;

            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FlatEditText);

            // getting common attributes
            attributes.setThemeSilent(a.getInt(R.styleable.FlatEditText_theme, FlatUI.DEFAULT_THEME));

            int customTheme = a.getResourceId(R.styleable.FlatEditText_customTheme, FlatUI.INVALID_ATTRIBUTE);
            if (customTheme != FlatUI.INVALID_ATTRIBUTE) attributes.setCustomThemeSilent(customTheme, getResources());

            attributes.setFontId(a.getInt(R.styleable.FlatEditText_fontFamily, FlatUI.DEFAULT_FONT_FAMILY));
            attributes.setFontWeight(a.getInt(R.styleable.FlatEditText_fontWeight, FlatUI.DEFAULT_FONT_WEIGHT));

            attributes.setTextAppearance(a.getInt(R.styleable.FlatEditText_textAppearance, FlatUI.DEFAULT_TEXT_APPEARANCE));
            attributes.setRadius(a.getDimensionPixelSize(R.styleable.FlatEditText_cornerRadius, FlatUI.DEFAULT_RADIUS));
            attributes.setBorderWidth(a.getDimensionPixelSize(R.styleable.FlatEditText_borderWidth, FlatUI.DEFAULT_BORDER_WIDTH));

            // getting view specific attributes
            style = a.getInt(R.styleable.FlatEditText_fieldStyle, 0);

            a.recycle();
        }

        GradientDrawable backgroundDrawable = new GradientDrawable();
        backgroundDrawable.setCornerRadius(attributes.getRadius());

        if (style == 0) {             // flat
            if(!hasOwnTextColor) setTextColor(attributes.getColor(3));
            backgroundDrawable.setColor(attributes.getColor(2));
            backgroundDrawable.setStroke(0, attributes.getColor(2));

        } else if (style == 1) {      // box
            if(!hasOwnTextColor) setTextColor(attributes.getColor(2));
            backgroundDrawable.setColor(Color.WHITE);
            backgroundDrawable.setStroke(attributes.getBorderWidth(), attributes.getColor(2));

        } else if (style == 2) {      // transparent
            if(!hasOwnTextColor) setTextColor(attributes.getColor(1));
            backgroundDrawable.setColor(Color.TRANSPARENT);
            backgroundDrawable.setStroke(attributes.getBorderWidth(), Color.TRANSPARENT);
        } else if (style == 3) {      // transparentBox
            if(!hasOwnTextColor) setTextColor(attributes.getColor(1));
            backgroundDrawable.setColor(Color.TRANSPARENT);
            backgroundDrawable.setStroke(attributes.getBorderWidth(), attributes.getColor(2));
        }

        setBackgroundDrawable(backgroundDrawable);

        if(!hasOwnHintColor) setHintTextColor(attributes.getColor(3));

        if (attributes.getTextAppearance() == 1) setTextColor(attributes.getColor(0));
        else if (attributes.getTextAppearance() == 2) setTextColor(attributes.getColor(3));

        Typeface typeface = FlatUI.getFont(getContext(), attributes.getFontId(), attributes.getFontWeight());
        if (typeface != null) setTypeface(typeface);
    }

    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public void onThemeChange() {
        init(null);
    }
}
