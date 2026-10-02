package android.support.design.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TextInputEditText extends AppCompatEditText {
    public TextInputEditText(Context context) {
        super(context);
    }

    public TextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection ic = super.onCreateInputConnection(outAttrs);
        if (ic != null && outAttrs.hintText == null) {
            for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
                if (parent instanceof TextInputLayout) {
                    outAttrs.hintText = ((TextInputLayout) parent).getHint();
                    break;
                }
            }
        }
        return ic;
    }
}
